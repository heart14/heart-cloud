package com.heart.heartcloud.domain;

/**
 * @ClassName:CloudRole
 * @Description:
 * @Author: Heart
 * @Date: 2019/7/12 10:19
 */
public class CloudRole {

    private Integer roleId;

    private String roleName;

    private String roleDesc;

    public CloudRole() {
    }

    public CloudRole(Integer roleId, String roleName, String roleDesc) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleDesc = roleDesc;
    }

    @Override
    public String toString() {
        return "CloudRole{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleDesc='" + roleDesc + '\'' +
                '}';
    }

    public Integer getRoleId() {
        return roleId;
    }

    public CloudRole setRoleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }

    public String getRoleName() {
        return roleName;
    }

    public CloudRole setRoleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public CloudRole setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
        return this;
    }
}
