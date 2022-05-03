package com.crm.platform.controller.pub;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.param.UnderlingParam;
import com.crm.platform.entity.pub.ManagerEntity;
import com.crm.platform.service.pub.ManagerService;
import com.crm.util.QRUtil;
import com.crm.util.SessionUtil;
import com.github.pagehelper.PageInfo;

import springfox.documentation.annotations.ApiIgnore;

/**
 * 下属管理控制器
 * 
 *
 */
@Controller
@RequestMapping("/pub/underling")
@ApiIgnore
public class UnderlingController {

    private final static Logger LOG = LoggerFactory.getLogger(UnderlingController.class);

    @Autowired
    private ManagerService managerService;

    /**
     * 打开下属管理页
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {
        return "pub/underling/list";
    }

    /**
     * 查询所有下属分页列表
     * 
     * @param grid
     * @param name
     * @return
     */
    @RequestMapping(value = "/query")
    @ResponseBody
    public PageInfo<JSONObject> query(DataGrid grid, UnderlingParam param) {
        ManagerEntity user = SessionUtil.getSession();
        return this.managerService.listManagerToUnderling(grid, user.getManagerid(), param);
    }

    /**
     * 推广链接二维码
     * 
     * @param urlLink
     * @param response
     */
    @RequestMapping(value = "qrCode")
    public void qrCode(String urlLink, HttpServletResponse response) {
        QRUtil.getRQ(urlLink, 0, response);
    }

    /**
     * 查询下属详情
     * 
     * @param managerId
     * @return
     */
    @RequestMapping(value = "getUnderlingManager")
    public String getUnderlingManager(int managerId, Model model) {
        try {
            model.addAttribute("dto", managerService.getUnderlingManger(managerId));
        } catch (Exception e) {
            LOG.error("", e);
        }
        return "pub/underling/info";
    }

    /**
     * 客户列表-汇总统计
     * 
     * @param param
     * @return
     */
    @RequestMapping("getUnderlingTotal")
    @ResponseBody
    public AjaxJson getUnderlingTotal(UnderlingParam param) {
        ManagerEntity user = SessionUtil.getSession();
        return managerService.getUnderlingTotal(param, user.getManagerid());
    }

    /**
     * 客户列表-代理统计
     * 
     * @param param
     * @return
     */
    @RequestMapping("getUnderlingAgentTotal")
    @ResponseBody
    public AjaxJson getUnderlingAgentTotal(UnderlingParam param) {
        ManagerEntity user = SessionUtil.getSession();
        return managerService.getUnderlingAgentTotal(param, user.getManagerid());
    }
}
