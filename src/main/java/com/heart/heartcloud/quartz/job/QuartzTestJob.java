package com.heart.heartcloud.quartz.job;

import com.heart.heartcloud.common.CloudErrorCodeEnums;
import com.heart.heartcloud.entity.CloudQuartzJob;
import com.heart.heartcloud.exception.CloudSystemException;
import com.heart.heartcloud.service.CloudQuartzJobService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @ClassName: QuartzTestJob
 * @Description: TODO
 * @Author: jayhe
 * @Date: 2019/9/19 10:49
 * @Version: v1.0
 */
@PersistJobDataAfterExecution
public class QuartzTestJob implements Job {

    public static final Logger logger = LoggerFactory.getLogger(QuartzTestJob.class);

    @Autowired
    private CloudQuartzJobService cloudQuartzJobService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String p0 = jobExecutionContext.getJobDetail().getJobDataMap().getString("p0");
        String p1 = jobExecutionContext.getJobDetail().getJobDataMap().getString("p1");
        logger.info(" -------- Quartz test job :params = [{},{}] {}", p0, p1, new Date());

        CloudQuartzJob cloudQuartzJobByPrimaryKey = cloudQuartzJobService.findCloudQuartzJobByPrimaryKey(p0);
        if (cloudQuartzJobByPrimaryKey != null && cloudQuartzJobByPrimaryKey.getJobStatus() == 0) {
            cloudQuartzJobByPrimaryKey.setJobStatus(1);
            cloudQuartzJobByPrimaryKey.setUpdateTime(new Date());
            int i = cloudQuartzJobService.editCloudQuartzJobByPrimaryKeySelective(cloudQuartzJobByPrimaryKey);
            if (i > 0) {
                logger.info("Quartz Job Status has been changed. JobId = {}", p0);
            } else {
                throw new CloudSystemException(CloudErrorCodeEnums.DBException.getCode(), CloudErrorCodeEnums.DBException.getMsg());
            }
        }
        jobExecutionContext.getJobDetail().getJobDataMap().put("p1", p1 + "77");
        logger.info("-------- After process, p1 = {}", jobExecutionContext.getJobDetail().getJobDataMap().getString("p1"));
    }
}
