package com.heart.heartcloud.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.heart.heartcloud.alipay.AlipayConfig;
import com.heart.heartcloud.request.alipay.AlipayRequest;
import com.heart.heartcloud.utils.CloudDoubleUtils;
import com.heart.heartcloud.utils.CloudStringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @ClassName: CloudPaymentController
 * @Description:
 * @Author: Heart
 * @Date: 2019/7/24 16:37
 */
@Controller
@RequestMapping("/cloudpay")
@Api(tags = "支付宝支付相关接口")
public class CloudPaymentController {

    private static final Logger logger = LoggerFactory.getLogger(CloudPaymentController.class);

    /**
     * 支付宝统一收单下单并支付页面接口
     *
     * @param totalAmount
     * @param subject
     * @param body
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "支付宝统一收单下单并支付页面接口")
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public void alipay(@RequestParam("totalAmount") String totalAmount, @RequestParam("subject") String subject, @RequestParam("body") String body, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, AlipayConfig.format, AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        //创建API对应的request
        AlipayTradePagePayRequest alipayTradePagePayRequest = new AlipayTradePagePayRequest();
        //在公共参数中设置回跳和通知地址
        alipayTradePagePayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayTradePagePayRequest.setNotifyUrl(AlipayConfig.notify_url);

        AlipayRequest alipayRequest = new AlipayRequest();
        alipayRequest.setTotal_amount(CloudDoubleUtils.String2Double(totalAmount));
        alipayRequest.setSubject(subject);
        alipayRequest.setBody(body);
        alipayRequest.setOut_trade_no(CloudStringUtils.getTradeNo());
        //填充业务参数 JSON格式
        alipayTradePagePayRequest.setBizContent(JSON.toJSONString(alipayRequest));
        logger.info(JSON.toJSONString(alipayRequest));
        //调用SDK生成表单
        String form = alipayClient.pageExecute(alipayTradePagePayRequest).getBody();
        response.setContentType("text/html;charset=" + AlipayConfig.charset);
        //直接将完整的表单html输出到页面
        response.getWriter().write(form);
        response.getWriter().flush();
        response.getWriter().close();
    }

    /**
     * 支付宝回调接口
     *
     * @param request
     */
    @ApiOperation(value = "支付宝回调接口")
    @RequestMapping(value = "/return", method = RequestMethod.GET)
    public void alipayReturn(HttpServletRequest request) {
        logger.info("alipay return :{}", request.getParameterMap());
    }

    /**
     * 支付宝通知接口
     *
     * @param request
     */
    @ApiOperation(value = "支付宝通知接口")
    @RequestMapping(value = "/notify", method = RequestMethod.GET)
    public void alipayNotify(HttpServletRequest request) {
        logger.info("alipay notify :{}", request.getParameterMap());
    }
}
