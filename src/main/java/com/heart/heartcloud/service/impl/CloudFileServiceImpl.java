package com.heart.heartcloud.service.impl;

import com.heart.heartcloud.dao.CloudFileDao;
import com.heart.heartcloud.domain.CloudFile;
import com.heart.heartcloud.service.CloudFileService;
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
        return cloudFileDao.selectByPrimaryKey(cloudFileId);
    }

    @Override
    public List<CloudFile> fincCloudFilesByCloudDirId(Integer cloudDirId) {
        return cloudFileDao.selectByCloudDirId(cloudDirId);
    }

    @Override
    public List<CloudFile> findCloudFilesByCloudUserId(Integer cloudUserId) {
        return cloudFileDao.selectByCloudUserId(cloudUserId);
    }
}
