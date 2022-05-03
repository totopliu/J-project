package com.crm.platform.entity.pub;

import java.io.Serializable;

public class ManagerRebateConfigBO implements Serializable {

    private static final long serialVersionUID = -2028696451520640492L;

    private Integer managerid;
    private Integer belongid;
    private Integer autoRebate;
    private Integer login;
    private Integer rebateLogin;
    private Integer level;
    private Integer type;
    private String groupName;

    public Integer getManagerid() {
        return managerid;
    }

    public void setManagerid(Integer managerid) {
        this.managerid = managerid;
    }

    public Integer getBelongid() {
        return belongid;
    }

    public void setBelongid(Integer belongid) {
        this.belongid = belongid;
    }

    public Integer getAutoRebate() {
        return autoRebate;
    }

    public void setAutoRebate(Integer autoRebate) {
        this.autoRebate = autoRebate;
    }

    public Integer getLogin() {
        return login;
    }

    public void setLogin(Integer login) {
        this.login = login;
    }

    public Integer getRebateLogin() {
        return rebateLogin;
    }

    public void setRebateLogin(Integer rebateLogin) {
        this.rebateLogin = rebateLogin;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

}
