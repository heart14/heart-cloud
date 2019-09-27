package com.heart.heartcloud.quartz.job;

import com.heart.heartcloud.common.CloudErrorCodeEnums;
import com.heart.heartcloud.exception.CloudMailException;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.net.UnknownHostException;

/**
 * @ClassName: LoginMailJob
 * @Description: TODO
 * @Author: jayhe
 * @Date: 2019/9/27 10:09
 * @Version: v1.0
 */
public class LoginMailJob implements Job {

    public static final Logger logger = LoggerFactory.getLogger(LoginMailJob.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String sender = (String) jobDataMap.get("p0");
        String recipient = (String) jobDataMap.get("p1");
        String subject = (String) jobDataMap.get("p2");
        String text = (String) jobDataMap.get("p3");
        logger.info("登录提醒邮件定时发送任务开始执行 :sender = {}, recipient = {}, subject = {}, text = {}", sender, recipient, subject, text);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setTo(recipient);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);

        try {
            javaMailSender.send(simpleMailMessage);
            logger.info("登录提醒邮件定时发送任务执行完毕");
        } catch (MailException e) {
            logger.error("登录提醒邮件定时发送任务执行失败 :{}", e.getMessage());
        }
    }
}
