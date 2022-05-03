package com.crm.platform.controller.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.platform.entity.AjaxJson;
import com.crm.platform.service.pub.SysSettingService;

@Controller
@RequestMapping("/pub/setting")
public class SysSettingController {

    @Autowired
    private SysSettingService sysSettingService;

    @RequestMapping("list")
    public String list(Model model) {
        model.addAttribute("autoIn", sysSettingService.getAutoIn());
        model.addAttribute("remindEmail", sysSettingService.getConfigByKey("remindEmail"));
        return "pub/setting/list";
    }

    @RequestMapping("saveSysSetting")
    @ResponseBody
    public AjaxJson saveSysSetting(int autoIn) {
        return sysSettingService.saveSysSetting(autoIn);
    }

    /**
     * 2017-9-18 新增保存提醒审核邮箱
     * 
     */
    @RequestMapping("saveRemindEmail")
    @ResponseBody
    public AjaxJson saveRemindEmail(String remindEmail) {
        return sysSettingService.updateSysConfig("remindEmail", remindEmail);
    }

}
