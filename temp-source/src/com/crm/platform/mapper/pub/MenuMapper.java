package com.crm.platform.mapper.pub;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crm.platform.entity.pub.MenuEntity;

import tk.mybatis.mapper.common.Mapper;

public interface MenuMapper extends Mapper<MenuEntity> {
    MenuEntity findMenuByPrimaryKey(@Param("id") Integer id);

    void deleteMenu(Integer id);

    void treeNode();

    List<MenuEntity> findAll();
}
