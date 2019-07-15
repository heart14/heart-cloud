package com.heart.heartcloud.service.impl;

import com.heart.heartcloud.service.CloudDiskService;
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
    @Override
    public void createDiskDir(File file) {
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
