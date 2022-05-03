package com.crm.platform.entity.pub;

import java.io.Serializable;

/**
 * 银盛支付回调参数实体类
 * 
 *
 */
public class PayResultYs implements Serializable {

    private static final long serialVersionUID = -4292712737498648650L;

    private String sign_type; // 签名类型 RSA
    private String sign; // 签名 RSA签名字符串，再用Base64编码
    private String notify_type; // 通知类型 directpay.status.sync
    private String notify_time; // 请求的时间 发送请求的时间，格式"yyyy-MM-dd HH:mm:ss"
    private String out_trade_no; // 商户请求的订单号
    private Double total_amount; // 订单金额 该笔订单的资金总额单位为RMB-Yuan.取值范围为[0.01,
    // 100000000.00],精确到小数点后两位
    private String trade_no; // 银盛流水号
    private String trade_status; // 交易目前所处的状态。成功状态的值： TRADE_SUCCESS|TRADE_CLOSED
    private String account_date; // 入账的时间，格式"yyyy-MM-dd"

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getNotify_type() {
        return notify_type;
    }

    public void setNotify_type(String notify_type) {
        this.notify_type = notify_type;
    }

    public String getNotify_time() {
        return notify_time;
    }

    public void setNotify_time(String notify_time) {
        this.notify_time = notify_time;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getTrade_status() {
        return trade_status;
    }

    public void setTrade_status(String trade_status) {
        this.trade_status = trade_status;
    }

    public String getAccount_date() {
        return account_date;
    }

    public void setAccount_date(String account_date) {
        this.account_date = account_date;
    }

}
