package com.crm.platform.entity.pub;

import java.io.Serializable;

/**
 * 高志支付回调数据传输对象
 * 
 *
 */
public class PayResultShenfu implements Serializable {

    private static final long serialVersionUID = -1505011220088997418L;

    private String respCode; // 交易结果码 00表示成功
    private String respMsg; // 交易结果消息
    private String logNo; // 交易流水号
    private String orderNo; // 订单号
    private String settleAmt; // 清算金额
    private String txnTime; // 交易时间
    private String txnAmt; // 交易金额
    private String traceNo; // 银联交易追踪号
    private String traceTime; // 追踪时间
    private String signature; // 签名

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getLogNo() {
        return logNo;
    }

    public void setLogNo(String logNo) {
        this.logNo = logNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getSettleAmt() {
        return settleAmt;
    }

    public void setSettleAmt(String settleAmt) {
        this.settleAmt = settleAmt;
    }

    public String getTxnTime() {
        return txnTime;
    }

    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }

    public String getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public String getTraceTime() {
        return traceTime;
    }

    public void setTraceTime(String traceTime) {
        this.traceTime = traceTime;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

}
