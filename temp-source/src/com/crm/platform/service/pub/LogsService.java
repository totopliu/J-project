package com.crm.platform.service.pub;

import java.util.List;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.pub.LogsEntity;
import com.github.pagehelper.PageInfo;

public interface LogsService {
    List<LogsEntity> exportLogExcel();

    PageInfo<LogsEntity> queryLogsForList(DataGrid grid);

    public boolean insert(LogsEntity entity);
}
