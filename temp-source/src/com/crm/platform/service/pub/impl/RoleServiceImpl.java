package com.crm.platform.service.pub.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.pub.RoleEntity;
import com.crm.platform.entity.pub.RoleOperateEntity;
import com.crm.platform.mapper.pub.RoleMapper;
import com.crm.platform.mapper.pub.RoleOperateMapper;
import com.crm.platform.service.BaseService;
import com.crm.platform.service.pub.RoleService;
import com.github.pagehelper.PageInfo;

@Service
@Transactional
public class RoleServiceImpl extends BaseService<RoleEntity> implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RoleOperateMapper roleOperateMapper;

    @Override
    public PageInfo<RoleEntity> queryRoleForList(DataGrid grid) {
        return this.queryForDataGrid(grid);
    }

    @Override
    public AjaxJson batchDelete(Integer[] ids) {
        AjaxJson json = new AjaxJson();
        super.batchDeleteByPrimaryKey(ids);
        json.setSuccess(true);
        json.setMsg("删除成功！");
        return json;
    }

    @Override
    public AjaxJson save(RoleEntity dto, Integer operates[], Integer ruleId) {
        AjaxJson json = new AjaxJson();
        boolean flag = false;
        // ID不为空
        if (dto.getId() != null) {
            // 修改
            flag = super.updateByPrimaryKey(dto);
            if (flag) {
                // 删除旧权限
                this.roleMapper.deleteOperateByRole(dto.getId());
            }
        } else {
            flag = insertSelective(dto);
        }
        // 是否添加成功或者修改成功，是否有新权限
        if (flag && operates != null && operates.length > 0) {
            for (Integer opid : operates) {
                RoleOperateEntity roleOperate = new RoleOperateEntity();
                roleOperate.setRole(dto.getId());
                roleOperate.setOpId(opid);
                this.roleOperateMapper.insertSelective(roleOperate);
            }
        }
        json.setSuccess(true);
        json.setMsg("保存成功！");
        return json;
    }

    @Override
    public void getRoleOperate(Model model, Integer id) {
        RoleEntity dto = super.selectByPrimaryKey(id);
        if (dto != null) {
            model.addAttribute("dto", dto);
            JSONObject map = new JSONObject();
            map = this.roleMapper.getRuleRoleByRoleId(dto.getId());
            if (map != null) {
                model.addAttribute("ruleId", map.get("ruleId"));
            } else {
                map = new JSONObject();
            }
            map.put("role", dto.getId());
            List<JSONObject> list = this.roleMapper.getRoleOperate(dto.getId());
            model.addAttribute("ops", list);
        }
    }

}
