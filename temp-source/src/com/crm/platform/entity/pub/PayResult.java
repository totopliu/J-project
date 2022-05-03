package com.crm.platform.entity.pub;

import java.io.Serializable;

public class PayResult implements Serializable {

    private static final long serialVersionUID = 18909503222993696L;

    private String ver; // 接口版本
    private String merid; // 商户号
    private String orderid; // 商户订单号
    private Double amount; // 订单金额
    private String orderdate; // 订单日期
    private String transtat; // 订单状态
    private String notifytype; // 返回签名类型 1=MD5WITHRSA; 2=MD5
    private String curtype; // 收单币种
    private String serialno; // 高志系统流水号
    private String systime; // 高志交易时间YYYYMMDDHHMMSS
    private String bankorderid; // 上送银行的流水号
    private String message; // 交易信息
    private String remark1; // 备注
    private String sign; // 数字签名

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getMerid() {
        return merid;
    }

    public void setMerid(String merid) {
        this.merid = merid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getTranstat() {
        return transtat;
    }

    public void setTranstat(String transtat) {
        this.transtat = transtat;
    }

    public String getNotifytype() {
        return notifytype;
    }

    public void setNotifytype(String notifytype) {
        this.notifytype = notifytype;
    }

    public String getCurtype() {
        return curtype;
    }

    public void setCurtype(String curtype) {
        this.curtype = curtype;
    }

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public String getSystime() {
        return systime;
    }

    public void setSystime(String systime) {
        this.systime = systime;
    }

    public String getBankorderid() {
        return bankorderid;
    }

    public void setBankorderid(String bankorderid) {
        this.bankorderid = bankorderid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
