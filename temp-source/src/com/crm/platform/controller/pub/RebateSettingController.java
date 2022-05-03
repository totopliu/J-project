package com.crm.platform.controller.pub;

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
import com.crm.platform.entity.pub.ManagerEntity;
import com.crm.platform.service.pub.ManagerService;
import com.crm.platform.service.pub.RebateService;
import com.crm.util.SessionUtil;
import com.github.pagehelper.PageInfo;

/**
 * 设置自动返佣控制器
 * 
 *
 */
@Controller
@RequestMapping("/pub/rebateSetting")
public class RebateSettingController {

    private final static Logger LOG = LoggerFactory.getLogger(RebateSettingController.class);

    @Autowired
    private RebateService rebateService;

    @Autowired
    private ManagerService managerService;

    /**
     * 打开自动返佣管理页
     * 
     * @return
     */
    @RequestMapping(value = "list")
    public String list() {
        return "pub/rebate/setting_list";
    }

    /**
     * 查询所有下属自动分页列表
     * 
     * @param grid
     * @return
     */
    @RequestMapping(value = "query")
    @ResponseBody
    public PageInfo<JSONObject> query(DataGrid grid) {
        ManagerEntity user = SessionUtil.getSession();
        Integer managerId = user.getManagerid();
        return rebateService.listSetting(grid, managerId);
    }

    /**
     * 打开设置是否返佣编辑页
     * 
     * @param managerid
     * @param model
     * @return
     */
    @RequestMapping(value = "edit")
    public String edit(Integer managerid, Model model) {
        ManagerEntity managerEntity = managerService.selectByPrimaryKey(managerid);
        model.addAttribute("dto", managerEntity);
        return "pub/rebate/setting_edit";
    }

    /**
     * 保存设置是否自动返佣
     * 
     * @param dto
     * @return
     */
    @RequestMapping(value = "save")
    @ResponseBody
    public AjaxJson save(ManagerEntity dto) {
        try {
            return managerService.updateAutoRebate(dto);
        } catch (Exception e) {
            LOG.error("", e);
        }
        return null;
    }
}
