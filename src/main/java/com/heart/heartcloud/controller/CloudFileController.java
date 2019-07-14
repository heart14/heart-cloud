package com.heart.heartcloud.controller;

import com.heart.heartcloud.common.CloudConstants;
import com.heart.heartcloud.common.CloudErrorCodeEnums;
import com.heart.heartcloud.domain.CloudFile;
import com.heart.heartcloud.domain.CloudUser;
import com.heart.heartcloud.exception.CloudException;
import com.heart.heartcloud.response.CloudResponse;
import com.heart.heartcloud.service.CloudFileService;
import com.heart.heartcloud.utils.CloudResponseUtil;
import com.heart.heartcloud.utils.CloudStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @Description:
 * @Author: Heart
 * @Date: 2019/3/11 14:20
 */
@RequestMapping("/cloudfile")
@RestController
public class CloudFileController {

    private static final Logger logger = LoggerFactory.getLogger(CloudFileController.class);

    @Autowired
    private CloudFileService cloudFileService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public CloudResponse uploadCloudFile(@RequestParam("multipartFiles") MultipartFile[] multipartFiles, @RequestParam("cloudDirId") Integer cloudDirId, HttpServletRequest request) throws IOException {
        CloudUser cloudUser = getCloudUserFromSession(request);
        logger.info("文件上传 :cloudDirId => {}", cloudDirId);

        String filePath = CloudConstants.ROOT_DIR + cloudUser.getUserName() + "\\";
        for (MultipartFile multipartFile : multipartFiles) {
            File targetPath = new File(filePath);
            if (!targetPath.exists()) {
                boolean mkdirs = targetPath.mkdirs();
            }
            File file = new File(filePath + multipartFile.getOriginalFilename());
            multipartFile.transferTo(file);

            CloudFile cloudFile = new CloudFile();
            cloudFile.setCloudFileId(CloudStringUtils.getId());
            cloudFile.setCloudFileName(multipartFile.getOriginalFilename());
            cloudFile.setCloudFileSize(String.valueOf(multipartFile.getSize()));
            cloudFile.setCloudFileUrl(filePath + multipartFile.getOriginalFilename());
            cloudFile.setCloudFileType(getCloudFileType(multipartFile.getOriginalFilename() != null ? multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")) : ""));
            cloudFile.setCloudFileStatus(CloudConstants.STATUS_YES);
            cloudFile.setCloudFileDirId(cloudDirId);
            cloudFile.setCloudFileUserId(cloudUser.getUserId());
            cloudFile.setCloudFileCreateDate(new Date());
            cloudFileService.saveCloudFile(cloudFile);
        }

        return CloudResponseUtil.success();
    }

    /**
     * 从SESSION中获取当前登录用户
     *
     * @param request
     * @return
     */
    private CloudUser getCloudUserFromSession(HttpServletRequest request) {
        CloudUser currentCloudUser = (CloudUser) request.getSession().getAttribute("CurrentCloudUser");
        if (currentCloudUser == null) {
            throw new CloudException(CloudErrorCodeEnums.LoginExpiredException.getCode(), CloudErrorCodeEnums.LoginExpiredException.getMsg());
        }
        return currentCloudUser;
    }

    private String getCloudFileType(String filePrefix) {
        switch (filePrefix) {
            case "jpg":
                return "图片";
            case "jpeg":
                return "图片";
            case "png":
                return "图片";
            case "bmp":
                return "图片";
            case "gif":
                return "图片";
            case "mp3":
                return "音乐";
            case "wav":
                return "音乐";
            case "flac":
                return "音乐";
            case "mp4":
                return "视频";
            case "flv":
                return "视频";
            case "avi":
                return "视频";
            case "mkv":
                return "视频";
            case "rmvb":
                return "视频";
            case "wmv":
                return "视频";
            case "txt":
                return "文档";
            case "md":
                return "文档";
            case "doc":
                return "文档";
            case "docx":
                return "文档";
            case "pdf":
                return "文档";
            case "exe":
                return "应用程序";
            default:
                return "其它";
        }
    }
}
