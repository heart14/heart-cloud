<template>
  <div class="registered">
    <div class="header">
      <img
        src="../assets/images/banner3.jpg"
        alt="banner"
      >
    </div>
    <div class="main">
      <div class="title">注册本站帐号</div>
      <el-divider></el-divider>
      <div class="notes">
        请完整填写以下信息进行注册。<br>
        注册完成后，该帐号将作为您在本站的通行帐号，您可以享受本站提供的各种服务。
      </div>
      <div class="terms">
        <div class="terms-title">上海市人类辅助生殖技术质量控制系统使用协议</div>
        <div class="terms-detail">
          <span style="text-indent:2em">
            上海市人类辅助生殖技术质量控制系统是由中华医学会生殖医学分会筹建，服务于广大生殖中心进行数据管理及质量控制工作的专业性平台。
            本系统主要用于各生殖中心上传本中心年度汇总数据并对数据进行统计分析，辅助进行各项质量控制，
            以期提高中心各项技术水平，进而推动全国辅助生殖技术的发展。
            各生殖中心使用本系统过程中需遵守以下使用协议条款：
          </span>
          <span>1. 中华医学会生殖医学分会作为平台提供者拥有本软件系统版权，负责系统的日常管理和维护；全体上传数据生殖中心共同拥有系统数据的所有权。</span>
          <span>2. 各生殖中心按时上传本中心数据，并保证上传数据真实性，
            上传本中心数据后即可使用系统各项功能对本中心数据进行相应的统计分析，生成相应的统计报表和质控图表。
          </span>
          <span>3. 各生殖中心可以引用系统数据用于专业技术领域，
            引用数据时标明数据出处：“本数据来源于中华医学会生殖医学分会辅助生殖技术数据上报系统
            （Data were obtained from CSRM ART Reporting System data base）”，
            使用数据产生的技术成果归数据使用者所有；严禁将系统数据用于任何商业活动。
          </span>
          <span>4. 上传数据为单位行为，由中心负责人指定专人进行操作；因个人利用单位数据使用系统带来的任何纠纷与中华医学会生殖医学分会无关。</span>
          <span>5. 各生殖中心需注意系统数据的隐私保护，妥善保管本中心账号密码，不得将账号密码转借他人使用。</span>
          <span>6. 各生殖中心用户使用系统过程中出现违反上述协议情况时，中华医学会生殖医学分会有权停止该中心使用本系统。</span>
          <span>7. 本生殖中心承诺：<b>按时上传数据并保证所上传数据真实可靠，所提供信息资料真实有效。</b></span>
          <span>8. 以上条款解释权归中华医学会生殖医学分会</span>
        </div>
      </div>
      <div class="regisster-form">
        <el-form
          :model="ruleForm"
          :rules="rules"
          ref="ruleForm"
          label-width="100px"
        >
          <el-form-item
            label="登录名称"
            prop="userLoginName"
          >
            <el-row>
              <el-col :span="14">
                <el-input
                  v-model.trim="ruleForm.userLoginName"
                  placeholder="请输入登录名称"
                ></el-input>
              </el-col>
            </el-row>
          </el-form-item>
          <el-form-item
            label="中心名称"
            prop="userName"
          >
            <el-row>
              <el-col :span="14">
                <el-input
                  v-model.trim="ruleForm.userName"
                  placeholder="请输入中心名称"
                ></el-input>
              </el-col>
            </el-row>
          </el-form-item>
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
          <el-form-item
            label="确认密码"
            prop="reUserLoginPass"
          >
            <el-row>
              <el-col :span="14">
                <el-input
                  type="password"
                  v-model.trim="ruleForm.reUserLoginPass"
                  placeholder="请确认登录密码"
                ></el-input>
              </el-col>
            </el-row>
          </el-form-item>
          <el-form-item prop="agreementFlag">
            <el-row>
              <el-col :span="14">
                <el-checkbox v-model.trim="ruleForm.agreementFlag">同意遵守承诺书</el-checkbox>
              </el-col>
            </el-row>
          </el-form-item>
        </el-form>
        <el-divider></el-divider>
        <el-row>
          <el-col
            :span="6"
            :offset="3"
          >
            <el-button
              style="width:100%"
              type="primary"
              @click="submit"
            >注册新用户</el-button>
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
import { storage } from '../utils';
import store from '../store';
import { PROVICE_LIST } from '../utils/enum';

export default {
  name: 'Reginstered',
  data() {
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else if (value.length < 6 || value.length > 32) {
        callback(new Error('密码长度在 6 到 32 个字符'));
      } else {
        if (this.ruleForm.reUserLoginPass !== '') {
          this.$refs.ruleForm.validateField('reUserLoginPass');
        }
        callback();
      }
    };
    const validateConfirmPass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.userLoginPass) {
        callback(new Error('两次输入密码不一致!'));
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
      activeName: '1',
      countTime: 60,
      timer: null,
      codeDisabled: false,
      codeMsg: '获取验证码',
      smscaptchaId: '',
      ruleForm: {
        smscaptchaCode: '',
        userLoginName: '',
        userLoginPass: '',
        reUserLoginPass: '',
        userName: '',
        userTel: '',
        userLocation: '上海市',
        agreementFlag: false,
      },
      rules: {
        smscaptchaCode: [
          { required: true, message: '请输入短信验证码', trigger: 'blur' },
        ],
        userLoginName: [
          { required: true, message: '请输入登录名称', trigger: 'blur' },
        ],
        userLoginPass: [
          { required: true, validator: validatePass, trigger: 'blur' },
        ],
        reUserLoginPass: [
          { required: true, validator: validateConfirmPass, trigger: 'blur' },
        ],
        userName: [
          { required: true, message: '请输入中心名称', trigger: 'blur' },
        ],
        userTel: [
          { required: true, validator: validateUserTel, trigger: 'blur' },
        ],
        userLocation: [
          { required: true, message: '请选择用户所属(省)', trigger: 'change' },
        ],
        agreementFlag: [
          { required: true, message: '请勾选同意遵守承诺书', trigger: 'change' },
        ],
      },
      PROVICE_LIST,
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
          if (this.ruleForm.userLoginPass !== this.ruleForm.reUserLoginPass) {
            this.$message.error('登录密码和确认密码不匹配。');
            return false;
          }
          if (!this.ruleForm.agreementFlag) {
            this.$message.error('请勾选同意遵守承诺书！');
            return false;
          }
          this.$axios.post('/usr/reg ', {
            ...this.ruleForm,
            userTel: this.ruleForm.userTel.toString(),
            captchaId: this.smscaptchaId,
          }).then((res) => {
            if (res.state === 0) {
              this.$notify({
                title: '成功',
                message: '注册成功！',
                type: 'success',
              });
              storage('token', res.data.accessToken);
              store.dispatch('getUserInfo');
              this.$router.replace('/');
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
              action: 'register',
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
.registered {
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
    .notes {
      font-size: 14px;
      color: #666666;
      line-height: 24px;
    }
    .terms {
      width: 60%;
      margin: 30px auto 0;
      letter-spacing: 1px;
      line-height: 24px;

      .terms-title {
        display: block;
        font-weight: bold;
        text-align: center;
      }
      .terms-detail {
        font-size: 14px;
        span {
          padding: 5px;
          display: block;
        }
      }
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
