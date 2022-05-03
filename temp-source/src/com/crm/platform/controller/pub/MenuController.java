package com.crm.platform.controller.pub;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.crm.platform.controller.BaseController;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.pub.MenuEntity;
import com.crm.platform.service.pub.MenuService;

import springfox.documentation.annotations.ApiIgnore;

/**
 * 菜单管理控制器
 * 
 * 
 */
@Controller
@RequestMapping("/pub/menu")
@ApiIgnore
public class MenuController extends BaseController {

    @Resource
    MenuService menuService;

    /**
     * 打开菜单页
     * 
     * @param model
     * @param grid
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public String list(Model model, DataGrid grid) throws Exception {
        model.addAttribute("page", this.menuService.queryForDataGrid(grid));
        return "pub/menu/list";
    }

    /**
     * 打开菜单编辑页
     * 
     * @param id
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/edit")
    public String edit(Integer id, ModelMap model) throws Exception {
        if (id != null) {
            model.addAttribute("dto", this.menuService.findMenuByPrimaryKey(id));
        }
        return "pub/menu/edit";
    }

    /**
     * 保存菜单
     * 
     * @param entity
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public AjaxJson save(@ModelAttribute("dto") MenuEntity entity) {
        return this.menuService.saveOrUpdate(entity);
    }

    /**
     * 批量删除菜单
     * 
     * @param ids
     * @return
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public AjaxJson del(Integer[] ids) {
        return this.menuService.batchDeleteMenu(ids);
    }

    /**
     * 查询全部菜单
     * 
     * @return
     */
    @RequestMapping(value = "/findAll")
    @ResponseBody
    public AjaxJson findAll() {
        return this.menuService.findAll();
    }
}
