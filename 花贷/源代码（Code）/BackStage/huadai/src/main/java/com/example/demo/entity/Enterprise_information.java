package com.example.demo.entity;

public class Enterprise_information {
    private String businesslicence;
    private Integer servicenum;
    private double loan_amount;
    private double total_amount;
    private Integer loan_number;

    @Override
    public String toString() {
        return "Enterprise_information{" +
                "businesslicence='" + businesslicence + '\'' +
                ", servicenum=" + servicenum +
                ", loan_amount=" + loan_amount +
                ", total_amount=" + total_amount +
                ", loan_number=" + loan_number +
                '}';
    }

    public String getBusinesslicence() {
        return businesslicence;
    }

    public void setBusinesslicence(String businesslicence) {
        this.businesslicence = businesslicence;
    }

    public Integer getServicenum() {
        return servicenum;
    }

    public void setServicenum(Integer servicenum) {
        this.servicenum = servicenum;
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

    public Integer getLoan_number() {
        return loan_number;
    }

    public void setLoan_number(Integer loan_number) {
        this.loan_number = loan_number;
    }
}
