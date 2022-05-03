package com.crm.platform.entity.pub;

import java.io.Serializable;

/**
 * 银盛支付参数传输对象
 * 
 *
 */
public class PayYsDTO implements Serializable {

    private static final long serialVersionUID = -3519543151249690735L;
    private String partner_id;
    private String timestamp;
    private String charset;
    private String notify_url;
    private String return_url;
    private String out_trade_no;
    private String subject;
    private String total_amount;
    private String bank_type;
    private String support_card_type;
    private String sub_merchant;
    private String extra_common_param;

    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getBank_type() {
        return bank_type;
    }

    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }

    public String getSupport_card_type() {
        return support_card_type;
    }

    public void setSupport_card_type(String support_card_type) {
        this.support_card_type = support_card_type;
    }

    public String getSub_merchant() {
        return sub_merchant;
    }

    public void setSub_merchant(String sub_merchant) {
        this.sub_merchant = sub_merchant;
    }

    public String getExtra_common_param() {
        return extra_common_param;
    }

    public void setExtra_common_param(String extra_common_param) {
        this.extra_common_param = extra_common_param;
    }
}
