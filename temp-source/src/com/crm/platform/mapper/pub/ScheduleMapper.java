package com.crm.platform.mapper.pub;

import java.util.List;

import com.crm.platform.entity.quartz.ScheduleJobEntity;

public interface ScheduleMapper {

    List<ScheduleJobEntity> queryScheduleJob();

}
