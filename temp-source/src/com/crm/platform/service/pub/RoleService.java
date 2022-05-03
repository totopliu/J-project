package com.crm.platform.service.pub;

import org.springframework.ui.Model;

import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.pub.RoleEntity;
import com.github.pagehelper.PageInfo;

public interface RoleService {
    PageInfo<RoleEntity> queryRoleForList(DataGrid grid);

    AjaxJson batchDelete(Integer[] ids);

    AjaxJson save(RoleEntity dto, Integer operates[], Integer ruleId);

    void getRoleOperate(Model model, Integer id);

}
