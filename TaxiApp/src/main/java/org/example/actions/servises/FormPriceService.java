package org.example.actions.servises;


import org.example.UI.panels.TaxiPanel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class FormPriceService{


    public float formPrice(String mode){
        float price = 0;
        if(mode.equals("Эконом")){
            price = ecoPrice();
        }if(mode.equals("Средний")){
            price = mediumPrice();
        }if(mode.equals("Бизнесс")){
            price = businessPrice();
        }
        return price;
    }

    private float ecoPrice(){
        float startPrice = 280;
        startPrice = startPrice*(float) getKF();
        return startPrice;
    }

    private float mediumPrice(){
        float startPrice = 350;
        startPrice = startPrice*(float) getKF();
        return startPrice;
    }
    private float businessPrice(){
        float startPrice = 400;
        startPrice = startPrice*(float) getKF();
        return startPrice;
    }

    private int getDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String formattedDate = formatter.format(date);
        return Integer.parseInt(formattedDate.split(":")[0]);
    }

    private double getKF() {
        double k = 1;
        int date = getDate();

        if (date <= 2) {
           return k += 0.45;
        }
        if (date <= 4) {
            return k += 0.5;
        }
        if (date <= 6) {
            return  k += 0.3;
        }
        if (date <= 8) {
            return k += 0.25;
        }
        if (date <= 10) {
            return k += 0.25;
        }
        if (date <= 12) {
            return k += 0.22;
        }
        if (date <= 14) {
            return k += 0.2;
        }
        if (date <= 16) {
            return k += 0.23;
        }
        if (date <= 18) {
            return k += 0.32;
        }
        if (date <= 20) {
            return k += 0.4;
        }
        if (date <= 22) {
            return k += 0.43;
        }
        if (date <= 24) {
            return k += 0.41;
        }

        return k;
    }

}

