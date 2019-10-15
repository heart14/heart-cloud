package com.heart.heartcloud.controller.manager;

import com.heart.heartcloud.common.CloudErrorCodeEnums;
import com.heart.heartcloud.domain.CloudUser;
import com.heart.heartcloud.exception.CloudSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: CloudManagerController
 * @Description: 后台管理页面控制类，跳转各页面
 * @Author: jayhe
 * @Date: 2019/10/11 16:36
 * @Version: v1.0
 */
@RestController
@RequestMapping("/manager")
public class CloudManagerController {

    private static final Logger logger = LoggerFactory.getLogger(CloudManagerController.class);

    /**
     * @param viewName
     * @param request
     * @return
     */
    @RequestMapping(value = "/{viewName}", method = RequestMethod.GET)
    public ModelAndView chartsPage(@PathVariable("viewName") String viewName, HttpServletRequest request) {
        CloudUser currentCloudUser = (CloudUser) request.getSession().getAttribute("CurrentCloudUser");
        if (currentCloudUser == null) {
            throw new CloudSystemException(CloudErrorCodeEnums.LoginExpiredException.getCode(), CloudErrorCodeEnums.LoginExpiredException.getMsg());
        }
        logger.info("Current CloudUser :{}", currentCloudUser);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("manager/" + viewName);
        modelAndView.addObject("cloudUser", currentCloudUser);
        return modelAndView;
    }

}
