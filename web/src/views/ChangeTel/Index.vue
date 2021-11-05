<template>
  <div class="change-tel body-container">
    <div class="body-container_title">
      换绑手机
    </div>
    <div class="main">
      <div class="tips">请完整填写以下信息进行修改。</div>
      <div class="regisster-form">
        <i class="form_tips">输入原手机号</i>
        <el-form
          ref="ruleForm"
          label-width="120px"
          :model="ruleForm"
          :rules="rules"
        >
          <el-form-item
            label="原手机号"
            prop="oldUserTel"
          >
            <el-input
              v-model.trim="ruleForm.oldUserTel"
              :placeholder=oldUserTelPlaceholder
            ></el-input>
          </el-form-item>
          <el-form-item
            label="新手机号"
            prop="newUserTel"
          >
            <el-input
              v-model.trim="ruleForm.newUserTel"
              placeholder="请输入新手机号"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="短信验证码"
            prop="smscaptchaCode"
          >
            <el-input
              v-model.trim="ruleForm.smscaptchaCode"
              placeholder="请输入验证码"
              maxlength="4"
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

          <el-form-item>
            <el-button
              type="primary"
              @click="submit"
            >提交</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>
<script>
import store from '../../store';

export default {
  name: 'ForgetPassword',
  computed: {
    oldUserTelPlaceholder() {
      return `请输入原手机号  ${store.state.userInfo.userTel}`;
    },
  },
  data() {
    const validateNewTel = (rule, value, callback) => {
      const reg = /^[1]([3-9])[0-9]{9}$/;
      if (value === '') {
        callback(new Error('请输入新手机号'));
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
        newUserTel: '',
        oldUserTel: '',
      },
      rules: {
        oldUserTel: [
          { required: true, validator: validateNewTel, trigger: 'blur' },
        ],
        smscaptchaCode: [
          { required: true, message: '请输入短信验证码', trigger: 'blur' },
        ],
        newUserTel: [
          { required: true, validator: validateNewTel, trigger: 'blur' },
        ],
      },
    };
  },
  methods: {
    submit() {
      // eslint-disable-next-line consistent-return
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          this.$axios.post('/usr/modify/tel ', {
            smscaptchaCode: this.ruleForm.smscaptchaCode,
            oldUserTel: this.ruleForm.oldUserTel.toString(),
            newUserTel: this.ruleForm.newUserTel.toString(),
            captchaId: this.smscaptchaId,
          }).then((res) => {
            if (res.state === 0) {
              this.$notify({
                title: '成功',
                message: '修改成功！',
                type: 'success',
              });
              store.dispatch('getUserInfo');
              this.ruleForm = {
                smscaptchaCode: '',
                newUserTel: '',
                oldUserTel: '',
              };
            }
          });
        } else {
          return false;
        }
      });
    },
    getSmsCode() {
      // eslint-disable-next-line consistent-return
      this.$refs.ruleForm.validateField(['newUserTel'], (valid) => {
        if (!valid) {
          if (!this.timer) {
            this.$axios.post('/usr/smskaptcha', {
              action: 'resetTel',
              userTel: this.ruleForm.newUserTel.toString(),
              oldUserTel: this.ruleForm.oldUserTel.toString,
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
.change-tel {
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
    .tips {
      font-size: 13px;
    }
    .title {
      font-weight: bold;
      margin-top: 30px;
      font-size: 20px;
    }
    .regisster-form {
      width: 40%;
      margin: 20px auto;
      .captchaCode {
        display: block;
        border: 1px solid #d9d9d9;
        width: 100%;
        height: 100%;
      }
      .form_tips {
        display: inline-block;
        width: 100%;
        text-align: center;
        font-size: 16px;
        color: red;
        font-style: normal;
        line-height: 40px;
      }
    }
  }
}
</style>
