package com.crm.platform.entity.pub;

import java.io.Serializable;

/**
 * 订单统计传输对象
 * 
 *
 */
public class StatisticsTradesDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1153507322508739892L;

    private double profitTotal;
    private double volumeTotal;

    public double getProfitTotal() {
        return profitTotal;
    }

    public void setProfitTotal(double profitTotal) {
        this.profitTotal = profitTotal;
    }

    public double getVolumeTotal() {
        return volumeTotal;
    }

    public void setVolumeTotal(double volumeTotal) {
        this.volumeTotal = volumeTotal;
    }

}
