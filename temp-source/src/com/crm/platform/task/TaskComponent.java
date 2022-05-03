package com.crm.platform.task;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crm.platform.service.pub.TradesService;
import com.crm.util.DatabaseUtil;

@Component
public class TaskComponent {

    public final Logger LOG = Logger.getLogger(TaskComponent.class);

    @Autowired
    private TradesService tradesService;

    public void refreshTradesToRebate() {
        try {
            tradesService.refreshTradesToRebate();
        } catch (Exception e) {
            LOG.error("返佣定时任务跑出异常", e);
        }
    }

    public void backupDatabase() {
        Calendar calstar = Calendar.getInstance();
        DatabaseUtil.backup();
        Calendar calover = Calendar.getInstance();
        long runtime = calover.getTimeInMillis() - calstar.getTimeInMillis();
        LOG.info("备份数据库耗时" + runtime);
    }

}
