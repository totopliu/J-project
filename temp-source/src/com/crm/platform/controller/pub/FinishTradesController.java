package com.crm.platform.controller.pub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.param.TradeQuery;
import com.crm.platform.entity.pub.ManagerEntity;
import com.crm.platform.service.pub.TradesService;
import com.crm.util.SessionUtil;
import com.github.pagehelper.PageInfo;

/**
 * 完结订单控制器
 * 
 * 
 */
@Controller
@RequestMapping("/pub/finishTrades")
public class FinishTradesController {

    private final static Logger LOG = LoggerFactory.getLogger(FinishTradesController.class);

    @Autowired
    private TradesService tradesService;

    /**
     * 完结订单页
     * 
     * @return
     */
    @RequestMapping(value = "list")
    public String list() {
        return "pub/finish_trades/list";
    }

    /**
     * 完结订单分页列表
     * 
     * @param grid
     * @param param
     * @return
     */
    @RequestMapping(value = "query")
    @ResponseBody
    public PageInfo<JSONObject> query(DataGrid grid, TradeQuery param) {
        try {
            return tradesService.listFinishTrades(grid, param);
        } catch (Exception e) {
            LOG.error("", e);
        }
        return null;
    }

    /**
     * 查询完结订单合计
     * 
     * @param param
     * @return
     */
    @RequestMapping(value = "countFinishProfitTotal")
    @ResponseBody
    public AjaxJson countFinishProfitTotal(TradeQuery param) {
        try {
            return tradesService.countFinishProfitTotal(param);
        } catch (Exception e) {
            LOG.error("", e);
        }
        return null;
    }
}
