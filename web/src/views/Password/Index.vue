<template>
  <div class="password-change body-container">
    <div class="body-container_title">
      密码修改
    </div>
    <div class="main">
      <div class="tips">请完整填写以下信息进行修改。</div>
      <div class="form">
        <i class="form_tips">输入原密码</i>
        <el-form
          ref="form"
          label-width="120px"
          :model="form"
          :rules="rules"
        >
          <el-form-item
            label="原密码:"
            prop="oldUserLoginPass"
          >
            <el-input
              v-model.trim="form.oldUserLoginPass"
              clearable
              type="password"
              placeholder="请输入原密码/验证码"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="新密码:"
            prop="userLoginPass"
          >
            <el-input
              v-model.trim="form.userLoginPass"
              clearable
              type="password"
              placeholder="请输入新密码"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="确认密码:"
            prop="reUserLoginPass"
          >
            <el-input
              v-model.trim="form.reUserLoginPass"
              clearable
              type="password"
              placeholder="请输入确认密码"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              @click="submitForm"
            >提交</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>
<script>

export default {
  name: 'password-change',
  data() {
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else if (value.length < 6 || value.length > 32) {
        callback(new Error('密码长度在 6 到 32 个字符'));
      } else {
        if (this.form.reUserLoginPass !== '') {
          this.$refs.form.validateField('reUserLoginPass');
        }
        callback();
      }
    };
    const validateConfirmPass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.form.userLoginPass) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      form: {
        oldUserLoginPass: '',
        userLoginPass: '',
        reUserLoginPass: '',
      },
      rules: {
        oldUserLoginPass: [
          { required: true, message: '请输入原密码或验证码', trigger: 'blur' },
        ],
        userLoginPass: [
          { required: true, validator: validatePass, trigger: 'blur' },
        ],
        reUserLoginPass: [
          { required: true, validator: validateConfirmPass, trigger: 'blur' },

        ],
      },
    };
  },
  methods: {
    submitForm() {
      // eslint-disable-next-line consistent-return
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.$axios.post('/usr/modify/password', { ...this.form }).then((res) => {
            if (res.state === 0) {
              this.$notify({
                title: '提示',
                message: '修改成功！',
                type: 'success',
              });
              this.form = {
                oldUserLoginPass: '',
                userLoginPass: '',
                reUserLoginPass: '',
              };
            }
          });
        } else {
          return false;
        }
      });
    },
  },
};
</script>
<style lang="less">
.password-change {
  .main {
    .tips {
      font-size: 13px;
    }
    .form {
      width: 40%;
      margin: 20px auto;

      .form_tips {
        display: inline-block;
        width: 100%;
        text-align: center;
        font-size: 16px;
        color: red;
        font-style: normal;
        line-height: 40px;
      }
      // .el-form-item__label {
      //   background-color: #66B1FF;
      //   color: #fff;
      // }
    }
  }
}
</style>
