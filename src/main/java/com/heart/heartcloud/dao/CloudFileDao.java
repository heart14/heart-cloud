package com.heart.heartcloud.dao;

import com.heart.heartcloud.domain.CloudFile;
import org.springframework.stereotype.Repository;

@Repository
public interface CloudFileDao {
    int deleteByPrimaryKey(Integer cloudFileId);

    int insert(CloudFile record);

    CloudFile selectByPrimaryKey(Integer cloudFileId);

    int updateByPrimaryKey(CloudFile record);
}