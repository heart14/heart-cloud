package com.heart.heartcloud.service.impl;

import com.heart.heartcloud.common.CloudErrorCodeEnums;
import com.heart.heartcloud.dao.CloudDirDao;
import com.heart.heartcloud.domain.CloudDir;
import com.heart.heartcloud.exception.CloudException;
import com.heart.heartcloud.service.CloudDirService;
import com.heart.heartcloud.utils.CloudStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: Heart
 * @Date: 2019/3/11 14:12
 */

@Service
public class CloudDirServiceImpl implements CloudDirService {

    private static final Logger logger = LoggerFactory.getLogger(CloudDirServiceImpl.class);

    @Autowired
    private CloudDirDao cloudDirDao;

    @Override
    public int saveCloudDir(CloudDir cloudDir) {
        return cloudDirDao.insert(cloudDir);
    }

    @Override
    public int removeCloudDirByPrimaryKey(Integer cloudDirId) {
        return cloudDirDao.deleteByPrimaryKey(cloudDirId);
    }

    @Override
    public int editCloudDirByPrimaryKey(CloudDir cloudDir) {
        return cloudDirDao.updateByPrimaryKey(cloudDir);
    }

    @Override
    public CloudDir findCloudDirByPrimaryKey(Integer cloudDirId) {
        return cloudDirDao.selectByPrimaryKey(cloudDirId);
    }

    @Override
    public List<CloudDir> findCloudDirByParentId(Integer cloudDirParentId) {
        if (null == cloudDirParentId|| CloudStringUtils.isBlank(cloudDirParentId.toString())) {
            logger.error("查询失败 :参数异常");
            throw new CloudException(CloudErrorCodeEnums.ParamException.getCode(), CloudErrorCodeEnums.ParamException.getMsg());
        }
        return cloudDirDao.selectByParentId(cloudDirParentId);
    }

    @Override
    public List<CloudDir> findCloudDirsByCloudUserId(Integer cloudUserId) {
        return cloudDirDao.selectByCloudUserId(cloudUserId);
    }
}
