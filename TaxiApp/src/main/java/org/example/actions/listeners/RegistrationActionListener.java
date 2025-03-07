package org.example.actions.listeners;

import lombok.NoArgsConstructor;
import org.example.UI.frames.regframes.NewUserFrame;
import org.example.UI.frames.regframes.NewWorkerFrame;
import org.example.config.HibernateUtil;
import org.example.repositories.models.UserLoginModel;
import org.example.repositories.models.WorkerLoginModel;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
@Scope
@NoArgsConstructor
public class RegistrationActionListener {

    JFrame todayFrame;

    NewUserFrame userFrame;

    NewWorkerFrame workerFrame;

    public RegistrationActionListener(NewUserFrame frame){
        todayFrame = frame;
        userFrame = frame;
        listen();
    }

    public RegistrationActionListener(NewWorkerFrame frame){
        todayFrame = frame;
        workerFrame = frame;
        listenWorker();
    }

    private void listen(){
        if(todayFrame == userFrame){
            listenUser();
        }if(todayFrame == workerFrame){
            listenWorker();
        }
    }

    private void listenUser(){
        userFrame.getRegButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = userFrame.getLogin().getText();
                String password = userFrame.getPassword().getText();
                if(login.equals("") || login.equals("")){
                    JOptionPane.showConfirmDialog(null,"Заполните все поля!","Предупреждение",-1);
                }else {
                    UserLoginModel user = new UserLoginModel(login, password);
                    Session session = HibernateUtil.getSessionFactory().openSession();
                    session.beginTransaction();
                    session.persist(user);
                    session.getTransaction().commit();
                    session.close();
                    userFrame.setVisible(false);
                    userFrame.getLogin().setText("");
                    userFrame.getPassword().setText("");
                    user = null;
                    JOptionPane.showConfirmDialog(null,
                            "Успешно сохранено!" +
                                    "\nтеперь по логину "+login+" вы можете зайти в личный кабинет!","Оповещение",-1);
                }
            }
        });
    }

    private void listenWorker(){
        workerFrame.getRegThisButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = workerFrame.getLogin().getText();
                String password = workerFrame.getPassword().getText();
                String autoName = workerFrame.getAutoName().getText();
                String age = workerFrame.getAge().getText();
                String mode = (String) workerFrame.getWorkMode().getSelectedItem();
                if (login.equals("") || password.equals("") || autoName.equals("") || age.equals("") || mode.equals("")) {
                    JOptionPane.showConfirmDialog(null, "Заполните все поля анкеты", "Предупреждение", -1);
                } else {
                    Session session = HibernateUtil.getSessionFactory().openSession();
                    session.beginTransaction();
                    UserLoginModel user = (UserLoginModel) session.createQuery("from UserLoginModel where login = :login").setParameter("login",login).uniqueResult();

                    if(user!=null){
                        JOptionPane.showConfirmDialog(null,"Логин занят пользователем!","Предупреждение",-1);

                    }
                    WorkerLoginModel worker = (WorkerLoginModel) session.createQuery("from WorkerLoginModel where login = :login").setParameter("login",login).uniqueResult();
                    if(worker != null){
                        JOptionPane.showConfirmDialog(null,"Логин уже занят работником!","Предупреждение",-1);
                    }
                    WorkerLoginModel workerObject = new WorkerLoginModel(login, password, autoName, age, mode);
                    session.persist(workerObject);
                    session.getTransaction().commit();
                    session.close();
                    JOptionPane.showConfirmDialog(null, "Теперь вы сможете зайти по логину " + login + "\n" +
                            "В свой личный кабинет и брать заказы!", "Успешно", -1);
                }
            }
        });
    }
}
