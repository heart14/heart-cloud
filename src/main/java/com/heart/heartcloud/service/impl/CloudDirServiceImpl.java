package com.heart.heartcloud.service.impl;

import com.heart.heartcloud.common.CloudConstants;
import com.heart.heartcloud.common.CloudErrorCodeEnums;
import com.heart.heartcloud.dao.CloudDirDao;
import com.heart.heartcloud.dao.CloudFileDao;
import com.heart.heartcloud.domain.CloudDir;
import com.heart.heartcloud.exception.CloudSystemException;
import com.heart.heartcloud.service.CloudDirService;
import com.heart.heartcloud.utils.CloudStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: Heart
 * @Date: 2019/3/11 14:12
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class CloudDirServiceImpl implements CloudDirService {

    private static final Logger logger = LoggerFactory.getLogger(CloudDirServiceImpl.class);

    @Autowired
    private CloudDirDao cloudDirDao;

    @Autowired
    private CloudFileDao cloudFileDao;

    @Override
    public int saveCloudDir(CloudDir cloudDir) {
        if (null == cloudDir.getCloudDirParentId() || CloudStringUtils.isBlank(cloudDir.getCloudDirName())) {
            logger.error("保存失败 :参数异常");
            throw new CloudSystemException(CloudErrorCodeEnums.ParamException.getCode(), CloudErrorCodeEnums.ParamException.getMsg());
        }
        if (cloudDirDao.selectByDirName(cloudDir.getCloudDirName()) != null) {
            logger.error("保存失败 :文件夹已存在");
            throw new CloudSystemException(CloudErrorCodeEnums.ServiceException.getCode(), CloudErrorCodeEnums.ServiceException.getMsg());
        }
        cloudDir.setCloudDirId(CloudStringUtils.getId());
        cloudDir.setCloudDirStatus(CloudConstants.STATUS_YES);
        cloudDir.setCloudDirSize(String.valueOf(0L));
        cloudDir.setCloudDirCreateDate(new Date());
        cloudDir.setCloudDirUpdateDate(new Date());
        return cloudDirDao.insert(cloudDir);
    }

    @Override
    public int removeCloudDirByPrimaryKey(Integer cloudDirId, Integer cloudUserId) {
        List<CloudDir> childDirs = cloudDirDao.selectByParentId(cloudDirId, cloudUserId);
        if (childDirs != null && childDirs.size() > 0) {
            for (CloudDir childDir : childDirs) {
                cloudDirDao.deleteByPrimaryKey(cloudDirId);
                cloudFileDao.deleteByCloudDirId(cloudDirId, cloudUserId);
                removeCloudDirByPrimaryKey(childDir.getCloudDirId(), cloudUserId);
            }
        } else {
            cloudDirDao.deleteByPrimaryKey(cloudDirId);
            cloudFileDao.deleteByCloudDirId(cloudDirId, cloudUserId);
        }
        return 0;
    }

    @Override
    public int editCloudDirByPrimaryKey(CloudDir cloudDir) {
        return cloudDirDao.updateByPrimaryKey(cloudDir);
    }

    @Override
    public CloudDir findCloudDirByPrimaryKey(Integer cloudDirId) {
        if (null == cloudDirId || CloudStringUtils.isBlank(cloudDirId.toString())) {
            logger.error("查询失败 :参数异常");
            throw new CloudSystemException(CloudErrorCodeEnums.ParamException.getCode(), CloudErrorCodeEnums.ParamException.getMsg());
        }
        return cloudDirDao.selectByPrimaryKey(cloudDirId);
    }

    @Override
    public List<CloudDir> findCloudDirByParentId(Integer cloudDirParentId, Integer cloudUserId) {
        if (null == cloudDirParentId || CloudStringUtils.isBlank(cloudDirParentId.toString())) {
            logger.error("查询失败 :参数异常");
            throw new CloudSystemException(CloudErrorCodeEnums.ParamException.getCode(), CloudErrorCodeEnums.ParamException.getMsg());
        }
        return cloudDirDao.selectByParentId(cloudDirParentId, cloudUserId);
    }

    @Override
    public List<CloudDir> findCloudDirsByCloudUserId(Integer cloudUserId) {
        return cloudDirDao.selectByCloudUserId(cloudUserId);
    }
}
