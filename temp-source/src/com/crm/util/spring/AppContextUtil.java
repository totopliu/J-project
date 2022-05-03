package com.crm.util.spring;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.crm.framework.quartz.SchedulerFactory;
import com.crm.platform.entity.quartz.ScheduleJobEntity;
import com.crm.platform.mapper.pub.ScheduleMapper;

public class AppContextUtil implements ApplicationContextAware {

    private static ApplicationContext ctx = null;

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private SchedulerFactory schedulerFactory;

    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        AppContextUtil.ctx = ctx;
        try {
            List<ScheduleJobEntity> scheduleJobEntities = scheduleMapper.queryScheduleJob();
            for (ScheduleJobEntity scheduleJobEntity : scheduleJobEntities) {
                schedulerFactory.addJob(scheduleJobEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Object getBean(String beanName) {
        return ctx.getBean(beanName);
    }

}
