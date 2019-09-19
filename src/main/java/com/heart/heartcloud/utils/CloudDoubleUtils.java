package com.heart.heartcloud.utils;

import com.heart.heartcloud.common.CloudErrorCodeEnums;
import com.heart.heartcloud.exception.CloudException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: CloudDoubleUtils
 * @Description: double数值工具类，用于金额相关
 * @Author: Heart
 * @Date: 2019/7/25 16:28
 */
public class CloudDoubleUtils {

    private static final Logger logger = LoggerFactory.getLogger(CloudDoubleUtils.class);

    private static final String DOT = ".";

    private static final String PREFIX = ".00";

    /**
     * 单位元，字符串转DOUBLE类型
     *
     * @param amount
     * @return
     */
    public static Double String2Double(String amount) {
        logger.info("待转化金额 :{}", amount);
        if (amount == null) {
            throw new CloudException(CloudErrorCodeEnums.ParamException.getCode(), CloudErrorCodeEnums.ParamException.getMsg());
        }

        if (!amount.contains(DOT)) {
            amount += PREFIX;
            return Double.valueOf(amount);
        }
        String substring = amount.substring(amount.lastIndexOf(DOT));
        if (substring.length() == 1) {
            return Double.valueOf(amount.replace(DOT, PREFIX));
        }
        if (substring.length() == 2) {
            amount += "0";
            return Double.valueOf(amount);
        }
        if (substring.length() > 3) {
            String s = substring.substring(0, 2);
            amount = amount.substring(0, amount.lastIndexOf(DOT)) + s;
            return Double.valueOf(amount);
        }
        throw new CloudException(CloudErrorCodeEnums.ParamException.getCode(), CloudErrorCodeEnums.ParamException.getMsg());
    }
}
