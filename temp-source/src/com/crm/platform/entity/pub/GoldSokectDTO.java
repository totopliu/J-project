package com.crm.platform.entity.pub;

import java.io.Serializable;

/**
 * 出入金接口数据传输对象
 * 
 *
 */
public class GoldSokectDTO implements Serializable {

    private static final long serialVersionUID = 4872684949196731201L;

    private Integer login; // MT4ID
    private String ip; // MT4地址：端口
    private Integer lgmanage; // MT4账号
    private String lgpword; // MT4密码
    private Integer function; // 功能号
    private Integer money; // 金额
    private String comment; // 注释

    public Integer getLogin() {
        return login;
    }

    public void setLogin(Integer login) {
        this.login = login;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getLgmanage() {
        return lgmanage;
    }

    public void setLgmanage(Integer lgmanage) {
        this.lgmanage = lgmanage;
    }

    public String getLgpword() {
        return lgpword;
    }

    public void setLgpword(String lgpword) {
        this.lgpword = lgpword;
    }

    public Integer getFunction() {
        return function;
    }

    public void setFunction(Integer function) {
        this.function = function;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
