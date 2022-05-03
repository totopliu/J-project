package com.crm.platform.entity.pub;

import java.io.Serializable;

public class RebatePlanSettingBO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4657912921230315411L;

    private int managerId;
    private int currencyId;
    private Double rebateFixed;
    private Double rebatePoint;

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
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
