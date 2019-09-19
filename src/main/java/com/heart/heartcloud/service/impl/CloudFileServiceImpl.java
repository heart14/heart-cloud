package com.heart.heartcloud.service.impl;

import com.heart.heartcloud.common.CloudErrorCodeEnums;
import com.heart.heartcloud.dao.CloudFileDao;
import com.heart.heartcloud.domain.CloudFile;
import com.heart.heartcloud.exception.CloudSystemException;
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
            throw new CloudSystemException(CloudErrorCodeEnums.ParamException.getCode(), CloudErrorCodeEnums.ParamException.getMsg());
        }
        return cloudFileDao.selectByPrimaryKey(cloudFileId);
    }

    @Override
    public List<CloudFile> fincCloudFilesByCloudDirId(Integer cloudDirId,Integer cloudUserId) {
        if (null == cloudDirId|| CloudStringUtils.isBlank(cloudDirId.toString())) {
            logger.error("查询失败 :参数异常");
            throw new CloudSystemException(CloudErrorCodeEnums.ParamException.getCode(), CloudErrorCodeEnums.ParamException.getMsg());
        }
        return cloudFileDao.selectByCloudDirId(cloudDirId,cloudUserId);
    }

    @Override
    public List<CloudFile> findCloudFilesByCloudUserId(Integer cloudUserId) {
        return cloudFileDao.selectByCloudUserId(cloudUserId);
    }

    @Override
    public List<CloudFile> findCloudFileLikeName(String searchFileName, Integer cloudUserId) {
        if (CloudStringUtils.isBlank(searchFileName)) {
            //如果没有输入文件名，则查询该用户所有文件
            return cloudFileDao.selectByCloudUserId(cloudUserId);
        }
        //按文件名模糊查询
        String cloudFileName = "%" + searchFileName + "%";
        return cloudFileDao.selectByCloudFileNameLike(cloudFileName,cloudUserId);
    }
}
