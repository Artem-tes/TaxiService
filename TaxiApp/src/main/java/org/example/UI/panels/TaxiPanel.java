package org.example.UI.panels;

import lombok.Getter;
import lombok.Setter;
import org.example.UI.frames.mainframes.MainFrame;
import org.example.actions.listeners.CallTaxiAction;
import org.example.actions.servises.FormPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
@Scope("singleton")
@Setter
@Getter
public class TaxiPanel extends JPanel {
    private JTextField dotStreet;
    private JTextField toStreet;
    private JComboBox<String> modes;
    private JLabel priceLabel;
    private JButton callButton;
    private boolean listenersAdded;

    @Autowired
    @Qualifier("waitPanel")
    WaitPanel waitPanel;

    @Autowired
    @Qualifier("mainFrame")
    MainFrame mainFrame;

    @PostConstruct
    private void init(){
        initComponent();
        addToPanel();
        new CallTaxiAction(this,waitPanel,mainFrame).listenAll();
    }

    private void initComponent(){
        dotStreet = new JTextField();
        toStreet = new JTextField();
        modes = new JComboBox<>(new String[]{
                "Эконом",
                "Средний",
                "Бизнесс"
        });
        priceLabel = new JLabel("Цена: "+new FormPriceService().formPrice("Эконом"));
        callButton = new JButton("Заказать");
    }

    private void addToPanel(){
        setLayout(new GridLayout(8,1));
        add(new JLabel("Текущий адрес"));
        add(dotStreet);
        add(new JLabel("Адрес прибытия"));
        add(toStreet);
        add(new JLabel("Уровень комфорта"));
        add(modes);
        add(priceLabel);
        add(callButton);
    }
}
