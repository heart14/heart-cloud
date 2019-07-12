package com.heart.heartcloud.controller;

import com.heart.heartcloud.domain.CloudUser;
import com.heart.heartcloud.response.CloudResponse;
import com.heart.heartcloud.service.CloudUserService;
import com.heart.heartcloud.utils.CloudResponseUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Description:
 * @Author: Heart
 * @Date: 2019/3/11 14:21
 */

@Controller
@RequestMapping
public class HeartCloudController {

    private static final Logger logger = LoggerFactory.getLogger(HeartCloudController.class);

    @Autowired
    private CloudUserService cloudUserService;

    /**
     * 系统登陆页面
     *
     * @return
     */
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
    @RequestMapping(value = "/regpage", method = RequestMethod.GET)
    public ModelAndView regPage() {

        return new ModelAndView("reg");
    }

    /**
     * 系统登出页面
     *
     * @return
     */
    @RequestMapping(value = "/logoutpage", method = RequestMethod.GET)
    public ModelAndView logoutPage() {

        return null;
    }

    /**
     * 系统首页页面
     *
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    /**
     * 系统用户登录
     *
     * @param cloudUser
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CloudResponse userLogin(@RequestBody CloudUser cloudUser, HttpServletRequest request) {

        logger.info("用户登录 :cloudUser => {}", cloudUser);

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(cloudUser.getUserName(), cloudUser.getUserPass());

        //shiro进行登录验证
        subject.login(token);

        HttpSession session = request.getSession();
        session.setAttribute("CurrentCloudUser", cloudUserService.findCloudUserByUserName(cloudUser.getUserName()));
        return CloudResponseUtil.success();
    }

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public CloudResponse userReg(@RequestBody CloudUser cloudUser) {
        logger.info("用户注册 :cloudUser => {}", cloudUser);
        return null;
    }

    /**
     * 系统用户登出
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView userLogout(HttpServletRequest request) {
        logger.info("系统用户登出 :CurrentCloudUser => {}", request.getSession().getAttribute("CurrentCloudUser"));
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new ModelAndView("login");
    }
}
