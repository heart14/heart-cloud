<template>
  <div class="data-report-upload">
    <el-dialog
      title="数据导入"
      width="600px"
      :visible="dialogVisible"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :before-close="handleClose"
    >
      <el-upload
        ref="upload"
        drag
        show-file-list
        :limit="1"
        :headers="{
          Authorization: token
        }"
        :data="{
          overrideFlag: overrideFlag
        }"
        :auto-upload="false"
        :action="uplaodUrl"
        :before-upload="beforeUplaod"
        :on-success="onSuccess"
        :on-exceed="onExceed"
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div
          class="el-upload__tip"
          slot="tip"
        >请按照模板上传 <em>xls</em> 或 <em>xlsx</em> 格式的文件</div>
      </el-upload>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button size="medium" @click="handleClose">取 消</el-button>
        <el-button
          size="medium"
          type="primary"
          @click="submitData"
        >上 传</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { getToken } from '../../utils';

export default {
  name: 'data-report-upload',
  data() {
    return {
      dialogVisible: true,
      overrideFlag: false,
      file: '',
    };
  },
  computed: {
    token() {
      return getToken();
    },
    uplaodUrl() {
      return `${this.$axios.defaults.baseURL}/report/upload`;
    },
  },
  methods: {
    handleClose(flag = false) {
      this.$emit('listenToUpload', flag);
    },
    submitData() {
      this.$refs.upload.submit();
    },
    beforeUplaod(file) {
      this.file = file;
      const fileType = file.name.substring(file.name.lastIndexOf('.') + 1);
      const f = fileType === 'xls';
      const f2 = fileType === 'xlsx';
      if (!f && !f2) {
        this.$notify({
          title: '提示',
          message: '上传文件只能是 xls/xlsx 格式！',
          type: 'warning',
        });
      }
      return f || f2;
    },
    onSuccess(response) {
      if (response.state === 0) {
        if (response.code === 1021) {
          this.$confirm('当前年份数据已上传, 是否覆盖?', '提示', {
            confirmButtonText: '是',
            cancelButtonText: '否',
            type: 'warning',
          }).then(() => {
            const formData = new FormData();
            formData.append('file', this.file);
            formData.append('overrideFlag', true);
            this.$axios.post('/report/upload', formData, {
              headers: {
                'Content-Type': 'multipart/form-data',
                Authorization: window.sessionStorage.getItem('token'),
              },
            }).then((res) => {
              if (res.state === 0) {
                this.$notify({
                  title: '提示',
                  message: '覆盖成功！',
                  type: 'success',
                });
                this.handleClose(true);
              }
            });
          });
        } else {
          this.$notify({
            title: '提示',
            message: '上传成功！',
            type: 'success',
          });
          this.handleClose(true);
        }
      } else if (response.code === 401) {
        this.$alert('登录已失效，请重新登录！', '提示', {
          type: 'error',
          callback: () => {
            this.$router.replace('/login');
          },
        });
        this.$refs.upload.clearFiles();
      } else {
        this.$notify({
          title: '提示',
          message: response.msg,
          type: 'error',
        });
        this.$refs.upload.clearFiles();
      }
    },
    onExceed() {
      this.$notify({
        title: '提示',
        message: '当前限制选择 1 个文件！',
        type: 'warning',
      });
    },
  },
};
</script>
<style lang="less">
.data-report-upload {
  .el-upload,
  .el-upload-dragger {
    width: 100%;
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
