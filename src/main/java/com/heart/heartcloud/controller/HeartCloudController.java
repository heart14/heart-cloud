package com.heart.heartcloud.controller;

import com.heart.heartcloud.common.CloudErrorCodeEnums;
import com.heart.heartcloud.domain.CloudUser;
import com.heart.heartcloud.entity.CloudQuartzJob;
import com.heart.heartcloud.exception.CloudSystemException;
import com.heart.heartcloud.quartz.QuartzJobService;
import com.heart.heartcloud.quartz.job.LoginMailJob;
import com.heart.heartcloud.response.CloudResponse;
import com.heart.heartcloud.service.CloudQuartzJobService;
import com.heart.heartcloud.service.CloudUserService;
import com.heart.heartcloud.utils.CloudDateUtils;
import com.heart.heartcloud.utils.CloudIpUtils;
import com.heart.heartcloud.utils.CloudResponseUtils;
import com.heart.heartcloud.utils.CloudStringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: Heart
 * @Date: 2019/3/11 14:21
 */

@Controller
@RequestMapping
@Api(tags = "系统相关接口")
public class HeartCloudController {

    private static final Logger logger = LoggerFactory.getLogger(HeartCloudController.class);

    @Autowired
    private CloudUserService cloudUserService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private QuartzJobService quartzJobService;

    @Autowired
    private CloudQuartzJobService cloudQuartzJobService;

    /**
     * 系统登陆页面
     *
     * @return
     */
    @ApiIgnore
    @RequestMapping(value = "/loginpage", method = RequestMethod.GET)
    public ModelAndView loginPage() {
        logger.info("系统登陆页面");
        return new ModelAndView("login");
    }

    /**
     * 系统注册页面
     *
     * @return
     */
    @ApiIgnore
    @RequestMapping(value = "/regpage", method = RequestMethod.GET)
    public ModelAndView regPage() {

        return new ModelAndView("reg");
    }

    /**
     * 系统登出页面
     *
     * @return
     */
    @ApiIgnore
    @RequestMapping(value = "/logoutpage", method = RequestMethod.GET)
    public ModelAndView logoutPage() {

        return null;
    }

    /**
     * 系统首页页面
     *
     * @return
     */
    @ApiIgnore
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {
        CloudUser currentCloudUser = (CloudUser) request.getSession().getAttribute("CurrentCloudUser");
        if (currentCloudUser == null) {
            throw new CloudSystemException(CloudErrorCodeEnums.LoginExpiredException.getCode(), CloudErrorCodeEnums.LoginExpiredException.getMsg());
        }
        logger.info("HEART CLOUD首页 :cloudUser => {}", currentCloudUser);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("cloudUser", currentCloudUser);
        return modelAndView;
    }

    /**
     * 会员中心页面
     *
     * @param request
     * @return
     */
    @ApiIgnore
    @RequestMapping(value = "/vip", method = RequestMethod.GET)
    public ModelAndView vip(HttpServletRequest request) {
        CloudUser currentCloudUser = (CloudUser) request.getSession().getAttribute("CurrentCloudUser");
        if (currentCloudUser == null) {
            throw new CloudSystemException(CloudErrorCodeEnums.LoginExpiredException.getCode(), CloudErrorCodeEnums.LoginExpiredException.getMsg());
        }
        logger.info("HEART CLOUD会员中心 :cloudUser => {}", currentCloudUser);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("vip");
        modelAndView.addObject("cloudUser", currentCloudUser);
        return modelAndView;
    }

    /**
     * 资源商城页面
     *
     * @param request
     * @return
     */
    @ApiIgnore
    @RequestMapping(value = "/market", method = RequestMethod.GET)
    public ModelAndView market(HttpServletRequest request) {
        CloudUser currentCloudUser = (CloudUser) request.getSession().getAttribute("CurrentCloudUser");
        if (currentCloudUser == null) {
            throw new CloudSystemException(CloudErrorCodeEnums.LoginExpiredException.getCode(), CloudErrorCodeEnums.LoginExpiredException.getMsg());
        }
        logger.info("HEART CLOUD资源商城 :cloudUser => {}", currentCloudUser);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("market");
        modelAndView.addObject("cloudUser", currentCloudUser);
        return modelAndView;
    }

    /**
     * 系统用户登录
     *
     * @param cloudUser
     */
    @ApiOperation(value = "系统用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings("all")
    public CloudResponse userLogin(@RequestBody CloudUser cloudUser, HttpServletRequest request) {

        logger.info("用户登录 :cloudUser => {}", cloudUser);

        CloudUser cloudUserByUserName = cloudUserService.findCloudUserByUserName(cloudUser.getUserName());
        if (cloudUserByUserName == null) {
            logger.error("用户不存在 :{}", cloudUser.getUserName());
            throw new CloudSystemException(CloudErrorCodeEnums.UnKnownUserException.getCode(), CloudErrorCodeEnums.UnKnownUserException.getMsg());
        }

        if (redisTemplate.hasKey(CloudStringUtils.getRedisLoginLockKey(cloudUser.getUserName()))) {
            logger.info("用户已锁定，{} 秒后重试！", redisTemplate.getExpire(CloudStringUtils.getRedisLoginLockKey(cloudUser.getUserName()), TimeUnit.SECONDS));
            throw new CloudSystemException(CloudErrorCodeEnums.LoginLockException.getCode(), CloudErrorCodeEnums.LoginLockException.getMsg());
        }

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(cloudUser.getUserName(), cloudUser.getUserPass());

        //shiro进行登录验证
        try {
            subject.login(token);
            if (redisTemplate.hasKey(CloudStringUtils.getRedisLoginFailCountKey(cloudUser.getUserName()))) {
                redisTemplate.delete(CloudStringUtils.getRedisLoginFailCountKey(cloudUser.getUserName()));
            }
        } catch (AuthenticationException e) {
            if (redisTemplate.hasKey(CloudStringUtils.getRedisLoginFailCountKey(cloudUser.getUserName()))) {
                int loginFailCount = (int) redisTemplate.opsForValue().get(CloudStringUtils.getRedisLoginFailCountKey(cloudUser.getUserName()));
                if (loginFailCount == 4) {
                    logger.info("登录失败5次，用户已锁定，2分钟后解除锁定 :{}", cloudUser.getUserName());
                    redisTemplate.opsForValue().set(CloudStringUtils.getRedisLoginLockKey(cloudUser.getUserName()), "用户已锁定", 120, TimeUnit.SECONDS);
                    redisTemplate.delete(CloudStringUtils.getRedisLoginFailCountKey(cloudUser.getUserName()));
                    throw new CloudSystemException(CloudErrorCodeEnums.LoginLockException.getCode(), CloudErrorCodeEnums.LoginLockException.getMsg());
                } else {
                    logger.info("登录失败 :{}", cloudUser.getUserName());
                    redisTemplate.opsForValue().set(CloudStringUtils.getRedisLoginFailCountKey(cloudUser.getUserName()), loginFailCount + 1);
                    throw new CloudSystemException(CloudErrorCodeEnums.LoginFailException.getCode(), CloudErrorCodeEnums.LoginFailException.getMsg());
                }
            } else {
                logger.info("登录失败 :{}", cloudUser.getUserName());
                redisTemplate.opsForValue().set(CloudStringUtils.getRedisLoginFailCountKey(cloudUser.getUserName()), 1);
                throw new CloudSystemException(CloudErrorCodeEnums.LoginFailException.getCode(), CloudErrorCodeEnums.LoginFailException.getMsg());
            }
        }

        HttpSession session = request.getSession();
        session.setAttribute("CurrentCloudUser", cloudUserService.findCloudUserByUserName(cloudUser.getUserName()));

        //java mail
        String ipAddr = CloudIpUtils.getIpAddr(request);
        String sender = "lwf14@qq.com";
        String recipient = "jayhei14@163.com";
        String mailSubject = "JavaMailSender test mail.";
        String mailText = cloudUser.getUserName() + "于" + CloudDateUtils.formatDateToString(new Date()) + "在" + ipAddr + "登录了 HEART CLOUD。";

        threadPoolExecutor.execute(new Thread(() -> {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(recipient);
            simpleMailMessage.setSubject(mailSubject);
            simpleMailMessage.setText(mailText);

            try {
                javaMailSender.send(simpleMailMessage);
                logger.info("登录提醒邮件已发送 :{}", simpleMailMessage);
            } catch (MailException e) {
                logger.error("登录提醒邮件发送失败 :{}", e.getMessage());
                //throw new CloudMailException(CloudErrorCodeEnums.MailSendException.getCode(), CloudErrorCodeEnums.MailSendException.getMsg());
                //邮件发送失败不要对程序造成影响，可以放到定时任务重发
                CloudQuartzJob cloudQuartzJob = new CloudQuartzJob();

                String jobId = CloudStringUtils.getShotUuid();
                cloudQuartzJob.setJobId(jobId);
                cloudQuartzJob.setJobName("loginMailJob:name:" + jobId);
                cloudQuartzJob.setJobGroupName("loginMailJob:group:name:" + jobId);
                cloudQuartzJob.setTriggerName("loginMailJob:trigger:name:" + jobId);
                cloudQuartzJob.setTriggerGroupName("loginMailJob:trigger:group:name:" + jobId);
                cloudQuartzJob.setJob(LoginMailJob.class);
                cloudQuartzJob.setMethodName("execute");
                cloudQuartzJob.setExecuteType("TIME");
                //测试在指定时间执行 1分钟后开始执行 重复3次 每次间隔5秒
                cloudQuartzJob.setStartTime(CloudDateUtils.addMinutes(new Date(), 1).getTime());
                cloudQuartzJob.setDescription("Heart cloud 登录提醒邮件定时任务 :" + jobId);
                cloudQuartzJob.setJobStatus(0);
                cloudQuartzJob.setCreateTime(new Date());

                List<String> paramList = new ArrayList<>();
                paramList.add(sender);
                paramList.add(recipient);
                paramList.add(mailSubject);
                paramList.add(mailText);
                cloudQuartzJob.setJobParamsList(paramList);

                quartzJobService.addJob(cloudQuartzJob);
                cloudQuartzJobService.saveCloudQuartzJobSelective(cloudQuartzJob);
            }
        }));

        return CloudResponseUtils.success();
    }

    /**
     * 系统管理员登录
     *
     * @param cloudUser
     */
    @ApiOperation(value = "系统管理员登录")
    @RequestMapping(value = "/login/m", method = RequestMethod.POST)
    @ResponseBody
    public CloudResponse mUserLogin(@RequestBody CloudUser cloudUser, HttpServletRequest request) {

        logger.info("管理员登录 :cloudUser => {}", cloudUser);

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(cloudUser.getUserName(), cloudUser.getUserPass());

        //shiro进行登录验证
        subject.login(token);
        subject.checkRole("Manager");

        HttpSession session = request.getSession();
        session.setAttribute("CurrentCloudUser", cloudUserService.findCloudUserByUserName(cloudUser.getUserName()));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("manager/index");
        return CloudResponseUtils.success(CloudErrorCodeEnums.ManagerLoginSuccess.getCode(), CloudErrorCodeEnums.ManagerLoginSuccess.getMsg(), null);
    }

    /**
     * 用户注册
     *
     * @param cloudUser
     * @return
     */
    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "用户注册")
    public CloudResponse userReg(@RequestBody CloudUser cloudUser) {

        logger.info("用户注册 :cloudUser => {}", cloudUser);

        return CloudResponseUtils.success(cloudUserService.userReg(cloudUser));
    }

    /**
     * 系统用户登出
     *
     * @param request
     * @return
     */
    @ApiIgnore
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView userLogout(HttpServletRequest request) {

        logger.info("系统用户登出 :CurrentCloudUser => {}", request.getSession().getAttribute("CurrentCloudUser"));

        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new ModelAndView("login");
    }

}
