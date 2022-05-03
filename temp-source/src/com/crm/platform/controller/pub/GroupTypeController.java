package com.crm.platform.controller.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.service.pub.GroupTypeService;
import com.github.pagehelper.PageInfo;

/**
 * 用户组交易类型
 * 
 * 
 *
 */
@Controller
@RequestMapping("/pub/groupType")
public class GroupTypeController {

    @Autowired
    private GroupTypeService groupTypeService;

    @RequestMapping("list")
    public String list() {
        return "pub/groupType/list";
    }

    @RequestMapping("query")
    @ResponseBody
    public PageInfo<JSONObject> query(DataGrid grid) {
        return groupTypeService.query(grid);
    }

    @RequestMapping("edit")
    public String edit(String groupNames, Model model) {
        model.addAttribute("groupNames", groupNames);
        return "pub/groupType/edit";
    }

    @RequestMapping("save")
    @ResponseBody
    public AjaxJson save(String groupNames, Integer transactionType) {
        return groupTypeService.save(groupNames, transactionType);
    }

}
