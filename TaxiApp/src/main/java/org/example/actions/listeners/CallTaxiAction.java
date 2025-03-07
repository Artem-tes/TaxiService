package org.example.actions.listeners;


import org.example.App;
import org.example.UI.frames.mainframes.MainFrame;
import org.example.UI.panels.TaxiPanel;
import org.example.UI.panels.WaitPanel;
import org.example.actions.servises.FormPriceService;
import org.example.config.HibernateUtil;
import org.example.repositories.models.RequestModel;
import org.example.users.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

@Component
@Scope("singleton")
public class CallTaxiAction {

    private MainFrame mainFrame;
    private WaitPanel waitPanel;
    private TaxiPanel panel;


    public CallTaxiAction(TaxiPanel panel, WaitPanel waitPanel,MainFrame mainFrame) {
        this.panel = panel;
        this.waitPanel = waitPanel;
        this.mainFrame = mainFrame;
    }

    public void listenAll(){
        actionSelectMode();
        actionCall();
    }

    private void actionSelectMode(){
        panel.getModes().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mode = (String) panel.getModes().getSelectedItem();
                float price = new FormPriceService().formPrice(mode);
                panel.getPriceLabel().setText("Цена: "+price);
            }
        });
    }

    private void actionCall(){
        final String[] price = new String[1];
        panel.getCallButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dotStreet = panel.getDotStreet().getText();
                String toStreet = panel.getToStreet().getText();
                String selectedMode = (String) panel.getModes().getSelectedItem();
                try {
                    price[0] = panel.getPriceLabel().getText().split(":")[1];
                }catch (ArrayIndexOutOfBoundsException index){

                }
                if(dotStreet.equals("") || toStreet.equals("") || selectedMode.equals("")){
                    JOptionPane.showConfirmDialog(null,"Заполните все поля","Ошибка",-1);
                }else {
                    RequestModel request = new RequestModel(
                            null, App.getUser().getLogin(),dotStreet,toStreet,null,"create", price[0],selectedMode);
                    int result = JOptionPane.showConfirmDialog(null,
                            "Вы точно хотите оставить заявку?\n" +
                                    "Откуда: "+request.getDotStreet()+
                                    "\nКуда: "+request.getToStreet()+
                                    "\nЦена: "+request.getPrice(),"Предупреждение",JOptionPane.YES_NO_OPTION);
                    if(result == 0) {
                        Session session = HibernateUtil.getSessionFactory().openSession();
                        Transaction transaction = session.getTransaction();
                        transaction.begin();
                        List<RequestModel> requestCheck = session.createQuery("from RequestModel where loginUser = :login")
                                        .setParameter("login",App.getUser().getLogin())
                                                .getResultList();
                        if(!requestCheck.isEmpty()){
                            JOptionPane.showConfirmDialog(null,"У вас уже есть активный заказ","Ошибка",-1);
                            waitPanel.initStatusLabel();
                            waitPanel.initInfoLabelGroup();
                            mainFrame.getContentPane().remove(panel);
                            mainFrame.repaint();
                            mainFrame.revalidate();
                            mainFrame.add(BorderLayout.CENTER,waitPanel);
                            mainFrame.repaint();
                            mainFrame.revalidate();
                        }else {
                            session.persist(request);
                            transaction.commit();
                            session.close();
                            JOptionPane.showConfirmDialog(null,"Успешно!\nСкоро ваш заказ возьмут работники","Опопвещение",-1);
                            panel.getDotStreet().setText(" ");
                            panel.getToStreet().setText(" ");
                            panel.getModes().setSelectedIndex(0);
                            panel.getPriceLabel().setText("Цена:");
                            waitPanel.initStatusLabel();
                            waitPanel.initInfoLabelGroup();
                            mainFrame.getContentPane().remove(panel);
                            mainFrame.repaint();
                            mainFrame.revalidate();
                            mainFrame.add(BorderLayout.CENTER,waitPanel);
                            mainFrame.repaint();
                            mainFrame.revalidate();

                        }
                    }else {
                        panel.getDotStreet().setText("");
                        panel.getToStreet().setText("");
                        panel.getModes().setSelectedIndex(0);
                        panel.getPriceLabel().setText("Цена:");
                    }
                }
                panel.getCallButton().removeActionListener(this);
            }
        });
    }
}
