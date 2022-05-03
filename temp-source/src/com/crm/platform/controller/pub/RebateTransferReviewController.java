package com.crm.platform.controller.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.service.pub.RebateService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/pub/rebateTransferReview")
public class RebateTransferReviewController {

    @Autowired
    private RebateService rebateService;

    @RequestMapping(value = "list")
    public String list() {
        return "pub/rebate_transfer_review/list";
    }

    @RequestMapping("query")
    @ResponseBody
    public PageInfo<JSONObject> query(DataGrid grid) {
        return rebateService.listTransferRebateToReview(grid);
    }

    @RequestMapping(value = "review")
    public String review(Integer id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("dto", rebateService.getRebateTransferDTO(id));
        return "pub/rebate_transfer_review/review";
    }

    @RequestMapping("save")
    @ResponseBody
    public AjaxJson save(Integer id, Integer state, String reason) {
        return rebateService.saveRebateTransferReview(id, state, reason);
    }
}
