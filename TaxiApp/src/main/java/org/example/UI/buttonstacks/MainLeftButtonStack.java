package org.example.UI.buttonstacks;

import lombok.Getter;
import lombok.Setter;
import org.example.UI.frames.mainframes.MainFrame;
import org.example.UI.frames.regframes.RegistrationFrame;
import org.example.actions.listeners.ButtonListeners;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component("stack")
@Scope
@Getter
@Setter
public class MainLeftButtonStack extends JPanel{

    @Autowired
    @Qualifier("mainFrame")
    MainFrame mainFrame;

    @Autowired
    @Qualifier("regFrame")
    RegistrationFrame registrationFrame;


    private JButton toMainButton;
    private JButton personalAccount;
    private JButton exitButton;

    @PostConstruct
    public void init(){
        setLayout(new GridLayout(3,1));
        add(toMainButton = new JButton("На главную"));
        add(personalAccount = new JButton("Личный аккаунт"));
        add(exitButton = new JButton("Выйти из системы"));
        new ButtonListeners(mainFrame,registrationFrame,this);
    }
}
