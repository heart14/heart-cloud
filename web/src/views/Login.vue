<template>
  <div class="login container">
    <div class="main">
      <div class="title">
        <span>上海市人类辅助生殖技术质量控制系统</span><br>
        <span><b>监管单位：</b>上海市卫生健康委员会</span>
      </div>
      <div class="form-wrapper">
        <el-tabs v-model.trim="activeName" @tab-click="tapClick">
          <el-tab-pane
            label="账号登录"
            name="1"
          >
            <el-form :model="account" ref="accountForm" :rules="rulesAccount">
              <el-form-item prop="userLoginName">
                <el-input
                  placeholder="请输入登录名"
                  v-model.trim="account.userLoginName"
                >
                  <i
                    slot="prefix"
                    class="el-input__icon el-icon-user"
                  ></i>
                </el-input>
              </el-form-item>
              <el-form-item prop="userLoginPass">
                <el-input
                  placeholder="请输入密码"
                  v-model.trim="account.userLoginPass"
                  type="password"
                >
                  <i
                    slot="prefix"
                    class="el-input__icon el-icon-lock"
                  ></i>
                </el-input>
              </el-form-item>
              <el-form-item prop="captchaCode">
                <el-row>
                  <el-col :span="7">
                    <img
                      width="100%"
                      alt="验证码"
                      style="display:block;height:38px; border: 1px solid #909399"
                      :src="captchaInfo.captcha"
                      @click="getCaptcha"
                    >
                  </el-col>
                  <el-col
                    :span="15"
                    :offset="2"
                  >
                    <el-input
                      placeholder="请输入验证码"
                      v-model.trim="account.captchaCode"
                      maxlength="4"
                    >
                    </el-input>
                  </el-col>
                </el-row>
              </el-form-item>
              <el-form-item>
                <el-row>
                  <el-col :span="11">
                    <el-button
                      style="width:100%"
                      type="primary"
                      @click="accountLogin"
                    >登录</el-button>
                  </el-col>
                  <el-col
                    :span="11"
                    :offset="2"
                  >
                    <el-button
                      style="width:100%"
                      type="primary"
                      @click="registered"
                    >注册</el-button>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="24" style="text-align:right">
                    <el-button type="text" @click="forgetPassword">忘记密码？</el-button>
                  </el-col>
                </el-row>
              </el-form-item>
            </el-form>
          </el-tab-pane>
          <el-tab-pane
            label="手机登录"
            name="2"
          >
            <el-form :model="phoneLogin" ref="phoneForm" :rules="rulesPhoneLogin">
              <el-form-item prop="phone">
                <el-input
                  placeholder="请输入手机号码"
                  v-model.trim="phoneLogin.phone"
                  maxlength="11"
                >
                  <el-button
                    slot="append"
                    type="primary"
                    :disabled="codeDisabled"
                    @click="getSmsCode"
                  >
                    {{ codeMsg }}
                  </el-button>
                </el-input>
              </el-form-item>
              <el-form-item prop="code">
                <el-input
                  placeholder="请输入验证码"
                  v-model.trim="phoneLogin.captchaCode"
                >
                </el-input>
              </el-form-item>
              <el-form-item>
                <el-row>
                  <el-col :span="11">
                    <el-button
                      style="width:100%"
                      type="primary"
                      @click="phoneLoginHandle"
                    >登录</el-button>
                  </el-col>
                  <el-col
                    :span="11"
                    :offset="2"
                  >
                    <el-button
                      style="width:100%"
                      type="primary"
                      @click="registered"
                    >注册</el-button>
                  </el-col>
                </el-row>
              </el-form-item>
            </el-form>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
  </div>
</template>
<script>
import { storage } from '../utils';
import store from '../store';

export default {
  name: 'login',
  data() {
    const validateTel = (rule, value, callback) => {
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
      captchaInfo: {
        captchaId: '',
        captcha: '',
      },
      account: {
        userLoginName: '',
        userLoginPass: '',
        captchaCode: '',
      },
      phoneLogin: {
        phone: '',
        captchaCode: '',
        captchaId: '',
      },
      rulesAccount: {
        userLoginName: [
          { required: true, message: '请输入中心名称', trigger: 'blur' },
        ],
        userLoginPass: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
        captchaCode: [
          { required: true, message: '请输入验证码', trigger: 'blur' },
        ],
      },
      rulesPhoneLogin: {
        phone: [
          // { required: true, message: '请输入手机号码', trigger: 'blur' },
          { required: true, validator: validateTel, trigger: 'blur' },
        ],
        captchaCode: [
          { required: true, message: '请输入验证码', trigger: 'blur' },
        ],
      },
      activeName: '1',
      countTime: 60,
      timer: null,
      codeDisabled: false,
      codeMsg: '获取验证码',
    };
  },
  created() {
    this.getCaptcha();
  },
  methods: {
    tapClick() {
      this.$refs.accountForm.clearValidate();
      this.$refs.phoneForm.clearValidate();
    },
    getCaptcha() {
      this.$axios.post('/usr/kaptcha').then((r) => {
        if (r.state === 0) {
          this.captchaInfo = r.data;
        }
      });
    },
    phoneLoginHandle() {
      this.$refs.phoneForm.validate((valid) => {
        if (valid) {
          this.$axios.post('/usr/phonelogin', {
            userTel: this.phoneLogin.phone,
            captchaId: this.phoneLogin.captchaId,
            captchaCode: this.phoneLogin.captchaCode,
          }).then((res) => {
            if (res.state === 0) {
              storage('token', res.data.accessToken);
              store.dispatch('getUserInfo');
              this.$router.replace('/');
            }
          });
        } else {
          return false;
        }
        return true;
      });
    },
    accountLogin() {
      this.$refs.accountForm.validate((valid) => {
        if (valid) {
          this.$axios.post('/usr/login', {
            ...this.account,
            captchaId: this.captchaInfo.captchaId,
          }).then((res) => {
            if (res.state === 0) {
              storage('token', res.data.accessToken);
              store.dispatch('getUserInfo');
              this.$router.replace('/');
            }
          });
        } else {
          return false;
        }
        return true;
      });
    },
    registered() {
      this.$router.push('/registered');
    },
    getSmsCode() {
      // eslint-disable-next-line consistent-return
      this.$refs.phoneForm.validateField(['phone'], (valid) => {
        if (!valid) {
          if (!this.timer) {
            this.$axios.post('/usr/smskaptcha', {
              action: 'login',
              userTel: this.phoneLogin.phone.toString(),
            }).then((r) => {
              if (r.state === 0) {
                this.phoneLogin.captchaId = r.data.captchaId;
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
    forgetPassword() {
      this.$router.push('/ForgetPassword');
    },
  },
};
</script>
<style lang="less">
.login {
  background: url("../assets/images/login_bg.jpg") no-repeat;
  background-size: cover;
  .main {
    width: 550px;
    position: relative;
    top: 45%;
    left: 50%;
    transform: translate(-50%, -50%);
    .title {
      text-align: center;
      margin-bottom: 15px;
      font-weight: bold;
      span {
        font-size: 32px;
        color: #409eff;
        font-style: italic;
        line-height: 48px;
        b {
          color: #e6a23c;
          display: inline-block;
          padding: 0 5px;
        }
      }
    }
    .form-wrapper {
      box-sizing: border-box;
      width: 90%;
      margin: 0 auto;
      border: 1px solid #d9d9d9;
      background-color: #fff;
      padding: 20px 40px 30px;
    }
  }
}
</style>
