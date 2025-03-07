package org.example.UI.frames.regframes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
@Scope
@Setter
@Getter
@NoArgsConstructor
public class NewUserFrame extends JFrame {

    private JTextField login;
    private JTextField password;
    protected JButton regButton;

    @PostConstruct
    private void init(){
        initView();
        addView();
    }

    private void initView(){
        setTitle("Регистрация");
        login = new JTextField();
        password = new JTextField();
        regButton = new JButton("Зарегестрироватся");
    }

    private void addView(){
        setBounds(500,300,300,200);
        setLayout(new GridLayout(5,1));
        add(new JLabel("Введите логин для аккаунта"));
        add(login);
        add(new JLabel("Введите пароль для аккаунта"));
        add(password);
        add(regButton);
    }

}
