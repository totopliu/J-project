package com.crm.platform.service.pub;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.github.pagehelper.PageInfo;

public interface GroupTypeService {

    PageInfo<JSONObject> query(DataGrid grid);

    AjaxJson save(String groupNames, Integer transactionType);

}
