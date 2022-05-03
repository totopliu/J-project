package com.crm.platform.service.pub.impl;

import com.crm.platform.entity.pub.ExchangeRateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.platform.entity.AjaxJson;
import com.crm.platform.mapper.pub.ExchangeMapper;
import com.crm.platform.service.pub.ExchangeService;
import com.crm.util.MathUtil;

@Service
@Transactional
public class ExchangeServiceImpl implements ExchangeService {

    @Autowired
    private ExchangeMapper exchangeMapper;

    @Override
    public String getRate() {
        return MathUtil.roundDoubleSetHalfUpToStr(3, exchangeMapper.getRate());
    }

    @Override
    public AjaxJson save(ExchangeRateDTO dto) {
        AjaxJson json = new AjaxJson();
        int count = exchangeMapper.countRate();
        if (count > 0) {
            exchangeMapper.updRate(dto);
        } else {
            exchangeMapper.saveRate(dto);
        }
        json.setSuccess(true);
        return json;
    }

    @Override
    public String getOutRate() {
        return MathUtil.roundDoubleSetHalfUpToStr(3, exchangeMapper.getOutRate());
    }

    @Override
    public ExchangeRateDTO getDTO() {
        return exchangeMapper.getExchangeRateDTO();
    }
}
