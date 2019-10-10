package com.heart.heartcloud.controller;

import com.heart.heartcloud.common.CloudConstants;
import com.heart.heartcloud.common.CloudErrorCodeEnums;
import com.heart.heartcloud.domain.CloudDir;
import com.heart.heartcloud.domain.CloudFile;
import com.heart.heartcloud.domain.CloudGoods;
import com.heart.heartcloud.domain.CloudUser;
import com.heart.heartcloud.exception.CloudSystemException;
import com.heart.heartcloud.response.CloudResponse;
import com.heart.heartcloud.service.CloudDirService;
import com.heart.heartcloud.service.CloudDiskService;
import com.heart.heartcloud.service.CloudFileService;
import com.heart.heartcloud.service.CloudGoodsService;
import com.heart.heartcloud.utils.CloudResponseUtils;
import com.heart.heartcloud.utils.CloudStringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: Heart
 * @Date: 2019/3/11 14:20
 */
@RequestMapping("/cloudfile")
@RestController
@Api(tags = "网盘文件相关接口")
public class CloudFileController {

    private static final Logger logger = LoggerFactory.getLogger(CloudFileController.class);

    @Autowired
    private CloudFileService cloudFileService;

    @Autowired
    private CloudDirService cloudDirService;

    @Autowired
    private CloudDiskService cloudDiskService;

    @Autowired
    private CloudGoodsService cloudGoodsService;

    /**
     * 上传文件
     *
     * @param multipartFiles
     * @param cloudDirId
     * @param request
     * @return
     * @throws IOException
     */
    @ApiOperation(value = "文件上传接口", response = CloudResponse.class)
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public CloudResponse uploadCloudFile(@RequestParam("multipartFiles") MultipartFile[] multipartFiles, @RequestParam("cloudDirId") Integer cloudDirId, HttpServletRequest request) throws IOException {
        CloudUser cloudUser = getCloudUserFromSession(request);
        logger.info("文件上传 :cloudDirId => {}", cloudDirId);

        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder parentDirPath = getParentDirPath(stringBuilder, cloudDirId);
        String filePath = CloudConstants.ROOT_DIR + cloudUser.getUserName() + "\\" + parentDirPath.toString();

        for (MultipartFile multipartFile : multipartFiles) {

            CloudFile cloudFile = new CloudFile();

            File targetPath = new File(filePath);
            if (!targetPath.exists()) {
                boolean mkdirs = targetPath.mkdirs();
            }
            File file = new File(filePath + multipartFile.getOriginalFilename());
            if (file.exists()) {
                String substring = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
                String shotUuid = CloudStringUtils.getShotUuid();

                file = new File(filePath + multipartFile.getOriginalFilename().replace(substring, "") + "_" + shotUuid + substring);

                cloudFile.setCloudFileName(multipartFile.getOriginalFilename().replace(substring, "") + "_" + shotUuid + substring);
                cloudFile.setCloudFileUrl(filePath + multipartFile.getOriginalFilename().replace(substring, "") + "_" + shotUuid + substring);
            } else {
                cloudFile.setCloudFileName(multipartFile.getOriginalFilename());
                cloudFile.setCloudFileUrl(filePath + multipartFile.getOriginalFilename());
            }
            multipartFile.transferTo(file);

            cloudFile.setCloudFileId(CloudStringUtils.getId());
            cloudFile.setCloudFileSize(String.valueOf(multipartFile.getSize()));
            cloudFile.setCloudFileType(getCloudFileType(multipartFile.getOriginalFilename() != null ? multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")) : ""));
            cloudFile.setCloudFileStatus(CloudConstants.STATUS_YES);
            cloudFile.setCloudFileDirId(cloudDirId);
            cloudFile.setCloudFileUserId(cloudUser.getUserId());
            cloudFile.setCloudFileSellPrice(CloudConstants.STATUS_NO);
            cloudFile.setCloudFileCreateDate(new Date());
            cloudFile.setCloudFileUpdateDate(new Date());
            cloudFileService.saveCloudFile(cloudFile);

            CloudDir cloudDirByPrimaryKey = cloudDirService.findCloudDirByPrimaryKey(cloudDirId);
            if (cloudDirByPrimaryKey != null) {
                long cloudDirSize = Long.parseLong(cloudDirByPrimaryKey.getCloudDirSize());
                cloudDirByPrimaryKey.setCloudDirSize(String.valueOf(cloudDirSize + multipartFile.getSize()));
                cloudDirByPrimaryKey.setCloudDirUpdateDate(new Date());
                cloudDirService.editCloudDirByPrimaryKey(cloudDirByPrimaryKey);
            }
        }

        return CloudResponseUtils.success();
    }

    @ApiOperation(value = "文件下载接口")
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public void downloadFile(@RequestParam Integer cloudFileId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CloudUser cloudUser = getCloudUserFromSession(request);
        logger.info("文件下载 :cloudFileId => {}", cloudFileId);

        CloudFile cloudFileByPrimaryKey = cloudFileService.findCloudFileByPrimaryKey(cloudFileId);
        if (cloudFileByPrimaryKey == null || CloudStringUtils.isBlank(cloudFileByPrimaryKey.getCloudFileUrl())) {
            throw new CloudSystemException(CloudErrorCodeEnums.UnknownFileException.getCode(), CloudErrorCodeEnums.UnknownFileException.getMsg());
        }

        String cloudFileUrl = cloudFileByPrimaryKey.getCloudFileUrl();
        File file = new File(cloudFileUrl);
        if (file.exists()) {
            response.setContentType("application/force-download");
            response.addHeader("Content-Disposition", "attachment;fileName=" + cloudFileByPrimaryKey.getCloudFileName());

            byte[] buffer = new byte[1024];
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            os.flush();
            fis.close();
            bis.close();
            os.close();
        } else {
            throw new CloudSystemException(CloudErrorCodeEnums.UnknownFileException.getCode(), CloudErrorCodeEnums.UnknownFileException.getMsg());
        }
    }

    //TODO 上传、删除文件和删除文件夹（包含文件夹内文件）时，没有递归更新父父...文件夹的大小
    //TODO 文件上传后丢失位置、设备等Exif信息（https://www.jianshu.com/p/c6b413cea2dd）

    /**
     * 删除文件
     *
     * @param cloudFileId
     * @param request
     * @return
     */
    @ApiOperation(value = "删除文件（根据文件id）")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CloudResponse removeFile(@RequestParam Integer cloudFileId, HttpServletRequest request) {
        CloudUser cloudUser = getCloudUserFromSession(request);
        logger.info("文件删除 :cloudDirId => {}", cloudFileId);

        CloudFile cloudFileByPrimaryKey = cloudFileService.findCloudFileByPrimaryKey(cloudFileId);
        if (cloudFileByPrimaryKey != null) {
            Integer cloudFileDirId = cloudFileByPrimaryKey.getCloudFileDirId();

            CloudDir cloudDirByPrimaryKey = cloudDirService.findCloudDirByPrimaryKey(cloudFileDirId);
            if (cloudDirByPrimaryKey != null) {
                long oldCloudDirSize = Long.parseLong(cloudDirByPrimaryKey.getCloudDirSize());
                long cloudFileSize = Long.parseLong(cloudFileByPrimaryKey.getCloudFileSize());
                cloudDirByPrimaryKey.setCloudDirSize(String.valueOf(oldCloudDirSize - cloudFileSize));
                cloudDirByPrimaryKey.setCloudDirUpdateDate(new Date());
                cloudDirService.editCloudDirByPrimaryKey(cloudDirByPrimaryKey);
            }

            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder parentDirPath = getParentDirPath(stringBuilder, cloudFileDirId);
            logger.info("parentPath :{}", parentDirPath);
            String filePath = CloudConstants.ROOT_DIR + cloudUser.getUserName() + "\\" + parentDirPath.toString();
            File file = new File(filePath + cloudFileByPrimaryKey.getCloudFileName());
            cloudDiskService.removeDiskDir(file);

            cloudFileService.removeCloudFileByPrimaryKey(cloudFileId);
            return CloudResponseUtils.success();
        } else {
            return CloudResponseUtils.success();
        }
    }

    /**
     * 修改文件
     *
     * @param cloudFile
     * @param request
     * @return
     */
    @ApiOperation(value = "更新文件信息")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CloudResponse editCloudFile(@RequestBody CloudFile cloudFile, HttpServletRequest request) {
        CloudUser currentCloudUser = getCloudUserFromSession(request);
        logger.info("修改文件 :cloudUser => {}", currentCloudUser);
        logger.info("修改文件 :cloudFile => {}", cloudFile);

        CloudFile cloudFileByPrimaryKey = cloudFileService.findCloudFileByPrimaryKey(cloudFile.getCloudFileId());

        String cloudFileUrl = cloudFileByPrimaryKey.getCloudFileUrl();

        String cloudFileName = cloudFileByPrimaryKey.getCloudFileName();
        String substring = cloudFileName.substring(cloudFileName.lastIndexOf("."));
        String newFileName = CloudStringUtils.isBlank(substring) ? cloudFile.getCloudFileName() : cloudFile.getCloudFileName() + substring;

        cloudFileByPrimaryKey.setCloudFileName(newFileName);
        cloudFileByPrimaryKey.setCloudFileUpdateDate(new Date());
        cloudFileByPrimaryKey.setCloudFileUrl(cloudFileUrl.replace(cloudFileName, newFileName));
        cloudFileService.editCloudFileByPrimaryKey(cloudFileByPrimaryKey);

        boolean renameTo = new File(cloudFileUrl).renameTo(new File(cloudFileByPrimaryKey.getCloudFileUrl()));
        logger.info("修改文件 :同步修改本地存储{}", renameTo ? "成功" : "失败");

        return CloudResponseUtils.success();
    }

    //TODO 关于文件和目录的XML文件里貌似没有设置status

    /**
     * 查询文件（根据文件名，模糊查询）
     *
     * @param searchFileName
     * @return
     */
    @ApiOperation(value = "查询文件（根据文件名，模糊查询）")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public CloudResponse searchFileByName(@RequestParam("searchFileName") String searchFileName, HttpServletRequest request) {
        CloudUser currentCloudUser = getCloudUserFromSession(request);
        logger.info("搜索文件(文件名) :cloudUser => {}", currentCloudUser);
        logger.info("搜索文件(文件名) :searchFileName => {}", searchFileName);
        List<CloudFile> cloudFileLikeName = cloudFileService.findCloudFileLikeName(searchFileName, currentCloudUser.getUserId());
        logger.info("搜索文件(文件名) :cloudFile <= {}", cloudFileLikeName);
        return CloudResponseUtils.success(cloudFileLikeName);
    }

    @ApiOperation(value = "查询文件（根据文件类型）")
    @RequestMapping(value = "/searchbytype", method = RequestMethod.GET)
    public CloudResponse searchFileByType(@RequestParam("searchFileType") String searchFileType, HttpServletRequest request) {
        CloudUser currentCloudUser = getCloudUserFromSession(request);
        logger.info("搜索文件(类型) :cloudUser => {}", currentCloudUser);
        logger.info("搜索文件(类型) :searchFileType => {}", searchFileType);
        List<CloudFile> cloudFileByType = cloudFileService.findCloudFileByType(searchFileType, currentCloudUser.getUserId());
        logger.info("搜索文件(类型) :cloudFile <= {}", cloudFileByType);
        return CloudResponseUtils.success(cloudFileByType);
    }

    /**
     * 上架文件
     *
     * @param cloudFileId
     * @param cloudFilePrice
     * @param request
     * @return
     */
    @ApiOperation(value = "上架出售文件")
    @RequestMapping(value = "/sell", method = RequestMethod.POST)
    public CloudResponse sellFile(@RequestParam("cloudFileId") Integer cloudFileId, @RequestParam("cloudFilePrice") String cloudFilePrice, HttpServletRequest request) {
        CloudUser currentCloudUser = getCloudUserFromSession(request);
        logger.info("上架文件 :cloudUser => {}", currentCloudUser);
        logger.info("上架文件 :cloudFileId => {}, cloudFilePrice => {}", cloudFileId, cloudFilePrice);

        CloudFile cloudFile = cloudFileService.findCloudFileByPrimaryKey(cloudFileId);
        cloudFile.setCloudFileSellFlag(CloudConstants.STATUS_YES);
        cloudFile.setCloudFileSellPrice(cloudFilePrice);
        cloudFile.setCloudFileUpdateDate(new Date());
        cloudFileService.editCloudFileByPrimaryKey(cloudFile);

        CloudGoods cloudGoods = new CloudGoods();
        cloudGoods.setCloudGoodsId(CloudStringUtils.getId());
        cloudGoods.setCloudGoodsFileId(cloudFileId);
        cloudGoods.setCloudGoodsFilePrice(cloudFilePrice);
        cloudGoods.setCloudGoodsUrl(cloudFile.getCloudFileUrl());
        cloudGoods.setCloudGoodsStatus(CloudConstants.STATUS_YES);
        cloudGoods.setCloudGoodsCreateDate(new Date());
        cloudGoodsService.saveCloudGoods(cloudGoods);

        logger.info("上架文件 :cloudFile <= {}", cloudFile);
        logger.info("上架文件 :cloudGoods <= {}", cloudGoods);

        return CloudResponseUtils.success();
    }

    /**
     * 下架文件
     *
     * @param cloudFileId
     * @param request
     * @return
     */
    @ApiOperation(value = "下架文件")
    @RequestMapping(value = "/unsell", method = RequestMethod.POST)
    public CloudResponse unSellFile(@RequestParam("cloudFileId") Integer cloudFileId, HttpServletRequest request) {
        CloudUser currentCloudUser = getCloudUserFromSession(request);
        logger.info("下架文件 :cloudUser => {}", currentCloudUser);
        logger.info("下架文件 :cloudFileId => {}", cloudFileId);
        CloudFile cloudFileByPrimaryKey = cloudFileService.findCloudFileByPrimaryKey(cloudFileId);
        cloudFileByPrimaryKey.setCloudFileSellFlag(CloudConstants.STATUS_NO);
        cloudFileByPrimaryKey.setCloudFileUpdateDate(new Date());
        cloudFileService.editCloudFileByPrimaryKey(cloudFileByPrimaryKey);
        //更新T_CLOUD_GOODS表中文件状态
        CloudGoods cloudGoodsByCloudFileId = cloudGoodsService.findCloudGoodsByCloudFileId(cloudFileId);
        cloudGoodsByCloudFileId.setCloudGoodsStatus(CloudConstants.STATUS_NO);
        cloudGoodsByCloudFileId.setCloudGoodsUpdateDate(new Date());
        cloudGoodsService.editCloudGoodsByPrimaryKey(cloudGoodsByCloudFileId);
        logger.info("下架文件 :cloudFile <= {}", cloudFileByPrimaryKey);
        logger.info("下架文件 :cloudGoods <= {}", cloudGoodsByCloudFileId);
        return CloudResponseUtils.success();
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
            throw new CloudSystemException(CloudErrorCodeEnums.LoginExpiredException.getCode(), CloudErrorCodeEnums.LoginExpiredException.getMsg());
        }
        return currentCloudUser;
    }

    /**
     * 根据后缀名获取文件类型
     *
     * @param filePrefix
     * @return
     */
    private String getCloudFileType(String filePrefix) {
        switch (filePrefix.toLowerCase()) {
            case ".jpg":
                return "图片";
            case ".jpeg":
                return "图片";
            case ".png":
                return "图片";
            case ".bmp":
                return "图片";
            case ".gif":
                return "图片";
            case ".heic":
                return "图片";
            case ".mp3":
                return "音乐";
            case ".wav":
                return "音乐";
            case ".flac":
                return "音乐";
            case ".mp4":
                return "视频";
            case ".flv":
                return "视频";
            case ".avi":
                return "视频";
            case ".mkv":
                return "视频";
            case ".mov":
                return "视频";
            case ".rmvb":
                return "视频";
            case ".wmv":
                return "视频";
            case ".txt":
                return "文档";
            case ".md":
                return "文档";
            case ".doc":
                return "文档";
            case ".docx":
                return "文档";
            case ".pdf":
                return "文档";
            case ".exe":
                return "应用程序";
            default:
                return "其它";
        }
    }

    /**
     * 根据文件夹ID，获取当前文件夹及所有父文件夹目录路径
     *
     * @param stringBuilder
     * @param cloudDirId
     * @return
     */
    private StringBuilder getParentDirPath(StringBuilder stringBuilder, Integer cloudDirId) {
        CloudDir cloudDirByPrimaryKey = cloudDirService.findCloudDirByPrimaryKey(cloudDirId);
        if (cloudDirByPrimaryKey != null) {
            stringBuilder.append(cloudDirByPrimaryKey.getCloudDirName());
            stringBuilder.append("_");
            if (cloudDirByPrimaryKey.getCloudDirParentId() != 0) {
                return getParentDirPath(stringBuilder, cloudDirByPrimaryKey.getCloudDirParentId());
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
}
