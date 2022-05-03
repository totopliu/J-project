package com.crm.platform.service.pub;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.param.OutGoldReviewParam;
import com.crm.platform.entity.pub.OutGoldDTO;
import com.github.pagehelper.PageInfo;

public interface OutGoldService {

    PageInfo<JSONObject> listOutGold(DataGrid grid, Integer login, Integer ticket, String outDate);

    AjaxJson countOutGoldTotal(Integer login, Integer ticket, String outDate);

    AjaxJson save(OutGoldDTO dto);

    PageInfo<JSONObject> listOutGoldReview(DataGrid grid, OutGoldReviewParam param);

    OutGoldDTO getOutGoldById(Integer id);

    AjaxJson saveReview(OutGoldDTO dto);

    AjaxJson getOutRate();

    AjaxJson findOutGoldReviewTotal(OutGoldReviewParam param);

}
