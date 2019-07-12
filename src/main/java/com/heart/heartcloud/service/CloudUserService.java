package com.heart.heartcloud.service;

import com.heart.heartcloud.domain.CloudUser;

import java.util.List;

/**
 * @Description:
 * @Author: Heart
 * @Date: 2019/3/11 14:01
 */
public interface CloudUserService {

    /**
     * 新增用户
     *
     * @param cloudUser
     * @return
     */
    int saveCloudUser(CloudUser cloudUser);

    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    int removeCloudUserByPrimaryKey(Integer userId);

    /**
     * 更新用户
     *
     * @param cloudUser
     * @return
     */
    int editCloudUserByPrimaryKey(CloudUser cloudUser);

    /**
     * 查询用户（根据主键）
     *
     * @param userId
     * @return
     */
    CloudUser findCloudUserByPrimaryKey(Integer userId);

    /**
     * 查询用户（根据用户名）
     *
     * @param userName
     * @return
     */
    CloudUser findCloudUserByUserName(String userName);

    /**
     * 查询所有用户
     *
     * @return
     */
    List<CloudUser> findAllUser();
}
