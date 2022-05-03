package com.crm.platform.controller.pub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.pub.ManagerEntity;
import com.crm.platform.service.pub.ManagerService;
import com.crm.platform.entity.AjaxJson;
import com.github.pagehelper.PageInfo;

import springfox.documentation.annotations.ApiIgnore;

/**
 * 审核账户控制器
 * 
 *
 */
@Controller
@RequestMapping("/pub/regReview")
@ApiIgnore
public class RegReviewController {

    private final static Logger LOG = LoggerFactory.getLogger(RegReviewController.class);

    @Autowired
    private ManagerService managerService;

    /**
     * 打开账户审核页
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {
        return "pub/reg_review/list";
    }

    /**
     * 查询未审核账户分页列表
     * 
     * @param grid
     * @param name
     * @return
     */
    @RequestMapping(value = "/query")
    @ResponseBody
    public PageInfo<JSONObject> query(DataGrid grid, String name, String account) {
        return this.managerService.listManagerToReview(grid, name, account);
    }

    /**
     * 打开账户审核页
     * 
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/review")
    public String review(Long id, Model model) {
        if (id != null) {
            model.addAttribute("dto", this.managerService.getByIdToReview(id));
        }
        return "pub/reg_review/review";
    }

    /**
     * 保存账户审核结果
     * 
     * @param dto
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public AjaxJson save(@ModelAttribute(value = "dto") ManagerEntity dto, String group) {
        try {
            return this.managerService.updateManagerReview(dto, group);
        } catch (Exception e) {
            LOG.error("", e);
        }
        return null;
    }
}
