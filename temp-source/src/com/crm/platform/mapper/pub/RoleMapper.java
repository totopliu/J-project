package com.crm.platform.mapper.pub;

import tk.mybatis.mapper.common.Mapper;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.pub.RoleEntity;

public interface RoleMapper extends Mapper<RoleEntity> {
    List<JSONObject> getRoleOperate(Integer id);

    int deleteOperateByRole(Integer role);

    JSONObject getRuleRoleByRoleId(Integer id);

    RoleEntity selectDefaultRole();
}
