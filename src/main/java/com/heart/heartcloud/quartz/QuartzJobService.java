package com.heart.heartcloud.quartz;

import com.heart.heartcloud.common.CloudErrorCodeEnums;
import com.heart.heartcloud.entity.CloudQuartzJob;
import com.heart.heartcloud.exception.CloudSchedulerException;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class QuartzJobService {

    private static final Logger logger = LoggerFactory.getLogger(QuartzJobService.class);

    @Autowired
    @Qualifier("scheduler")
    private Scheduler scheduler;

    /**
     * 新增任务
     *
     * @param quartzJob
     */
    public void addJob(CloudQuartzJob quartzJob) {
        logger.info("新增定时任务 :{}", quartzJob);
        try {
            //创建JobDetail
            JobDetail jobDetail = JobBuilder.newJob(quartzJob.getJob()).withIdentity(quartzJob.getJobName(), quartzJob.getJobGroupName()).build();
            //创建触发器Trigger
            Trigger trigger;
            //创建一个TriggerBuilder
            TriggerBuilder<Trigger> triggerTriggerBuilder = TriggerBuilder.newTrigger();
            //设置触发器名和触发器组名
            triggerTriggerBuilder.withIdentity(quartzJob.getTriggerName(), quartzJob.getTriggerGroupName());
            if ("TIME".equals(quartzJob.getExecuteType())) {
                //如果传入开始时间，则触发器使用传入的开始时间
                if (quartzJob.getStartTime() > 0) {
                    triggerTriggerBuilder.startAt(new Date(quartzJob.getStartTime()));
                } else {
                    //否则立即开始
                    triggerTriggerBuilder.startNow();
                }
                //如果传入了结束时间，则触发器使用传入的结束时间
                if (quartzJob.getEndTime() > 0) {
                    triggerTriggerBuilder.endAt(new Date(quartzJob.getEndTime()));
                }
                //如果传入了重复次数和重复间隔，则触发器 以指定触发间隔重复执行指定次数
                if (quartzJob.getInternalTime() > 0 && quartzJob.getRepeatTime() > 0) {
                    switch (quartzJob.getInternalUnit()) {
                        case "HOURS":
                            triggerTriggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours((int) quartzJob.getInternalTime()).withRepeatCount(quartzJob.getRepeatTime()));
                            break;
                        case "MINUTES":
                            triggerTriggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes((int) quartzJob.getInternalTime()).withRepeatCount(quartzJob.getRepeatTime()));
                            break;
                        case "SECONDS":
                            triggerTriggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds((int) quartzJob.getInternalTime()).withRepeatCount(quartzJob.getRepeatTime()));
                            break;
                        case "MILLISECONDS":
                            triggerTriggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(quartzJob.getInternalTime()).withRepeatCount(quartzJob.getRepeatTime()));
                            break;
                        default:
                            throw new CloudSchedulerException(CloudErrorCodeEnums.SchedulerException.getCode(), CloudErrorCodeEnums.SchedulerException.getMsg());
                    }
                    //如果只传入了重复间隔，没传重复次数，则触发器 以指定的间隔一直执行
                } else if (quartzJob.getInternalTime() > 0) {
                    switch (quartzJob.getInternalUnit()) {
                        case "HOURS":
                            triggerTriggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours((int) quartzJob.getInternalTime()));
                            break;
                        case "MINUTES":
                            triggerTriggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes((int) quartzJob.getInternalTime()));
                            break;
                        case "SECONDS":
                            triggerTriggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds((int) quartzJob.getInternalTime()));
                            break;
                        case "MILLISECONDS":
                            triggerTriggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(quartzJob.getInternalTime()));
                            break;
                        default:
                            throw new CloudSchedulerException(CloudErrorCodeEnums.SchedulerException.getCode(), CloudErrorCodeEnums.SchedulerException.getMsg());
                    }
                    //如果只传入了重复次数，没传重复间隔，则触发器 同时按指定次数个线程并发执行
                } else if (quartzJob.getRepeatTime() > 0) {
                    triggerTriggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(quartzJob.getRepeatTime()));
                    //如果重复次数和重复间隔都没传，则立即或在指定的开始时间，执行一次
                } else {

                }
                trigger = triggerTriggerBuilder.build();
            } else if ("CRON".equals(quartzJob.getExecuteType())) {
                //如果传入开始时间，则触发器使用传入的开始时间
                if (quartzJob.getStartTime() > 0) {
                    triggerTriggerBuilder.startAt(new Date(quartzJob.getStartTime()));
                } else {
                    //否则立即开始
                    triggerTriggerBuilder.startNow();
                }
                //如果传入了结束时间，则触发器使用传入的结束时间
                if (quartzJob.getEndTime() > 0) {
                    triggerTriggerBuilder.endAt(new Date(quartzJob.getEndTime()));
                }
                triggerTriggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression()));
                trigger = triggerTriggerBuilder.build();
            } else {
                throw new CloudSchedulerException(CloudErrorCodeEnums.ParamException.getCode(), CloudErrorCodeEnums.ParamException.getMsg());
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
            if (e instanceof ObjectAlreadyExistsException) {
                logger.error("定时任务已存在 :{}", e.getMessage());
                throw new CloudSchedulerException(CloudErrorCodeEnums.JobRepeatException.getCode(), CloudErrorCodeEnums.JobRepeatException.getMsg());
            } else {
                logger.error("新建定时任务异常 :{}", e.getMessage());
                throw new CloudSchedulerException(CloudErrorCodeEnums.SchedulerException.getCode(), CloudErrorCodeEnums.SchedulerException.getMsg());
            }
        }
    }

    /**
     * 移除任务
     *
     * @param quartzJob
     */
    public void removeJob(CloudQuartzJob quartzJob) {
        logger.info("移除定时任务 :{}", quartzJob);
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
        logger.info("立即执行定时任务 :{}", quartzJob);
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
        logger.info("暂停定时任务 :{}", quartzJob);
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
        logger.info("恢复定时任务 :{}", quartzJob);
        try {
            JobKey jobKey = JobKey.jobKey(quartzJob.getJobName());
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            throw new CloudSchedulerException(CloudErrorCodeEnums.SchedulerException.getCode(), CloudErrorCodeEnums.SchedulerException.getMsg());
        }
    }
}
