package com.crm.platform.controller.pub;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.controller.BaseController;
import com.crm.platform.entity.pub.MenuEntity;
import com.crm.platform.entity.pub.OperateEntity;
import com.crm.platform.service.pub.MenuService;
import com.crm.platform.service.pub.RoleOperateService;
import com.crm.util.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 首页控制
 * 
 * 
 *
 */
@Controller
@Api(value = "index")
public class IndexController extends BaseController {

    @Autowired
    MenuService menuService;

    @Autowired
    RoleOperateService roleOperateService;

    @RequestMapping("/op_{oper}_{id}")
    @ApiOperation(value = "根据oper与id转发到指定的地址", httpMethod = "GET")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 404, message = "地址错误"),
            @ApiResponse(code = 500, message = "系统错误,请联系管理人员") })
    public String op(@PathVariable String oper, @PathVariable Integer id, Model model) {
        // 获取对应的菜单对象
        MenuEntity menu = this.menuService.findMenuByPrimaryKey(id);
        if (menu != null) {
            JSONObject dto = new JSONObject();
            dto.put("role", SessionUtil.getSession().getRole());
            dto.put("menu", id);
            List<OperateEntity> op = this.roleOperateService.queryOperateForList(dto);
            if (op.isEmpty()) {
                // 无权限
                return "denied";
            }
            model.addAttribute("operates", op);
            model.addAttribute("OP", op.get(0));
            // 获取对应的菜单对象
            model.addAttribute("MENU", this.menuService.findMenuByPrimaryKey(id));
            return "forward:" + menu.getChannel() + "/" + oper;
        }
        return "404";
    }
}
