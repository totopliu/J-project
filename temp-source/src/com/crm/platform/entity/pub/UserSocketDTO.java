package com.crm.platform.entity.pub;

import java.io.Serializable;

/**
 * @ClassName: ParamReg
 * @Description: 调用MT4注册接口实体类
 * @date 2017年4月26日 上午10:20:50
 * @version 1.0
 */
public class UserSocketDTO implements Serializable {

    private static final long serialVersionUID = -3919631661428570643L;
    private String username; // 用户名
    private String password; // 用户密码
    private Integer login; // MT4ID
    private String useremail; // 邮箱
    private Integer leve; // 杠杆
    private String ip; // MT4地址：端口
    private Integer lgmanage; // MT4账号
    private String lgpword; // MT4密码
    private Integer function; // 功能号
    private String usergroup; // 用户组
    private String passgc; // MT4观察密码

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

    public Integer getLogin() {
        return login;
    }

    public void setLogin(Integer login) {
        this.login = login;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public Integer getLeve() {
        return leve;
    }

    public void setLeve(Integer leve) {
        this.leve = leve;
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

    public String getUsergroup() {
        return usergroup;
    }

    public void setUsergroup(String usergroup) {
        this.usergroup = usergroup;
    }

    public String getPassgc() {
        return passgc;
    }

    public void setPassgc(String passgc) {
        this.passgc = passgc;
    }
}
