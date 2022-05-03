package com.crm.platform.entity.pub;

import java.io.Serializable;

/**
 * 出入金统计传输对象
 * 
 *
 */
public class StatisticsGoldDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -199223639027504856L;

    private double profitTotal;
    private int countTotal;

    public double getProfitTotal() {
        return profitTotal;
    }

    public void setProfitTotal(double profitTotal) {
        this.profitTotal = profitTotal;
    }

    public int getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(int countTotal) {
        this.countTotal = countTotal;
    }

}
