package org.example.UI.frames.regframes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Getter
@Setter
@NoArgsConstructor
@Component("regFrame")
@Scope("singleton")
public class RegistrationFrame extends JFrame {

    private JTextField logField;
    private JTextField passField;
    private JButton regButton;
    private JButton registrButton;

    @PostConstruct
    public void init(){
        initView();
        addView();
    }

    private void initView(){
        setTitle("Регистрация");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(500,300,300,200);
        setLayout(new GridLayout(5,1));
        logField = new JTextField();
        passField = new JTextField();
        regButton = new JButton("Войти");
        registrButton = new JButton("Зарегестрироватся");
    }

    private void addView(){
        add(new JLabel("Введите логин"));
        add(logField);
        add(new JLabel("Введите пароль"));
        add(passField);
        add(regButton);
        add(registrButton);
    }


    

}
