<template>
  <div class="forget-password">
    <div class="header">
      <img
        src="../assets/images/banner3.jpg"
        alt="banner"
      >
    </div>
    <div class="main">
      <div class="title">忘记密码</div>
      <el-divider></el-divider>
      <div class="regisster-form">
        <el-form
          :model="ruleForm"
          :rules="rules"
          ref="ruleForm"
          label-width="100px"
        >
          <el-form-item
            label="手机号码"
            prop="userTel"
          >
            <el-row>
              <el-col :span="14">
                <el-input
                  v-model.trim.number="ruleForm.userTel"
                  placeholder="请输入手机号码"
                  maxlength="11"
                ></el-input>
              </el-col>
            </el-row>
          </el-form-item>
          <el-form-item
            label="短信验证码"
            prop="smscaptchaCode"
          >
            <el-row>
              <el-col :span="14">
                <el-input
                  v-model.trim="ruleForm.smscaptchaCode"
                  placeholder="请输入验证码"
                  maxlength="4">
                  <el-button
                    slot="append"
                    type="primary"
                    :disabled="codeDisabled"
                    @click="getSmsCode"
                  >
                  {{ codeMsg }}
                  </el-button>
                </el-input>
              </el-col>
            </el-row>
          </el-form-item>
          <el-form-item
            label="登录密码"
            prop="userLoginPass"
          >
            <el-row>
              <el-col :span="14">
                <el-input
                  type="password"
                  v-model.trim="ruleForm.userLoginPass"
                  placeholder="请输入登录密码"
                ></el-input>
              </el-col>
            </el-row>
          </el-form-item>
        </el-form>
        <el-row>
          <el-col
            :span="6"
            :offset="3"
          >
            <el-button
              style="width:100%"
              type="primary"
              @click="submit"
            >确定</el-button>
          </el-col>
          <el-col
            :span="6"
            :offset="2"
          >
            <el-button
              style="width:100%"
              type="primary"
              @click="cancelReg"
            >取消</el-button>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>
<script>

export default {
  name: 'ForgetPassword',
  data() {
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else if (value.length < 6 || value.length > 32) {
        callback(new Error('密码长度在 6 到 32 个字符'));
      } else {
        callback();
      }
    };
    const validateUserTel = (rule, value, callback) => {
      const reg = /^[1]([3-9])[0-9]{9}$/;
      if (value === '') {
        callback(new Error('请输入手机号码'));
      } else if (!reg.test(value)) {
        callback(new Error('请输入正确格式的手机号'));
      } else {
        callback();
      }
    };
    return {
      countTime: 60,
      timer: null,
      codeDisabled: false,
      codeMsg: '获取验证码',
      smscaptchaId: '',
      ruleForm: {
        smscaptchaCode: '',
        userLoginPass: '',
        userTel: '',
      },
      rules: {
        smscaptchaCode: [
          { required: true, message: '请输入短信验证码', trigger: 'blur' },
        ],
        userLoginPass: [
          { required: true, validator: validatePass, trigger: 'blur' },
        ],
        userTel: [
          { required: true, validator: validateUserTel, trigger: 'blur' },
        ],
      },
    };
  },
  methods: {
    cancelReg() {
      this.$router.push('/login');
    },
    submit() {
      // eslint-disable-next-line consistent-return
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          this.$axios.post('/usr/reset ', {
            ...this.ruleForm,
            userTel: this.ruleForm.userTel.toString(),
            captchaId: this.smscaptchaId,
          }).then((res) => {
            if (res.state === 0) {
              this.$notify({
                title: '成功',
                message: '密码重置成功！',
                type: 'success',
              });
              this.$router.push('/login');
            }
          });
        } else {
          return false;
        }
      });
    },
    getSmsCode() {
      // eslint-disable-next-line consistent-return
      this.$refs.ruleForm.validateField(['userTel'], (valid) => {
        if (!valid) {
          if (!this.timer) {
            this.$axios.post('/usr/smskaptcha', {
              action: 'resetPass',
              userTel: this.ruleForm.userTel.toString(),
            }).then((r) => {
              if (r.state === 0) {
                this.smscaptchaId = r.data.captchaId;
                // console.log(this.smscaptchaId);
                this.$message.success({
                  showClose: true,
                  duration: 2000,
                  message: '验证码发送成功！',
                });
                this.timer = setInterval(() => {
                  if (this.countTime > 0 && this.countTime <= 60) {
                    this.countTime -= 1;
                    if (this.countTime !== 0) {
                      this.codeMsg = `重新发送(${this.countTime})`;
                    } else {
                      clearInterval(this.timer);
                      this.codeMsg = '获取验证码';
                      this.countTime = 60;
                      this.timer = null;
                      this.codeDisabled = false;
                    }
                  }
                }, 1000);
              }
            });
          }
        } else {
          return false;
        }
      });
    },
  },
};
</script>
<style lang="less" scoped>
.forget-password {
  .header {
    background-color: #087279;
    img {
      width: 100%;
      height: 110px;
      display: block;
      margin: 0 auto;
    }
  }
  .main {
    max-width: 1200px;
    padding: 0 50px 100px;
    margin: 0 auto;
    .title {
      font-weight: bold;
      margin-top: 30px;
      font-size: 20px;
    }
    .regisster-form {
      width: 60%;
      margin: 20px auto 0;
      .captchaCode {
        display: block;
        border: 1px solid #d9d9d9;
        width: 100%;
        height: 100%;
      }
    }
  }
}
</style>
