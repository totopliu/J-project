package com.crm.platform.entity.pub;

/**
 * 汇率模型
 *
 */
public class ExchangeRateDTO{

    /**
     * 入金汇率
     */
    private Double rate;

    /**
     * 出金汇率
     */
    private Double outRate;

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getOutRate() {
        return outRate;
    }

    public void setOutRate(Double outRate) {
        this.outRate = outRate;
    }

}
