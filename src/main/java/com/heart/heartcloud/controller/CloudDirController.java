package com.heart.heartcloud.controller;

import com.heart.heartcloud.common.CloudConstants;
import com.heart.heartcloud.common.CloudErrorCodeEnums;
import com.heart.heartcloud.domain.CloudDir;
import com.heart.heartcloud.domain.CloudFile;
import com.heart.heartcloud.domain.CloudUser;
import com.heart.heartcloud.entity.CloudDirFiles;
import com.heart.heartcloud.exception.CloudException;
import com.heart.heartcloud.response.CloudResponse;
import com.heart.heartcloud.service.CloudDirService;
import com.heart.heartcloud.service.CloudDiskService;
import com.heart.heartcloud.service.CloudFileService;
import com.heart.heartcloud.utils.CloudResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: Heart
 * @Date: 2019/3/11 14:21
 */
@RequestMapping("/clouddir")
@RestController
@Api(tags = "文件夹相关接口")
public class CloudDirController {

    private static final Logger logger = LoggerFactory.getLogger(CloudDirController.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CloudDirService cloudDirService;

    @Autowired
    private CloudFileService cloudFileService;

    @Autowired
    private CloudDiskService cloudDiskService;

    /**
     * 新增文件夹
     *
     * @param cloudDir
     * @return
     */
    @ApiOperation(value = "新增文件夹")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CloudResponse saveCloudDir(HttpServletRequest request, @RequestBody CloudDir cloudDir) {
        CloudUser cloudUser = getCloudUserFromSession(request);
        logger.info("新建文件夹 :cloudUser => {}", cloudUser);
        logger.info("新建文件夹 :cloudDir => {}", cloudDir);
        cloudDir.setCloudDirUserId(cloudUser.getUserId());
        cloudDirService.saveCloudDir(cloudDir);
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder parentDirPath = getParentDirPath(stringBuilder, cloudDir.getCloudDirId());
        logger.info("parentPath :{}", parentDirPath);
        File file = new File(CloudConstants.ROOT_DIR + cloudUser.getUserName() + "\\" + parentDirPath.toString());
        cloudDiskService.createDiskDir(file);

        return CloudResponseUtils.success();
    }

    /**
     * 删除文件夹（根据主键）
     *
     * @param cloudDirId
     * @return
     */
    @ApiOperation(value = "删除文件夹(根据文件夹主键)")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CloudResponse removeCloudDir(HttpServletRequest request, Integer cloudDirId) {
        CloudUser cloudUser = getCloudUserFromSession(request);
        logger.info("删除文件夹 :cloudUser => {}", cloudUser);
        logger.info("删除文件夹 :cloudDirId => {}", cloudDirId);

        CloudDir cloudDirByPrimaryKey = cloudDirService.findCloudDirByPrimaryKey(cloudDirId);
        long cloudDirSize = Long.parseLong(cloudDirByPrimaryKey.getCloudDirSize());
        Integer cloudDirParentId = cloudDirByPrimaryKey.getCloudDirParentId();

        if (cloudDirParentId != 0) {
            CloudDir parentDir = cloudDirService.findCloudDirByPrimaryKey(cloudDirParentId);
            long parentDirSize = Long.parseLong(parentDir.getCloudDirSize());
            parentDir.setCloudDirSize(String.valueOf(parentDirSize - cloudDirSize));
            parentDir.setCloudDirUpdateDate(new Date());
            cloudDirService.editCloudDirByPrimaryKey(parentDir);
        }

        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder parentDirPath = getParentDirPath(stringBuilder, cloudDirId);
        logger.info("parentPath :{}", parentDirPath);
        File targetFile = new File(CloudConstants.ROOT_DIR + cloudUser.getUserName() + "\\" + parentDirPath.toString());
        cloudDiskService.removeDiskDir(targetFile);

        cloudDirService.removeCloudDirByPrimaryKey(cloudDirId, cloudUser.getUserId());
        return CloudResponseUtils.success();
    }

    /**
     * 删除文件夹（根据用户ID）
     *
     * @param cloudUserId
     * @return
     */
    @ApiOperation(value = "删除文件夹（根据用户ID）")
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
    @ApiOperation(value = "修改文件夹")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CloudResponse editCloudDir(@RequestBody CloudDir cloudDir, HttpServletRequest request) {
        CloudUser currentCloudUser = getCloudUserFromSession(request);
        logger.info("修改文件夹 :cloudUser => {}", currentCloudUser);
        logger.info("修改文件夹 :cloudDir => {}", cloudDir);
        CloudDir cloudDirByPrimaryKey = cloudDirService.findCloudDirByPrimaryKey(cloudDir.getCloudDirId());

        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder parentDirPath = getParentDirPath(stringBuilder, cloudDir.getCloudDirId());
        String oldDirPath = CloudConstants.ROOT_DIR + currentCloudUser.getUserName() + "\\" + parentDirPath.toString();
        String newDirPath = oldDirPath.replace(cloudDirByPrimaryKey.getCloudDirName(), cloudDir.getCloudDirName());

        cloudDirByPrimaryKey.setCloudDirName(cloudDir.getCloudDirName());
        cloudDirByPrimaryKey.setCloudDirUpdateDate(new Date());
        cloudDirService.editCloudDirByPrimaryKey(cloudDirByPrimaryKey);

        boolean renameTo = new File(oldDirPath).renameTo(new File(newDirPath));
        logger.info("修改文件夹 :同步修改本地存储{}", renameTo ? "成功" : "失败");

        List<CloudFile> cloudFiles = cloudFileService.fincCloudFilesByCloudDirId(cloudDirByPrimaryKey.getCloudDirId(), currentCloudUser.getUserId());
        for (CloudFile cloudFile : cloudFiles) {
            cloudFile.setCloudFileUrl(newDirPath + cloudFile.getCloudFileName());
            cloudFileService.editCloudFileByPrimaryKey(cloudFile);
        }
        logger.info("修改文件夹 :同步修改文件表中URL成功");

        return CloudResponseUtils.success();
    }

    /**
     * 查询文件夹（根据主键）
     *
     * @param cloudDirId
     * @return
     */
    @ApiIgnore
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public CloudResponse findCloudDir(Integer cloudDirId) {
        return null;
    }

    /**
     * 查询根文件夹（根据用户ID）
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "查询根文件夹（根据用户ID）")
    @RequestMapping(value = "/findall", method = RequestMethod.POST)
    public CloudResponse findCloudDirs(HttpServletRequest request) {
        CloudUser currentCloudUser = getCloudUserFromSession(request);
        logger.info("查询文件夹（根据用户ID） :cloudUser => {}", currentCloudUser);
        List<CloudDir> cloudDirsByCloudUserId = cloudDirService.findCloudDirsByCloudUserId(currentCloudUser.getUserId());
        logger.info("查询文件夹（根据用户ID） :cloudDirs <= {}", cloudDirsByCloudUserId);
        return CloudResponseUtils.success(cloudDirsByCloudUserId);
    }

    /**
     * 查询子文件夹和文件（根据文件夹ID）
     *
     * @param request
     * @param cloudDirId
     * @return
     */
    @ApiOperation(value = "查询子文件夹和文件（根据文件夹ID）")
    @RequestMapping(value = "/findchild", method = RequestMethod.POST)
    public CloudResponse findChildDirsAndFiles(HttpServletRequest request, Integer cloudDirId) {
        CloudUser currentCloudUser = getCloudUserFromSession(request);
        logger.info("查询文件夹子文件夹和文件 :cloudUser => {}", currentCloudUser);
        logger.info("查询文件夹子文件夹和文件 :cloudDirId => {}", cloudDirId);
        List<CloudDir> dirsByParentId = cloudDirService.findCloudDirByParentId(cloudDirId, currentCloudUser.getUserId());
        List<CloudFile> filesByCloudDirId = cloudFileService.fincCloudFilesByCloudDirId(cloudDirId, currentCloudUser.getUserId());
        CloudDirFiles cloudDirFiles = new CloudDirFiles(dirsByParentId, filesByCloudDirId);
        logger.info("查询文件夹子文件夹和文件 :cloudDirFiles => {}", cloudDirFiles);
        return CloudResponseUtils.success(cloudDirFiles);
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

    @GetMapping("/redis/{type}")
    public CloudResponse redisTest(@PathVariable("type") int type) {

        switch (type) {
            //String
            case 1:
                logger.info("redis String test");
                String stringkey1 = "s:k1:alphabet";
                String stringValue1 = "english alphabet";
                String stringkey2 = "s:k2:number";
                String stringValue2 = "0123456789";
                String stringkey3 = "s:k3:chinese";
                String stringValue3 = "我是大哥大";
                String stringkey4 = "s:k4:chinese value";
                String stringValue4 = "中文测试值 ";
                String stringkey5 = "s:k5:symbol";
                String stringValue5 = "！@#￥%……&*（）——+!@#$%^&*()_+";
                redisTemplate.opsForValue().set(stringkey1, stringValue1);
                redisTemplate.opsForValue().set(stringkey2, stringValue2);
                redisTemplate.opsForValue().set(stringkey3, stringValue3);
                redisTemplate.opsForValue().set(stringkey4, stringValue4);
                redisTemplate.opsForValue().set(stringkey5, stringValue5);

                return CloudResponseUtils.success();
            //hash
            case 2:
                logger.info("redis hash test");

                return CloudResponseUtils.success();
            //list
            case 3:
                logger.info("redis list test");

                return CloudResponseUtils.success();
            //set
            case 4:
                logger.info("redis set test");


                return CloudResponseUtils.success();
            //zset
            case 5:
                logger.info("redis zset test");

                return CloudResponseUtils.success();
            default:
                return CloudResponseUtils.fail(CloudErrorCodeEnums.UnknownException.getCode(), CloudErrorCodeEnums.UnknownException.getMsg());
        }
    }
}
