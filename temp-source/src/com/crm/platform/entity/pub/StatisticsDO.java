package com.crm.platform.entity.pub;

import java.math.BigDecimal;

public class StatisticsDO {

    private Integer id;
    private Integer managerid;// 用户ID
    private Integer login;// MT账号
    private Integer dealProfitAmount;// 交易盈利笔数
    private Integer dealLossAmount;// 交易亏损笔数
    private Integer dealAmount;// 总交易笔数
    private Integer dealProfitVolume;// 交易盈利手数
    private Integer dealLossVolume;// 交易亏损手数
    private Integer dealVolume;// 总交易手数
    private BigDecimal inSum;// 入金额
    private Integer inCount;// 入金笔数
    private BigDecimal outSum;// 出金额
    private Integer outCount;// 出金笔数
    private BigDecimal inOutMargin;// 出入金差额
    private BigDecimal profitSum;// 盈利
    private BigDecimal LossSum;// 亏损
    private BigDecimal profit;// 总盈利
    private BigDecimal rebateSum;// 返佣
    private BigDecimal equity;// 净值
    private Integer profitPoint;// 盈利点数
    private Integer lossPoint;// 亏损点数
    private Integer point;// 总点数
    private BigDecimal roi;// 投资回报率
    private BigDecimal winRate;// 胜出交易率
    private BigDecimal maxLossRatio;// 最大亏损比率

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getManagerid() {
        return managerid;
    }

    public void setManagerid(Integer managerid) {
        this.managerid = managerid;
    }

    public Integer getLogin() {
        return login;
    }

    public void setLogin(Integer login) {
        this.login = login;
    }

    public Integer getDealProfitAmount() {
        return dealProfitAmount;
    }

    public void setDealProfitAmount(Integer dealProfitAmount) {
        this.dealProfitAmount = dealProfitAmount;
    }

    public Integer getDealLossAmount() {
        return dealLossAmount;
    }

    public void setDealLossAmount(Integer dealLossAmount) {
        this.dealLossAmount = dealLossAmount;
    }

    public Integer getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(Integer dealAmount) {
        this.dealAmount = dealAmount;
    }

    public Integer getDealProfitVolume() {
        return dealProfitVolume;
    }

    public void setDealProfitVolume(Integer dealProfitVolume) {
        this.dealProfitVolume = dealProfitVolume;
    }

    public Integer getDealLossVolume() {
        return dealLossVolume;
    }

    public void setDealLossVolume(Integer dealLossVolume) {
        this.dealLossVolume = dealLossVolume;
    }

    public Integer getDealVolume() {
        return dealVolume;
    }

    public void setDealVolume(Integer dealVolume) {
        this.dealVolume = dealVolume;
    }

    public BigDecimal getInSum() {
        return inSum;
    }

    public void setInSum(BigDecimal inSum) {
        this.inSum = inSum;
    }

    public Integer getInCount() {
        return inCount;
    }

    public void setInCount(Integer inCount) {
        this.inCount = inCount;
    }

    public BigDecimal getOutSum() {
        return outSum;
    }

    public void setOutSum(BigDecimal outSum) {
        this.outSum = outSum;
    }

    public Integer getOutCount() {
        return outCount;
    }

    public void setOutCount(Integer outCount) {
        this.outCount = outCount;
    }

    public BigDecimal getInOutMargin() {
        return inOutMargin;
    }

    public void setInOutMargin(BigDecimal inOutMargin) {
        this.inOutMargin = inOutMargin;
    }

    public BigDecimal getProfitSum() {
        return profitSum;
    }

    public void setProfitSum(BigDecimal profitSum) {
        this.profitSum = profitSum;
    }

    public BigDecimal getLossSum() {
        return LossSum;
    }

    public void setLossSum(BigDecimal lossSum) {
        LossSum = lossSum;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getRebateSum() {
        return rebateSum;
    }

    public void setRebateSum(BigDecimal rebateSum) {
        this.rebateSum = rebateSum;
    }

    public BigDecimal getEquity() {
        return equity;
    }

    public void setEquity(BigDecimal equity) {
        this.equity = equity;
    }

    public Integer getProfitPoint() {
        return profitPoint;
    }

    public void setProfitPoint(Integer profitPoint) {
        this.profitPoint = profitPoint;
    }

    public Integer getLossPoint() {
        return lossPoint;
    }

    public void setLossPoint(Integer lossPoint) {
        this.lossPoint = lossPoint;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public BigDecimal getRoi() {
        return roi;
    }

    public void setRoi(BigDecimal roi) {
        this.roi = roi;
    }

    public BigDecimal getWinRate() {
        return winRate;
    }

    public void setWinRate(BigDecimal winRate) {
        this.winRate = winRate;
    }

    public BigDecimal getMaxLossRatio() {
        return maxLossRatio;
    }

    public void setMaxLossRatio(BigDecimal maxLossRatio) {
        this.maxLossRatio = maxLossRatio;
    }

}
