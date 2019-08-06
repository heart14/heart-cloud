package com.heart.heartcloud.domain;

import java.util.Date;

public class CloudFile {

    private Integer cloudFileId;

    private String cloudFileName;

    private String cloudFileSize;

    private Date cloudFileCreateDate;

    private Date cloudFileUpdateDate;

    private String cloudFileType;

    private String cloudFileStatus;

    private String cloudFileUrl;

    private String cloudFileSellFlag = "0";

    private String cloudFileSellPrice;

    private Integer cloudFileDirId;

    private Integer cloudFileUserId;

    public Integer getCloudFileId() {
        return cloudFileId;
    }

    public void setCloudFileId(Integer cloudFileId) {
        this.cloudFileId = cloudFileId;
    }

    public String getCloudFileName() {
        return cloudFileName;
    }

    public void setCloudFileName(String cloudFileName) {
        this.cloudFileName = cloudFileName == null ? null : cloudFileName.trim();
    }

    public String getCloudFileSize() {
        return cloudFileSize;
    }

    public void setCloudFileSize(String cloudFileSize) {
        this.cloudFileSize = cloudFileSize == null ? null : cloudFileSize.trim();
    }

    public Date getCloudFileCreateDate() {
        return cloudFileCreateDate;
    }

    public void setCloudFileCreateDate(Date cloudFileCreateDate) {
        this.cloudFileCreateDate = cloudFileCreateDate;
    }

    public Date getCloudFileUpdateDate() {
        return cloudFileUpdateDate;
    }

    public void setCloudFileUpdateDate(Date cloudFileUpdateDate) {
        this.cloudFileUpdateDate = cloudFileUpdateDate;
    }

    public String getCloudFileType() {
        return cloudFileType;
    }

    public void setCloudFileType(String cloudFileType) {
        this.cloudFileType = cloudFileType == null ? null : cloudFileType.trim();
    }

    public String getCloudFileStatus() {
        return cloudFileStatus;
    }

    public void setCloudFileStatus(String cloudFileStatus) {
        this.cloudFileStatus = cloudFileStatus == null ? null : cloudFileStatus.trim();
    }

    public String getCloudFileUrl() {
        return cloudFileUrl;
    }

    public void setCloudFileUrl(String cloudFileUrl) {
        this.cloudFileUrl = cloudFileUrl == null ? null : cloudFileUrl.trim();
    }

    public String getCloudFileSellFlag() {
        return cloudFileSellFlag;
    }

    public CloudFile setCloudFileSellFlag(String cloudFileSellFlag) {
        this.cloudFileSellFlag = cloudFileSellFlag;
        return this;
    }

    public String getCloudFileSellPrice() {
        return cloudFileSellPrice;
    }

    public CloudFile setCloudFileSellPrice(String cloudFileSellPrice) {
        this.cloudFileSellPrice = cloudFileSellPrice;
        return this;
    }

    public Integer getCloudFileDirId() {
        return cloudFileDirId;
    }

    public void setCloudFileDirId(Integer cloudFileDirId) {
        this.cloudFileDirId = cloudFileDirId;
    }

    public Integer getCloudFileUserId() {
        return cloudFileUserId;
    }

    public void setCloudFileUserId(Integer cloudFileUserId) {
        this.cloudFileUserId = cloudFileUserId;
    }

    @Override
    public String toString() {
        return "CloudFile{" +
                "cloudFileId=" + cloudFileId +
                ", cloudFileName='" + cloudFileName + '\'' +
                ", cloudFileSize='" + cloudFileSize + '\'' +
                ", cloudFileCreateDate=" + cloudFileCreateDate +
                ", cloudFileUpdateDate=" + cloudFileUpdateDate +
                ", cloudFileType='" + cloudFileType + '\'' +
                ", cloudFileStatus='" + cloudFileStatus + '\'' +
                ", cloudFileUrl='" + cloudFileUrl + '\'' +
                ", cloudFileSellFlag='" + cloudFileSellFlag + '\'' +
                ", cloudFileSellPrice='" + cloudFileSellPrice + '\'' +
                ", cloudFileDirId=" + cloudFileDirId +
                ", cloudFileUserId=" + cloudFileUserId +
                '}';
    }
}


