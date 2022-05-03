package com.crm.platform.controller.pub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.crm.annotation.SystemLog;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.pub.ManagerEntity;
import com.crm.platform.service.pub.RebateService;
import com.crm.util.SessionUtil;
import com.github.pagehelper.PageInfo;

/**
 * 设置下属返佣控制器
 * 
 *
 */
@Controller
@RequestMapping("/pub/rebate")
public class RebateController {

    private final static Logger LOG = LoggerFactory.getLogger(RebateController.class);

    @Autowired
    private RebateService rebateService;

    /**
     * 打开下属返佣页
     * 
     * @return
     */
    @RequestMapping(value = "list")
    public String list() {
        return "pub/rebate/list";
    }

    /**
     * 查询下属返佣设置分页列表
     * 
     * @param grid
     * @param managerId
     * @return
     */
    @RequestMapping(value = "query")
    @ResponseBody
    public PageInfo<JSONObject> query(DataGrid grid, Integer managerId) {
        if (managerId == null) {
            ManagerEntity user = SessionUtil.getSession();
            managerId = Integer.parseInt(String.valueOf(user.getManagerid()));
        }
        return rebateService.listUnderlingRebate(grid, managerId);
    }

    /**
     * 打开设置定值返佣页
     * 
     * @param currencyId
     * @param managerId
     * @param model
     * @return
     */
    @RequestMapping(value = "fixed")
    public String fixed(Integer currencyId, Integer managerId, Model model) {
        model.addAttribute("currencyId", currencyId);
        model.addAttribute("managerId", managerId);
        return "pub/rebate/fixed";
    }

    /**
     * 打开设置点值返佣页
     * 
     * @param currencyId
     * @param managerId
     * @param model
     * @return
     */
    @RequestMapping(value = "point")
    public String point(Integer currencyId, Integer managerId, Model model) {
        model.addAttribute("currencyId", currencyId);
        model.addAttribute("managerId", managerId);
        return "pub/rebate/point";
    }

    /**
     * 保存定值返佣设置
     * 
     * @param currencyId
     * @param managerId
     * @param rebateFixed
     * @return
     */
    @RequestMapping(value = "saveFixed")
    @ResponseBody
    @SystemLog(description = "设置定值返佣", module = "返佣配置模块", methods = "saveFixed")
    public AjaxJson saveFixed(Integer currencyId, Integer managerId, Double rebateFixed) {
        try {
            ManagerEntity user = SessionUtil.getSession();
            return rebateService.saveFixed(currencyId, managerId, rebateFixed, user.getManagerid());
        } catch (Exception e) {
            LOG.error("", e);
        }
        return null;
    }

    /**
     * 保存点值返佣设置
     * 
     * @param currencyId
     * @param managerId
     * @param rebatePoint
     * @return
     */
    @RequestMapping(value = "savePoint")
    @ResponseBody
    @SystemLog(description = "设置点值返佣", module = "返佣配置模块", methods = "savePoint")
    public AjaxJson savePoint(Integer currencyId, Integer managerId, Double rebatePoint) {
        try {
            ManagerEntity user = SessionUtil.getSession();
            return rebateService.savePoint(currencyId, managerId, rebatePoint, user.getManagerid());
        } catch (Exception e) {
            LOG.error("", e);
        }
        return null;
    }

}
