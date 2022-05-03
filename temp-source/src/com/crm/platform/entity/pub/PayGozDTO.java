package com.crm.platform.entity.pub;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.crm.util.MD5Utils;

/**
 * 高志支付传输对象
 * 
 *
 */
public class PayGozDTO implements Serializable {

    private static final long serialVersionUID = 2594576002574535317L;

    private String ver; // 版本号
    private String merid; // 商户号 分配的商户号
    private String orderid; // 商户订单号，商户系统生成的订单编号不能重复
    private String amount; // 订单金额，正值，保留两位小数
    private String orderdate; // 订单日期，格式：YYYYMMDD
    private String curtype; // 币种 注意：这里需要根据实际内容替换
    private String paytype; // 收单类型 注意：这里需要根据实际内容替换
    private String lang; // 语言 注意：这里需要根据实际内容替换
    private String returnurl; // 支付结果成功返回的商户URL
    private String errorurl; // 支付结果失败返回的商户URL
    private String remark1; // 备注 注意：这里需要根据实际内容替换
    private String enctype; // 提交签名类型 注意：这里需要根据实际内容替换
    private String notifytype; // 返回签名类型 注意：这里需要根据实际内容替换
    private String urltype; // 返回方式 注意：这里需要根据实际内容替换
    private String s2surl; // 后台返回地址
    private String goodsname; // 商品名称 注意：这里需要根据实际内容替换
    private String channelid; // 直连银行编号，注意：这里需要根据实际内容替换
    private String md5;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getCurtype() {
        return curtype;
    }

    public void setCurtype(String curtype) {
        this.curtype = curtype;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getReturnurl() {
        return returnurl;
    }

    public void setReturnurl(String returnurl) {
        this.returnurl = returnurl;
    }

    public String getErrorurl() {
        return errorurl;
    }

    public void setErrorurl(String errorurl) {
        this.errorurl = errorurl;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getEnctype() {
        return enctype;
    }

    public void setEnctype(String enctype) {
        this.enctype = enctype;
    }

    public String getNotifytype() {
        return notifytype;
    }

    public void setNotifytype(String notifytype) {
        this.notifytype = notifytype;
    }

    public String getUrltype() {
        return urltype;
    }

    public void setUrltype(String urltype) {
        this.urltype = urltype;
    }

    public String getS2surl() {
        return s2surl;
    }

    public void setS2surl(String s2surl) {
        this.s2surl = s2surl;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String toParam() {
        DecimalFormat df = new DecimalFormat("0.00");
        Double amountTemp = new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        StringBuffer sb = new StringBuffer();
        sb.append("ver=").append(ver).append("&merid=").append(merid).append("&orderid=").append(orderid)
                .append("&amount=").append(df.format(amountTemp)).append("&orderdate=").append(orderdate)
                .append("&curtype=").append(curtype).append("&paytype=").append(paytype).append("&lang=").append(lang)
                .append("&returnurl=").append(returnurl).append("&errorurl=").append(errorurl).append("&remark1=")
                .append(remark1).append("&enctype=").append(enctype).append("&notifytype=").append(notifytype)
                .append("&urltype=").append(urltype).append("&s2surl=").append(s2surl).append("&goodsname=")
                .append(goodsname).append("&channelid=").append(channelid).append(md5);
        return MD5Utils.md5(sb.toString());
    }

}
