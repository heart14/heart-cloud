package com.heart.heartcloud.controller;

import com.alibaba.fastjson.JSON;
import com.heart.heartcloud.common.CloudConstants;
import com.heart.heartcloud.domain.CloudUser;
import com.heart.heartcloud.response.CloudResponse;
import com.heart.heartcloud.service.CloudUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description:
 * @Author: Heart
 * @Date: 2019/3/11 14:20
 */
@RequestMapping("/clouduser")
@RestController
public class CloudUserController {

    @Autowired
    private CloudUserService cloudUserService;

    /**
     * 获取用户信息
     */
    @RequestMapping("/user")
    public CloudResponse getCloudUser() {

        CloudResponse cloudResponse = new CloudResponse();
        cloudResponse.setErrCode(CloudConstants.ERR_CODE_SUCCESS);
        cloudResponse.setSign("");
        cloudResponse.setErrMessage("查询失败。");
        cloudResponse.setData("");

        CloudUser cloudUserByPrimaryKey = cloudUserService.findCloudUserByPrimaryKey(1);
        if (cloudUserByPrimaryKey != null) {
            cloudResponse.setErrCode(CloudConstants.ERR_CODE_SUCCESS);
            cloudResponse.setSign("");
            cloudResponse.setErrMessage("查询成功。");
            cloudResponse.setData(JSON.toJSONString(cloudUserByPrimaryKey));
        }
        return cloudResponse;
    }

    /**
     * 获取所有用户信息
     * @return
     */
    @RequestMapping("/users")
    public CloudResponse getCloudUserList() {

        CloudResponse cloudResponse = new CloudResponse();
        cloudResponse.setErrCode(CloudConstants.ERR_CODE_SUCCESS);
        cloudResponse.setSign("");
        cloudResponse.setErrMessage("查询失败。");
        cloudResponse.setData("");

        List<CloudUser> allUser = cloudUserService.findAllUser();
        if (allUser != null && allUser.size() > 0) {
            cloudResponse.setErrCode(CloudConstants.ERR_CODE_SUCCESS);
            cloudResponse.setSign("");
            cloudResponse.setErrMessage("查询成功。");
            cloudResponse.setData(JSON.toJSONString(allUser));
        }
        return cloudResponse;
    }

    /**
     * 添加用户
     * @return
     */
    @RequestMapping("/save")
    public CloudResponse saveCloudUser(CloudUser cloudUser) {

        CloudResponse cloudResponse = new CloudResponse();
        cloudResponse.setErrCode(CloudConstants.ERR_CODE_SUCCESS);
        cloudResponse.setSign("");
        cloudResponse.setErrMessage("添加失败。");
        cloudResponse.setData("");

        cloudUser.setUserName("Heartdd");
        cloudUser.setUserPass("123456");
        cloudUser.setUserRole("normal");
        cloudUser.setUserStatus("1");

        int saveCloudUser = cloudUserService.saveCloudUser(cloudUser);

        if (saveCloudUser == 1) {
            cloudResponse.setErrCode(CloudConstants.ERR_CODE_SUCCESS);
            cloudResponse.setSign("");
            cloudResponse.setErrMessage("添加成功。");
            cloudResponse.setData("");
        }
        return cloudResponse;
    }

    /**
     * 删除用户
     * @param cloudUserId
     * @return
     */
    @RequestMapping("/remove")
    public CloudResponse removeCloudUser(int cloudUserId) {

        CloudResponse cloudResponse = new CloudResponse();
        cloudResponse.setErrCode(CloudConstants.ERR_CODE_SUCCESS);
        cloudResponse.setSign("");
        cloudResponse.setErrMessage("删除失败。");
        cloudResponse.setData("");

        CloudUser cloudUserByPrimaryKey = cloudUserService.findCloudUserByPrimaryKey(cloudUserId);
        cloudUserByPrimaryKey.setUserStatus("0");
        int editCloudUserByPrimaryKey = cloudUserService.editCloudUserByPrimaryKey(cloudUserByPrimaryKey);

        if (editCloudUserByPrimaryKey == 1) {
            cloudResponse.setErrCode(CloudConstants.ERR_CODE_SUCCESS);
            cloudResponse.setSign("");
            cloudResponse.setErrMessage("删除成功。");
            cloudResponse.setData("");
        }
        return cloudResponse;
    }

    /**
     * 修改用户信息
     * @param cloudUser
     * @return
     */
    @RequestMapping("/edit")
    public CloudResponse editCloudUser(CloudUser cloudUser) {

        CloudResponse cloudResponse = new CloudResponse();
        cloudResponse.setErrCode(CloudConstants.ERR_CODE_SUCCESS);
        cloudResponse.setSign("");
        cloudResponse.setErrMessage("修改失败。");
        cloudResponse.setData("");

        CloudUser cloudUserByPrimaryKey = cloudUserService.findCloudUserByPrimaryKey(cloudUser.getUserId());
        cloudUserByPrimaryKey.setUserPass("123456");
        cloudUserByPrimaryKey.setUserStatus("1");
        int editCloudUserByPrimaryKey = cloudUserService.editCloudUserByPrimaryKey(cloudUserByPrimaryKey);

        if (editCloudUserByPrimaryKey == 1) {
            cloudResponse.setErrCode(CloudConstants.ERR_CODE_SUCCESS);
            cloudResponse.setSign("");
            cloudResponse.setErrMessage("修改成功。");
            cloudResponse.setData("");
        }
        return cloudResponse;
    }

}
