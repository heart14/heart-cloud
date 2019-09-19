package com.heart.heartcloud.dao;

import com.heart.heartcloud.entity.CloudQuartzJob;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: CloudQuartzJobDao
 * @Description: TODO
 * @Author: jayhe
 * @Date: 2019/9/19 10:39
 * @Version: v1.0
 */

@Repository
public interface CloudQuartzJobDao {

    int insertCloudQuartzJob(CloudQuartzJob cloudQuartzJob);

    int insertCloudQuartzJobSelective(CloudQuartzJob cloudQuartzJob);

    int deleteCloudQuartzJobByPrimaryKey(String jobId);

    int updateCloudQuartzJobByPrimaryKeySelective(CloudQuartzJob cloudQuartzJob);

    int updateCloudQuartzJobByPrimaryKey(CloudQuartzJob cloudQuartzJob);

    CloudQuartzJob selectCloudQuartzJobByPrimaryKey(String jobId);

}
