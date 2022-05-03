package com.crm.platform.controller.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.service.pub.PricesService;
import com.github.pagehelper.PageInfo;

/**
 * 货币分组设置
 * 
 * 
 */
@Controller
@RequestMapping("/pub/prices")
public class PricesController {

    @Autowired
    private PricesService pricesService;

    /**
     * 打开货币分组设置管理页
     * 
     * @return
     */
    @RequestMapping(value = "list")
    public String list() {
        return "pub/prices/list";
    }

    /**
     * 查询货币分组管理分页列表
     * 
     * @param grid
     * @return
     */
    @RequestMapping(value = "query")
    @ResponseBody
    public PageInfo<JSONObject> query(DataGrid grid) {
        return pricesService.listPrices(grid);
    }

    /**
     * 打开货币分组设置页
     * 
     * @param symbols
     * @param model
     * @return
     */
    @RequestMapping(value = "edit")
    public String edit(String symbols, Model model) {
        model.addAttribute("symbols", symbols);
        return "pub/prices/edit";
    }

    /**
     * 保存货币分组设置
     * 
     * @param symbols
     * @param currency_id
     * @return
     */
    @RequestMapping(value = "save")
    @ResponseBody
    public AjaxJson save(String symbols, Integer currencyId) {
        return pricesService.save(symbols, currencyId);
    }
}
