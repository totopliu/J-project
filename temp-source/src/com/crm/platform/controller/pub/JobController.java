package com.crm.platform.controller.pub;

import java.util.List;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.framework.quartz.SchedulerFactory;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.quartz.ScheduleJobEntity;

/**
 * 定时任务
 * 
 * 
 *
 */
@Controller
@RequestMapping("/pub/job")
public class JobController {

    @Autowired
    private SchedulerFactory schedulerFactory;

    private final static Logger LOG = LoggerFactory.getLogger(JobController.class);

    @RequestMapping("list")
    public String list(Model model) {
        try {
            List<ScheduleJobEntity> schedulerFactories = schedulerFactory.getAllJob();
            model.addAttribute("dtos", schedulerFactories);
        } catch (SchedulerException e) {
            LOG.error("", e);
        }
        return "pub/job/list";
    }

    @RequestMapping("pauseJob")
    @ResponseBody
    public AjaxJson pauseJob(String jobName) {
        AjaxJson json = new AjaxJson();
        try {
            List<ScheduleJobEntity> schedulerFactories = schedulerFactory.getAllJob();
            for (ScheduleJobEntity scheduleJobEntity : schedulerFactories) {
                if (scheduleJobEntity.getJobName().equals(jobName)) {
                    schedulerFactory.pauseJob(scheduleJobEntity);
                }
            }
            json.setSuccess(true);
        } catch (Exception e) {
            LOG.error("", e);
            json.setSuccess(false);
        }
        return json;
    }

    @RequestMapping("resumeJob")
    @ResponseBody
    public AjaxJson resumeJob(String jobName) {
        AjaxJson json = new AjaxJson();
        try {
            List<ScheduleJobEntity> schedulerFactories = schedulerFactory.getAllJob();
            for (ScheduleJobEntity scheduleJobEntity : schedulerFactories) {
                if (scheduleJobEntity.getJobName().equals(jobName)) {
                    schedulerFactory.resumeJob(scheduleJobEntity);
                }
            }
            json.setSuccess(true);
        } catch (Exception e) {
            LOG.error("", e);
            json.setSuccess(false);
        }
        return json;
    }

}
