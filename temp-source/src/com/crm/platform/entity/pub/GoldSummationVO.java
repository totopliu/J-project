package com.crm.platform.entity.pub;

/**
 * 出入金汇总显类
 * 
 * 
 *
 */
public class GoldSummationVO {

    private Integer countTotal;
    private Double dollarSum;
    private Double moneySum;

    public Integer getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(Integer countTotal) {
        this.countTotal = countTotal;
    }

    public Double getDollarSum() {
        return dollarSum;
    }

    public void setDollarSum(Double dollarSum) {
        this.dollarSum = dollarSum;
    }

    public Double getMoneySum() {
        return moneySum;
    }

    public void setMoneySum(Double moneySum) {
        this.moneySum = moneySum;
    }

}
