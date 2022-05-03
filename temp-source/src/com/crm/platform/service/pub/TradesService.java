package com.crm.platform.service.pub;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.param.TradeQuery;
import com.github.pagehelper.PageInfo;

public interface TradesService {

    PageInfo<JSONObject> listHoldTrades(DataGrid grid, TradeQuery param);

    PageInfo<JSONObject> listFinishTrades(DataGrid grid, TradeQuery param);

    AjaxJson countFinishProfitTotal(TradeQuery param);

    void refreshTradesToRebate();

    PageInfo<JSONObject> queryStatistics(DataGrid grid, Integer login, String group, String startTime, String endTime);

    AjaxJson summary(Integer login, String group, String startTime, String endTime);

}
