package com.crm.platform.entity.pub;

import java.io.Serializable;

public class RebateBO implements Serializable {

    private static final long serialVersionUID = 782767965533202669L;

    private Integer managerId;
    private Integer currencyId;
    private Double rebateFixed;
    private Double rebatePoint;

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
