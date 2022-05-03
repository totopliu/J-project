package com.crm.platform.entity.pub;

import java.io.Serializable;
import java.util.Date;

public class TradesToRebateEntity implements Serializable {

    private static final long serialVersionUID = -9177774811697681954L;

    private Integer deal;
    private Integer login;
    private Double volume;
    private Date time;
    private String symbol;

    public Integer getDeal() {
        return deal;
    }

    public void setDeal(Integer deal) {
        this.deal = deal;
    }

    public Integer getLogin() {
        return login;
    }

    public void setLogin(Integer login) {
        this.login = login;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

}
