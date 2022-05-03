package com.crm.platform.service.pub.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.constant.Constant;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.mapper.pub.PricesMapper;
import com.crm.platform.service.pub.PricesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
@Transactional
public class PricesServiceImpl implements PricesService {

    @Autowired
    private PricesMapper pricesMapper;

    @Override
    public PageInfo<JSONObject> listPrices(DataGrid grid) {
        if (StringUtils.isNotEmpty(grid.getSort())) {
            PageHelper.orderBy(grid.getSort() + "   " + grid.getOrder());
        }
        PageHelper.startPage(grid.getPageNum(), grid.getPageSize());
        return new PageInfo<JSONObject>(pricesMapper.selectPricesForList());
    }

    @Override
    public AjaxJson save(String symbols, Integer currencyId) {
        AjaxJson ajaxJson = new AjaxJson();
        String[] symbolsArr = symbols.split(",");
        for (int i = 0; i < symbolsArr.length; i++) {
            int count = pricesMapper.countBySymbol(symbolsArr[i]);
            if (count > 0) {
                pricesMapper.updCurrencyBySymbol(symbolsArr[i], currencyId);
            } else {
                pricesMapper.insertRelation(symbolsArr[i], currencyId);
            }
        }
        ajaxJson.setSuccess(true);
        ajaxJson.setMsg(Constant.SUCCESS_SAVE);
        return ajaxJson;
    }

}
