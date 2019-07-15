package com.heart.heartcloud.service.impl;

import com.heart.heartcloud.common.CloudErrorCodeEnums;
import com.heart.heartcloud.dao.CloudFileDao;
import com.heart.heartcloud.domain.CloudFile;
import com.heart.heartcloud.exception.CloudException;
import com.heart.heartcloud.service.CloudFileService;
import com.heart.heartcloud.utils.CloudStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: Heart
 * @Date: 2019/3/11 14:10
 */

@Service
public class CloudFileServiceImpl implements CloudFileService {

    private static final Logger logger = LoggerFactory.getLogger(CloudFileServiceImpl.class);

    @Autowired
    private CloudFileDao cloudFileDao;

    @Override
    public int saveCloudFile(CloudFile cloudFile) {
        return cloudFileDao.insert(cloudFile);
    }

    @Override
    public int removeCloudFileByPrimaryKey(Integer cloudFileId) {
        return cloudFileDao.deleteByPrimaryKey(cloudFileId);
    }

    @Override
    public int editCloudFileByPrimaryKey(CloudFile cloudFile) {
        return cloudFileDao.updateByPrimaryKey(cloudFile);
    }

    @Override
    public CloudFile findCloudFileByPrimaryKey(Integer cloudFileId) {
        if (null == cloudFileId|| CloudStringUtils.isBlank(cloudFileId.toString())) {
            logger.error("查询失败 :参数异常");
            throw new CloudException(CloudErrorCodeEnums.ParamException.getCode(), CloudErrorCodeEnums.ParamException.getMsg());
        }
        return cloudFileDao.selectByPrimaryKey(cloudFileId);
    }

    @Override
    public List<CloudFile> fincCloudFilesByCloudDirId(Integer cloudDirId,Integer cloudUserId) {
        if (null == cloudDirId|| CloudStringUtils.isBlank(cloudDirId.toString())) {
            logger.error("查询失败 :参数异常");
            throw new CloudException(CloudErrorCodeEnums.ParamException.getCode(), CloudErrorCodeEnums.ParamException.getMsg());
        }
        return cloudFileDao.selectByCloudDirId(cloudDirId,cloudUserId);
    }

    @Override
    public List<CloudFile> findCloudFilesByCloudUserId(Integer cloudUserId) {
        return cloudFileDao.selectByCloudUserId(cloudUserId);
    }
}
