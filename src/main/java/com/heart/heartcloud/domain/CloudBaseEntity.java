package com.heart.heartcloud.domain;

import java.util.Date;

/**
 * @ClassName:CloudBaseEntity
 * @Description:
 * @Author: Heart
 * @Date: 2019/7/12 10:22
 */
public class CloudBaseEntity {

    private Date createTime;

    private Date updateTime;

    @Override
    public String toString() {
        return "CloudBaseEntity{" +
                "createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Date getCreateTime() {
        return createTime;
    }

    public CloudBaseEntity setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public CloudBaseEntity setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
