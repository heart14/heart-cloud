package com.heart.heartcloud.domain;

/**
 * @ClassName:CloudPermission
 * @Description:
 * @Author: Heart
 * @Date: 2019/7/12 10:19
 */
public class CloudPermission {

    private Integer permissionId;

    private String permissionName;

    private String permissionDesc;

    private String permissionUrl;

    private Integer permissionParentId;

    public CloudPermission() {
    }

    public CloudPermission(Integer permissionId, String permissionName, String permissionDesc, String permissionUrl, Integer permissionParentId) {
        this.permissionId = permissionId;
        this.permissionName = permissionName;
        this.permissionDesc = permissionDesc;
        this.permissionUrl = permissionUrl;
        this.permissionParentId = permissionParentId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public CloudPermission setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
        return this;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public CloudPermission setPermissionName(String permissionName) {
        this.permissionName = permissionName;
        return this;
    }

    public String getPermissionDesc() {
        return permissionDesc;
    }

    public CloudPermission setPermissionDesc(String permissionDesc) {
        this.permissionDesc = permissionDesc;
        return this;
    }

    public String getPermissionUrl() {
        return permissionUrl;
    }

    public CloudPermission setPermissionUrl(String permissionUrl) {
        this.permissionUrl = permissionUrl;
        return this;
    }

    public Integer getPermissionParentId() {
        return permissionParentId;
    }

    public CloudPermission setPermissionParentId(Integer permissionParentId) {
        this.permissionParentId = permissionParentId;
        return this;
    }

    @Override
    public String toString() {
        return "CloudPermission{" +
                "permissionId=" + permissionId +
                ", permissionName='" + permissionName + '\'' +
                ", permissionDesc='" + permissionDesc + '\'' +
                ", permissionUrl='" + permissionUrl + '\'' +
                ", permissionParentId=" + permissionParentId +
                '}';
    }
}
