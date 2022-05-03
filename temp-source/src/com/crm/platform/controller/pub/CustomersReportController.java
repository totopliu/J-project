package com.crm.platform.controller.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.platform.entity.AjaxJson;
import com.crm.platform.service.pub.CustomersReportService;

/**
 * 客户报表
 * 
 * 
 *
 */
@Controller
@RequestMapping("/pub/customersReport")
public class CustomersReportController {

    @Autowired
    private CustomersReportService customersReportService;

    @RequestMapping("list")
    public String list() {
        return "pub/customers_report/list";
    }

    @RequestMapping("table")
    @ResponseBody
    public AjaxJson table() {
        return customersReportService.table();
    }

}
