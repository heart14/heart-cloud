package com.heart.heartcloud.service;

import com.heart.heartcloud.domain.CloudDir;

import java.util.List;

/**
 * @Description:
 * @Author: Heart
 * @Date: 2019/3/11 14:02
 */
public interface CloudDirService {

    /**
     * 新增文件夹
     *
     * @param cloudDir
     * @return
     */
    int saveCloudDir(CloudDir cloudDir);

    /**
     * 删除文件夹（物理删除）
     *
     * @param cloudDirId
     * @return
     */
    int removeCloudDirByPrimaryKey(Integer cloudDirId);

    /**
     * 修改文件夹信息（根据主键）
     *
     * @param cloudDir
     * @return
     */
    int editCloudDirByPrimaryKey(CloudDir cloudDir);

    /**
     * 查询文件夹信息（根据主键）
     *
     * @param cloudDirId
     * @return
     */
    CloudDir findCloudDirByPrimaryKey(Integer cloudDirId);

    /**
     * 查询文件夹信息（根据父文件夹ID）
     *
     * @param cloudDirParentId
     * @return
     */
    List<CloudDir> findCloudDirByParentId(Integer cloudDirParentId);

    /**
     * 查询用戶文件夹（根据用户ID）
     *
     * @param cloudUserId
     * @return
     */
    List<CloudDir> findCloudDirsByCloudUserId(Integer cloudUserId);

}
