package com.crm.platform.service.pub.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.pub.LogsEntity;
import com.crm.platform.service.BaseService;
import com.crm.platform.service.pub.LogsService;
import com.github.pagehelper.PageInfo;

@Service
@Transactional
public class LogsServiceImpl extends BaseService<LogsEntity> implements LogsService {

    @Override
    public PageInfo<LogsEntity> queryLogsForList(DataGrid grid) {
        return super.queryForDataGrid(grid);
    }

    @Override
    public boolean insert(LogsEntity entity) {
        return super.insertSelective(entity);
    }

    @Override
    public List<LogsEntity> exportLogExcel() {
        return super.queryObjectForList();
    }

}
