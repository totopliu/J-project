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
import com.crm.platform.constant.Constant;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.pub.ManagerEntity;
import com.crm.platform.entity.pub.OutGoldDTO;
import com.crm.platform.service.pub.OutGoldService;
import com.crm.util.MsgUtil;
import com.crm.util.SessionUtil;
import com.github.pagehelper.PageInfo;

/**
 * 出金管理
 * 
 * 
 */
@Controller
@RequestMapping("/pub/outGold")
public class OutGoldController {

    private final static Logger LOG = LoggerFactory.getLogger(OutGoldController.class);

    @Autowired
    private OutGoldService outGoldService;

    /**
     * 打开出金管理页
     * 
     * @return
     */
    @RequestMapping(value = "list")
    public String list() {
        return "pub/out_gold/list";
    }

    /**
     * 查询出金分页列表
     * 
     * @param grid
     * @param login
     * @param ticket
     * @param outDate
     * @return
     */
    @RequestMapping(value = "query")
    @ResponseBody
    public PageInfo<JSONObject> query(DataGrid grid, Integer login, Integer ticket, String outDate) {
        if (login == null) {
            ManagerEntity user = SessionUtil.getSession();
            login = user.getLogin();
        }
        return outGoldService.listOutGold(grid, login, ticket, outDate);
    }

    /**
     * 查询出金合计
     * 
     * @param login
     * @param ticket
     * @param outDate
     * @return
     */
    @RequestMapping(value = "findOutGoldTotal")
    @ResponseBody
    public AjaxJson findOutGoldTotal(Integer login, Integer ticket, String outDate) {
        if (login == null) {
            ManagerEntity user = SessionUtil.getSession();
            login = user.getLogin();
        }
        return outGoldService.countOutGoldTotal(login, ticket, outDate);
    }

    /**
     * 打开出金申请页
     * 
     * @return
     */
    @RequestMapping(value = "outPage")
    public String outPage(Model model) {
        // 查询出金汇率
        model.addAttribute("outRate", outGoldService.getOutRate().getData());
        return "pub/out_gold/out";
    }

    /**
     * 提交出金申请
     * 
     * @param dto
     * @return
     */
    @RequestMapping(value = "save")
    @ResponseBody
    @SystemLog(description = "出金申请", module = "出金模块", methods = "save")
    public AjaxJson save(OutGoldDTO dto) {
        try {
            ManagerEntity user = SessionUtil.getSession();
            dto.setManagerid(user.getManagerid());
            return outGoldService.save(dto);
        } catch (Exception e) {
            LOG.error("==== Withdraw apply exception", e);
        }
        AjaxJson json = new AjaxJson();
        json.setMsg(MsgUtil.getSessionLgMsg(Constant.SYSTEM_EXCEPTION));
        return json;
    }

}
