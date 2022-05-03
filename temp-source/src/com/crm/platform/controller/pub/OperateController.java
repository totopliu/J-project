package com.crm.platform.controller.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.controller.BaseController;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.pub.OperateEntity;
import com.crm.platform.service.pub.OperateService;
import com.github.pagehelper.PageInfo;

import springfox.documentation.annotations.ApiIgnore;

/**
 * 操作管理控制器
 * 
 * 
 */
@Controller
@RequestMapping("/pub/operate")
@ApiIgnore
public class OperateController extends BaseController {

    @Autowired
    OperateService operateService;

    /**
     * 打开操作管理页面
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {
        return "pub/operate/list";
    }

    /**
     * 查询操作分页列表
     * 
     * @param grid
     * @param menuId
     * @return
     */
    @RequestMapping(value = "/query")
    @ResponseBody
    public PageInfo<JSONObject> query(DataGrid grid, Integer menuId) {
        return this.operateService.queryOperateForList(grid, menuId);
    }

    /**
     * 打开操作编辑页
     * 
     * @param dto
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit")
    public String edit(OperateEntity dto, Model model) {
        if (dto != null) {
            model.addAttribute("dto", this.operateService.findByOperate(dto));
        }
        return "pub/operate/edit";
    }

    /**
     * 保存操作
     * 
     * @param dto
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public AjaxJson save(OperateEntity dto) {
        return this.operateService.save(dto);
    }

    /**
     * 删除操作
     * 
     * @param ids
     * @return
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public AjaxJson del(String[] ids) {
        return this.operateService.deleteByOperate(ids);
    }

}
