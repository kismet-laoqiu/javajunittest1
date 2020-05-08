package com.example.demo.entity;

import java.math.BigInteger;

public class Enterprise_record {
    private String businesslicence;
    private double loan_amount;
    private double total_amount;
    private String time;
    private String month;

    @Override
    public String toString() {
        return "Enterprise_record{" +
                "bussinesslicence='" + businesslicence + '\'' +
                ", loan_amount=" + loan_amount +
                ", total_amount=" + total_amount +
                ", time='" + time + '\'' +
                '}';
    }

    public String getBusinesslicence() {
        return businesslicence;
    }

    public void setBusinesslicence(String businesslicence) {
        this.businesslicence = businesslicence;
    }

    public double getLoan_amount() {
        return loan_amount;
    }

    public void setLoan_amount(double loan_amount) {
        this.loan_amount = loan_amount;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
        setMonth(time);
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month.substring(month.length() -2,month.length());
    }
}
