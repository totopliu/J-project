package com.crm.platform.controller.pub;

import com.crm.platform.entity.pub.ExchangeRateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.platform.entity.AjaxJson;
import com.crm.platform.service.pub.ExchangeService;

/**
 * 汇率设置
 * 
 *
 */
@Controller
@RequestMapping("pub/exchange")
public class ExchangeController {

    @Autowired
    private ExchangeService exchangeService;

    @RequestMapping("list")
    public String list(Model model) {
        model.addAttribute("dto", exchangeService.getDTO());
        return "pub/exchange/list";
    }

    @RequestMapping("save")
    @ResponseBody
    public AjaxJson save(ExchangeRateDTO dto) {
        return exchangeService.save(dto);
    }

}
