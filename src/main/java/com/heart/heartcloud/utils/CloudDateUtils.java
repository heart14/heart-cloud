package com.heart.heartcloud.utils;

import com.heart.heartcloud.common.CloudErrorCodeEnums;
import com.heart.heartcloud.exception.CloudSystemException;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: CloudDateUtils
 * @Description: 时间相关工具类
 * @Author: jayhe
 * @Date: 2019/9/19 10:50
 * @Version: v1.0
 */
public class CloudDateUtils extends DateUtils {

    public static final Logger logger = LoggerFactory.getLogger(CloudDateUtils.class);

    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将日期格式化为字符串
     *
     * @param date
     * @return
     */
    public static String formatDateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        return simpleDateFormat.format(date);
    }

    public static Date formatStringToDate(String timeStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        try {
            return simpleDateFormat.parse(timeStr);
        } catch (ParseException e) {
            logger.error("时间字符串格式化为Date失败 :{}", e.getMessage());
            throw new CloudSystemException(CloudErrorCodeEnums.SystemException.getCode(), e.getMessage());
        }
    }
}
