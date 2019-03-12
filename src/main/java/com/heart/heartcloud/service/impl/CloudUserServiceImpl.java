package com.heart.heartcloud.service.impl;

import com.heart.heartcloud.dao.CloudUserDao;
import com.heart.heartcloud.domain.CloudUser;
import com.heart.heartcloud.service.CloudUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: Heart
 * @Date: 2019/3/11 14:07
 */

@Service
public class CloudUserServiceImpl implements CloudUserService {

    @Autowired
    private CloudUserDao cloudUserDao;

    @Override
    public int saveCloudUser(CloudUser cloudUser) {
        return cloudUserDao.insert(cloudUser);
    }

    @Override
    public int removeCloudUserByPrimaryKey(Integer userId) {
        return cloudUserDao.deleteByPrimaryKey(userId);
    }

    @Override
    public int editCloudUserByPrimaryKey(CloudUser cloudUser) {
        return cloudUserDao.updateByPrimaryKey(cloudUser);
    }

    @Override
    public List<CloudUser> findAllUser() {
        return cloudUserDao.selectAllUser();
    }

    @Override
    public CloudUser findCloudUserByPrimaryKey(Integer userId) {
        return cloudUserDao.selectByPrimaryKey(userId);
    }
}
