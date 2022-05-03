package com.crm.platform.service.pub.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.pub.OperateEntity;
import com.crm.platform.entity.pub.RoleOperateEntity;
import com.crm.platform.mapper.pub.AuthMapper;
import com.crm.platform.service.BaseService;
import com.crm.platform.service.pub.RoleOperateService;

@Service
@Transactional
public class RoleOperateServiceImpl extends BaseService<RoleOperateEntity> implements RoleOperateService {
    @Autowired
    AuthMapper authMapper;

    @Override
    public List<OperateEntity> queryOperateForList(JSONObject dto) {
        return this.authMapper.queryOperateForList(dto);
    }
}
