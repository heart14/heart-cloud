package com.ums.itms.itas.webservice;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;
//自定义返回结果类
@Component
@JsonIgnoreProperties(ignoreUnknown=true)
public class ResultData implements Serializable {

    private static final long serialVersionUID = 1L;

    private String appId;     //应用ID

    private String requestId;  //请求Id

    private String returnCode; //处理结果码

    private String reason;     //失败原因

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId == null ? null : requestId.trim();
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode == null ? null : returnCode.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

}