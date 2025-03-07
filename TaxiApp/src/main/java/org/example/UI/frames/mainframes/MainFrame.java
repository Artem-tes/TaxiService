package org.example.UI.frames.mainframes;

import lombok.Getter;
import lombok.Setter;
import org.example.App;
import org.example.UI.buttonstacks.MainLeftButtonStack;
import org.example.UI.panels.TaxiPanel;
import org.example.UI.tablemodels.MainTablePanel;
import org.example.actions.listeners.CallTaxiAction;
import org.example.config.HibernateUtil;
import org.example.repositories.models.RequestModel;
import org.example.users.User;
import org.example.users.WorkerUser;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
@Scope("singleton")
public class MainFrame extends JFrame {

    @Getter
    @Setter
    private User user;

    @Autowired
    @Qualifier("mainTablePanel")
    private MainTablePanel tablePanel;
    
    @Autowired
    @Qualifier("stack")
    private MainLeftButtonStack buttonStack;

    @Autowired
    @Qualifier("taxiPanel")
    private TaxiPanel taxiPanel;

    @PostConstruct
    private void init(){
        initComponent();
        windowCloseListener();
    }

    private void initComponent(){
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(false);
        setBounds(500,300,500,200);
        setTitle("Главное меню");
    }

    public void addComponent(){
        setLayout(new BorderLayout());
        add(BorderLayout.NORTH,new JLabel("Главный разработчик - @psvms"));
        add(BorderLayout.WEST,buttonStack);
        if(user instanceof WorkerUser){
            add(BorderLayout.CENTER,tablePanel);
        }else{
            add(BorderLayout.CENTER,taxiPanel);
        }
    }

    private void windowCloseListener(){
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(user instanceof WorkerUser){

                }else {
                    Session session = HibernateUtil.getSessionFactory().openSession();
                    Transaction transaction = session.getTransaction();
                    transaction.begin();
                    List<RequestModel> requests = session.createQuery("from RequestModel where loginUser = :login")
                            .setParameter("login", App.getUser().getLogin())
                            .getResultList();
                    for (RequestModel request : requests) {
                        session.delete(request);
                    }
                        }
                MainFrame.this.setVisible(false);
                System.exit(0);
            }
        });
    }

}
