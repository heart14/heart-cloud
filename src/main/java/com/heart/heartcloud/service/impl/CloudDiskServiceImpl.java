package com.heart.heartcloud.service.impl;

import com.heart.heartcloud.service.CloudDiskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @ClassName:CloudDiskServiceImpl
 * @Description:
 * @Author: Heart
 * @Date: 2019/7/15 17:25
 */
@Service
public class CloudDiskServiceImpl implements CloudDiskService {

    private static final Logger logger = LoggerFactory.getLogger(CloudDiskServiceImpl.class);

    @Override
    public void createDiskDir(File file) {
        logger.info("创建本地文件夹 :filePath => {}", file.getPath());
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    @Override
    public void removeDiskDir(File targetFile) {

        if (targetFile.isFile()) {
            targetFile.delete();
        } else {
            File[] files = targetFile.listFiles();
            for (File file : files) {
                removeDiskDir(file);
            }
            targetFile.delete();
        }
    }
}
