package com.heart.heartcloud.controller;

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

    @RequestMapping(value = "/loginpage",method = RequestMethod.GET)
    public ModelAndView loginPage() {

        return null;
    }

    @RequestMapping(value = "/regpage",method = RequestMethod.GET)
    public ModelAndView regPage() {

        return null;
    }

    @RequestMapping(value = "/logoutpage",method = RequestMethod.GET)
    public ModelAndView logoutPage() {

        return null;
    }
}
