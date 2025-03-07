package org.example.UI.panels;

import lombok.Getter;
import lombok.Setter;
import org.example.App;
import org.example.config.HibernateUtil;
import org.example.repositories.models.RequestModel;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
@Scope("singleton")
@Getter
@Setter

public class WaitPanel extends JPanel {

    private JLabel statusLabel;
    private JLabel infoLabel;
    private JButton workerInfo;
    private JButton cancelCallButton;
    private JButton callTaxistButton;
    private JButton updateButton;

    @PostConstruct
    private void init() {
        initComponent();
        addComponent();
    }

    public void initStatusLabel() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction(); // beginTransaction() перед запросом к базе

        try {
            RequestModel request = session.createQuery("from RequestModel where loginUser = :login", RequestModel.class)
                    .setParameter("login", App.getUser().getLogin())
                    .uniqueResult();
            statusLabel.setText("Статус: "+request.getStatus());
        } catch (NonUniqueResultException e) {
            JOptionPane.showMessageDialog(null, "Вы отправили более 2 вызовов", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }finally {
            transaction.commit();
            session.close();
        }
    }

    private void initComponent() {
        statusLabel = new JLabel();
        infoLabel = new JLabel();
        workerInfo = new JButton("Информация о Таксисте");
        cancelCallButton = new JButton("Отменить заказ");
        callTaxistButton = new JButton("Написать");
        updateButton = new JButton("Обновить");
    }

    private void addComponent() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(statusLabel);
        add(infoLabel);
        add(callTaxistButton);
        add(workerInfo);
        add(cancelCallButton);
        add(updateButton);
    }

    public void initInfoLabelGroup(){
        String login = App.getUser().getLogin();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        RequestModel request = (RequestModel) session.createQuery("from RequestModel where loginUser = :login")
                .setParameter("login",login)
                .uniqueResult();
        infoLabel.setText("Откуда: "+request.getDotStreet()+
                "\nКуда: "+request.getToStreet()+
                "\nЦена: "+request.getPrice()+
                "\nВаш логин: "+request.getLoginUser());
    }

}
