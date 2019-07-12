package com.heart.heartcloud.dao;

import com.heart.heartcloud.domain.CloudUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CloudUserDao {

    /**
     * 新增用户
     *
     * @param record
     * @return
     */
    int insert(CloudUser record);

    /**
     * 删除用户（物理删除）
     *
     * @param userId
     * @return
     */
    int deleteByPrimaryKey(Integer userId);

    /**
     * 更新用户
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(CloudUser record);

    /**
     * 查询用户（根据主键）
     *
     * @param userId
     * @return
     */
    CloudUser selectByPrimaryKey(Integer userId);

    /**
     * 查询用户（根据用户名）
     *
     * @param userName
     * @return
     */
    CloudUser selectByUserName(String userName);

    /**
     * 查询所有用户
     *
     * @return
     */
    List<CloudUser> selectAllUser();
}