package com.example.demo.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Repay_person {
    private String username;
    private String identification;
    private String card;
    private double money_amount;
    private Integer servicenum;
    private Date time;
    private String times;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public double getMoney_amount() {
        return money_amount;
    }

    public void setMoney_amount(double money_amount) {
        this.money_amount = money_amount;
    }

    public Integer getServicenum() {
        return servicenum;
    }

    public void setServicenum(Integer servicenum) {
        this.servicenum = servicenum;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
        setTimes(time);
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(Date time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        this.times = sdf.format(time);
    }
}
