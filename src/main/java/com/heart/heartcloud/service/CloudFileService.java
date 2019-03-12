package com.heart.heartcloud.service;

import com.heart.heartcloud.domain.CloudFile;

/**
 * @Description:
 * @Author: Heart
 * @Date: 2019/3/11 14:02
 */
public interface CloudFileService {

    int saveCloudFile(CloudFile cloudFile);

    int removeCloudFileByPrimaryKey(Integer cloudFileId);

    int editCloudFileByPrimaryKey(CloudFile cloudFile);

    CloudFile findCloudFileByPrimaryKey(Integer cloudFileId);
}
