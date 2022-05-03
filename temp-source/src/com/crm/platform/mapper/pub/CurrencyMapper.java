package com.crm.platform.mapper.pub;

import java.util.List;

import tk.mybatis.mapper.common.Mapper;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.pub.CurrencyBO;

public interface CurrencyMapper extends Mapper<CurrencyBO> {

    List<JSONObject> queryCurrencyList();

    List<JSONObject> getCurrencyListForPrice();

    void insertCurrency(CurrencyBO dto);

    void updateCurrency(CurrencyBO dto);

    List<CurrencyBO> listCurrency();
}
