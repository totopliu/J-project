package com.crm.platform.entity.pub;

import java.io.Serializable;

public class RebatePlanCurrencyBO implements Serializable {

    private static final long serialVersionUID = 684454674466122595L;
    private int levelId;
    private int currencyId;
    private Double fixedRebate;
    private Double pointRebate;

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public Double getFixedRebate() {
        return fixedRebate;
    }

    public void setFixedRebate(Double fixedRebate) {
        this.fixedRebate = fixedRebate;
    }

    public Double getPointRebate() {
        return pointRebate;
    }

    public void setPointRebate(Double pointRebate) {
        this.pointRebate = pointRebate;
    }

}
