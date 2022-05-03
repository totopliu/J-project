package com.crm.platform.entity.pub;

import java.io.Serializable;

public class RebatePlanCurrencyDTO implements Serializable {

    private static final long serialVersionUID = -5231987318426914523L;

    private String currencyId;
    private String fixedRebate;
    private String pointRebate;

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getFixedRebate() {
        return fixedRebate;
    }

    public void setFixedRebate(String fixedRebate) {
        this.fixedRebate = fixedRebate;
    }

    public String getPointRebate() {
        return pointRebate;
    }

    public void setPointRebate(String pointRebate) {
        this.pointRebate = pointRebate;
    }

}
