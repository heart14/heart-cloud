package com.heart.heartcloud.service;

import com.heart.heartcloud.domain.CloudDir;

/**
 * @Description:
 * @Author: Heart
 * @Date: 2019/3/11 14:02
 */
public interface CloudDirService {

    int saveCloudDir(CloudDir cloudDir);

    int removeCloudDirByPrimaryKey(Integer cloudDirId);

    int editCloudDirByPrimaryKey(CloudDir cloudDir);

    CloudDir findCloudDirByPrimaryKey(Integer cloudDirId);

}
