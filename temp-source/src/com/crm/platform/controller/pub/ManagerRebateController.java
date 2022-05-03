package com.crm.platform.controller.pub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * 账户返佣控制器
 * 
 * 
 */
@Controller
@RequestMapping("/pub/managerRebate")
public class ManagerRebateController {

    private final static Logger LOG = LoggerFactory.getLogger(ManagerRebateController.class);

    @Autowired
    private RebateService rebateService;

    /**
     * 打开返佣页
     * 
     * @return
     */
    @RequestMapping(value = "list")
    public String list() {
        return "pub/manager_rebate/list";
    }

    /**
     * 查询返佣分页列表
     * 
     * @param grid
     * @param login
     * @param rebateDate
     * @return
     */
    @RequestMapping(value = "query")
    @ResponseBody
    public PageInfo<JSONObject> query(DataGrid grid, Integer login, String rebateDate, String ticket, String over) {
        if (login == null) {
            ManagerEntity user = SessionUtil.getSession();
            login = user.getLogin();
        }
        return rebateService.listManagerRebate(grid, login, rebateDate, ticket, over);
    }

    /**
     * 确认返佣入金
     * 
     * @param ids
     * @return
     */
    @RequestMapping(value = "saveRebateIn")
    @ResponseBody
    public AjaxJson saveRebateIn(String ids, String login) {
        try {
            return rebateService.saveRebateIn(ids, login);
        } catch (Exception e) {
            LOG.error("", e);
        }
        return null;
    }

    /**
     * 返佣余额入金
     * 
     * @param rebateSum
     * @param login
     * @return
     */
    @RequestMapping(value = "rebateSumToMT4")
    @ResponseBody
    @SystemLog(description = "用户手动返佣入金", module = "返佣入金模块", methods = "rebateSumToMT4")
    public AjaxJson rebateSumToMT4(Integer login) {
        return rebateService.rebateSumToMT4(login);
    }

    /**
     * 查询返佣合计
     * 
     * @param login
     * @param ticket
     * @param rebateDate
     * @param over
     * @return
     */
    @RequestMapping(value = "getRebateTotal")
    @ResponseBody
    public AjaxJson getRebateTotal(Integer login, String ticket, String rebateDate, String over) {
        if (login == null) {
            ManagerEntity user = SessionUtil.getSession();
            login = user.getLogin();
        }
        return rebateService.countRebateTotal(login, ticket, rebateDate, over);
    }
}
