package com.crm.platform.entity.pub;

import java.io.Serializable;

public class CurrencyPriceBO implements Serializable {

    private static final long serialVersionUID = -6292582377933808453L;

    private String symbol;
    private Integer currency_id;
    private Double fixed;
    private String point_relation;
    private Integer digits;
    private Double bid;
    private Double relbid;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(Integer currency_id) {
        this.currency_id = currency_id;
    }

    public Double getFixed() {
        return fixed;
    }

    public void setFixed(Double fixed) {
        this.fixed = fixed;
    }

    public String getPoint_relation() {
        return point_relation;
    }

    public void setPoint_relation(String point_relation) {
        this.point_relation = point_relation;
    }

    public Integer getDigits() {
        return digits;
    }

    public void setDigits(Integer digits) {
        this.digits = digits;
    }

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    public Double getRelbid() {
        return relbid;
    }

    public void setRelbid(Double relbid) {
        this.relbid = relbid;
    }

}
