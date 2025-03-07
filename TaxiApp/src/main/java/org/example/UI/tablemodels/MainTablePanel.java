package org.example.UI.tablemodels;

import lombok.Getter;
import lombok.Setter;
import org.example.App;
import org.example.UI.frames.mainframes.MainFrame;
import org.example.UI.panels.TaskPanel;
import org.example.actions.listeners.TableTaskListener;
import org.example.config.HibernateUtil;
import org.example.repositories.models.RequestModel;
import org.example.users.WorkerMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

@Component
@Scope
@Getter
@Setter
public class MainTablePanel extends JPanel {

    @Autowired
    @Qualifier("mainFrame")
    MainFrame mainFrame;

    @Autowired
    @Qualifier("taskPanel")
    TaskPanel taskPanel;

    private DefaultTableModel model = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private JTable table;
    private JComboBox<String> sortModes;
    private JButton updateButton;

    private static DefaultTableModel initMode(DefaultTableModel model){
        model.addColumn("ID");
        model.addColumn("Логин");
        model.addColumn("Откуда");
        model.addColumn("Куда");
        model.addColumn("Цена");
        model.addColumn("Комфорт");

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        System.out.println(WorkerMode.getMode());
        List<RequestModel> requests = session.createQuery("from RequestModel where status = :status")
                .setParameter("status","create")
                .getResultList();
        for (RequestModel request : requests) {
            if (request.getLoginWorker() == null)
                model.addRow(new Object[]{
                        request.getID(),
                        request.getLoginUser(),
                        request.getDotStreet(),
                        request.getToStreet(),
                        request.getPrice(),
                        request.getMode()
                });
        }
        transaction.commit();
        session.close();
        return model;
    }

    @PostConstruct
    private void init(){
        setLayout(new BorderLayout());
        add(BorderLayout.NORTH,sortModes = new JComboBox<>(new String[]{
             "Стандарт",
             "Режим",
             "Цена"
        }));
        add(BorderLayout.CENTER,new JScrollPane(table = new JTable(initMode(model))));
        add(BorderLayout.SOUTH,updateButton = new JButton("Обновить список"));
        new TableTaskListener(this,mainFrame,taskPanel).listenAll();
    }

}
