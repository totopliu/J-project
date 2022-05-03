package com.crm.platform.entity.param;

import java.io.Serializable;

public class TradeQuery implements Serializable{

    private static final long serialVersionUID = 7204630313308141238L;
    private Integer login;
    private String account;
    private Integer getLimit;
    private String loginFlt;
    private String positon;
    private String profitStart;
    private String profitEnd;
    private String priceGroup;
    private String action;
    private String group;
    private String createTimeStart;
    private String createTimeEnd;
    private String logins;
    private Integer treeManagerId;

    public Integer getLogin() {
        return login;
    }

    public void setLogin(Integer login) {
        this.login = login;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getGetLimit() {
        return getLimit;
    }

    public void setGetLimit(Integer getLimit) {
        this.getLimit = getLimit;
    }

    public String getLoginFlt() {
        return loginFlt;
    }

    public void setLoginFlt(String loginFlt) {
        this.loginFlt = loginFlt;
    }

    public String getPositon() {
        return positon;
    }

    public void setPositon(String positon) {
        this.positon = positon;
    }

    public String getProfitStart() {
        return profitStart;
    }

    public void setProfitStart(String profitStart) {
        this.profitStart = profitStart;
    }

    public String getProfitEnd() {
        return profitEnd;
    }

    public void setProfitEnd(String profitEnd) {
        this.profitEnd = profitEnd;
    }

    public String getPriceGroup() {
        return priceGroup;
    }

    public void setPriceGroup(String priceGroup) {
        this.priceGroup = priceGroup;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public String getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public String getLogins() {
        return logins;
    }

    public void setLogins(String logins) {
        this.logins = logins;
    }

    public Integer getTreeManagerId() {
        return treeManagerId;
    }

    public void setTreeManagerId(Integer treeManagerId) {
        this.treeManagerId = treeManagerId;
    }

}
