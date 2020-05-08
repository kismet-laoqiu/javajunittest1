package com.example.demo.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Repay_enterprise {
    private String name;
    private String businesslicence;
    private String publicaccounts;
    private double money_amount;
    private Integer servicenum;
    private Date time;
    private String times;
    private Integer loan_num;

    @Override
    public String toString() {
        return "Repay_enterprise{" +
                "name='" + name + '\'' +
                ", businesslicence='" + businesslicence + '\'' +
                ", publicaccounts='" + publicaccounts + '\'' +
                ", money_amount=" + money_amount +
                ", servicenum=" + servicenum +
                ", date=" + time +
                ", loan_num=" + loan_num +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinesslicence() {
        return businesslicence;
    }

    public void setBusinesslicence(String businesslicence) {
        this.businesslicence = businesslicence;
    }

    public String getPublicaccounts() {
        return publicaccounts;
    }

    public void setPublicaccounts(String publicaccounts) {
        this.publicaccounts = publicaccounts;
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

    public Integer getLoan_num() {
        return loan_num;
    }

    public void setLoan_num(Integer loan_num) {
        this.loan_num = loan_num;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(Date time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        this.times = sdf.format(time);
    }
}
