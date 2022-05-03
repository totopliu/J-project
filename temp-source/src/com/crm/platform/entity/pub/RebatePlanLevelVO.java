package com.crm.platform.entity.pub;

import java.io.Serializable;
import java.util.List;

public class RebatePlanLevelVO implements Serializable {

    private static final long serialVersionUID = 1562269233150159096L;

    private int id;
    private int planId;
    private int level;
    private List<RebatePlanCurrencyVO> RebatePlanCurrencyVOs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<RebatePlanCurrencyVO> getRebatePlanCurrencyVOs() {
        return RebatePlanCurrencyVOs;
    }

    public void setRebatePlanCurrencyVOs(List<RebatePlanCurrencyVO> rebatePlanCurrencyVOs) {
        RebatePlanCurrencyVOs = rebatePlanCurrencyVOs;
    }

}
