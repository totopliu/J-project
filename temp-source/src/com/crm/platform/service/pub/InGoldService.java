package com.crm.platform.service.pub;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.pub.InGoldBO;
import com.crm.platform.entity.pub.InGoldReviewDTO;
import com.crm.platform.entity.pub.PayResult;
import com.crm.platform.entity.pub.PayResultShenfu;
import com.github.pagehelper.PageInfo;

public interface InGoldService {

    PageInfo<JSONObject> listInGold(DataGrid grid, Integer login, Integer ticket, String inDate);

    AjaxJson countInGoldTotal(Integer login, Integer ticket, String inDate);

    AjaxJson save(InGoldBO dto);

    PageInfo<JSONObject> queryInGoldReview(DataGrid grid, InGoldReviewDTO inGoldReviewDTO);

    InGoldBO getInGoldById(Integer id);

    AjaxJson saveReview(InGoldBO dto);

    AjaxJson getRate();

    void updateInGoldByResult(PayResult payResult);

    void checkResultShenfu(PayResultShenfu result);

    void checkResultDinpay(String order_no, String ipsbillno);

    AjaxJson findInGoldReviewTotal(InGoldReviewDTO inGoldReviewDTO);

    void payNotify(String orderid);

}
