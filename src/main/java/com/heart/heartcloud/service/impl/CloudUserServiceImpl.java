package com.heart.heartcloud.service.impl;

import com.heart.heartcloud.common.CloudConstants;
import com.heart.heartcloud.common.CloudErrorCodeEnums;
import com.heart.heartcloud.dao.CloudUserDao;
import com.heart.heartcloud.domain.CloudUser;
import com.heart.heartcloud.exception.CloudSystemException;
import com.heart.heartcloud.service.CloudUserService;
import com.heart.heartcloud.utils.CloudStringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: Heart
 * @Date: 2019/3/11 14:07
 */

@Service
public class CloudUserServiceImpl implements CloudUserService {

    private static final Logger logger = LoggerFactory.getLogger(CloudUserServiceImpl.class);

    @Autowired
    private CloudUserDao cloudUserDao;

    @Override
    public int saveCloudUser(CloudUser cloudUser) {
        return cloudUserDao.insert(cloudUser);
    }

    @Override
    public int removeCloudUserByPrimaryKey(Integer userId) {
        if (null == userId||CloudStringUtils.isBlank(userId.toString())) {
            logger.error("删除失败 :参数异常");
            throw new CloudSystemException(CloudErrorCodeEnums.ParamException.getCode(), CloudErrorCodeEnums.ParamException.getMsg());
        }
        return cloudUserDao.deleteByPrimaryKey(userId);
    }

    @Override
    public int editCloudUserByPrimaryKey(CloudUser cloudUser) {
        if (findCloudUserByPrimaryKey(cloudUser.getUserId()) == null) {
            logger.error("更新失败 :用户不存在");
            throw new CloudSystemException(CloudErrorCodeEnums.DuplicateUserException.getCode(), CloudErrorCodeEnums.DuplicateUserException.getMsg());
        }
        return cloudUserDao.updateByPrimaryKey(cloudUser);
    }

    @Override
    public List<CloudUser> findAllUser() {
        return cloudUserDao.selectAllUser();
    }

    @Override
    public CloudUser findCloudUserByUserName(String userName) {
        return cloudUserDao.selectByUserName(userName);
    }

    @Override
    public CloudUser findCloudUserByPrimaryKey(Integer userId) {
        if (null == userId||CloudStringUtils.isBlank(userId.toString())) {
            logger.error("查询失败 :参数异常");
            throw new CloudSystemException(CloudErrorCodeEnums.ParamException.getCode(), CloudErrorCodeEnums.ParamException.getMsg());
        }
        return cloudUserDao.selectByPrimaryKey(userId);
    }

    @Override
    public CloudUser userReg(CloudUser cloudUser) {
        if (CloudStringUtils.isAnyBlank(cloudUser.getUserName(), cloudUser.getUserPass())) {
            logger.error("注册失败 :参数异常");
            throw new CloudSystemException(CloudErrorCodeEnums.ParamException.getCode(), CloudErrorCodeEnums.ParamException.getMsg());
        }
        if (findCloudUserByUserName(cloudUser.getUserName()) != null) {
            logger.error("注册失败 :用户已存在");
            throw new CloudSystemException(CloudErrorCodeEnums.DuplicateUserException.getCode(), CloudErrorCodeEnums.DuplicateUserException.getMsg());
        }
        cloudUser.setUserId(CloudStringUtils.getId());
        cloudUser.setUserSalt(CloudStringUtils.getSalt(cloudUser.getUserName(), cloudUser.getUserPass()));
        cloudUser.setUserPass(String.valueOf(new Md5Hash(new Md5Hash(cloudUser.getUserPass(),cloudUser.getUserSalt()))));
        cloudUser.setUserStatus(CloudConstants.STATUS_YES);
        cloudUser.setUserCreateDate(new Date());
        cloudUserDao.insert(cloudUser);
        logger.info("注册成功 :cloudUser => {}", cloudUser);
        return cloudUser;
    }
}
