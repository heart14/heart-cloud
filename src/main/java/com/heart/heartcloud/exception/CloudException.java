package com.heart.heartcloud.exception;

/**
 * @ClassName:CloudException
 * @Description:
 * @Author: Heart
 * @Date: 2019/7/12 9:34
 */
public class CloudException extends RuntimeException {

    private static final long serialVersionUID = -4668969916089794000L;

    private Integer code;

    public CloudException(Integer code) {
        this.code = code;
    }

    public CloudException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public CloudException setCode(Integer code) {
        this.code = code;
        return this;
    }
}
