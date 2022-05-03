package com.crm.platform.controller.pub;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.pub.CurrencyBO;
import com.crm.platform.service.pub.CurrencyService;
import com.github.pagehelper.PageInfo;

/**
 * 货币组控制器
 *
 * 
 */
@Controller
@RequestMapping("/pub/currency")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    /**
     * 打开货币组页面
     *
     * @return
     */
    @RequestMapping(value = "list")
    public String list() {
        return "pub/currency/list";
    }

    /**
     * 货币组分页列表接口
     *
     * @param grid
     * @return
     */
    @RequestMapping(value = "query")
    @ResponseBody
    public PageInfo<JSONObject> query(DataGrid grid) {
        return currencyService.listCurrency(grid);
    }

    /**
     * 打开货币组编辑页
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "edit")
    public String edit(Integer id, Model model) {
        if (id != null) {
            model.addAttribute("dto", currencyService.selectByPrimaryKey(id));
        }
        return "pub/currency/edit";
    }

    /**
     * 保存货币组信息
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "save")
    @ResponseBody
    public AjaxJson save(@ModelAttribute(value = "dto") CurrencyBO dto) {
        return currencyService.save(dto);
    }

    /**
     * 删除货币组
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public AjaxJson del(Integer[] ids) {
        return currencyService.batchDelete(ids);
    }

    /**
     * 获取货币组列表
     *
     * @return
     */
    @RequestMapping(value = "listCurrency")
    @ResponseBody
    public AjaxJson listCurrency() {
        AjaxJson json = new AjaxJson();
        List<CurrencyBO> currencyBOs = currencyService.listCurrency();
        json.setData(currencyBOs);
        json.setSuccess(true);
        return json;
    }
}
