package com.heart.heartcloud.domain;

import java.util.Date;

public class CloudDir {

    private Integer cloudDirId;

    private String cloudDirName;

    private String cloudDirSize;

    private Date cloudDirCreateDate;

    private Date cloudDirUpdateDate;

    private String cloudDirStatus;

    private Integer cloudDirParentId;

    private Integer cloudDirUserId;

    public Integer getCloudDirId() {
        return cloudDirId;
    }

    public void setCloudDirId(Integer cloudDirId) {
        this.cloudDirId = cloudDirId;
    }

    public String getCloudDirName() {
        return cloudDirName;
    }

    public void setCloudDirName(String cloudDirName) {
        this.cloudDirName = cloudDirName;
    }

    public String getCloudDirSize() {
        return cloudDirSize;
    }

    public void setCloudDirSize(String cloudDirSize) {
        this.cloudDirSize = cloudDirSize;
    }

    public Date getCloudDirCreateDate() {
        return cloudDirCreateDate;
    }

    public void setCloudDirCreateDate(Date cloudDirCreateDate) {
        this.cloudDirCreateDate = cloudDirCreateDate;
    }

    public Date getCloudDirUpdateDate() {
        return cloudDirUpdateDate;
    }

    public void setCloudDirUpdateDate(Date cloudDirUpdateDate) {
        this.cloudDirUpdateDate = cloudDirUpdateDate;
    }

    public String getCloudDirStatus() {
        return cloudDirStatus;
    }

    public void setCloudDirStatus(String cloudDirStatus) {
        this.cloudDirStatus = cloudDirStatus;
    }

    public Integer getCloudDirParentId() {
        return cloudDirParentId;
    }

    public void setCloudDirParentId(Integer cloudDirParentId) {
        this.cloudDirParentId = cloudDirParentId;
    }

    public Integer getCloudDirUserId() {
        return cloudDirUserId;
    }

    public void setCloudDirUserId(Integer cloudDirUserId) {
        this.cloudDirUserId = cloudDirUserId;
    }

    @Override
    public String toString() {
        return "CloudDir{" +
                "cloudDirId=" + cloudDirId +
                ", cloudDirName='" + cloudDirName + '\'' +
                ", cloudDirSize='" + cloudDirSize + '\'' +
                ", cloudDirCreateDate=" + cloudDirCreateDate +
                ", cloudDirUpdateDate=" + cloudDirUpdateDate +
                ", cloudDirStatus='" + cloudDirStatus + '\'' +
                ", cloudDirParentId=" + cloudDirParentId +
                ", cloudDirUserId=" + cloudDirUserId +
                '}';
    }
}