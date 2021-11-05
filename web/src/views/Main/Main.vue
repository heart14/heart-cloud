<template>
  <div class="index-page body-container">
    <div class="body-container_title">
      首页
    </div>
    <div class="main">
      <el-form label-width="200px"
          ref="ruleForm" >
        <el-form-item label="用户状态：">
          <el-row>
            <el-col :span="14">
              <div>{{USER_STATUS_MAP[userInfo.userStatus]}}</div>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="原因：" v-if="userInfo.userStatus === 4 || userInfo.userStatus === 6">
          <el-row>
            <el-col :span="14">
              <div>{{userInfo.userCheckDesc}}</div>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="登录名：">
          <el-row>
            <el-col :span="14">
              <div>{{userInfo.userLoginName}}</div>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="中心名称：">
          <el-row>
            <el-col :span="14">
              <div>{{userInfo.userName}}</div>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="联系方式：">
          <el-row>
            <el-col :span="14">
              <div>{{userInfo.userTel}}</div>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="用户申请表管理" v-if="userInfo.userStatus === 0">
          <el-row>
            <el-col :span="4">
              <el-button type="text"
              @click="downloadTemplate">下载申请表模版</el-button>
            </el-col>
            <el-col :span="4">
              <el-button type="text" @click="dialogVisible = true">更改申请表模板</el-button>
            </el-col>
          </el-row>
        </el-form-item>
         <el-form-item label="用户申请表状态：" v-else>
          <el-row>
            <el-col :span="6">{{userInfo.applyFileUrl?'已上传':'未上传'}}</el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <el-upload
                class="upload-demo"
                ref="applyupload"
                :action="uplaodApplyUrl"
                :file-list="fileList"
                :auto-upload="false"
                :on-success="onApplySuccess"
                :headers="{
                  Authorization: token
                }"
                :on-change="onChange">
                <el-button type="text">{{userInfo.applyFileUrl?'重新上传':'点击上传'}}</el-button>
              </el-upload>
            </el-col>
            <el-col :span="4">
              <el-button type="text"
              @click="downloadTemplate">下载申请表模版</el-button>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item v-if="userInfo.userStatus != 0">
          <el-button type="primary" @click="uploadUserApplyTable"> 提交审核</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-dialog
      title="数据导入"
      width="600px"
      :visible="dialogVisible"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :before-close="handleClose"
    >
      <el-upload
        ref="templateupload"
        drag
        show-file-list
        :limit="1"
        :headers="{
          Authorization: token
        }"
        :auto-upload="false"
        :action="uplaodTemplateUrl"
        :on-success="onSuccess"
        :on-exceed="onExceed">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      </el-upload>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button size="medium" @click="handleClose">取 消</el-button>
        <el-button
          size="medium"
          type="primary"
          @click="modifyUserApplyTable"
        >上 传</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { USER_STATUS_MAP } from '../../utils/enum';
import store from '../../store';
import { getToken } from '../../utils';

export default {
  name: 'Main',
  data() {
    return {
      USER_STATUS_MAP,
      dialogVisible: false,
      fileList: [],
    };
  },
  computed: {
    userInfo() {
      return store.state.userInfo;
    },
    token() {
      return getToken();
    },
    uplaodTemplateUrl() {
      return `${this.$axios.defaults.baseURL}/usr/modifyApplyTemplate`;
    },
    uplaodApplyUrl() {
      return `${this.$axios.defaults.baseURL}/usr/uploadUserApplyTable`;
    },
  },
  methods: {
    onChange(file, fileList) {
      if (fileList.length > 0) {
        this.fileList = [fileList[fileList.length - 1]]; // 这一步，是 展示最后一次选择的csv文件
      }
    },
    handleClose() {
      this.dialogVisible = false;
    },
    downloadTemplate() {
      const link = document.createElement('a');
      link.href = `${this.$axios.defaults.baseURL}/file/downloadApplyTemplate`;
      document.body.appendChild(link);
      link.click();
      URL.revokeObjectURL(link.href); // 释放url
      document.body.removeChild(link);// 释放标签
    },
    beforeUplaod(file) {
      this.file = file;
      const fileType = file.name.substring(file.name.lastIndexOf('.') + 1);
      const f = fileType === 'doc';
      const f2 = fileType === 'docx';
      if (!f && !f2) {
        this.$notify({
          title: '提示',
          message: '上传文件只能是 doc/docx 格式！',
          type: 'warning',
        });
      }
      return f || f2;
    },
    onSuccess(response) {
      this.$refs.templateupload.clearFiles();
      if (response.state === 0) {
        this.$notify({
          title: '提示',
          message: '上传成功！',
          type: 'success',
        });
        this.handleClose(true);
      } else if (response.code === 401) {
        this.$alert('登录已失效，请重新登录！', '提示', {
          type: 'error',
          callback: () => {
            this.$router.replace('/login');
          },
        });
      } else {
        this.$notify({
          title: '提示',
          message: response.msg,
          type: 'error',
        });
      }
    },
    onExceed() {
      this.$notify({
        title: '提示',
        message: '当前限制选择 1 个文件！',
        type: 'warning',
      });
    },
    onApplySuccess(response) {
      if (response.state === 0) {
        this.$notify({
          title: '提示',
          message: '上传成功！',
          type: 'success',
        });
        store.dispatch('getUserInfo');
      } else if (response.code === 401) {
        this.$alert('登录已失效，请重新登录！', '提示', {
          type: 'error',
          callback: () => {
            this.$router.replace('/login');
          },
        });
        this.$refs.applyupload.clearFiles();
      } else {
        this.$notify({
          title: '提示',
          message: response.msg,
          type: 'error',
        });
        this.$refs.applyupload.clearFiles();
      }
    },
    uploadUserApplyTable() {
      if (this.fileList.length === 0) {
        this.$notify({
          title: '提示',
          message: '请先选择申请表',
          type: 'error',
        });
      } else {
        this.$refs.applyupload.submit();
      }
    },
    modifyUserApplyTable() {
      this.$refs.templateupload.submit();
    },
  },
};
</script>
<style lang="less">
.index-page {
  height: 100%;
  .main {
    width: 100%;
    height: 100%;
  }
  .file-icon{
    width: 30px;
    height: 30px;
  }
  .file-title{
    height: 30px;
    text-align: left;
    font-size: 20px;
    line-height: 30px;
  }
  /deep/ .el-list-enter-active,
  /deep/ .el-list-leave-active {
    transition: none;
  }
  /deep/ .el-list-enter,
  /deep/ .el-list-leave-active {
    opacity: 0;
  }
  /deep/ .el-upload-list {
    height: 40px;
  }
  .el-upload__tip {
    font-size: 13px;
    line-height: 32px;
    em {
      font-style: normal;
      color: #f00
    }
  }
}
</style>
