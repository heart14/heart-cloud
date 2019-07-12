package com.heart.heartcloud.domain;

import java.util.Date;
import java.util.List;

/**
 * @author heart
 */
public class CloudUser {

    private Integer userId;

    private String userName;

    private String userPass;

    private String userSalt;

    private String userStatus;

    private Date userCreateDate;

    private Date userUpdateDate;

    private List<CloudRole> userRoles;

    private List<CloudPermission> userPermissions;

    public CloudUser() {
    }

    public CloudUser(Integer userId, String userName, String userPass, String userSalt, String userStatus, Date userCreateDate, Date userUpdateDate, List<CloudRole> userRoles, List<CloudPermission> userPermissions) {
        this.userId = userId;
        this.userName = userName;
        this.userPass = userPass;
        this.userSalt = userSalt;
        this.userStatus = userStatus;
        this.userCreateDate = userCreateDate;
        this.userUpdateDate = userUpdateDate;
        this.userRoles = userRoles;
        this.userPermissions = userPermissions;
    }

    public Integer getUserId() {
        return userId;
    }

    public CloudUser setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public CloudUser setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserPass() {
        return userPass;
    }

    public CloudUser setUserPass(String userPass) {
        this.userPass = userPass;
        return this;
    }

    public String getUserSalt() {
        return userSalt;
    }

    public CloudUser setUserSalt(String userSalt) {
        this.userSalt = userSalt;
        return this;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public CloudUser setUserStatus(String userStatus) {
        this.userStatus = userStatus;
        return this;
    }

    public Date getUserCreateDate() {
        return userCreateDate;
    }

    public CloudUser setUserCreateDate(Date userCreateDate) {
        this.userCreateDate = userCreateDate;
        return this;
    }

    public Date getUserUpdateDate() {
        return userUpdateDate;
    }

    public CloudUser setUserUpdateDate(Date userUpdateDate) {
        this.userUpdateDate = userUpdateDate;
        return this;
    }

    public List<CloudRole> getUserRoles() {
        return userRoles;
    }

    public CloudUser setUserRoles(List<CloudRole> userRoles) {
        this.userRoles = userRoles;
        return this;
    }

    public List<CloudPermission> getUserPermissions() {
        return userPermissions;
    }

    public CloudUser setUserPermissions(List<CloudPermission> userPermissions) {
        this.userPermissions = userPermissions;
        return this;
    }

    @Override
    public String toString() {
        return "CloudUser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPass='" + userPass + '\'' +
                ", userSalt='" + userSalt + '\'' +
                ", userStatus='" + userStatus + '\'' +
                ", userCreateDate=" + userCreateDate +
                ", userUpdateDate=" + userUpdateDate +
                ", userRoles=" + userRoles +
                ", userPermissions=" + userPermissions +
                '}';
    }
}