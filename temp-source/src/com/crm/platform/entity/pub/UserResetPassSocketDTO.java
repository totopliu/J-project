package com.crm.platform.entity.pub;

public class UserResetPassSocketDTO {

    private Integer login; // MT4ID
    private String password; // 用户密码
    private String ip; // MT4地址：端口
    private Integer lgmanage; // MT4账号
    private String lgpword; // MT4密码
    private Integer function; // 功能号
    private Integer type; // 0-主密码 1-投资密码

    public Integer getLogin() {
        return login;
    }

    public void setLogin(Integer login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
