package com.crm.platform.mapper.pub;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.param.TradeQuery;
import com.crm.platform.entity.pub.TardeTotalVO;
import com.crm.platform.entity.pub.TradesToRebateEntity;

public interface TradesMapper {

    List<JSONObject> queryHoldTradesList(TradeQuery param);

    List<JSONObject> queryFinishTradesList(TradeQuery param);

    Date findTempTime();

    void updTempTime(Date tempTime);

    List<TradesToRebateEntity> findTradesByCloseTime(Date tempTime);

    TardeTotalVO countFinishTotal(TradeQuery param);

    Double getTradeAllProfitSum(TradeQuery param);

    Integer getInCount(TradeQuery param);

    Double getInGoldSum(TradeQuery param);

    Double getOutGoldSum(TradeQuery param);

    List<JSONObject> queryStatistics(@Param("login") Integer login, @Param("group") String group,
            @Param("startTime") String startTime, @Param("endTime") String endTime);

    JSONObject summary(@Param("login") Integer login, @Param("group") String group,
            @Param("startTime") String startTime, @Param("endTime") String endTime);

    Double getBalanceSum(TradeQuery param);

    Double getEquitySum(TradeQuery param);

}
