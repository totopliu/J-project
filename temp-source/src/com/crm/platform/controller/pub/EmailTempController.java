package com.crm.platform.controller.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.pub.EmailSettingDO;
import com.crm.platform.entity.pub.EmailTempDO;
import com.crm.platform.service.pub.EmailTempService;
import com.github.pagehelper.PageInfo;

/**
 * 邮件模板控制器
 * 
 * 
 */
@Controller
@RequestMapping("/pub/emailTemp")
public class EmailTempController {

    @Autowired
    private EmailTempService emailTempService;

    /**
     * 邮件模板页
     * 
     * @return
     */
    @RequestMapping(value = "list")
    public String list() {
        return "pub/email/list";
    }

    /**
     * 邮件模板列表
     * 
     * @param grid
     * @return
     */
    @RequestMapping(value = "query")
    @ResponseBody
    public PageInfo<JSONObject> query(DataGrid grid) {
        return emailTempService.listEmailTemp(grid);
    }

    /**
     * 打开邮件模板编辑页
     * 
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "edit")
    public String edit(Integer id, Model model) {
        model.addAttribute("dto", emailTempService.getById(id));
        return "pub/email/edit";
    }

    /**
     * 保存邮件模板
     * 
     * @param dto
     * @return
     */
    @RequestMapping(value = "save")
    @ResponseBody
    public AjaxJson save(EmailTempDO dto) {
        return emailTempService.save(dto);
    }

    /**
     * 邮件服务配置页
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "setting")
    public String setting(Model model) {
        model.addAttribute("dto", emailTempService.getEmailSetting());
        return "pub/email/setting";
    }

    /**
     * 保存邮件服务配置
     * 
     * @param dto
     * @return
     */
    @RequestMapping(value = "saveSetting")
    @ResponseBody
    public AjaxJson saveSetting(EmailSettingDO dto) {
        return emailTempService.saveSetting(dto);
    }
}
