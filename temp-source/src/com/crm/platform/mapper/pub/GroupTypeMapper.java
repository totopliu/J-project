package com.crm.platform.mapper.pub;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONObject;

public interface GroupTypeMapper {

    List<JSONObject> query();

    int countByGroupName(@Param("groupName") String groupName);

    void updateByGroupName(@Param("groupName") String groupName, @Param("transactionType") Integer transactionType);

    void insert(@Param("groupName") String groupName, @Param("transactionType") Integer transactionType);

    Integer getTypeByGroupName(@Param("groupName") String groupName);

}
