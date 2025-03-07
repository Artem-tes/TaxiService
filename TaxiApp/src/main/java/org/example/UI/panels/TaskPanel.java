package org.example.UI.panels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

@Component
@Scope
@Getter
@Setter
public class TaskPanel extends JPanel{
    private JLabel dotLabel;
    private JLabel toLabel;
    private JButton closeButton;
    private int IDTask;

    public void initID(int IDTask){
        this.IDTask = IDTask;
    }


    @PostConstruct
    private void init(){
        initView();
        addToPanel();
    }

    private void initView(){
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        String dotStreet = (String) session.createQuery("select dotStreet from RequestModel where ID = :IDTask")
                .setParameter("IDTask",IDTask)
                .uniqueResult();
        String toStreet = (String) session.createQuery("select toStreet from RequestModel where ID = :IDTask")
                .setParameter("IDTask",IDTask)
                .uniqueResult();
        transaction.commit();
        session.close();
        dotLabel = new JLabel("Начало: "+dotStreet);
        toLabel = new JLabel("Конец: "+toStreet);
        closeButton = new JButton("Закончить поездку");

    }


    private void addToPanel(){
        add(dotLabel);
        add(toLabel);
        add(closeButton);
    }
}
