package com.heart.heartcloud.domain;

import java.util.Date;

public class CloudGoods {

    private Integer cloudGoodsId;

    private Integer cloudGoodsFileId;

    private String cloudGoodsFilePrice;

    private String cloudGoodsUrl;

    private String cloudGoodsStatus;

    private Date cloudGoodsCreateDate;

    private Date cloudGoodsUpdateDate;

    public Integer getCloudGoodsId() {
        return cloudGoodsId;
    }

    public void setCloudGoodsId(Integer cloudGoodsId) {
        this.cloudGoodsId = cloudGoodsId;
    }

    public Integer getCloudGoodsFileId() {
        return cloudGoodsFileId;
    }

    public void setCloudGoodsFileId(Integer cloudGoodsFileId) {
        this.cloudGoodsFileId = cloudGoodsFileId;
    }

    public String getCloudGoodsFilePrice() {
        return cloudGoodsFilePrice;
    }

    public void setCloudGoodsFilePrice(String cloudGoodsFilePrice) {
        this.cloudGoodsFilePrice = cloudGoodsFilePrice == null ? null : cloudGoodsFilePrice.trim();
    }

    public String getCloudGoodsUrl() {
        return cloudGoodsUrl;
    }

    public CloudGoods setCloudGoodsUrl(String cloudGoodsUrl) {
        this.cloudGoodsUrl = cloudGoodsUrl;
        return this;
    }

    public String getCloudGoodsStatus() {
        return cloudGoodsStatus;
    }

    public void setCloudGoodsStatus(String cloudGoodsStatus) {
        this.cloudGoodsStatus = cloudGoodsStatus == null ? null : cloudGoodsStatus.trim();
    }

    public Date getCloudGoodsCreateDate() {
        return cloudGoodsCreateDate;
    }

    public void setCloudGoodsCreateDate(Date cloudGoodsCreateDate) {
        this.cloudGoodsCreateDate = cloudGoodsCreateDate;
    }

    public Date getCloudGoodsUpdateDate() {
        return cloudGoodsUpdateDate;
    }

    public void setCloudGoodsUpdateDate(Date cloudGoodsUpdateDate) {
        this.cloudGoodsUpdateDate = cloudGoodsUpdateDate;
    }

    @Override
    public String toString() {
        return "CloudGoods{" +
                "cloudGoodsId=" + cloudGoodsId +
                ", cloudGoodsFileId=" + cloudGoodsFileId +
                ", cloudGoodsFilePrice='" + cloudGoodsFilePrice + '\'' +
                ", cloudGoodsUrl='" + cloudGoodsUrl + '\'' +
                ", cloudGoodsStatus='" + cloudGoodsStatus + '\'' +
                ", cloudGoodsCreateDate=" + cloudGoodsCreateDate +
                ", cloudGoodsUpdateDate=" + cloudGoodsUpdateDate +
                '}';
    }
}