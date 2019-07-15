package com.heart.heartcloud.service;

import com.heart.heartcloud.domain.CloudFile;

import java.util.List;

/**
 * @Description:
 * @Author: Heart
 * @Date: 2019/3/11 14:02
 */
public interface CloudFileService {

    /**
     * 新增文件
     *
     * @param cloudFile
     * @return
     */
    int saveCloudFile(CloudFile cloudFile);

    /**
     * 删除文件
     *
     * @param cloudFileId
     * @return
     */
    int removeCloudFileByPrimaryKey(Integer cloudFileId);

    /**
     * 更新文件
     *
     * @param cloudFile
     * @return
     */
    int editCloudFileByPrimaryKey(CloudFile cloudFile);

    /**
     * 查询文件（根据文件ID）
     *
     * @param cloudFileId
     * @return
     */
    CloudFile findCloudFileByPrimaryKey(Integer cloudFileId);

    /**
     * 查询文件（根据文件夹ID）
     *
     * @param cloudDirId
     * @return
     */
    List<CloudFile> fincCloudFilesByCloudDirId(Integer cloudDirId, Integer cloudUserId);

    /**
     * 查询文件（根据用户ID）
     *
     * @param cloudUserId
     * @return
     */
    List<CloudFile> findCloudFilesByCloudUserId(Integer cloudUserId);
}
