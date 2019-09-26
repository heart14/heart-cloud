package com.heart.heartcloud.exception;

import com.heart.heartcloud.common.CloudErrorCodeEnums;
import com.heart.heartcloud.utils.CloudResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName:CloudExceptionHandler
 * @Description:
 * @Author: Heart
 * @Date: 2019/7/12 13:05
 */
@RestControllerAdvice
public class CloudExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CloudExceptionHandler.class);

    private static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler({CloudSystemException.class})
    public Object cloudExceptionHandler(CloudSystemException e) {
        //自定异常
        logger.error("自定义异常 :{}", e.getMessage());
        return CloudResponseUtils.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({CloudSchedulerException.class})
    public Object cloudSchedulerExceptionHandler(CloudSchedulerException e) {
        //Quartz异常
        logger.error("Quartz Scheduler异常 :{}", e.getMessage());
        return CloudResponseUtils.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public Object exceptionHandler(Exception e) {
        //如果是系统异常，如空指针，下标越界等
        logger.error("系统异常 :{}", e.getMessage(), e);
        return CloudResponseUtils.fail(CloudErrorCodeEnums.SystemException.getCode(), CloudErrorCodeEnums.SystemException.getMsg());
    }
}
