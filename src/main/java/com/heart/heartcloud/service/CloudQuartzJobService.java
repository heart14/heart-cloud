package com.heart.heartcloud.service;

import com.heart.heartcloud.entity.CloudQuartzJob;

/**
 * @ClassName: CloudQuartzJobService
 * @Description: TODO
 * @Author: jayhe
 * @Date: 2019/9/19 10:57
 * @Version: v1.0
 */
public interface CloudQuartzJobService {

    int saveCloudQuartzJob(CloudQuartzJob cloudQuartzJob);

    int saveCloudQuartzJobSelective(CloudQuartzJob cloudQuartzJob);

    int removeCloudQuartzJobByPrimaryKey(String jobId);

    int editCloudQuartzJobByPrimaryKeySelective(CloudQuartzJob cloudQuartzJob);

    int editCloudQuartzJobByPrimaryKey(CloudQuartzJob cloudQuartzJob);

    CloudQuartzJob findCloudQuartzJobByPrimaryKey(String jobId);
}
