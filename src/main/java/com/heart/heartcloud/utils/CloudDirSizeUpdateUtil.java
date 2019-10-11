package com.heart.heartcloud.utils;

import com.heart.heartcloud.common.CloudErrorCodeEnums;
import com.heart.heartcloud.domain.CloudDir;
import com.heart.heartcloud.exception.CloudSystemException;
import com.heart.heartcloud.service.CloudDirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName: CloudDirSizeUpdateUtil
 * @Description: TODO
 * @Author: jayhe
 * @Date: 2019/10/11 17:35
 * @Version: v1.0
 */
@Component
public class CloudDirSizeUpdateUtil {

    @Autowired
    private CloudDirService cloudDirService;

    public void recursiveUpdateParentDirSize(Integer cloudDirId, long addSize) {
            CloudDir cloudDirByPrimaryKey = cloudDirService.findCloudDirByPrimaryKey(cloudDirId);
            if (cloudDirByPrimaryKey != null) {
                long cloudDirSize = Long.parseLong(cloudDirByPrimaryKey.getCloudDirSize());
                cloudDirByPrimaryKey.setCloudDirSize(String.valueOf(cloudDirSize + addSize));
                cloudDirByPrimaryKey.setCloudDirUpdateDate(new Date());
                cloudDirService.editCloudDirByPrimaryKey(cloudDirByPrimaryKey);
                if (cloudDirByPrimaryKey.getCloudDirParentId() != 0) {
                    recursiveUpdateParentDirSize(cloudDirByPrimaryKey.getCloudDirParentId(), addSize);
                }
            }
    }
}
