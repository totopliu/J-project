package com.crm.platform.entity.pub;

import java.io.Serializable;

/**
 * 邮件服务配置数据对象
 * 
 *
 */
public class EmailSettingDO implements Serializable {

    private static final long serialVersionUID = 5114742844178841697L;

    private String smtpServer;
    private String mailAccount;
    private String mailPassword;

    public String getSmtpServer() {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    public String getMailAccount() {
        return mailAccount;
    }

    public void setMailAccount(String mailAccount) {
        this.mailAccount = mailAccount;
    }

    public String getMailPassword() {
        return mailPassword;
    }

    public void setMailPassword(String mailPassword) {
        this.mailPassword = mailPassword;
    }

}
