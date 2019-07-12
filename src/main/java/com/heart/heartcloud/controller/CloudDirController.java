package com.heart.heartcloud.controller;

import com.heart.heartcloud.domain.CloudDir;
import com.heart.heartcloud.response.CloudResponse;
import com.heart.heartcloud.service.CloudDirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: Heart
 * @Date: 2019/3/11 14:21
 */
@RequestMapping("/clouddir")
@RestController
public class CloudDirController {

    @Autowired
    private CloudDirService cloudDirService;

    /**
     * 新增文件夹
     *
     * @param cloudDir
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CloudResponse saveCloudDir(CloudDir cloudDir) {
        return null;
    }

    /**
     * 删除文件夹（根据主键）
     *
     * @param cloudDirId
     * @return
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CloudResponse removeCloudDir(Integer cloudDirId) {
        return null;
    }

    /**
     * 删除文件夹（根据用户ID）
     *
     * @param cloudUserId
     * @return
     */
    @RequestMapping(value = "/removeAll", method = RequestMethod.POST)
    public CloudResponse removeCloudDirs(Integer cloudUserId) {
        return null;
    }

    /**
     * 修改文件夹
     *
     * @param cloudDir
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CloudResponse editCloudDir(CloudDir cloudDir) {
        return null;
    }

    /**
     * 查询文件夹（根据主键）
     *
     * @param cloudDirId
     * @return
     */
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public CloudResponse findCloudDir(Integer cloudDirId) {
        return null;
    }

    /**
     * 查询文件夹（根据用户ID）
     *
     * @param cloudUserId
     * @return
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public CloudResponse findCloudDirs(Integer cloudUserId) {
        return null;
    }
}
