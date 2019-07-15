package com.heart.heartcloud.service;

import java.io.File;

/**
 * @ClassName:CloudDiskService
 * @Description:
 * @Author: Heart
 * @Date: 2019/7/15 17:23
 */
public interface CloudDiskService {

    /**
     * 从存储服务器上物理删除文件夹（包括其中文件）
     *
     * @param file
     */
    void removeDiskDir(File file);
}
