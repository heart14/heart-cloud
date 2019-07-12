package com.heart.heartcloud.utils;

import com.heart.heartcloud.common.CloudConstants;
import com.heart.heartcloud.response.CloudResponse;

/**
 * @ClassName:CloudResponseUtil
 * @Description:
 * @Author: Heart
 * @Date: 2019/7/12 13:08
 */
public class CloudResponseUtil {

    public static CloudResponse success() {
        return success(null);
    }

    public static CloudResponse<Object> success(Object object) {
        CloudResponse<Object> cloudResponse = new CloudResponse<>();
        cloudResponse.setErrCode(CloudConstants.ERR_CODE_SUCCESS);
        cloudResponse.setErrMessage(CloudConstants.ERR_MSG_SUCCESS);
        cloudResponse.setData(object);
        return cloudResponse;
    }

    public static CloudResponse fail(Integer code, String msg) {
        return new CloudResponse(code, msg);
    }

    public static CloudResponse<Object> fail(Integer code, String msg, Object object) {
        CloudResponse<Object> cloudResponse = new CloudResponse<>();
        cloudResponse.setErrCode(code);
        cloudResponse.setErrMessage(msg);
        cloudResponse.setData(object);
        return cloudResponse;
    }
}
