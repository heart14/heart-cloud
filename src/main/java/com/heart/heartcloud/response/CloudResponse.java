package com.heart.heartcloud.response;

import java.io.Serializable;

/**
 * @Description:
 * @Author: Heart
 * @Date: 2019/3/11 15:17
 */
public class CloudResponse implements Serializable {

    private String errCode;

    private String sign;

    private String errMessage;

    private String data;

    public String getErrCode() {
        return errCode;
    }

    public CloudResponse setErrCode(String errCode) {
        this.errCode = errCode;
        return this;
    }

    public String getSign() {
        return sign;
    }

    public CloudResponse setSign(String sign) {
        this.sign = sign;
        return this;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public CloudResponse setErrMessage(String errMessage) {
        this.errMessage = errMessage;
        return this;
    }

    public String getData() {
        return data;
    }

    public CloudResponse setData(String data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "CloudResponse{" +
                "errCode='" + errCode + '\'' +
                ", sign='" + sign + '\'' +
                ", errMessage='" + errMessage + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
