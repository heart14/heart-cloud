package com.heart.heartcloud.dao;

import com.heart.heartcloud.domain.CloudDir;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CloudDirDao {

    /**
     * 新增文件夹
     * @param record
     * @return
     */
    int insert(CloudDir record);

    /**
     * 删除文件夹（根据主键，物理删除）
     * @param cloudDirId
     * @return
     */
    int deleteByPrimaryKey(Integer cloudDirId);

    /**
     * 更新文件夹信息（根据主键）
     * @param record
     * @return
     */
    int updateByPrimaryKey(CloudDir record);

    /**
     * 查询文件夹（根据主键）
     * @param cloudDirId
     * @return
     */
    CloudDir selectByPrimaryKey(Integer cloudDirId);

    /**
     * 查询文件夹（根据文件夹名）
     * @param cloudDirName
     * @return
     */
    CloudDir selectByDirName(String cloudDirName);

    /**
     * 查询子文件夹（根据父文件夹ID）
     * @param cloudDirParentId
     * @return
     */
    List<CloudDir> selectByParentId(Integer cloudDirParentId,Integer cloudUserId);

    /**
     * 查询文件夹（根据用户ID）
     * @param cloudUserId
     * @return
     */
    List<CloudDir> selectByCloudUserId(Integer cloudUserId);
}