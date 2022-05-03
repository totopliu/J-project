package com.crm.platform.entity.pub;

import java.io.Serializable;

public class RebateEntity implements Serializable {

    private static final long serialVersionUID = 7624308593032378003L;

    private Integer id;
    private Integer managerId;
    private Integer currencyId;
    private Double rebateFixed;
    private Double rebatePoint;
    private Double fixed;
    private Double bid;
    private Double relbid;
    private Integer digits;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public Double getFixed() {
        return fixed;
    }

    public void setFixed(Double fixed) {
        this.fixed = fixed;
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

    public Integer getDigits() {
        return digits;
    }

    public void setDigits(Integer digits) {
        this.digits = digits;
    }

    public Double getRebateFixed() {
        return rebateFixed;
    }

    public void setRebateFixed(Double rebateFixed) {
        this.rebateFixed = rebateFixed;
    }

    public Double getRebatePoint() {
        return rebatePoint;
    }

    public void setRebatePoint(Double rebatePoint) {
        this.rebatePoint = rebatePoint;
    }

}
