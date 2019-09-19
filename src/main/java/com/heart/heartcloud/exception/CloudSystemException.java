package com.heart.heartcloud.exception;

/**
 * @ClassName: CloudException
 * @Description: 系统异常类
 * @Author: Heart
 * @Date: 2019/7/12 9:34
 */
public class CloudSystemException extends RuntimeException {

    private static final long serialVersionUID = -4668969916089794000L;

    private Integer code;

    public CloudSystemException(Integer code) {
        this.code = code;
    }

    public CloudSystemException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public CloudSystemException setCode(Integer code) {
        this.code = code;
        return this;
    }
}
