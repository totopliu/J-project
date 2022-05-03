package com.crm.platform.service.pub.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.constant.Constant;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.pub.CurrencyBO;
import com.crm.platform.mapper.pub.CurrencyMapper;
import com.crm.platform.service.BaseService;
import com.crm.platform.service.pub.CurrencyService;
import com.crm.util.MsgUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
@Transactional
public class CurrencyServiceImpl extends BaseService<CurrencyBO> implements CurrencyService {

    @Autowired
    private CurrencyMapper currencyMapper;

    @Override
    public PageInfo<JSONObject> listCurrency(DataGrid grid) {
        if (StringUtils.isNotEmpty(grid.getSort())) {
            PageHelper.orderBy(grid.getSort() + "   " + grid.getOrder());
        }
        PageHelper.startPage(grid.getPageNum(), grid.getPageSize());
        return new PageInfo<JSONObject>(currencyMapper.queryCurrencyList());
    }

    @Override
    public CurrencyBO selectByPrimaryKey(int id) {
        return currencyMapper.selectByPrimaryKey(id);
    }

    @Override
    public AjaxJson save(CurrencyBO dto) {
        AjaxJson json = new AjaxJson();
        if (dto.getId() == null) {
            currencyMapper.insertCurrency(dto);
        } else {
            currencyMapper.updateCurrency(dto);
        }
        json.setSuccess(true);
        json.setMsg(MsgUtil.getSessionLgMsg(Constant.SUCCESS_SAVE));
        return json;
    }

    @Override
    public AjaxJson batchDelete(Integer[] ids) {
        AjaxJson ajaxJson = new AjaxJson();
        if (ids != null && ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                currencyMapper.deleteByPrimaryKey(ids[i]);
            }
        }
        ajaxJson.setSuccess(true);
        ajaxJson.setMsg(MsgUtil.getSessionLgMsg(Constant.SUCCESS_DELETE));
        return ajaxJson;
    }

    @Override
    public List<CurrencyBO> listCurrency() {
        return currencyMapper.listCurrency();
    }

}
