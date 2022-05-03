package com.crm.platform.entity.pub;

import java.io.Serializable;

/**
 * 申付参数传输对象
 * 
 *
 */
public class PayShenfuDTO implements Serializable {

    private static final long serialVersionUID = 8597815147749431346L;

    private String mercId;// 商户ID
    private String txnAmt;// 交易金额
    private String orderNo;// 订单号
    private String frontUrl;// 前端跳转url
    private String notifyUrl;// 通知url

    public String getMercId() {
        return mercId;
    }

    public void setMercId(String mercId) {
        this.mercId = mercId;
    }

    public String getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getFrontUrl() {
        return frontUrl;
    }

    public void setFrontUrl(String frontUrl) {
        this.frontUrl = frontUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

}
