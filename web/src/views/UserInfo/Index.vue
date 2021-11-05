<template>
  <div class="userInfo body-container">
    <div class="body-container_title">
      用户信息
    </div>
    <div class="main">
      <el-form label-width="200px" size="small" style="width: 80%;margin:0 auto;">
        <el-form-item label="审核未通过原因">
          <el-input  v-model="formData.reason" readonly></el-input>
        </el-form-item>
        <el-form-item label="用户登陆名称">
          <el-input  v-model="formData.userName" readonly></el-input>
        </el-form-item>
        <el-form-item label="手机号码">
          <el-input  v-model="formData.phoneNumber" placeholder="请输入手机号码"></el-input>
        </el-form-item>
        <el-form-item label="医院名称">
          <el-input  v-model="formData.hospitalName" placeholder="请输入医院名称"></el-input>
        </el-form-item>
        <el-form-item label="中心名称">
          <el-input  v-model="formData.centerName" placeholder="请输入中心名称"></el-input>
        </el-form-item>
        <el-form-item label="单位类型">
          <el-input  v-model="formData.companyName" placeholder="请输入单位类型"></el-input>
        </el-form-item>
        <el-form-item label="所在省">
          <el-input  v-model="formData.proviceName" readonly></el-input>
        </el-form-item>
        <el-form-item label="所在市">
          <el-select v-model="formData.cityName">
            <el-option label="北京市" value="北京市"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="负责人">
          <el-input  v-model="formData.principal" placeholder="请输入负责人"></el-input>
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input  v-model="formData.contractNumber" placeholder="请输入联系电话"></el-input>
        </el-form-item>
        <el-form-item label="E-mail">
          <el-input  v-model="formData.email" placeholder="请输入E-mail"></el-input>
        </el-form-item>
        <el-form-item label="通讯地址">
          <el-input  v-model="formData.address" placeholder="请输入通讯地址"></el-input>
        </el-form-item>
        <el-form-item label="已开展的项目">
          <el-checkbox-group v-model="formData.checkList">
            <el-checkbox label="AIH"></el-checkbox>
            <el-checkbox label="AID"></el-checkbox>
            <el-checkbox label="IVF"></el-checkbox>
            <el-checkbox label="ICSI"></el-checkbox>
            <el-checkbox label="PGD"></el-checkbox>
            <el-checkbox label="受卵"></el-checkbox>
            <el-checkbox label="多胎妊娠减胎术"></el-checkbox>
          </el-checkbox-group>
        </el-form-item>

        <el-form-item label="供精管理员">
          <el-input  v-model="formData.address" placeholder="请输入供精管理员"></el-input>
        </el-form-item>
        <el-form-item label="供精管理员联系方式">
          <el-input  v-model="formData.address" placeholder="请输入供精管理员联系方式"></el-input>
        </el-form-item>
        <el-form-item label="AID妊娠率">
          <el-input  v-model="formData.address" placeholder="请输入AID妊娠率"></el-input>
        </el-form-item>
        <el-form-item label="IVF/ICSI妊娠率">
          <el-input  v-model="formData.address" placeholder="请输入IVF/ICSI妊娠率"></el-input>
        </el-form-item>
        <el-form-item label="申请开展项目">
          <el-checkbox-group v-model="formData.checkList">
            <el-checkbox label="AIH"></el-checkbox>
            <el-checkbox label="AID"></el-checkbox>
            <el-checkbox label="IVF"></el-checkbox>
            <el-checkbox label="ICSI"></el-checkbox>
            <el-checkbox label="PGD"></el-checkbox>
            <el-checkbox label="受卵"></el-checkbox>
            <el-checkbox label="多胎妊娠减胎术"></el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="总面积">
          <el-input  v-model="formData.address" placeholder="请输入总面积"></el-input>
        </el-form-item>
        <el-form-item label="培养室面积">
          <el-input  v-model="formData.address" placeholder="请输入培养室面积"></el-input>
        </el-form-item>
        <el-form-item label="是否有病房">
          <el-select v-model="formData.room">
            <el-option label="是" value="是"></el-option>
            <el-option label="否" value="否"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="病房床位数">
          <el-input  v-model="formData.address" placeholder="请输入病房床位数"></el-input>
        </el-form-item>
        <el-form-item label="工作人员总数">
          <el-input  v-model="formData.address" placeholder="请输入工作人员总数"></el-input>
        </el-form-item>
        <el-form-item label="临床医生数">
          <el-input  v-model="formData.address" placeholder="请输入临床医生数"></el-input>
        </el-form-item>
        <el-form-item label="培养室工作人员数">
          <el-input  v-model="formData.address" placeholder="请输入培养室工作人员数"></el-input>
        </el-form-item>
        <el-form-item label="护理人员数">
          <el-input  v-model="formData.address" placeholder="请输入护理人员数"></el-input>
        </el-form-item>
        <b class="sub-title">现有设备</b>

        <el-row>
          <el-col :span="12">
            <el-form-item label="B超数">
              <el-input  v-model="formData.address" placeholder="请输入B超数"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
             <el-form-item label="培养箱数">
              <el-input  v-model="formData.address" placeholder="请输入培养箱数"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="B超数">
              <el-input  v-model="formData.address" placeholder="请输入B超数"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
             <el-form-item label="培养箱数">
              <el-input  v-model="formData.address" placeholder="请输入培养箱数"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="超净台数">
              <el-input  v-model="formData.address" placeholder="请输入超净台数"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
             <el-form-item label="体视显微镜数">
              <el-input  v-model="formData.address" placeholder="请输入体视显微镜数"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="倒置显微镜数">
              <el-input  v-model="formData.address" placeholder="请输入倒置显微镜数"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
             <el-form-item label="显微注射系统数">
              <el-input  v-model="formData.address" placeholder="请输入显微注射系统数"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="离心机数">
              <el-input  v-model="formData.address" placeholder="请输入离心机数"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
             <el-form-item label="液氮罐数">
              <el-input  v-model="formData.address" placeholder="请输入液氮罐数"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="AIH证书">
         <el-upload
            class="avatar-uploader"
            action="https://jsonplaceholder.typicode.com/posts/"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="AID证书">
          <el-upload
            class="avatar-uploader"
            action="https://jsonplaceholder.typicode.com/posts/"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="IVF/ICSI证书">
          <el-upload
            class="avatar-uploader"
            action="https://jsonplaceholder.typicode.com/posts/"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="PGD证书">
          <el-upload
            class="avatar-uploader"
            action="https://jsonplaceholder.typicode.com/posts/"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item>
          <el-button type="primary">提交修改</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
export default {
  name: 'UserInfo',
  data() {
    return {
      formData: {
        reason: 'asdasdasd',
        userName: 'YupingTest',
        phoneNumber: '',
        hospitalName: '',
        centerName: '',
        companyName: '',
        proviceName: '北京市',
        cityName: '北京市',
        principal: '',
        contractNumber: '',
        email: '',
        address: '',
        checkList: [],
        room: '是',
      },
      imageUrl: '',
    };
  },
  methods: {
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 10;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
  },
};
</script>
<style lang="less">

  .userInfo {
    .el-form-item {
      margin-bottom: 5px;
      .el-form-item__label {
        background-color: #66B1FF;
        color: #fff;
      }
      .el-form-item__content {
        padding: 0 10px;
      }
      .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
      }
      .avatar-uploader .el-upload:hover {
        // /82D5C6
        border-color: #409EFF;
      }
      .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
      }
      .avatar {
        width: 178px;
        height: 178px;
        display: block;
      }
    }
    .sub-title {
      display: block;
      width: 100%;
      line-height: 40px;
    }
  }
</style>
