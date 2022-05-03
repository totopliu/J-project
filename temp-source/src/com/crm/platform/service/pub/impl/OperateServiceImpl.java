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
import com.crm.platform.entity.pub.OperateEntity;
import com.crm.platform.mapper.pub.OperateMapper;
import com.crm.platform.service.BaseService;
import com.crm.platform.service.pub.OperateService;
import com.crm.util.MsgUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
@Transactional
public class OperateServiceImpl extends BaseService<OperateEntity> implements OperateService {

    @Autowired
    OperateMapper operateMapper;

    @Override
    public OperateEntity findByOperate(OperateEntity entity) {
        return this.operateMapper.findOperateByOpAndMenu(entity);
    }

    @Override
    public PageInfo<JSONObject> queryOperateForList(DataGrid grid, Integer menuId) {
        if (StringUtils.isNotEmpty(grid.getOrder())) {
            PageHelper.orderBy(grid.getSort() + "   " + grid.getOrder());
        } else {
            PageHelper.orderBy(" menu desc ");
        }
        PageHelper.startPage(grid.getPageNum(), grid.getPageSize());
        return new PageInfo<JSONObject>(this.operateMapper.queryOperateForList(menuId));
    }

    @Override
    public Boolean checkPermission(Integer menu, String op, Integer accountid) {
        return null;
    }

    @Override
    public List<OperateEntity> getOperatesInPermissionByMenu(Integer menuid, Integer accountid) {
        return null;
    }

    @Override
    public AjaxJson save(OperateEntity opt) {
        AjaxJson json = new AjaxJson();
        if (this.operateMapper.findOperateByOpAndMenu(opt) == null) {
            this.operateMapper.insertByOperate(opt);
        } else {
            this.operateMapper.updateByOperate(opt);
        }
        json.setSuccess(true);
        json.setMsg(MsgUtil.getSessionLgMsg(Constant.SUCCESS_SAVE));
        return json;
    }

    @Override
    public AjaxJson deleteByOperate(String[] ids) {
        AjaxJson json = new AjaxJson();
        for (String tid : ids) {
            String[] tids = tid.split("-");
            OperateEntity dto = new OperateEntity(Integer.valueOf(tids[0]), tids[1]);
            this.operateMapper.deleteByOperate(dto);
        }
        json.setSuccess(true);
        json.setMsg(MsgUtil.getSessionLgMsg(Constant.SUCCESS_DELETE));
        return json;
    }

}
