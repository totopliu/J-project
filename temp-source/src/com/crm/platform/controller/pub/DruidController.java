package com.crm.platform.controller.pub;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 数据库监控工具控制器
 * 
 * 
 */
@Controller
@RequestMapping("/pub/druid")
public class DruidController {

    /**
     * 打开数据库监控跳转页
     * 
     * @return
     */
    @RequestMapping(value = "list")
    public String list() {
        return "pub/druid/list";
    }

}
