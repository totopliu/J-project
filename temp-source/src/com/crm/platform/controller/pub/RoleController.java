package com.crm.platform.controller.pub;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.platform.controller.BaseController;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.pub.RoleEntity;
import com.crm.platform.service.pub.MenuService;
import com.crm.platform.service.pub.RoleService;
import com.github.pagehelper.PageInfo;

/**
 * 角色管理控制器
 * 
 *
 */
@Controller
@RequestMapping(value = "/pub/role")
@Api(value = "role", description = "角色控制")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    /**
     * 打开角色管理页
     * 
     * @return
     */
    @RequestMapping(value = "/list")
    @ApiOperation(value = "", hidden = true)
    public String list() {
        return "pub/role/list";
    }

    /**
     * 打开角色编辑页
     * 
     * @param id
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/edit")
    @ApiOperation(value = "", hidden = true)
    public String edit(Integer id, Model model) throws Exception {
        if (id != null) {
            this.roleService.getRoleOperate(model, id);
        }
        model.addAttribute("menus", menuService.getAll());
        return "pub/role/edit";
    }

    /**
     * 查询角色分页列表
     * 
     * @param grid
     * @return
     */
    @RequestMapping(value = "/query")
    @ResponseBody
    @ApiOperation(value = "", hidden = true)
    public PageInfo<RoleEntity> list(DataGrid grid) {
        return this.roleService.queryRoleForList(grid);
    }

    /**
     * 保存角色
     * 
     * @param operates
     * @param ruleId
     * @param dto
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    @ApiOperation(value = "", hidden = true)
    public AjaxJson save(Integer[] operates, Integer ruleId, @ModelAttribute("dto") RoleEntity dto) {
        return this.roleService.save(dto, operates, ruleId);
    }

    /**
     * 批量删除角色
     * 
     * @param ids
     * @return
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    @ApiOperation(value = "", hidden = true)
    public AjaxJson del(Integer[] ids) {
        return this.roleService.batchDelete(ids);
    }
}
