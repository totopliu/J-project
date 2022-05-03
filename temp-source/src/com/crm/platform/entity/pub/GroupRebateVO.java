package com.crm.platform.entity.pub;

import java.io.Serializable;

public class GroupRebateVO implements Serializable {

    private static final long serialVersionUID = 5764528250955432878L;

    private String groupName;
    private Integer level;
    private Integer currencyId;
    private Double fixed;
    private Double point;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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

    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

}
