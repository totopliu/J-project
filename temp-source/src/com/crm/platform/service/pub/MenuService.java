package com.crm.platform.service.pub;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.pub.ManagerEntity;
import com.crm.platform.entity.pub.MenuEntity;
import com.crm.util.TreeNode;
import com.github.pagehelper.PageInfo;

public interface MenuService {
    public List<JSONObject> queryAllUrlForList();

    List<String> queryPermissionForList(Integer userId);

    public List<TreeNode> listTree(ManagerEntity manager);

    public PageInfo<MenuEntity> queryForDataGrid(DataGrid grid);

    public List<MenuEntity> queryMenuByUserId(Long userId);

    public List<JSONObject> getAll();

    public MenuEntity findMenuByPrimaryKey(Integer key);

    AjaxJson saveOrUpdate(MenuEntity entity);

    AjaxJson batchDeleteMenu(Integer[] ids);

    public AjaxJson findAll();
}
