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

    @RequestMapping("/user")
    public void getCloudUser() {



    }

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

    @RequestMapping("/save")
    public CloudResponse saveCloudUser() {

        CloudResponse cloudResponse = new CloudResponse();
        cloudResponse.setErrCode(CloudConstants.ERR_CODE_SUCCESS);
        cloudResponse.setSign("");
        cloudResponse.setErrMessage("添加失败。");
        cloudResponse.setData("");

        CloudUser cloudUser = new CloudUser();
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

    @RequestMapping("/remove")
    public CloudResponse removeCloudUser(int userId) {

        CloudResponse cloudResponse = new CloudResponse();
        cloudResponse.setErrCode(CloudConstants.ERR_CODE_SUCCESS);
        cloudResponse.setSign("");
        cloudResponse.setErrMessage("删除失败。");
        cloudResponse.setData("");

        CloudUser cloudUserByPrimaryKey = cloudUserService.findCloudUserByPrimaryKey(userId);
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

    @RequestMapping("/edit")
    public CloudResponse editCloudUser(int userId) {

        CloudResponse cloudResponse = new CloudResponse();
        cloudResponse.setErrCode(CloudConstants.ERR_CODE_SUCCESS);
        cloudResponse.setSign("");
        cloudResponse.setErrMessage("修改失败。");
        cloudResponse.setData("");

        CloudUser cloudUserByPrimaryKey = cloudUserService.findCloudUserByPrimaryKey(userId);
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
