package com.crm.platform.service.pub.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.constant.Constant;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.mapper.pub.GroupTypeMapper;
import com.crm.platform.service.pub.GroupTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
@Transactional
public class GroupTypeServiceImpl implements GroupTypeService {

    @Autowired
    private GroupTypeMapper groupTypeMapper;

    @Override
    public PageInfo<JSONObject> query(DataGrid grid) {
        if (StringUtils.isNotEmpty(grid.getSort())) {
            PageHelper.orderBy(grid.getSort() + "   " + grid.getOrder());
        }
        PageHelper.startPage(grid.getPageNum(), grid.getPageSize());
        return new PageInfo<>(groupTypeMapper.query());
    }

    @Override
    public AjaxJson save(String groupNames, Integer transactionType) {
        AjaxJson json = new AjaxJson();
        String[] groupNameArr = groupNames.split(",");
        for (String groupName : groupNameArr) {
            int count = groupTypeMapper.countByGroupName(groupName);
            if (count > 0) {
                groupTypeMapper.updateByGroupName(groupName, transactionType);
            } else {
                groupTypeMapper.insert(groupName, transactionType);
            }
        }
        json.setSuccess(true);
        json.setMsg(Constant.SUCCESS_SAVE);
        return json;
    }

}
