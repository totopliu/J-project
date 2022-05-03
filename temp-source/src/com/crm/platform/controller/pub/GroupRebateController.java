package com.crm.platform.controller.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.platform.entity.AjaxJson;
import com.crm.platform.service.pub.GroupRebateService;

/**
 * 返佣设置
 * 
 * 
 *
 */
@Controller
@RequestMapping("/pub/groupRebate")
public class GroupRebateController {

    @Autowired
    private GroupRebateService groupRebateService;

    @RequestMapping("list")
    public String list() {
        return "pub/groupRebate/list";
    }

    @RequestMapping("save")
    @ResponseBody
    public AjaxJson save(String groupName, String levelJson) {
        return groupRebateService.save(groupName, levelJson);
    }

    @RequestMapping("getConfig")
    @ResponseBody
    public AjaxJson getConfig(String groupName) {
        return groupRebateService.getConfig(groupName);
    }

}
