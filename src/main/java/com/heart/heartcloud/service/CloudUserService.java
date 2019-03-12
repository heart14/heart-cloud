package com.heart.heartcloud.service;

import com.heart.heartcloud.domain.CloudUser;

import java.util.List;

/**
 * @Description:
 * @Author: Heart
 * @Date: 2019/3/11 14:01
 */
public interface CloudUserService {

    int saveCloudUser(CloudUser cloudUser);

    int removeCloudUserByPrimaryKey(Integer userId);

    int editCloudUserByPrimaryKey(CloudUser cloudUser);

    CloudUser findCloudUserByPrimaryKey(Integer userId);

    List<CloudUser> findAllUser();
}
