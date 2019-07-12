package com.heart.heartcloud.controller;

import com.heart.heartcloud.domain.CloudUser;
import com.heart.heartcloud.response.CloudResponse;
import com.heart.heartcloud.service.CloudUserService;
import com.heart.heartcloud.utils.CloudResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: Heart
 * @Date: 2019/3/11 14:20
 */
@RequestMapping("/clouduser")
@RestController
public class CloudUserController {

    private static final Logger logger = LoggerFactory.getLogger(CloudUserController.class);

    @Autowired
    private CloudUserService cloudUserService;

    /**
     * 查询用户信息
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public CloudResponse getCloudUser(@RequestParam("cloudUserId") Integer cloudUserId) {

        logger.info("查询用户信息 :cloudUserId => {}", cloudUserId);

        return CloudResponseUtil.success(null);
    }

    /**
     * 获取所有用户信息
     *
     * @return
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public CloudResponse getCloudUserList() {

        logger.info("查询所有用户信息");

        return CloudResponseUtil.success(null);
    }

    /**
     * 添加用户
     *
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CloudResponse saveCloudUser(@RequestBody CloudUser cloudUser) {

        logger.info("保存用户 :cloudUser => {}", cloudUser);

        return CloudResponseUtil.success();
    }

    /**
     * 删除用户
     *
     * @param cloudUserId
     * @return
     */
    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    public CloudResponse removeCloudUser(Integer cloudUserId) {

        logger.info("删除用户 :cloudUserId => {}", cloudUserId);

        return CloudResponseUtil.success();
    }

    /**
     * 修改用户信息
     *
     * @param cloudUser
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CloudResponse editCloudUser(CloudUser cloudUser) {

        logger.info("修改用户信息 :cloudUser => {}", cloudUser);

        return CloudResponseUtil.success();
    }

}
