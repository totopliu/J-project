package com.crm.platform.controller.pub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.crm.annotation.SystemLog;
import com.crm.platform.constant.Constant;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.pub.InGoldBO;
import com.crm.platform.entity.pub.InGoldReviewDTO;
import com.crm.platform.service.pub.InGoldService;
import com.crm.util.MsgUtil;
import com.github.pagehelper.PageInfo;

/**
 * 入金审核控制器
 * 
 * 
 */
@Controller
@RequestMapping("/pub/inGoldReview")
public class InGoldReviewController {

    private final static Logger LOG = LoggerFactory.getLogger(InGoldReviewController.class);

    @Autowired
    private InGoldService inGoldService;

    /**
     * 打开入金审核列表页
     * 
     * @return
     */
    @RequestMapping(value = "list")
    public String list() {
        return "pub/in_gold_review/list";
    }

    /**
     * 入金审核分页列表
     * 
     * @param grid
     * @param inGoldReviewDTO
     * @return
     */
    @RequestMapping(value = "query")
    @ResponseBody
    public PageInfo<JSONObject> query(DataGrid grid, InGoldReviewDTO inGoldReviewDTO) {
        return inGoldService.queryInGoldReview(grid, inGoldReviewDTO);
    }

    /**
     * 打开入金审核页
     * 
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "review")
    public String review(Integer id, Model model) {
        InGoldBO inGoldEntity = inGoldService.getInGoldById(id);
        model.addAttribute("dto", inGoldEntity);
        return "pub/in_gold_review/review";
    }

    /**
     * 保存入金审核结果
     * 
     * @param dto
     * @return
     */
    @RequestMapping(value = "save")
    @ResponseBody
    @SystemLog(description = "入金审核", module = "入金模块", methods = "save")
    public AjaxJson save(InGoldBO dto) {
        try {
            return inGoldService.saveReview(dto);
        } catch (Exception e) {
            LOG.info("入金审核异常！", e);
            AjaxJson json = new AjaxJson();
            json.setMsg(MsgUtil.getSessionLgMsg(Constant.SYSTEM_EXCEPTION));
            return json;
        }
    }

    /**
     * 查询入金汇总
     * 
     * @param inGoldReviewDTO
     * @return
     */
    @RequestMapping(value = "findInGoldReviewTotal")
    @ResponseBody
    public AjaxJson findInGoldReviewTotal(InGoldReviewDTO inGoldReviewDTO) {
        return inGoldService.findInGoldReviewTotal(inGoldReviewDTO);
    }

}
