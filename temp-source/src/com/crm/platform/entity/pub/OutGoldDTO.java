package com.crm.platform.entity.pub;

import java.io.Serializable;
import java.util.Date;

/**
 * 出金审核显示对象
 * 
 *
 */
public class OutGoldDTO implements Serializable {

    private static final long serialVersionUID = 4513565025772179016L;

    private Integer id;
    private Integer managerid;
    private Double money;
    private int state;
    private String name;
    private String login;
    private String bank;
    private String bankCard;
    private String bankCardUrl;
    private Double balance;
    private String reason;
    private Date createTime;
    private Double dollar;
    private Double outRate;
    private int loginType;
    private int rebateLogin;
    private Double rebateBalance;
    private int transactionType;
    private String transactionMoney;
    private String idcard;
    private String phone;
    private String createTimeFmt;
    private String reviewTimeFmt;

    public Double getDollar() {
        return dollar;
    }

    public void setDollar(Double dollar) {
        this.dollar = dollar;
    }

    public Double getOutRate() {
        return outRate;
    }

    public void setOutRate(Double outRate) {
        this.outRate = outRate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getManagerid() {
        return managerid;
    }

    public void setManagerid(Integer managerid) {
        this.managerid = managerid;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBankCardUrl() {
        return bankCardUrl;
    }

    public void setBankCardUrl(String bankCardUrl) {
        this.bankCardUrl = bankCardUrl;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public int getRebateLogin() {
        return rebateLogin;
    }

    public void setRebateLogin(int rebateLogin) {
        this.rebateLogin = rebateLogin;
    }

    public Double getRebateBalance() {
        return rebateBalance;
    }

    public void setRebateBalance(Double rebateBalance) {
        this.rebateBalance = rebateBalance;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionMoney() {
        return transactionMoney;
    }

    public void setTransactionMoney(String transactionMoney) {
        this.transactionMoney = transactionMoney;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReviewTimeFmt() {
        return reviewTimeFmt;
    }

    public void setReviewTimeFmt(String reviewTimeFmt) {
        this.reviewTimeFmt = reviewTimeFmt;
    }

    public String getCreateTimeFmt() {
        return createTimeFmt;
    }

    public void setCreateTimeFmt(String createTimeFmt) {
        this.createTimeFmt = createTimeFmt;
    }

}
