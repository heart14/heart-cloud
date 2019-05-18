package com.heart.heartcloud.controller;

import com.heart.heartcloud.domain.CloudUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description:
 * @Author: Heart
 * @Date: 2019/3/11 14:21
 */

@RequestMapping("/heartcloud")
@RestController
public class HeartCloudController {

    /**
     * 系统登陆页面
     *
     * @return
     */
    @RequestMapping(value = "/loginpage", method = RequestMethod.GET)
    public ModelAndView loginPage() {

        return null;
    }

    /**
     * 系统注册页面
     *
     * @return
     */
    @RequestMapping(value = "/regpage", method = RequestMethod.GET)
    public ModelAndView regPage() {

        return null;
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


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void userLogin(CloudUser cloudUser) {

    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void userLogout() {

    }
}
