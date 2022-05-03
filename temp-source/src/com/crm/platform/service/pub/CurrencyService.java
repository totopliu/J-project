package com.crm.platform.service.pub;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.pub.CurrencyBO;
import com.github.pagehelper.PageInfo;

public interface CurrencyService {

    PageInfo<JSONObject> listCurrency(DataGrid grid);

    CurrencyBO selectByPrimaryKey(int id);

    AjaxJson save(CurrencyBO dto);

    AjaxJson batchDelete(Integer[] ids);

    List<CurrencyBO> listCurrency();

}
