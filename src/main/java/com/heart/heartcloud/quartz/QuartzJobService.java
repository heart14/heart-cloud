package com.heart.heartcloud.quartz;

import com.heart.heartcloud.common.CloudErrorCodeEnums;
import com.heart.heartcloud.entity.CloudQuartzJob;
import com.heart.heartcloud.exception.CloudSchedulerException;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
public class QuartzJobService {

    @Autowired
    @Qualifier("scheduler")
    private Scheduler scheduler;

    /**
     * 新增任务
     *
     * @param quartzJob
     */
    public void addJob(CloudQuartzJob quartzJob) {
        try {
            //创建JobDetail
            JobDetail jobDetail = JobBuilder.newJob(quartzJob.getJob()).withIdentity(quartzJob.getJobName(), quartzJob.getJobGroupName()).build();
            //创建触发器Trigger
            Trigger trigger;
            if (!StringUtils.isEmpty(quartzJob.getCronExpression())) {
                trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression())).startNow().withIdentity(quartzJob.getTriggerName(), quartzJob.getTriggerGroupName()).build();
            } else {
                trigger = TriggerBuilder.newTrigger().startAt(new Date(quartzJob.getExecuteTime())).withIdentity(quartzJob.getTriggerName(), quartzJob.getTriggerGroupName()).build();
            }
            if (quartzJob.getJobParamsList() != null && quartzJob.getJobParamsList().size() > 0) {
                for (int i = 0; i < quartzJob.getJobParamsList().size(); i++) {
                    jobDetail.getJobDataMap().put("p" + i, quartzJob.getJobParamsList().get(i));
                }
            }
            scheduler.scheduleJob(jobDetail, trigger);
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (SchedulerException e) {
            throw new CloudSchedulerException(CloudErrorCodeEnums.SchedulerException.getCode(), CloudErrorCodeEnums.SchedulerException.getMsg());
        }
    }

    /**
     * 移除任务
     *
     * @param quartzJob
     */
    public void removeJob(CloudQuartzJob quartzJob) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(quartzJob.getTriggerName(), quartzJob.getTriggerGroupName());
            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);
            scheduler.deleteJob(JobKey.jobKey(quartzJob.getJobName(), quartzJob.getJobGroupName()));
        } catch (SchedulerException e) {
            throw new CloudSchedulerException(CloudErrorCodeEnums.SchedulerException.getCode(), CloudErrorCodeEnums.SchedulerException.getMsg());
        }
    }

    /**
     * 立即执行任务
     *
     * @param quartzJob
     */
    public void startJobNow(CloudQuartzJob quartzJob) {
        try {
            JobKey jobKey = JobKey.jobKey(quartzJob.getJobName());
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            throw new CloudSchedulerException(CloudErrorCodeEnums.SchedulerException.getCode(), CloudErrorCodeEnums.SchedulerException.getMsg());
        }
    }

    /**
     * 暂停任务
     *
     * @param quartzJob
     */
    public void pauseJob(CloudQuartzJob quartzJob) {
        try {
            JobKey jobKey = JobKey.jobKey(quartzJob.getJobName());
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            throw new CloudSchedulerException(CloudErrorCodeEnums.SchedulerException.getCode(), CloudErrorCodeEnums.SchedulerException.getMsg());
        }
    }

    /**
     * 恢复任务
     *
     * @param quartzJob
     */
    public void resumeJob(CloudQuartzJob quartzJob) {
        try {
            JobKey jobKey = JobKey.jobKey(quartzJob.getJobName());
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            throw new CloudSchedulerException(CloudErrorCodeEnums.SchedulerException.getCode(), CloudErrorCodeEnums.SchedulerException.getMsg());
        }
    }
}
