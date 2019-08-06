package com.heart.heartcloud.service.impl;

import com.heart.heartcloud.dao.CloudGoodsDao;
import com.heart.heartcloud.domain.CloudGoods;
import com.heart.heartcloud.service.CloudGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: CloudGoodsServiceImpl
 * @Description:
 * @Author: Heart
 * @Date: 2019/8/2 15:45
 */
@Service
public class CloudGoodsServiceImpl implements CloudGoodsService {

    @Autowired
    private CloudGoodsDao cloudGoodsDao;

    @Override
    public int saveCloudGoods(CloudGoods cloudGoods) {
        return cloudGoodsDao.insertSelective(cloudGoods);
    }

    @Override
    public int removeCloudGoodsByPrimaryKey(Integer cloudGoodsId) {
        return cloudGoodsDao.deleteByPrimaryKey(cloudGoodsId);
    }

    @Override
    public int editCloudGoodsByPrimaryKey(CloudGoods cloudGoods) {
        return cloudGoodsDao.updateByPrimaryKeySelective(cloudGoods);
    }

    @Override
    public CloudGoods findCloudGoodsByPrimaryKey(Integer cloudGoodsId) {
        return cloudGoodsDao.selectByPrimaryKey(cloudGoodsId);
    }

    @Override
    public CloudGoods findCloudGoodsByCloudFileId(Integer cloudFileId) {
        return cloudGoodsDao.selectByCloudFileId(cloudFileId);
    }

    @Override
    public List<CloudGoods> findAllCloudGoods() {
        return cloudGoodsDao.selectAllCloudGoods();
    }
}
