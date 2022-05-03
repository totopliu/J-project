package com.crm.platform.mapper.pub;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.pub.OperateEntity;

public interface OperateMapper extends Mapper<OperateEntity> {
    List<JSONObject> queryOperateForList(@Param("menuId") Integer menuId);

    OperateEntity findOperateByOpAndMenu(OperateEntity dto);

    void insertByOperate(OperateEntity dto);

    void updateByOperate(OperateEntity dto);

    void deleteByOperate(OperateEntity dto);
}
