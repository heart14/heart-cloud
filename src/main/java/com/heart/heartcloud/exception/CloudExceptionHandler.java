package com.heart.heartcloud.exception;

import com.heart.heartcloud.common.CloudErrorCodeEnums;
import com.heart.heartcloud.utils.CloudResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName:CloudExceptionHandler
 * @Description:
 * @Author: Heart
 * @Date: 2019/7/12 13:05
 */
@ControllerAdvice
public class CloudExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CloudExceptionHandler.class);

    private static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Object exceptionHandler(Exception e) {
        if (e instanceof CloudException) {
            //自定异常
            CloudException cloudException = (CloudException) e;
            return CloudResponseUtil.fail(cloudException.getCode(), cloudException.getMessage());
        } else {
            //如果是系统异常，如空指针，下标越界等
            logger.error("系统异常 :{}", e);
            return CloudResponseUtil.fail(CloudErrorCodeEnums.SystemException.getCode(), CloudErrorCodeEnums.SystemException.getMsg());
        }
    }
}
