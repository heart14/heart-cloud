package com.heart.heartcloud.dao;

import com.heart.heartcloud.domain.CloudUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CloudUserDao {

    int insert(CloudUser record);

    int deleteByPrimaryKey(Integer userId);

    int updateByPrimaryKey(CloudUser record);

    CloudUser selectByPrimaryKey(Integer userId);

    List<CloudUser> selectAllUser();
}