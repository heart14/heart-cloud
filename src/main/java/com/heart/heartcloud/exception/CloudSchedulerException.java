package com.heart.heartcloud.exception;

/**
 * @ClassName: CloudSchedulerException
 * @Description: TODO
 * @Author: jayhe
 * @Date: 2019/9/19 11:21
 * @Version: v1.0
 */
public class CloudSchedulerException extends RuntimeException {

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
