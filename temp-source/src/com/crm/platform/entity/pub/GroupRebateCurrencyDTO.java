package com.crm.platform.entity.pub;

import java.io.Serializable;

public class GroupRebateCurrencyDTO implements Serializable {

    private static final long serialVersionUID = -7767821714767211206L;
    private Integer currencyId;
    private Double fixed;
    private Double point;

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

    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

}
