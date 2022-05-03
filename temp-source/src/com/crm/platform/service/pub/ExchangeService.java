package com.crm.platform.service.pub;

import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.pub.ExchangeRateDTO;

public interface ExchangeService {

    String getRate();

    AjaxJson save(ExchangeRateDTO dto);

    String getOutRate();

    ExchangeRateDTO getDTO();
}
