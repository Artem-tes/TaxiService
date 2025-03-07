package org.example;

import lombok.Getter;
import lombok.Setter;
import org.example.UI.frames.regframes.RegistrationFrame;
import org.example.actions.listeners.StartAction;
import org.example.config.MainConfigure;
import org.example.users.User;
import org.example.users.WorkerUser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    @Getter
    @Setter
    private static User user;



    public static void main( String[] args ){
        ApplicationContext context = new AnnotationConfigApplicationContext(MainConfigure.class);
        RegistrationFrame frame = context.getBean("regFrame", RegistrationFrame.class);
        StartAction action = context.getBean("startAction", StartAction.class);
        frame.setVisible(true);
        action.actions();
    }
}
