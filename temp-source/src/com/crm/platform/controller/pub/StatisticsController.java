package com.crm.platform.controller.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.service.pub.TradesService;
import com.github.pagehelper.PageInfo;

/**
 * 统计报表
 * 
 * 
 *
 */
@Controller
@RequestMapping("/pub/statistics")
public class StatisticsController {

    @Autowired
    private TradesService tradesService;

    @RequestMapping("list")
    public String list() {
        return "pub/statistics/list";
    }

    @RequestMapping("query")
    @ResponseBody
    public PageInfo<JSONObject> query(DataGrid grid, Integer login, String group, String startTime, String endTime) {
        return tradesService.queryStatistics(grid, login, group, startTime, endTime);
    }
    
    @RequestMapping("summary")
    @ResponseBody
    public AjaxJson summary(Integer login, String group, String startTime, String endTime) {
        return tradesService.summary(login, group, startTime, endTime);
    }

}
