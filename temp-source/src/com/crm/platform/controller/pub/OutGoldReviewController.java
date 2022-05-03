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
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.param.OutGoldReviewParam;
import com.crm.platform.entity.pub.OutGoldDTO;
import com.crm.platform.service.pub.OutGoldService;
import com.crm.util.MathUtil;
import com.github.pagehelper.PageInfo;

/**
 * 出金审核
 * 
 * 
 */
@Controller
@RequestMapping("/pub/outGoldReview")
public class OutGoldReviewController {

    private final static Logger LOG = LoggerFactory.getLogger(OutGoldReviewController.class);

    @Autowired
    private OutGoldService outGoldService;

    /**
     * 打开出金审核管理页
     * 
     * @return
     */
    @RequestMapping(value = "list")
    public String list() {
        return "pub/out_gold_review/list";
    }

    /**
     * 查询出金审核管理分页列表
     * 
     * @param grid
     * @param outCreateDate
     * @return
     */
    @RequestMapping(value = "query")
    @ResponseBody
    public PageInfo<JSONObject> query(DataGrid grid, OutGoldReviewParam param) {
        return outGoldService.listOutGoldReview(grid, param);
    }

    /**
     * 打开出金审核页
     * 
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "review")
    public String review(Integer id, Model model) {
        try {
            OutGoldDTO outGoldDTO = outGoldService.getOutGoldById(id);
            model.addAttribute("dto", outGoldDTO);
            if (outGoldDTO.getBalance() != null) {
                model.addAttribute("balance", MathUtil.roundDoubleSetHalfUpToStr(2, outGoldDTO.getBalance()));
            }
            if (outGoldDTO.getRebateBalance() != null) {
                model.addAttribute("rebateBalance",
                        MathUtil.roundDoubleSetHalfUpToStr(2, outGoldDTO.getRebateBalance()));
            }
        } catch (Exception e) {
            LOG.error("", e);
        }
        return "pub/out_gold_review/review";
    }

    @RequestMapping(value = "info")
    public String info(Integer id, Model model) {
        try {
            OutGoldDTO outGoldDTO = outGoldService.getOutGoldById(id);
            model.addAttribute("dto", outGoldDTO);
            if (outGoldDTO.getBalance() != null) {
                model.addAttribute("balance", MathUtil.roundDoubleSetHalfUpToStr(2, outGoldDTO.getBalance()));
            }
            if (outGoldDTO.getRebateBalance() != null) {
                model.addAttribute("rebateBalance",
                        MathUtil.roundDoubleSetHalfUpToStr(2, outGoldDTO.getRebateBalance()));
            }
        } catch (Exception e) {
            LOG.error("", e);
        }
        return "pub/out_gold_review/info";
    }

    /**
     * 保存出金审核结果
     * 
     * @param dto
     * @return
     */
    @RequestMapping(value = "save")
    @ResponseBody
    @SystemLog(description = "出金审核", module = "出金模块", methods = "save")
    public AjaxJson save(OutGoldDTO dto) {
        try {
            return outGoldService.saveReview(dto);
        } catch (Exception e) {
            LOG.error("", e);
        }
        return null;
    }

    /**
     * 出金审核汇总
     * 
     * @param param
     * @return
     */
    @RequestMapping(value = "findOutGoldReviewTotal")
    @ResponseBody
    public AjaxJson findOutGoldReviewTotal(OutGoldReviewParam param) {
        return outGoldService.findOutGoldReviewTotal(param);
    }
}
