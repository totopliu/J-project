package com.crm.platform.controller.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.param.TradeQuery;
import com.crm.platform.service.pub.TradesService;
import com.github.pagehelper.PageInfo;

/**
 * 持有订单
 * 
 * 
 */
@Controller
@RequestMapping("/pub/holdTrades")
public class HoldTradesController {

    @Autowired
    private TradesService tradesService;

    /**
     * 持有订单页
     * 
     * @return
     */
    @RequestMapping(value = "list")
    public String list() {
        return "pub/hold_trades/list";
    }

    /**
     * 持有订单分页列表
     * 
     * @param grid
     * @param param
     * @return
     */
    @RequestMapping(value = "/query")
    @ResponseBody
    public PageInfo<JSONObject> query(DataGrid grid, TradeQuery param) {
        return tradesService.listHoldTrades(grid, param);
    }

}
