package org.example.actions.listeners;

import org.example.App;
import org.example.UI.frames.mainframes.MainFrame;
import org.example.UI.frames.regframes.NewUserFrame;
import org.example.UI.frames.regframes.NewWorkerFrame;
import org.example.UI.frames.regframes.RegistrationFrame;
import org.example.config.HibernateUtil;
import org.example.users.User;
import org.example.users.WorkerMode;
import org.example.users.WorkerUser;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


@Component("startAction")
@Scope("singleton")
public class StartAction {

    @Autowired
    @Qualifier("regFrame")
    RegistrationFrame frame;

    @Autowired
    @Qualifier("newWorkerFrame")
    NewWorkerFrame workerFrame;

    @Autowired
    @Qualifier("newUserFrame")
    NewUserFrame userFrame;

    @Autowired
    @Qualifier("mainFrame")
    MainFrame mainFrame;

    public void actions(){
        listenRegButton();
        actionButtonRegistration();
    }

    private void actionButtonRegistration(){
        JButton regButton = frame.getRegistrButton();
        regButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int whyUser = JOptionPane.showConfirmDialog(null,
                        "Вы хотите работать в нашей компании таксистом?","Сообщение",JOptionPane.YES_NO_OPTION);
                if(whyUser == 0){
                    workerFrame.setVisible(true);
                    new RegistrationActionListener(workerFrame);
                }if(whyUser == 1){
                    userFrame.setVisible(true);
                    new RegistrationActionListener(userFrame);
                }
            }
        });
    }

    private void listenRegButton(){
        JButton regButton = frame.getRegButton();
        regButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = frame.getLogField().getText();
                String pass = frame.getPassField().getText();
                if (login.equals("") || pass.equals("")) {
                    JOptionPane.showConfirmDialog(null,"Одно или два поля пустые","Ошибка",JOptionPane.DEFAULT_OPTION);
                } else {
                    Session session = HibernateUtil.getSessionFactory().openSession();
                    session.beginTransaction();
                    String passDB = (String) session.createQuery(
                            "select password from UserLoginModel where login = :setlogin").setParameter("setlogin", login).uniqueResult();
                    session.getTransaction().commit();
                    session.close();

                    if (passDB == null) {
                        Session sessionWork = HibernateUtil.getSessionFactory().openSession();
                        sessionWork.beginTransaction();
                        String password = (String) sessionWork.createQuery("select password from WorkerLoginModel where login = :loginWork")
                                .setParameter("loginWork", login)
                                .uniqueResult();
                        sessionWork.getTransaction().commit();
                        sessionWork.close();
                        if(password == null){
                            JOptionPane.showConfirmDialog(null, "Неправильно введен логин", "Ошибка", JOptionPane.DEFAULT_OPTION);
                        }else{
                            if(password.equals(pass)){
                                JOptionPane.showConfirmDialog(null,"Успешно!\nХорошего рабочего дня","Оповещение",-1);
                                Session session1 = HibernateUtil.getSessionFactory().openSession();
                                Transaction transaction = session1.getTransaction();
                                transaction.begin();
                                String mode = (String) session1.createQuery("select mode from WorkerLoginModel where login = :login")
                                        .setParameter("login",login)
                                        .uniqueResult();
                                WorkerUser user = new WorkerUser(login,password,mode);
                                transaction.commit();
                                session1.close();
                                App.setUser((user));
                                mainFrame.setUser(App.getUser());
                                frame.setVisible(false);
                                mainFrame.addComponent();
                                mainFrame.setVisible(true);
                            }else {
                                JOptionPane.showConfirmDialog(null,"Неправильно введен пароль","Оповещение",-1);
                            }
                        }

                    } else {
                        if (passDB.equals(pass)) {
                            JOptionPane.showConfirmDialog(null,
                                    "Вы успешно вошли в свой аккаунт", "Успешно", JOptionPane.DEFAULT_OPTION);
                            User user = new User(login,pass);
                            App.setUser(user);
                            mainFrame.setUser(App.getUser());
                            mainFrame.addComponent();
                            mainFrame.setVisible(true);
                            frame.setVisible(false);
                        } else {
                            JOptionPane.showConfirmDialog(null,
                                    "Неправильно введен пароль", "Ошибка", JOptionPane.DEFAULT_OPTION);
                        }
                    }
                }
            }
        });
    }

}
