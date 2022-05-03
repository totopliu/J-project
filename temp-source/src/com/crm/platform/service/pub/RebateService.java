package com.crm.platform.service.pub;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.pub.RebatePlanDTO;
import com.crm.platform.entity.pub.RebateTransferReviewDTO;
import com.github.pagehelper.PageInfo;

public interface RebateService {

    PageInfo<JSONObject> listUnderlingRebate(DataGrid grid, Integer managerId);

    AjaxJson saveFixed(Integer currencyId, Integer managerId, Double rebateFixed, Integer belongId);

    AjaxJson savePoint(Integer currencyId, Integer managerId, Double rebatePoint, Integer belongId);

    PageInfo<JSONObject> listSetting(DataGrid grid, Integer managerId);

    PageInfo<JSONObject> listManagerRebate(DataGrid grid, Integer login, String rebateDate, String ticket, String over);

    double countRebateTotal(String ids);

    AjaxJson saveRebateIn(String ids, String login);

    AjaxJson rebateSumToMT4(Integer login);

    AjaxJson countRebateTotal(Integer login, String ticket, String rebateDate, String over);

    PageInfo<JSONObject> listRebatePlan(DataGrid grid);

    RebatePlanDTO getRebatePlan(Integer id);

    AjaxJson saveRebatePlan(RebatePlanDTO dto);

    AjaxJson removeRebatePlans(int[] ids);

    AjaxJson getRebatePlanCurrency(int planId);

    AjaxJson saveRebatePlanCurrency(int planId, String levelJson);

    PageInfo<JSONObject> listTransferRebate(DataGrid grid, Integer managerid);

    AjaxJson saveTransferRebate(Integer managerid, String dollar);

    PageInfo<JSONObject> listTransferRebateToReview(DataGrid grid);

    RebateTransferReviewDTO getRebateTransferDTO(Integer id);

    AjaxJson saveRebateTransferReview(Integer id, Integer state, String reason);

}
