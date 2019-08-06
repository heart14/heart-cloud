package com.heart.heartcloud.service;

import com.heart.heartcloud.domain.CloudGoods;

import java.util.List;

/**
 * @ClassName: CloudGoodsService
 * @Description:
 * @Author: Heart
 * @Date: 2019/8/2 15:45
 */
public interface CloudGoodsService {

    /**
     * 插入CloudGoods记录
     *
     * @param cloudGoods
     * @return
     */
    int saveCloudGoods(CloudGoods cloudGoods);

    /**
     * 删除CloudGoods记录（根据主键）
     *
     * @param cloudGoodsId
     * @return
     */
    int removeCloudGoodsByPrimaryKey(Integer cloudGoodsId);

    /**
     * 更新（根据主键）
     */
    int editCloudGoodsByPrimaryKey(CloudGoods cloudGoods);

    /**
     * 查询（根据主键）
     *
     * @param cloudGoodsId
     * @return
     */
    CloudGoods findCloudGoodsByPrimaryKey(Integer cloudGoodsId);

    /**
     * 查询（根据商品ID/文件ID）
     *
     * @param cloudFileId
     * @return
     */
    CloudGoods findCloudGoodsByCloudFileId(Integer cloudFileId);

    /**
     * 查询所有已上架商品
     *
     * @return
     */
    List<CloudGoods> findAllCloudGoods();
}
