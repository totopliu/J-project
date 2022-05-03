package com.crm.platform.entity.pub;

import java.io.Serializable;

/**
 * 会员关系和状态业务对象
 * 
 *
 */
public class ManagerRelAndStateBO implements Serializable {

    private static final long serialVersionUID = 4787781508946841542L;
    private int managerid; // 会员ID
    private int belongid; // 父级ID
    private int autoRebate; // 返佣设置 0-手动 1-自动
    private int login; // MT4账号
    private int rebateLogin; // MT4返佣账号

    public int getManagerid() {
        return managerid;
    }

    public void setManagerid(int managerid) {
        this.managerid = managerid;
    }

    public int getBelongid() {
        return belongid;
    }

    public void setBelongid(int belongid) {
        this.belongid = belongid;
    }

    public int getAutoRebate() {
        return autoRebate;
    }

    public void setAutoRebate(int autoRebate) {
        this.autoRebate = autoRebate;
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

}
