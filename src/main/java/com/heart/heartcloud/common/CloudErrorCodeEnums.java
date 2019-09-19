package com.heart.heartcloud.common;

/**
 * @ClassName: CloudErrorCodeEnums
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
     * 用户登录已过期，ErrorCode :7005
     */
    LoginExpiredException(7005, "用户登录已过期"),

    /**
     * 管理员用户登录，ErrorCode :7006
     */
    ManagerLoginSuccess(7006, "管理员用户登录"),

    /**
     * 图床用户登录，ErrorCode :7007
     */
    ImageBedLoginSuccess(7007, "图床用户登录"),

    /**
     * 注册失败，用户已存在，ErrorCode :7011
     */
    DuplicateUserException(7011, "注册失败，用户已存在"),

    /**
     * 参数异常，ErrorCode :7012
     */
    ParamException(7012, "参数异常"),

    /**
     * 定时任务异常，SchedulerException :7014
     */
    SchedulerException(7014, "定时任务异常"),

    /**
     * 文件不存在，ErrorCode :7013
     */
    UnknownFileException(7013, "文件不存在");

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
