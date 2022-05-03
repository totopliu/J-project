package com.crm.platform.entity.pub;

import java.io.Serializable;

public class RebatePlanLevelBO implements Serializable {

    private static final long serialVersionUID = 7312440185793321068L;

    private int id;
    private int planId;
    private int level;

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

}
