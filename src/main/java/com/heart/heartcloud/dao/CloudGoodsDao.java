package com.heart.heartcloud.dao;

import com.heart.heartcloud.domain.CloudGoods;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CloudGoodsDao {

    /**
     * 插入CloudGoods记录
     *
     * @param record
     * @return
     */
    int insertSelective(CloudGoods record);

    /**
     * 删除CloudGoods记录（根据主键）
     *
     * @param cloudGoodsId
     * @return
     */
    int deleteByPrimaryKey(Integer cloudGoodsId);

    /**
     * 更新（根据主键）
     */
    int updateByPrimaryKeySelective(CloudGoods record);

    /**
     * 查询（根据主键）
     *
     * @param cloudGoodsId
     * @return
     */
    CloudGoods selectByPrimaryKey(Integer cloudGoodsId);

    /**
     * 查询（根据商品ID/文件ID）
     *
     * @param cloudFileId
     * @return
     */
    CloudGoods selectByCloudFileId(Integer cloudFileId);

    /**
     * 查询所有已上架商品
     *
     * @return
     */
    List<CloudGoods> selectAllCloudGoods();

}