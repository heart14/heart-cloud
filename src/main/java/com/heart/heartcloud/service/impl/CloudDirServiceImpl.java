package com.heart.heartcloud.service.impl;

import com.heart.heartcloud.dao.CloudDirDao;
import com.heart.heartcloud.domain.CloudDir;
import com.heart.heartcloud.service.CloudDirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: Heart
 * @Date: 2019/3/11 14:12
 */

@Service
public class CloudDirServiceImpl implements CloudDirService {

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
}
