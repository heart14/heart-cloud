package com.heart.heartcloud.exception;

/**
 * @ClassName: CloudSchedulerException
 * @Description: 定时任务异常类
 * @Author: jayhe
 * @Date: 2019/9/19 11:21
 * @Version: v1.0
 */
public class CloudSchedulerException extends RuntimeException {

    private static final long serialVersionUID = 8844183338465234266L;

    private Integer code;

    public CloudSchedulerException(Integer code) {
        this.code = code;
    }

    public CloudSchedulerException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public CloudSchedulerException setCode(Integer code) {
        this.code = code;
        return this;
    }
}
