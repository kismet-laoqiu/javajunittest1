package com.example.demo.entity;

public class Enterprise {
    private String enterprisename;
    private String businesslicence;
    private String publicaccounts;
    private String depositbank;
    private String accountname;
    private String accountowner;
    private String owneridentification;
    private String ownernumber;
    private String certification;
    private String password;
    private String avatar;

    @Override
    public String toString() {
        return "Enterprise{" +
                "enterprisename='" + enterprisename + '\'' +
                ", businesslicence='" + businesslicence + '\'' +
                ", publicaccounts='" + publicaccounts + '\'' +
                ", depositbank='" + depositbank + '\'' +
                ", accountname='" + accountname + '\'' +
                ", accountowner='" + accountowner + '\'' +
                ", owneridentification='" + owneridentification + '\'' +
                ", ownernumber='" + ownernumber + '\'' +
                ", certification='" + certification + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEnterprisename() {
        return enterprisename;
    }

    public void setEnterprisename(String enterprisename) {
        this.enterprisename = enterprisename;
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

    public String getDepositbank() {
        return depositbank;
    }

    public void setDepositbank(String depositbank) {
        this.depositbank = depositbank;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getAccountowner() {
        return accountowner;
    }

    public void setAccountowner(String accountowner) {
        this.accountowner = accountowner;
    }

    public String getOwneridentification() {
        return owneridentification;
    }

    public void setOwneridentification(String owneridentification) {
        this.owneridentification = owneridentification;
    }

    public String getOwnernumber() {
        return ownernumber;
    }

    public void setOwnernumber(String ownernumber) {
        this.ownernumber = ownernumber;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
