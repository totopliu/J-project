package com.crm.platform.mapper.pub;

import com.crm.platform.entity.pub.ExchangeRateDTO;

public interface ExchangeMapper {

    double getRate();

    void updRate(ExchangeRateDTO dto);

    double getOutRate();

    ExchangeRateDTO getExchangeRateDTO();

    int countRate();

    void saveRate(ExchangeRateDTO dto);

}
