package com.crm.platform.entity.pub;

public class RebateTransferReviewDTO {

    private int id;
    private String dollar;
    private String name;
    private int login;
    private int rebateLogin;
    private double balance;
    private double rebateBalance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDollar() {
        return dollar;
    }

    public void setDollar(String dollar) {
        this.dollar = dollar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public int getRebateLogin() {
        return rebateLogin;
    }

    public void setRebateLogin(int rebateLogin) {
        this.rebateLogin = rebateLogin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getRebateBalance() {
        return rebateBalance;
    }

    public void setRebateBalance(double rebateBalance) {
        this.rebateBalance = rebateBalance;
    }

}
