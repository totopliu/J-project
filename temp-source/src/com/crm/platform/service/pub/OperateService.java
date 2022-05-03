package com.crm.platform.service.pub;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.pub.OperateEntity;
import com.github.pagehelper.PageInfo;

public interface OperateService {
    OperateEntity findByOperate(OperateEntity entity);

    PageInfo<JSONObject> queryOperateForList(DataGrid grid, Integer menuId);

    Boolean checkPermission(Integer menu, String op, Integer accountid);

    List<OperateEntity> getOperatesInPermissionByMenu(Integer menuid, Integer accountid);

    AjaxJson save(OperateEntity opt);

    AjaxJson deleteByOperate(String[] ids);
}
