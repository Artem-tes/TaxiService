package org.example.UI.frames.regframes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component("newWorkerFrame")
@Scope("singleton")
@Getter
@Setter
@NoArgsConstructor
public class NewWorkerFrame extends NewUserFrame {
    private JTextField autoName;
    private JTextField age;
    private JComboBox<String> workMode;
    private JButton regThisButton;

    @PostConstruct
    private void init(){
        setTitle("Регистрация");
        remove(regButton);
        setBounds(500,300,300,360);
        setLayout(new GridLayout(11,1));
        initView();
        addView();
    }

    private void initView(){
        autoName = new JTextField();
        age = new JTextField();
        workMode = new JComboBox<>(new String[]{
                "Эконом",
                "Средний",
                "Бизнесс"
        });
        regThisButton = new JButton("Зарегестрироватся");
    }

    private void addView(){
        add(new JLabel("Название авто на котором будет работа"));
        add(autoName);
        add(new JLabel("Возраст"));
        add(age);
        add(new JLabel("Желаемый комфорт"));
        add(workMode);
        add(regThisButton);
    }
}
