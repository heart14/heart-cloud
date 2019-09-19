package com.heart.heartcloud.service.impl;

import com.heart.heartcloud.common.CloudErrorCodeEnums;
import com.heart.heartcloud.dao.CloudQuartzJobDao;
import com.heart.heartcloud.entity.CloudQuartzJob;
import com.heart.heartcloud.exception.CloudException;
import com.heart.heartcloud.exception.CloudSchedulerException;
import com.heart.heartcloud.service.CloudQuartzJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: CloudQuartzJobServiceImpl
 * @Description: TODO
 * @Author: jayhe
 * @Date: 2019/9/19 10:58
 * @Version: v1.0
 */
@Service
public class CloudQuartzJobServiceImpl implements CloudQuartzJobService {

    @Autowired
    private CloudQuartzJobDao cloudQuartzJobDao;

    @Override
    public int saveCloudQuartzJob(CloudQuartzJob cloudQuartzJob) {
        return cloudQuartzJobDao.insertCloudQuartzJob(cloudQuartzJob);
    }

    @Override
    public int saveCloudQuartzJobSelective(CloudQuartzJob cloudQuartzJob) {
        return cloudQuartzJobDao.insertCloudQuartzJobSelective(cloudQuartzJob);
    }

    @Override
    public int removeCloudQuartzJobByPrimaryKey(String jobId) {
        return cloudQuartzJobDao.deleteCloudQuartzJobByPrimaryKey(jobId);
    }

    @Override
    public int editCloudQuartzJobByPrimaryKeySelective(CloudQuartzJob cloudQuartzJob) {
        return cloudQuartzJobDao.updateCloudQuartzJobByPrimaryKeySelective(cloudQuartzJob);
    }

    @Override
    public int editCloudQuartzJobByPrimaryKey(CloudQuartzJob cloudQuartzJob) {
        return cloudQuartzJobDao.updateCloudQuartzJobByPrimaryKey(cloudQuartzJob);
    }

    @Override
    public CloudQuartzJob findCloudQuartzJobByPrimaryKey(String jobId) {
        CloudQuartzJob cloudQuartzJob = cloudQuartzJobDao.selectCloudQuartzJobByPrimaryKey(jobId);
        if (cloudQuartzJob == null) {
            throw new CloudSchedulerException(CloudErrorCodeEnums.SchedulerException.getCode(), CloudErrorCodeEnums.SchedulerException.getMsg());
        }
        return cloudQuartzJob;
    }
}
