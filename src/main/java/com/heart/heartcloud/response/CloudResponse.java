package com.heart.heartcloud.response;

/**
 * @Description:
 * @Author: Heart
 * @Date: 2019/3/11 15:17
 */
public class CloudResponse<T> {

    private Integer errCode;

    private String errMessage;

    private T data;

    public CloudResponse() {
    }

    public CloudResponse(Integer errCode, String errMessage) {
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public CloudResponse(Integer errCode, String errMessage, T data) {
        this.errCode = errCode;
        this.errMessage = errMessage;
        this.data = data;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public CloudResponse<T> setErrCode(Integer errCode) {
        this.errCode = errCode;
        return this;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public CloudResponse<T> setErrMessage(String errMessage) {
        this.errMessage = errMessage;
        return this;
    }

    public T getData() {
        return data;
    }

    public CloudResponse<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "CloudResponse{" +
                "errCode=" + errCode +
                ", errMessage='" + errMessage + '\'' +
                ", data=" + data +
                '}';
    }
}
