package com.heart.heartcloud.controller;

import com.alipay.api.AlipayClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @ClassName:CloudPaymentController
 * @Description:
 * @Author: Heart
 * @Date: 2019/7/24 16:37
 */
@Controller
@RequestMapping("/cloudpay")
public class CloudPaymentController {

    private static final Logger logger = LoggerFactory.getLogger(CloudPaymentController.class);

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public void pay() {

    }
}
