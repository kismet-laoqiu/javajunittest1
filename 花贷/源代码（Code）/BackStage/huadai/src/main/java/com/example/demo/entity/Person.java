package com.example.demo.entity;

public class Person {
    private String username;
    private String password;
    private String identification;
    private String phonenum;
    private String enterprisename;
    private String businesslicence;

    @Override
    public String toString() {
        return "Person{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", identification='" + identification + '\'' +
                ", phonenum='" + phonenum + '\'' +
                ", enterprisename='" + enterprisename + '\'' +
                ", businesslicence='" + businesslicence + '\'' +
                '}';
    }

    public String getBusinesslicence() {
        return businesslicence;
    }

    public void setBusinesslicence(String businesslicence) {
        this.businesslicence = businesslicence;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getEnterprisename() {
        return enterprisename;
    }

    public void setEnterprisename(String enterprise) {
        this.enterprisename = enterprise;
    }

}
