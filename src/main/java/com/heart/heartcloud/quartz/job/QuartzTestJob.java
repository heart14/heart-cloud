package com.heart.heartcloud.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @ClassName: QuartzTestJob
 * @Description: TODO
 * @Author: jayhe
 * @Date: 2019/9/19 10:49
 * @Version: v1.0
 */
public class QuartzTestJob implements Job {

    public static final Logger logger = LoggerFactory.getLogger(QuartzTestJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("Quartz test job :{}", new Date());
    }
}
