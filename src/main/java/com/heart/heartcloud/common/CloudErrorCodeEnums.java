package com.heart.heartcloud.common;

/**
 * @ClassName:CloudErrorCodeEnums
 * @Description:
 * @Author: Heart
 * @Date: 2019/7/12 13:01
 */
public enum CloudErrorCodeEnums {

    /**
     * 成功，ErrorCode :7000
     */
    SUCCESS(7000, "成功"),

    /**
     * 系统异常，ErrorCode :7001
     */
    SystemException(7001, "系统异常"),

    /**
     * 未知异常，ErrorCode :7002
     */
    UnknownException(7002, "未知异常"),

    /**
     * 服务异常，ErrorCode :7003
     */
    ServiceException(7003, "服务异常"),

    /**
     * 数据库操作异常，ErrorCode :7004
     */
    DBException(7004, "数据库操作异常"),

    /**
     * 参数异常，ErrorCode :7005
     */
    ParamException(7005, "参数异常");

    private Integer code;

    private String msg;

    CloudErrorCodeEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public CloudErrorCodeEnums setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public CloudErrorCodeEnums setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
