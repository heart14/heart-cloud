package com.heart.heartcloud.entity;

import org.apache.tomcat.jni.Time;
import org.quartz.Job;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CloudQuartzJob {

    /**
     * job id
     */
    private String jobId;

    /**
     * 任务名（唯一）
     */
    private String jobName;

    /**
     * 任务组名（可重复）
     */
    private String jobGroupName;

    /**
     * 触发器名（唯一）
     */
    private String triggerName;

    /**
     * 触发器组名（可重复）
     */
    private String triggerGroupName;

    /**
     * 任务参数
     */
    private String jobParams;

    /**
     * 触发器类型 "TIME" "CRON"
     */
    private String executeType;

    /**
     * 任务执行cron表达式
     */
    private String cronExpression;

    /**
     * 任务开始时间
     */
    private long startTime;

    /**
     * 任务结束时间
     */
    private long endTime;

    /**
     * 任务执行间隔
     */
    private long internalTime;

    /**
     * 任务执行间隔 TimeUnit类常量
     */
    private String internalUnit;

    /**
     * 任务重复次数
     */
    private int repeatTime;

    /**
     * 任务并发状态
     */
    private int concurrent;

    /**
     * 任务bean name
     */
    private String beanName;

    /**
     * 任务method name
     */
    private String methodName;

    /**
     * 任务描述
     */
    private String description;

    /**
     * 任务执行状态
     */
    private int jobStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 任务类
     */
    private Class<? extends Job> job;

    /**
     * 任务参数
     */
    private List<String> jobParamsList;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroupName() {
        return jobGroupName;
    }

    public void setJobGroupName(String jobGroupName) {
        this.jobGroupName = jobGroupName;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroupName() {
        return triggerGroupName;
    }

    public void setTriggerGroupName(String triggerGroupName) {
        this.triggerGroupName = triggerGroupName;
    }

    public String getJobParams() {
        return jobParams;
    }

    public void setJobParams(String jobParams) {
        this.jobParams = jobParams;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public int getConcurrent() {
        return concurrent;
    }

    public void setConcurrent(int concurrent) {
        this.concurrent = concurrent;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(int jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Class<? extends Job> getJob() {
        return job;
    }

    public void setJob(Class<? extends Job> job) {
        this.job = job;
        this.beanName = job.getName();
    }

    public List<String> getJobParamsList() {
        return jobParamsList;
    }

    public void setJobParamsList(List<String> jobParamsList) {
        this.jobParamsList = jobParamsList;
        this.jobParams = jobParamsList.toString();
    }

    public String getExecuteType() {
        return executeType;
    }

    public CloudQuartzJob setExecuteType(String executeType) {
        this.executeType = executeType;
        return this;
    }

    public long getStartTime() {
        return startTime;
    }

    public CloudQuartzJob setStartTime(long startTime) {
        this.startTime = startTime;
        return this;
    }

    public long getEndTime() {
        return endTime;
    }

    public CloudQuartzJob setEndTime(long endTime) {
        this.endTime = endTime;
        return this;
    }

    public long getInternalTime() {
        return internalTime;
    }

    public CloudQuartzJob setInternalTime(long internalTime) {
        this.internalTime = internalTime;
        return this;
    }

    public int getRepeatTime() {
        return repeatTime;
    }

    public CloudQuartzJob setRepeatTime(int repeatTime) {
        this.repeatTime = repeatTime;
        return this;
    }

    public String getInternalUnit() {
        return internalUnit;
    }

    public CloudQuartzJob setInternalUnit(String internalUnit) {
        this.internalUnit = internalUnit;
        return this;
    }

    @Override
    public String toString() {
        return "CloudQuartzJob{" +
                "jobId='" + jobId + '\'' +
                ", jobName='" + jobName + '\'' +
                ", jobGroupName='" + jobGroupName + '\'' +
                ", triggerName='" + triggerName + '\'' +
                ", triggerGroupName='" + triggerGroupName + '\'' +
                ", jobParams='" + jobParams + '\'' +
                ", executeType='" + executeType + '\'' +
                ", cronExpression='" + cronExpression + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", internalTime=" + internalTime +
                ", internalUnit=" + internalUnit +
                ", repeatTime=" + repeatTime +
                ", concurrent=" + concurrent +
                ", beanName='" + beanName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", description='" + description + '\'' +
                ", jobStatus=" + jobStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", job=" + job +
                ", jobParamsList=" + jobParamsList +
                '}';
    }
}
