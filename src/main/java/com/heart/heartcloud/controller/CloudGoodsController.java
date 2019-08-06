package com.heart.heartcloud.controller;

import com.heart.heartcloud.response.CloudResponse;
import com.heart.heartcloud.service.CloudGoodsService;
import com.heart.heartcloud.utils.CloudResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: CloudGoodsController
 * @Description:
 * @Author: Heart
 * @Date: 2019/8/5 10:22
 */
@RequestMapping("/cloudgoods")
@RestController
public class CloudGoodsController {

    public static final Logger logger = LoggerFactory.getLogger(CloudGoodsController.class);

    @Autowired
    private CloudGoodsService cloudGoodsService;

    @RequestMapping(value = "/findall",method = RequestMethod.POST)
    public CloudResponse findSellGoods(HttpServletResponse response) {
        return CloudResponseUtils.success(cloudGoodsService.findAllCloudGoods());
    }
}
