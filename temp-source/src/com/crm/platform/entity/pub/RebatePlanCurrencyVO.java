package com.crm.platform.entity.pub;

import java.io.Serializable;

public class RebatePlanCurrencyVO implements Serializable {

    private static final long serialVersionUID = 6128877571588751062L;

    private int id;
    private int levelId;
    private int currencyId;
    private String currencyName;
    private Double fixedRebate;
    private Double pointRebate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
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
