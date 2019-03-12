package com.heart.heartcloud.dao;

import com.heart.heartcloud.domain.CloudUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CloudUserDao {

    int deleteByPrimaryKey(Integer userId);

    int insert(CloudUser record);

    CloudUser selectByPrimaryKey(Integer userId);

    List<CloudUser> selectAllUser();

    int updateByPrimaryKey(CloudUser record);
}