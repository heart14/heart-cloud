package com.heart.heartcloud.dao;

import com.heart.heartcloud.domain.CloudFile;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CloudFileDao {

    /**
     * 新增文件
     *
     * @param record
     * @return
     */
    int insert(CloudFile record);

    /**
     * 删除文件（根据主键，物理删除）
     *
     * @param cloudFileId
     * @return
     */
    int deleteByPrimaryKey(Integer cloudFileId);

    /**
     * 删除文件（根据文件夹ID，物理删除）
     *
     * @param cloudDirId
     * @param cloudUserId
     * @return
     */
    int deleteByCloudDirId(@Param("cloudDirId") Integer cloudDirId, @Param("cloudUserId") Integer cloudUserId);

    /**
     * 删除文件（根据用户ID，物理删除）
     *
     * @param cloudUserId
     * @return
     */
    int deleteByCloudUserrId(Integer cloudUserId);

    /**
     * 更新文件信息（根据主键）
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(CloudFile record);

    /**
     * 查询文件（根据主键）
     *
     * @param cloudFileId
     * @return
     */
    CloudFile selectByPrimaryKey(Integer cloudFileId);

    /**
     * 查询文件（根据文件夹ID）
     *
     * @param cloudDirId
     * @return
     */
    List<CloudFile> selectByCloudDirId(@Param("cloudDirId") Integer cloudDirId, @Param("cloudUserId") Integer cloudUserId);

    /**
     * 查询文件（根据用户ID）
     *
     * @param cloudUserId
     * @return
     */
    List<CloudFile> selectByCloudUserId(Integer cloudUserId);
}