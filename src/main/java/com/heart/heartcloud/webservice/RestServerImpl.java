package com.ums.itms.itas.webservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ums.itms.itas.common.Constants;
import com.ums.itms.common.dao.AppversionMapper;
import com.ums.itms.itas.dao.AuditinfoMapper;
import com.ums.itms.common.model.App;
import com.ums.itms.common.model.Appversion;
import com.ums.itms.common.model.Auditinfo;
import com.ums.itms.itas.service.AppService;
import com.ums.itms.itas.service.AppcheckService;
import com.ums.itms.common.util.*;
import com.ums.itms.itas.util.FileUtils;
import com.ums.itms.itas.util.FtpUtil;
import com.ums.itms.itas.util.PropertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class RestServerImpl implements RestServer {

    private static Logger log = LoggerFactory.getLogger(RestServerImpl.class);

    @Value("${ftpAppDir}")
    private String ftpAppDir;

    @Value("${reFtpAppDir}")
    private String reFtpAppDir;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private AppcheckService appcheckService;

    @Autowired
    private AppService appService;

    @Autowired
    private AppversionMapper appversionMapper; //应用版本表

    @Autowired
    private AuditinfoMapper auditinfoMapper;

    @Override
    public Map signaturePost(Map<String, Object> params) {
        Map resMap = new HashMap();
        String appId = StringUtils.getObjStr(params.get("appId"));
        String requestId = StringUtils.getObjStr(params.get("requestId"));
        if(log.isInfoEnabled()){
            log.info("TPM 签名通知开始..."+requestId);
        }
        try{
            final Map resultMap = tpmMoveApp(params);
            resMap.put("returnCode",resultMap.get("returnCode"));
            resMap.put("appId",resultMap.get("appId"));
            resMap.put("requestId",resultMap.get("requestId"));
            resMap.put("reason",resultMap.get("reason"));

            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    if (Constants.TPM_RETURN_CODE_SUCCESS.equals(StringUtils.getObjStr(resultMap.get("returnCode")))) {
                        try {
                            appcheckService.saveCheckResultInfo(resultMap);
                        } catch (Exception e) {
                            log.error("保存最后的审核结果失败",e);
                        }
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            resMap.put("returnCode",Constants.TPM_RETURN_CODE_FAIL);
            resMap.put("appId",appId);
            resMap.put("requestId",requestId);
            resMap.put("reason","系统处理失败");
            if(log.isInfoEnabled()){
                log.info("TPM 签名通知处理异常..."+requestId);
            }
            if(log.isErrorEnabled()){
                log.error("TPM 签名通知处理异常:",e);
            }
        }
        if(log.isInfoEnabled()){
            log.info("TPM 签名通知结束..."+requestId);
        }
        return  resMap;
    }

    /**
     * 下载移动签名文件
     * @param map
     * @return
     * @throws Exception
     */
    private Map tpmMoveApp(Map<String, Object> map) throws Exception{
        Map<String, Object> params = new HashMap<String, Object>();
        String requestId = StringUtils.getObjStr(map.get("requestId"));
        if(log.isInfoEnabled()){
            log.info("MP处理TPM签名应答开始..." + requestId);
        }
        String result = StringUtils.getObjStr(map.get("result"));
        if(log.isInfoEnabled()){
            log.info("MP处理TPM签名应答开始1.1..." + result);
        }
        String ftpPath = StringUtils.getObjStr(map.get("fileDir"));
        if(log.isInfoEnabled()){
            log.info("MP处理TPM签名应答开始1.2..." + ftpPath);
        }
        String appIdT = StringUtils.getObjStr(map.get("appId"));
        if(log.isInfoEnabled()){
            log.info("MP处理TPM签名应答开始1.3..." + appIdT);
        }
        String description = StringUtils.getObjStr(map.get("description"));
        if(log.isInfoEnabled()){
            log.info("MP处理TPM签名应答开始1.4..." + description);
        }
        params.put("returnCode",Constants.TPM_RETURN_CODE_SUCCESS);
        if(log.isInfoEnabled()){
            log.info("MP处理TPM签名应答开始1.5..." + Constants.TPM_RETURN_CODE_SUCCESS);
        }
        params.put("appId",appIdT);
        if(log.isInfoEnabled()){
            log.info("MP处理TPM签名应答开始1.6..." + appIdT);
        }
        params.put("requestId",requestId);
        if(log.isInfoEnabled()){
            log.info("MP处理TPM签名应答开始1.7..." + requestId);
        }
        params.put("reason",result);
        if(log.isInfoEnabled()){
            log.info("MP处理TPM签名应答开始1.8..." + result);
        }

        if(StringUtils.isEmpty(requestId)) {//参数错误
            if(log.isInfoEnabled()){
                log.info("MP处理TPM签名应答开始1.9...");
            }
            params.put("returnCode",Constants.TPM_RETURN_CODE_FAIL);
            params.put("reason","requestId is not null");
            if(log.isWarnEnabled()){
                log.warn("MP处理TPM签名应答错误.requestId为空");
            }
            return params;
        }
        if(log.isInfoEnabled()){
            log.info("MP处理TPM签名应答开始2.1..." + requestId);
        }
        App app = appService.selectByKey(appIdT);
        if(app != null){
            if(log.isInfoEnabled()){
                log.info("MP处理TPM签名应答开始2..." + requestId);
            }
            Map<String, Object> pathMap = new HashMap<>();
            pathMap.clear();
            Map resMap = PropertUtils.getAppPath(pathMap);
            String appPathPre = StringUtils.getObjStr(resMap.get(Constants.ABSOLUTELY_PRE_PATH));
            if(app.getAppBetaType() != null && app.getAppBetaType().equals(Constants.app.APP_BETA_TYPE_TEST.getValue())) {
                //测试版本
                if(result.equals(Constants.TPM_RESULT_FAIL)) {//TPM处理失败
                    if(description.contains("签名驳回")){
                        params.put("returnCode",Constants.TPM_RETURN_CODE_SUCCESS);
                        params.put("reason","签名驳回");
                    }else{
                        params.put("returnCode",Constants.TPM_RETURN_CODE_FAIL);
                        params.put("reason","TPM签名失败");
                    }
                    if(log.isWarnEnabled()){
                        log.warn("MP处理TPM签名应答,签名失败");
                    }
                    Appversion appVersion = appversionMapper.selectByPrimaryKey(requestId);
                    if(appVersion != null){
                        if(appVersion.getApvNew().equals(Constants.appVersion.APPVERSION_NEW.getValue())){
                            //新增应用
                            app.setAppMark(Constants.app.APP_DISABLE.getValue());
                            appService.updateNotNull(app);
                        }
                        appVersion.setApvMark(Constants.appVersion.APPVERSION_DISABLE.getValue());
                        appversionMapper.updateByPrimaryKey(appVersion);
                    }
                    return params;
                }
                Appversion appVersion = appversionMapper.selectByPrimaryKey(requestId);
                if(appVersion != null){
                    if(appVersion.getApvNew().equals(Constants.appVersion.APPVERSION_NEW.getValue())){
                        //新增应用
                        //移动签名文件
                        String fileDirName = "";
                        if(!StringUtils.isEmpty(ftpPath)) {
                            fileDirName = new File(ftpPath).getName();
                        }
                        //路径：app+UUID+UUID
                        String fileDirPre = UuidUtils.getRandomUuidWithoutSeparator() + File.separator + UuidUtils.getRandomUuidWithoutSeparator() + File.separator;
                        String fileDir = appPathPre + fileDirPre;
                        String locfileDir = fileDir + fileDirName;
                        ftpPath = reFtpAppDir + File.separator + ftpPath;
                        if(log.isInfoEnabled()){
                            log.info("MP处理TPM签名应答，FTP下载开始|" + locfileDir + "|" + ftpPath + "|" + fileDir);
                        }
                        boolean flag = FtpUtil.download(locfileDir, ftpPath, fileDir);
                        if(flag == false) {
                            params.put("returnCode", Constants.TPM_RETURN_CODE_FAIL);
                            params.put("reason","FTP文件下载失败");
                            if(log.isWarnEnabled()){
                                log.warn("MP处理TPM签名应答,FTP文件下载失败");
                            }
                            return params;
                        }
                        if(log.isInfoEnabled()){
                            log.info("MP处理TPM签名应答，FTP下载结束|" + locfileDir + "|" + ftpPath + "|" + fileDir);
                        }
                        int size =Integer.parseInt( FileUtils.getFileSize(locfileDir));
                        String md5 = Md5Utils.getMd5ByFile(new File(locfileDir));
                        if(size == 0) {
                            FtpUtil.remove(locfileDir);
                            FtpUtil.download(locfileDir,ftpPath,fileDir);
                            size =Integer.parseInt(FileUtils.getFileSize(locfileDir));
                            if(size == 0) {
                                params.put("returnCode", Constants.TPM_RETURN_CODE_FAIL);
                                params.put("reason","移动签名文件失败，大小为0");
                                if(log.isWarnEnabled()){
                                    log.warn("MP处理TPM签名应答,移动签名文件失败，大小为0");
                                }
                                return params;
                            }
                        }
                        String apvFilePath = fileDirPre + fileDirName;
                        params.put("apvFilePath",apvFilePath);
                        params.put("fileDir",fileDir);
                        params.put("size",size);
                        params.put("md5",md5);
                    }else{
                        //新增测试版本
                        //移动签名文件
                        String fileDirName = "";
                        if(!StringUtils.isEmpty(ftpPath)) {
                            fileDirName  = new File(ftpPath).getName();
                        }
                        //app+应用表主键+版本表主键,用作拆分包
                        Map condiMap = new HashMap();
                        condiMap.put("appId", app.getAppId());
                        Map newAppIdMap = appService.getAppNewVersion(condiMap);
                        String apvFilePath = StringUtils.getObjStr(newAppIdMap.get("apvFilePath"));
                        String apvFilePathPre = apvFilePath.substring(0,32);//获取该版本应用文件目录
                        String fileDirPre = apvFilePathPre +File.separator+UuidUtils.getRandomUuidWithoutSeparator()+File.separator;
                        String fileDir =appPathPre +fileDirPre;
                        String locfileDir = fileDir+fileDirName;
                        ftpPath = reFtpAppDir+File.separator+ftpPath;
                        if(log.isInfoEnabled()){
                            log.info("MP处理TPM签名应答，FTP下载开始|" + locfileDir + "|" + ftpPath + "|" + fileDir);
                        }
                        boolean flag = FtpUtil.download(locfileDir,ftpPath,fileDir);
                        if(flag == false) {
                            params.put("returnCode", Constants.TPM_RETURN_CODE_FAIL);
                            params.put("reason","移动签名文件失败");
                            if(log.isWarnEnabled()){
                                log.warn("MP处理TPM签名应答,签名失败");
                            }
                            return params;
                        }
                        if(log.isInfoEnabled()){
                            log.info("MP处理TPM签名应答,FTP下载结束|" + locfileDir + "|" + ftpPath + "|" + fileDir);
                        }
                        int size = Integer.parseInt(FileUtils.getFileSize(locfileDir));
                        String md5 = Md5Utils.getMd5ByFile(new File(locfileDir));
                        if(size == 0) {
                            FtpUtil.remove(locfileDir);
                            FtpUtil.download(locfileDir,ftpPath,fileDir);
                            size =Integer.parseInt(FileUtils.getFileSize(locfileDir));
                            if(size == 0) {
                                params.put("returnCode",Constants.TPM_RETURN_CODE_FAIL);
                                params.put("reason","移动签名文件失败，大小为0");
                                if(log.isWarnEnabled()){
                                    log.warn("MP处理TPM签名应答,移动签名文件失败，大小为0");
                                }
                                return params;
                            }
                        }
                        apvFilePath = fileDirPre + fileDirName;
                        params.put("apvFilePath",apvFilePath);
                        params.put("fileDir",fileDir);
                        params.put("size",size);
                        params.put("md5",md5);
                    }
                }
            }else {
                if(log.isInfoEnabled()){
                    log.info("MP处理TPM签名应答开始3..." + requestId);
                }
                //正式版本
                Auditinfo auditInfo = auditinfoMapper.selectByPrimaryKey(requestId);
                String auiType = auditInfo.getAuiType();
                String appId = auditInfo.getAppId();
                if(result.equals(Constants.TPM_RESULT_FAIL)) {//TPM处理失败
                    boolean isNoPass = false;
                    if(description.contains("签名驳回")){
                        params.put("returnCode",Constants.TPM_RETURN_CODE_SUCCESS);
                        params.put("reason","签名驳回");
                        isNoPass = true;
                    }else{
                        params.put("returnCode",Constants.TPM_RETURN_CODE_FAIL);
                        params.put("reason","TPM签名失败");
                        if(Constants.auditInfo.AUI_STATE_PENDING.getValue() == auditInfo.getAuiState()){
                            String auiBody = auditInfo.getAuiBody();
                            JSONObject auiBodyJson = JSON.parseObject(auiBody.trim());
                            String signFailNum = auiBodyJson.getString("signFailNum");
                            //获取签名失败次数
                            if(StringUtils.isEmpty(signFailNum)){
                                signFailNum = "1";
                                auiBodyJson.put("signFailNum",signFailNum);
                                auditInfo.setAuiBody(auiBodyJson.toJSONString());
                                auditinfoMapper.updateByPrimaryKey(auditInfo);
                            }else{
                                //如果大于3次，直接不通过
                                if(Integer.valueOf(signFailNum) > 3){
                                    isNoPass = true;
                                }else{
                                    signFailNum = String.valueOf(Integer.valueOf(signFailNum) + 1);
                                    auiBodyJson.put("signFailNum",signFailNum);
                                    auditInfo.setAuiBody(auiBodyJson.toJSONString());
                                    auditinfoMapper.updateByPrimaryKey(auditInfo);
                                }
                            }
                        }
                    }

                    if(isNoPass){
                        if(Constants.auiType.APP_FIRSTCOMMIT.getValue().equals(auiType)) {
                            app.setAppState(Constants.app.APP_STATE_DEALFAILURE.getValue());
                            appService.updateNotNull(app);
                        }
                        auditInfo.setAuiFailInfo(description);
                        auditInfo.setAuiState(Constants.auditInfo.AUI_STATE_NOPASS.getValue());
                        auditinfoMapper.updateByPrimaryKey(auditInfo);
                    }

                    if(log.isWarnEnabled()){
                        log.warn("MP处理TPM签名应答,签名失败");
                    }
                    return params;
                }

                //如果不是审核中状态，直接返回
                if(Constants.auditInfo.AUI_STATE_PENDING.getValue() != auditInfo.getAuiState()){
                    return params;
                }
                String auiBody = auditInfo.getAuiBody();
                JSONObject auiBodyJson = JSON.parseObject(auiBody.trim());
                if(log.isInfoEnabled()){
                    log.info("MP处理TPM签名应答开始4..." + requestId);
                }
                if(Constants.auiType.APP_FIRSTCOMMIT.getValue().equals(auiType)) {
                    if(log.isInfoEnabled()){
                        log.info("MP处理TPM签名应答开始5..." + requestId);
                    }
                    //移动签名文件
                    String fileDirName = "";
                    if(!StringUtils.isEmpty(ftpPath)) {
                        fileDirName = new File(ftpPath).getName();
                    }
                    //路径：app+UUID+UUID
                    String fileDirPre = UuidUtils.getRandomUuidWithoutSeparator() + File.separator + UuidUtils.getRandomUuidWithoutSeparator() + File.separator;
                    String fileDir = appPathPre + fileDirPre;
                    String locfileDir = fileDir + fileDirName;
                    ftpPath = reFtpAppDir + File.separator + ftpPath;
                    if(log.isInfoEnabled()){
                        log.info("MP处理TPM签名应答，FTP下载开始|" + locfileDir + "|" + ftpPath + "|" + fileDir);
                    }
                    boolean flag = FtpUtil.download(locfileDir, ftpPath, fileDir);
                    if(flag == false) {
                        params.put("returnCode", Constants.TPM_RETURN_CODE_FAIL);
                        params.put("reason","FTP文件下载失败");
                        if(log.isWarnEnabled()){
                            log.warn("MP处理TPM签名应答,FTP文件下载失败");
                        }
                        return params;
                    }
                    if(log.isInfoEnabled()){
                        log.info("MP处理TPM签名应答，FTP下载结束|" + locfileDir + "|" + ftpPath + "|" + fileDir);
                    }
                    int size =Integer.parseInt(FileUtils.getFileSize(locfileDir));
                    String md5 = Md5Utils.getMd5ByFile(new File(locfileDir));
                    if(size == 0) {
                        FtpUtil.remove(locfileDir);
                        FtpUtil.download(locfileDir,ftpPath,fileDir);
                        size =Integer.parseInt(FileUtils.getFileSize(locfileDir));
                        if(size == 0) {
                            params.put("returnCode", Constants.TPM_RETURN_CODE_FAIL);
                            params.put("reason","移动签名文件失败，大小为0");
                            if(log.isWarnEnabled()){
                                log.warn("MP处理TPM签名应答,移动签名文件失败，大小为0");
                            }
                            return params;
                        }
                    }

                    String apvFilePath = fileDirPre + fileDirName;
                    params.put("apvFilePath",apvFilePath);
                    params.put("fileDir",fileDir);
                    params.put("size",size);
                    params.put("md5",md5);
                    JSONObject signApkInfo = new JSONObject();
                    signApkInfo.put("apvFilePath",apvFilePath);
                    signApkInfo.put("fileDir",fileDir);
                    signApkInfo.put("size",size);
                    signApkInfo.put("md5",md5);
                    auiBodyJson.put("signApkInfo",signApkInfo);
                }else if(Constants.auiType.APP_VERSIONCOMMIT.getValue().equals(auiType)) {
                    if(log.isInfoEnabled()){
                        log.info("MP处理TPM签名应答开始6..." + requestId);
                    }
                    //移动签名文件
                    String fileDirName = "";
                    if(!StringUtils.isEmpty(ftpPath)) {
                        fileDirName  = new File(ftpPath).getName();
                    }
                    //app+应用表主键+版本表主键,用作拆分包
                    Map condiMap = new HashMap();
                    condiMap.put("appId", appId);
                    Map newAppIdMap = appService.getAppNewVersion(condiMap);
                    String apvFilePath = StringUtils.getObjStr(newAppIdMap.get("apvFilePath"));
                    String apvFilePathPre = apvFilePath.substring(0,32);//获取该版本应用文件目录
                    String fileDirPre = apvFilePathPre +File.separator+UuidUtils.getRandomUuidWithoutSeparator()+File.separator;
                    String fileDir =appPathPre +fileDirPre;
                    String locfileDir = fileDir+fileDirName;
                    ftpPath = reFtpAppDir+File.separator+ftpPath;
                    if(log.isInfoEnabled()){
                        log.info("MP处理TPM签名应答，FTP下载开始|" + locfileDir + "|" + ftpPath + "|" + fileDir);
                    }
                    boolean flag = FtpUtil.download(locfileDir,ftpPath,fileDir);
                    if(flag == false) {
                        params.put("returnCode", Constants.TPM_RETURN_CODE_FAIL);
                        params.put("reason","移动签名文件失败");
                        if(log.isWarnEnabled()){
                            log.warn("MP处理TPM签名应答,签名失败");
                        }
                        return params;
                    }
                    if(log.isInfoEnabled()){
                        log.info("MP处理TPM签名应答,FTP下载结束|" + locfileDir + "|" + ftpPath + "|" + fileDir);
                    }
                    int size = Integer.parseInt(FileUtils.getFileSize(locfileDir));
                    String md5 = Md5Utils.getMd5ByFile(new File(locfileDir));
                    if(size == 0) {
                        FtpUtil.remove(locfileDir);
                        FtpUtil.download(locfileDir,ftpPath,fileDir);
                        size =Integer.parseInt(FileUtils.getFileSize(locfileDir));
                        if(size == 0) {
                            params.put("returnCode",Constants.TPM_RETURN_CODE_FAIL);
                            params.put("reason","移动签名文件失败，大小为0");
                            if(log.isWarnEnabled()){
                                log.warn("MP处理TPM签名应答,移动签名文件失败，大小为0");
                            }
                            return params;
                        }
                    }

                    apvFilePath = fileDirPre + fileDirName;
                    params.put("apvFilePath",apvFilePath);
                    params.put("fileDir",fileDir);
                    params.put("size",size);
                    params.put("md5",md5);
                    JSONObject signApkInfo = new JSONObject();
                    signApkInfo.put("apvFilePath",apvFilePath);
                    signApkInfo.put("fileDir",fileDir);
                    signApkInfo.put("size",size);
                    signApkInfo.put("md5",md5);
                    auiBodyJson.put("signApkInfo",signApkInfo);
                }

                //为了防止签名系统重复点击签名通知，新建一个审核对象修改
                Auditinfo newAuditInfo = new Auditinfo();
                newAuditInfo.setAuiId(auditInfo.getAuiId());
                newAuditInfo.setAuiBody(auiBodyJson.toJSONString());
                auditinfoMapper.updateByPrimaryKeySelective(newAuditInfo);
            }
        }else{
            params.put("returnCode",Constants.TPM_RETURN_CODE_FAIL);
            params.put("reason","app is not exist");
            if(log.isWarnEnabled()){
                log.warn("MP处理TPM签名应答错误.查找不到应用,appid="+appIdT);
            }
            return params;
        }
        return params;
    }

    @Override
    public ResultData signatureGet(String param) {
        ResultData resultData = new ResultData();
        resultData.setReason("hello rest get " + param);
        return resultData;
    }

    @Override
    public Map signaturePostTest(Map<String, Object> params) {
        Map resMap = new HashMap();
        String appId = StringUtils.getObjStr(params.get("appId"));
        String requestId = StringUtils.getObjStr(params.get("requestId"));
        if(log.isInfoEnabled()){
            log.info("TPM 签名通知开始..."+requestId);
        }
        try{
            final Map resultMap = tpmMoveAppTest(params);
            resMap.put("returnCode",resultMap.get("returnCode"));
            resMap.put("appId",resultMap.get("appId"));
            resMap.put("requestId",resultMap.get("requestId"));
            resMap.put("reason",resultMap.get("reason"));

        }catch (Exception e){
            e.printStackTrace();
            resMap.put("returnCode",Constants.TPM_RETURN_CODE_FAIL);
            resMap.put("appId",appId);
            resMap.put("requestId",requestId);
            resMap.put("reason","系统处理失败");
            if(log.isInfoEnabled()){
                log.info("TPM 签名通知处理异常..."+requestId);
            }
            if(log.isErrorEnabled()){
                log.error("TPM 签名通知处理异常:",e);
            }
        }
        if(log.isInfoEnabled()){
            log.info("TPM 签名通知结束..."+requestId);
        }
        return  resMap;
    }


    private Map tpmMoveAppTest(Map<String, Object> map) throws Exception{
        Map<String, Object> params = new HashMap<String, Object>();
        String requestId = (String) map.get("requestId");
        if(log.isInfoEnabled()){
            log.info("MP处理TPM签名应答开始..." + requestId);
        }
        String result = (String) map.get("result");
        String ftpPath = (String) map.get("fileDir");
        String appIdT = (String) map.get("appId");
        String description = (String) map.get("description");
        params.put("returnCode",Constants.TPM_RETURN_CODE_SUCCESS);
        params.put("appId",appIdT);
        params.put("requestId",requestId);
        params.put("reason",result);

        if(StringUtils.isEmpty(requestId)) {//参数错误
            params.put("returnCode",Constants.TPM_RETURN_CODE_FAIL);
            params.put("reason","requestId is not null");
            if(log.isWarnEnabled()){
                log.warn("MP处理TPM签名应答错误.requestId为空");
            }
            return params;
        }
        if(log.isInfoEnabled()){
            log.info("MP处理TPM签名应答开始2.1..." + requestId);
        }
        App app = appService.selectByKey(appIdT);
        if(app != null){
            if(log.isInfoEnabled()){
                log.info("MP处理TPM签名应答开始2..." + requestId);
            }
            Map<String, Object> pathMap = new HashMap<>();
            pathMap.clear();
            Map resMap = PropertUtils.getAppPath(pathMap);
            String appPathPre = StringUtils.getObjStr(resMap.get(Constants.ABSOLUTELY_PRE_PATH));
            if(log.isInfoEnabled()){
                log.info("MP处理TPM签名应答开始3..." + requestId);
            }
                //正式版本
                Auditinfo auditInfo = auditinfoMapper.selectByPrimaryKey(requestId);
                String auiType = auditInfo.getAuiType();
                String appId = auditInfo.getAppId();
                if(result.equals(Constants.TPM_RESULT_FAIL)) {//TPM处理失败
                    boolean isNoPass = false;
                    if(description.contains("签名驳回")){
                        params.put("returnCode",Constants.TPM_RETURN_CODE_SUCCESS);
                        params.put("reason","签名驳回");
                        isNoPass = true;
                    }else{
                        params.put("returnCode",Constants.TPM_RETURN_CODE_FAIL);
                        params.put("reason","TPM签名失败");
                        if(Constants.auditInfo.AUI_STATE_PENDING.getValue() == auditInfo.getAuiState()){
                            String auiBody = auditInfo.getAuiBody();
                            JSONObject auiBodyJson = JSON.parseObject(auiBody.trim());
                            String signFailNum = auiBodyJson.getString("signFailNum");
                            //获取签名失败次数
                            if(StringUtils.isEmpty(signFailNum)){
                                signFailNum = "1";
                                auiBodyJson.put("signFailNum",signFailNum);
                                auditInfo.setAuiBody(auiBodyJson.toJSONString());
                            }else{
                                //如果大于3次，直接不通过
                                if(Integer.valueOf(signFailNum) > 3){
                                    isNoPass = true;
                                }else{
                                    signFailNum = String.valueOf(Integer.valueOf(signFailNum) + 1);
                                    auiBodyJson.put("signFailNum",signFailNum);
                                    auditInfo.setAuiBody(auiBodyJson.toJSONString());
                                }
                            }
                        }
                    }

                    if(isNoPass){
                        if(Constants.auiType.APP_FIRSTCOMMIT.getValue().equals(auiType)) {
                            app.setAppState(Constants.app.APP_STATE_DEALFAILURE.getValue());
                        }
                        auditInfo.setAuiFailInfo(description);
                        auditInfo.setAuiState(Constants.auditInfo.AUI_STATE_NOPASS.getValue());
                    }

                    if(log.isWarnEnabled()){
                        log.warn("MP处理TPM签名应答,签名失败");
                    }
                    return params;
                }

                //如果不是审核中状态，直接返回
                if(Constants.auditInfo.AUI_STATE_PENDING.getValue() != auditInfo.getAuiState()){
                    return params;
                }
                String auiBody = auditInfo.getAuiBody();
                JSONObject auiBodyJson = JSON.parseObject(auiBody.trim());
            if(log.isInfoEnabled()){
                log.info("MP处理TPM签名应答开始4..." + requestId);
            }
                if(Constants.auiType.APP_FIRSTCOMMIT.getValue().equals(auiType)) {
                    if(log.isInfoEnabled()){
                        log.info("MP处理TPM签名应答开始5..." + requestId);
                    }
                    //移动签名文件
                    String fileDirName = "";
                    if(!StringUtils.isEmpty(ftpPath)) {
                        fileDirName = new File(ftpPath).getName();
                    }
                    //路径：app+UUID+UUID
                    String fileDirPre = UuidUtils.getRandomUuidWithoutSeparator() + File.separator + UuidUtils.getRandomUuidWithoutSeparator() + File.separator;
                    String fileDir = appPathPre + fileDirPre;
                    String locfileDir = fileDir + fileDirName;
                    ftpPath = reFtpAppDir + File.separator + ftpPath;
                    if(log.isInfoEnabled()){
                        log.info("MP处理TPM签名应答，FTP下载开始|" + locfileDir + "|" + ftpPath + "|" + fileDir);
                    }
                    boolean flag = FtpUtil.download(locfileDir, ftpPath, fileDir);
                    if(flag == false) {
                        params.put("returnCode", Constants.TPM_RETURN_CODE_FAIL);
                        params.put("reason","FTP文件下载失败");
                        if(log.isWarnEnabled()){
                            log.warn("MP处理TPM签名应答,FTP文件下载失败");
                        }
                        return params;
                    }
                    if(log.isInfoEnabled()){
                        log.info("MP处理TPM签名应答，FTP下载结束|" + locfileDir + "|" + ftpPath + "|" + fileDir);
                    }
                    int size =Integer.parseInt(FileUtils.getFileSize(locfileDir));
                    String md5 = Md5Utils.getMd5ByFile(new File(locfileDir));
                    if(size == 0) {
                        FtpUtil.remove(locfileDir);
                        FtpUtil.download(locfileDir,ftpPath,fileDir);
                        size =Integer.parseInt(FileUtils.getFileSize(locfileDir));
                        if(size == 0) {
                            params.put("returnCode", Constants.TPM_RETURN_CODE_FAIL);
                            params.put("reason","移动签名文件失败，大小为0");
                            if(log.isWarnEnabled()){
                                log.warn("MP处理TPM签名应答,移动签名文件失败，大小为0");
                            }
                            return params;
                        }
                    }

                    String apvFilePath = fileDirPre + fileDirName;
                    params.put("apvFilePath",apvFilePath);
                    params.put("fileDir",fileDir);
                    params.put("size",size);
                    params.put("md5",md5);
                    JSONObject signApkInfo = new JSONObject();
                    signApkInfo.put("apvFilePath",apvFilePath);
                    signApkInfo.put("fileDir",fileDir);
                    signApkInfo.put("size",size);
                    signApkInfo.put("md5",md5);
                    auiBodyJson.put("signApkInfo",signApkInfo);
                }else if(Constants.auiType.APP_VERSIONCOMMIT.getValue().equals(auiType)) {
                    if(log.isInfoEnabled()){
                        log.info("MP处理TPM签名应答开始6..." + requestId);
                    }
                    //移动签名文件
                    String fileDirName = "";
                    if(!StringUtils.isEmpty(ftpPath)) {
                        fileDirName  = new File(ftpPath).getName();
                    }
                    //app+应用表主键+版本表主键,用作拆分包
                    Map condiMap = new HashMap();
                    condiMap.put("appId", appId);
                    Map newAppIdMap = appService.getAppNewVersion(condiMap);
                    String apvFilePath = StringUtils.getObjStr(newAppIdMap.get("apvFilePath"));
                    String apvFilePathPre = apvFilePath.substring(0,32);//获取该版本应用文件目录
                    String fileDirPre = apvFilePathPre +File.separator+UuidUtils.getRandomUuidWithoutSeparator()+File.separator;
                    String fileDir =appPathPre +fileDirPre;
                    String locfileDir = fileDir+fileDirName;
                    ftpPath = reFtpAppDir+File.separator+ftpPath;
                    if(log.isInfoEnabled()){
                        log.info("MP处理TPM签名应答，FTP下载开始|" + locfileDir + "|" + ftpPath + "|" + fileDir);
                    }
                    boolean flag = FtpUtil.download(locfileDir,ftpPath,fileDir);
                    if(flag == false) {
                        params.put("returnCode", Constants.TPM_RETURN_CODE_FAIL);
                        params.put("reason","移动签名文件失败");
                        if(log.isWarnEnabled()){
                            log.warn("MP处理TPM签名应答,签名失败");
                        }
                        return params;
                    }
                    if(log.isInfoEnabled()){
                        log.info("MP处理TPM签名应答,FTP下载结束|" + locfileDir + "|" + ftpPath + "|" + fileDir);
                    }
                    int size = Integer.parseInt(FileUtils.getFileSize(locfileDir));
                    String md5 = Md5Utils.getMd5ByFile(new File(locfileDir));
                    if(size == 0) {
                        FtpUtil.remove(locfileDir);
                        FtpUtil.download(locfileDir,ftpPath,fileDir);
                        size =Integer.parseInt(FileUtils.getFileSize(locfileDir));
                        if(size == 0) {
                            params.put("returnCode",Constants.TPM_RETURN_CODE_FAIL);
                            params.put("reason","移动签名文件失败，大小为0");
                            if(log.isWarnEnabled()){
                                log.warn("MP处理TPM签名应答,移动签名文件失败，大小为0");
                            }
                            return params;
                        }
                    }

                    apvFilePath = fileDirPre + fileDirName;
                    params.put("apvFilePath",apvFilePath);
                    params.put("fileDir",fileDir);
                    params.put("size",size);
                    params.put("md5",md5);
                    JSONObject signApkInfo = new JSONObject();
                    signApkInfo.put("apvFilePath",apvFilePath);
                    signApkInfo.put("fileDir",fileDir);
                    signApkInfo.put("size",size);
                    signApkInfo.put("md5",md5);
                    auiBodyJson.put("signApkInfo",signApkInfo);
                }

                auditInfo.setAuiBody(auiBodyJson.toJSONString());
        }else{
            params.put("returnCode",Constants.TPM_RETURN_CODE_FAIL);
            params.put("reason","app is not exist");
            if(log.isWarnEnabled()){
                log.warn("MP处理TPM签名应答错误.查找不到应用,appid="+appIdT);
            }
            return params;
        }
        return params;
    }
}
