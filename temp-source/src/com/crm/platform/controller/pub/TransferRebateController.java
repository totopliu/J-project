package com.crm.platform.controller.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.pub.ManagerEntity;
import com.crm.platform.service.pub.RebateService;
import com.crm.util.SessionUtil;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/pub/transferRebate")
public class TransferRebateController {

    @Autowired
    private RebateService rebateService;

    @RequestMapping(value = "list")
    public String list() {
        return "pub/rebate_transfer/list";
    }

    @RequestMapping("query")
    @ResponseBody
    public PageInfo<JSONObject> query(DataGrid grid) {
        ManagerEntity user = SessionUtil.getSession();
        return rebateService.listTransferRebate(grid, user.getManagerid());
    }

    @RequestMapping("add")
    public String add() {
        return "pub/rebate_transfer/add";
    }

    @RequestMapping("save")
    @ResponseBody
    public AjaxJson save(String dollar) {
        ManagerEntity user = SessionUtil.getSession();
        return rebateService.saveTransferRebate(user.getManagerid(), dollar);
    }
}
