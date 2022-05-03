package com.crm.platform.controller.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.pub.RebatePlanDTO;
import com.crm.platform.service.pub.RebateService;
import com.github.pagehelper.PageInfo;

/**
 * 返佣方案控制器
 * 
 *
 */
@Controller
@RequestMapping("/pub/rebatePlan")
public class RebatePlanController {

    @Autowired
    private RebateService rebateService;

    /**
     * 打开返佣方案页
     * 
     * @return
     */
    @RequestMapping(value = "list")
    public String list() {
        return "pub/rebate_plan/list";
    }

    /**
     * 查询返佣方案列表
     * 
     * @param grid
     * @param managerId
     * @return
     */
    @RequestMapping(value = "query")
    @ResponseBody
    public PageInfo<JSONObject> query(DataGrid grid) {
        return rebateService.listRebatePlan(grid);
    }

    /**
     * 打开方案编辑页
     * 
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "edit")
    public String edit(Integer id, Model model) {
        if (id != null) {
            model.addAttribute("dto", rebateService.getRebatePlan(id));
        }
        return "pub/rebate_plan/edit";
    }

    /**
     * 保存返佣方案
     * 
     * @param dto
     * @return
     */
    @RequestMapping(value = "save")
    @ResponseBody
    public AjaxJson save(RebatePlanDTO dto) {
        return rebateService.saveRebatePlan(dto);
    }

    /**
     * 批量删除返佣方案
     * 
     * @param ids
     * @return
     */
    @RequestMapping(value = "remove")
    @ResponseBody
    public AjaxJson remove(int[] ids) {
        return rebateService.removeRebatePlans(ids);
    }

    /**
     * 获取返佣方案货币组返佣设置
     * 
     * @param planId
     * @return
     */
    @RequestMapping(value = "getRebatePlanCurrency")
    @ResponseBody
    public AjaxJson getRebatePlanCurrency(int planId) {
        return rebateService.getRebatePlanCurrency(planId);
    }

    /**
     * 保存返佣方案货币组返佣设置
     * 
     * @param planId
     * @param levelJson
     * @return
     */
    @RequestMapping(value = "saveRebatePlanCurrency")
    @ResponseBody
    public AjaxJson saveRebatePlanCurrency(int planId, String levelJson) {
        return rebateService.saveRebatePlanCurrency(planId, levelJson);
    }
}
