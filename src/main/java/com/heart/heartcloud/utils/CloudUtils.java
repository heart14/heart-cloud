package com.heart.heartcloud.utils;

import com.heart.heartcloud.common.CloudErrorCodeEnums;
import com.heart.heartcloud.domain.CloudDir;
import com.heart.heartcloud.domain.CloudUser;
import com.heart.heartcloud.exception.CloudSystemException;
import com.heart.heartcloud.service.CloudDirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @ClassName: CloudUtil
 * @Description: TODO
 * @Author: jayhe
 * @Date: 2019/10/11 17:35
 * @Version: v1.0
 */
@Component
public class CloudUtils {

    @Autowired
    private CloudDirService cloudDirService;

    /**
     * 从SESSION中获取当前登录用户
     *
     * @param request
     * @return
     */
    public CloudUser getCloudUserFromSession(HttpServletRequest request) {
        CloudUser currentCloudUser = (CloudUser) request.getSession().getAttribute("CurrentCloudUser");
        if (currentCloudUser == null) {
            throw new CloudSystemException(CloudErrorCodeEnums.LoginExpiredException.getCode(), CloudErrorCodeEnums.LoginExpiredException.getMsg());
        }
        return currentCloudUser;
    }

    /**
     * 根据文件夹ID，递归获取当前文件夹及所有父文件夹目录路径
     *
     * @param stringBuilder
     * @param cloudDirId
     * @return
     */
    public StringBuilder recursiveGetParentDirPath(StringBuilder stringBuilder, Integer cloudDirId) {
        CloudDir cloudDirByPrimaryKey = cloudDirService.findCloudDirByPrimaryKey(cloudDirId);
        if (cloudDirByPrimaryKey != null) {
            stringBuilder.append(cloudDirByPrimaryKey.getCloudDirName());
            stringBuilder.append("_");
            if (cloudDirByPrimaryKey.getCloudDirParentId() != 0) {
                return recursiveGetParentDirPath(stringBuilder, cloudDirByPrimaryKey.getCloudDirParentId());
            } else {
                String[] split = stringBuilder.toString().split("_");
                StringBuilder sb = new StringBuilder();
                for (int i = split.length - 1; i >= 0; i--) {
                    sb.append(split[i]);
                    sb.append("\\");
                }
                return sb;
            }
        } else {
            return stringBuilder;
        }
    }

    /**
     * 根据文件夹ID，递归更新当前文件夹及其父文件夹大小
     *
     * @param cloudDirId
     * @param addSize
     */
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
