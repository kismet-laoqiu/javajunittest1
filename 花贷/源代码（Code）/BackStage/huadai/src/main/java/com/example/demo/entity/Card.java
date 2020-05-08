package com.example.demo.entity;

public class Card {
    private String identification;
    private String card;
    private String accountId;
    private String cardpassword;
    private Double currentBalance;

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

    public String getCardpassword() {
        return cardpassword;
    }

    public void setCardpassword(String cardpassword) {
        this.cardpassword = cardpassword;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }
}
