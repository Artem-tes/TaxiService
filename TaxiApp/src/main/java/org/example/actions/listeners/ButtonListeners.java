package org.example.actions.listeners;

import org.example.UI.buttonstacks.MainLeftButtonStack;
import org.example.UI.frames.mainframes.MainFrame;
import org.example.UI.frames.regframes.RegistrationFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
@Scope
public class ButtonListeners {

    MainFrame frame;

    RegistrationFrame registrationFrame;

    MainLeftButtonStack stack;

    public ButtonListeners(MainFrame frame, RegistrationFrame registrationFrame, MainLeftButtonStack stack) {
        this.frame = frame;
        this.registrationFrame = registrationFrame;
        this.stack = stack;
    }

    private void listenExit(){
        stack.getExitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                registrationFrame.setVisible(true);
            }
        });
    }
}
