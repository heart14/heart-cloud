package com.heart.heartcloud.exception;

/**
 * @ClassName: CloudMailException
 * @Description: TODO
 * @Author: jayhe
 * @Date: 2019/9/27 9:39
 * @Version: v1.0
 */
public class CloudMailException extends RuntimeException {

    private static final long serialVersionUID = -5515467951779622362L;

    private Integer code;

    public CloudMailException(Integer code) {
        this.code = code;
    }

    public CloudMailException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public CloudMailException setCode(Integer code) {
        this.code = code;
        return this;
    }
}
