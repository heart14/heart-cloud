<template>
  <div class="check-dialog">
    <el-dialog
      title="原因"
      width="600px"
      :visible.sync="visible"
      :before-close="handleClose"
    >
      <el-form
        ref="ruleForm"
        :model="ruleForm"
        :rules="rules"
      >
        <el-form-item prop="desc">
          <el-input
            type="textarea"
            v-model="ruleForm.desc"
            maxlength="240"
            :autosize="{ minRows: 4 }"
          ></el-input>
        </el-form-item>
      </el-form>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="handleClose">取 消</el-button>
        <el-button
          type="primary"
          @click="submit"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: 'check-dialog',
  props: {
    currentRow: {
      type: Object,
      default() {
        return {};
      },
    },
  },
  data() {
    return {
      visible: true,
      ruleForm: {
        desc: '',
      },
      rules: {
        desc: [
          { required: true, message: '请输入原因', trigger: 'change' },
        ],
      },
    };
  },
  methods: {
    handleClose(flag = false) {
      this.$emit('listenToChild', flag);
    },
    submit() {
      // eslint-disable-next-line consistent-return
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          const params = {
            userId: this.currentRow.userId,
            userStatus: this.currentRow.flag,
            desc: this.ruleForm.desc,
          };
          this.$axios.post('/usr/check', params).then((res) => {
            if (res.state === 0) {
              this.$notify({
                title: '成功',
                message: '操作成功',
                type: 'success',
              });
              this.handleClose(true);
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
