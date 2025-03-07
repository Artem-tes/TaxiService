package org.example.actions.listeners;

import org.example.App;
import org.example.UI.frames.mainframes.MainFrame;
import org.example.UI.panels.TaskPanel;
import org.example.UI.tablemodels.MainTablePanel;
import org.example.config.HibernateUtil;
import org.example.repositories.models.RequestModel;
import org.example.users.WorkerMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Stream;


public class TableTaskListener {

    MainFrame frame;

    TaskPanel taskPanel;

    MainTablePanel panel;

    public TableTaskListener(MainTablePanel panel, MainFrame mainFrame,TaskPanel taskPanel) {
        this.panel = panel;
        this.taskPanel = taskPanel;
        this.frame = mainFrame;
    }

    public void listenAll(){
        actionTable();
        actionSort();
        actionUpdate();
    }

    private void actionUpdate(){
        panel.getUpdateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int columnCount = panel.getModel().getRowCount();
                while (columnCount > 0){
                    panel.getModel().removeRow(0);
                    columnCount--;
                }
                Session session = HibernateUtil.getSessionFactory().openSession();
                Transaction transaction = session.getTransaction();
                transaction.begin();
                List<RequestModel> requests = session.createQuery("from RequestModel where status = :status")
                        .setParameter("status","create")
                        .getResultList();
                for (RequestModel request : requests) {
                    System.out.println(request.getLoginWorker());
                    if(request.getLoginWorker() == null) {
                        panel.getModel().addRow(new Object[]{
                                request.getID(),
                                request.getLoginUser(),
                                request.getDotStreet(),
                                request.getToStreet(),
                                request.getPrice(),
                                request.getMode()
                        });
                    }
                }
                transaction.commit();
                session.close();

            }
        });
    }

    private void actionSort(){
        panel.getSortModes().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedItem = panel.getSortModes().getSelectedIndex();
                int columnCount = panel.getModel().getRowCount();
                while (columnCount > 0){
                    panel.getModel().removeRow(0);
                    columnCount--;
                }
                Session session = HibernateUtil.getSessionFactory().openSession();
                Transaction transaction = session.getTransaction();
                transaction.begin();
                List<RequestModel> requests = session.createQuery("from RequestModel").getResultList();
                transaction.commit();
                session.close();
                Stream<RequestModel> stream = requests.stream();
                List<RequestModel> requestModels = sortedRequest(selectedItem,stream);
                addToTable(requestModels);
            }
        });
    }

    private void addToTable(List<RequestModel> requestModels){
        for (RequestModel requestModel : requestModels) {
            panel.getModel().addRow(new Object[]{
                    requestModel.getID(),
                    requestModel.getLoginUser(),
                    requestModel.getDotStreet(),
                    requestModel.getToStreet(),
                    requestModel.getPrice(),
                    requestModel.getMode()
            });
        }
    }

    private List<RequestModel> sortedRequest(int selectedItem,Stream<RequestModel> requestsStream){
        List<RequestModel> requests = List.of();
        if(selectedItem == 2) {
                requests = requestsStream.sorted().toList();
            }
        return requests;
    }

    private void actionTable(){
        panel.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    int index = panel.getTable().getSelectedRow();
                    int ID = (int)panel.getTable().getValueAt(index,0);
                    int result = JOptionPane.showConfirmDialog(null,"Вы точно хотите взять заказ\n" +
                            "ID: "+ID,"Предупреждение",JOptionPane.YES_NO_OPTION);
                    if(result == 0){
                        Session session = HibernateUtil.getSessionFactory().openSession();
                        Transaction transaction = session.getTransaction();
                        transaction.begin();
                        RequestModel request = (RequestModel) session.createQuery("from RequestModel where ID = :id")
                                .setParameter("id",ID)
                                .uniqueResult();
                        request.setLoginWorker(App.getUser().getLogin());
                        transaction.commit();
                        session.close();
                        JOptionPane.showConfirmDialog(null,"Следуйте к месту назначения","Оповещение",-1);
                        taskPanel.initID(ID);
                        frame.getContentPane().remove(panel);
                        frame.repaint();
                        frame.revalidate();
                        frame.getContentPane().add(BorderLayout.CENTER,taskPanel);
                        frame.repaint();
                        frame.revalidate();
                    }
                }
            }
        });
    }
}
