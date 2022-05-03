package com.crm.platform.entity.pub;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.crm.util.MD5Utils;

public class PayHxt {

    private String merchantId;
    private String cardType;
    private String bankCode;
    private String orderAmt;
    private String merOrderId;
    private String returnUrl;
    private String notifyUrl;
    private String remark;
    private String timeStamp;
    private String sign;

    public void depositSign() {
        setMerchantId("999100072990007");
        setCardType("01");
        setReturnUrl("http://mt5.mmigasia.com/crmf");
        setNotifyUrl("http://mt5.mmigasia.com/crmf/pub/inGold/resultHxt");
        setTimeStamp(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        String plaintext = new StringBuffer("bankCode=").append(bankCode).append("&cardType=").append(cardType)
                .append("&merOrderId=").append(merOrderId).append("&merchantId=").append(merchantId)
                .append("&notifyUrl=").append(notifyUrl).append("&orderAmt=").append(orderAmt).append("&returnUrl=")
                .append(returnUrl).append("&timeStamp=").append(timeStamp).append("&key=").append("ee2940c027433244")
                .toString();
        setSign(MD5Utils.md5(plaintext));
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(String orderAmt) {
        this.orderAmt = orderAmt;
    }

    public String getMerOrderId() {
        return merOrderId;
    }

    public void setMerOrderId(String merOrderId) {
        this.merOrderId = merOrderId;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
