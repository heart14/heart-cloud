package com.heart.heartcloud.dao;

import com.heart.heartcloud.domain.CloudDir;
import org.springframework.stereotype.Repository;

@Repository
public interface CloudDirDao {

    int deleteByPrimaryKey(Integer cloudDirId);

    int insert(CloudDir record);

    CloudDir selectByPrimaryKey(Integer cloudDirId);

    int updateByPrimaryKey(CloudDir record);
}