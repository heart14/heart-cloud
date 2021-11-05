<template>
  <div class="user-list body-container">
    <div class="body-container_title">
      用户信息列表
    </div>
    <div class="main">
      <div class="search-form">
        <fieldset>
          <legend>检索条件</legend>
          <el-form
            ref="searchForm"
            :model="searchForm"
            :inline="true"
          >
            <el-form-item
              label="账号:"
              prop="userLoginName"
            >
              <el-input
                v-model="searchForm.userLoginName"
                clearable
                placeholder="请输入"
              ></el-input>
            </el-form-item>
            <el-form-item
              label="中心名称:"
              prop="userName"
            >
              <el-input
                v-model="searchForm.userName"
                clearable
                placeholder="请输入"
              ></el-input>
            </el-form-item>
            <el-form-item
              label="手机号码:"
              prop="userTel"
            >
              <el-input
                v-model="searchForm.userTel"
                clearable
                placeholder="请输入"
              ></el-input>
            </el-form-item>
            <!-- <el-form-item label="省份:" prop="userLocation">
              <el-select
                v-model="searchForm.userLocation"
                filterable
                clearable
                style="width:100%"
              >
                <el-option
                  v-for="item in PROVICE_LIST"
                  :label="item"
                  :value="item"
                  :key="item"
                ></el-option>
              </el-select>
            </el-form-item> -->
            <el-form-item
              label="状态:"
              prop="userStatus"
            >
              <el-select
                v-model="searchForm.userStatus"
                placeholder="请选择"
              >
                <el-option
                  v-for="item in USER_STATUS_LIST"
                  :label="item.label"
                  :value="item.value"
                  :key="item.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button
                type="primary"
                icon="el-icon-search"
                @click="search"
              >搜索</el-button>
              <el-button
                type="primary"
                icon="el-icon-refresh"
                @click="resetForm"
              >重置</el-button>
            </el-form-item>
          </el-form>
        </fieldset>
      </div>
      <div class="block"></div>
      <el-table
        :data="tableData"
        border
        size="medium"
        style="width: 100%;"
        max-height="500"
      >
        <el-table-column
          prop="userLoginName"
          label="账号"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="userName"
          label="中心名称"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="userTel"
          label="手机号码"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="applyFileUrl"
          label="用户申请表"
          align="center"
          width="150"
        >
        <template slot-scope="{ row }">
          <el-button v-if="row.applyFileUrl"
                slot="reference"
                type="text"
                size="medium"
                @click="downloadApplyFile(row.applyFileUrl)"
              >下载</el-button>
          <div v-else>未上传</div>
        </template>
        </el-table-column>
        <el-table-column
          prop="userStatus"
          label="用户状态"
          align="center"
          width="150"
        >
          <template slot-scope="{ row }">
            <span v-if="[0,1,3,5].includes(row.userStatus)">
              {{ USER_STATUS_MAP[row.userStatus] }}
            </span>
            <el-popover
              v-else
              placement="top"
              title="原因"
              width="200"
              trigger="hover"
              :content="row.checkDesc"
            >
              <el-button
                slot="reference"
                type="text"
                size="medium"
                style="color: #737477"
              >{{ USER_STATUS_MAP[row.userStatus] }}</el-button>
            </el-popover>

          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="{ row }">
            <el-button
              v-if="row.userStatus === 1 || row.userStatus === 2 || row.userStatus === 6"
              type="text"
              size="medium"
              @click="beforeChangeStatus(row, 3)"
            >审核通过</el-button>
            <el-button
              v-if="row.userStatus === 1 || row.userStatus === 2 || row.userStatus === 6"
              type="text"
              size="medium"
              @click="beforeChangeStatus(row, 6)"
            >不通过</el-button>
            <el-button
              v-if="row.userStatus === 3"
              type="text"
              size="medium"
              @click="beforeChangeStatus(row, 6)"
            >撤销审核</el-button>
            <el-button
              v-if="row.userStatus !== 4"
              type="text"
              size="medium"
              @click="beforeChangeStatus(row, 4)"
            >封停</el-button>
            <el-button
              v-if="row.userStatus === 4"
              type="text"
              size="medium"
              @click="beforeChangeStatus(row, 3)"
            >解封</el-button>
            <el-button
              type="text"
              size="medium"
              @click="deleteHandle(row, 5)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="block">
        <el-pagination
          class="right"
          layout="total, sizes, prev, pager, next, jumper"
          :total="pageInfo.total"
          :current-page="pageInfo.pageNum"
          :page-sizes="[10, 20, 30]"
          :page-size="pageInfo.pageSize"
          @current-change="handleCurrentChange"
          @size-change="handleSizeChange"
        >
        </el-pagination>
      </div>
    </div>
    <CheckDialog
      v-if="showDialog"
      :currentRow="currentRow"
      @listenToChild="listenToChild"
    />
  </div>
</template>
<script>
import { USER_STATUS_MAP, PROVICE_LIST, USER_STATUS_LIST } from '../../utils/enum';
import CheckDialog from './CheckDialog.vue';

export default {
  name: 'user-list',
  data() {
    return {
      USER_STATUS_MAP,
      PROVICE_LIST,
      USER_STATUS_LIST,
      pageInfo: {
        pageNum: 1,
        pageSize: 10,
        total: 0,
      },
      searchForm: {
        userLoginName: '',
        userName: '',
        userTel: '',
        userLocation: '上海市',
        userStatus: null,
      },
      tableData: [],
      loading: false,
      showDialog: false,
      currentRow: {},
    };
  },
  components: {
    CheckDialog,
  },
  created() {
    this.init();
  },
  methods: {
    search() {
      this.init();
    },
    resetForm() {
      this.$refs.searchForm.resetFields();
    },
    init() {
      this.loading = true;
      const params = JSON.parse(JSON.stringify(this.searchForm));
      this.$axios.post('/usr/queryUserByPage', {
        ...this.pageInfo,
        ...params,
      }).then((res) => {
        if (res.state === 0) {
          this.tableData = res.data.list;
          this.pageInfo.total = res.data.total;
        }
      });
      this.loading = false;
    },
    userStatusHandle(row, userStatus) {
      const params = { userId: row.userId, userStatus };
      this.$axios.post('/usr/check', params).then((res) => {
        if (res.state === 0) {
          this.$notify({
            title: '成功',
            message: '操作成功',
            type: 'success',
          });
          this.init();
        }
      });
    },
    beforeChangeStatus(row, userStatus) {
      if (userStatus === 4 || userStatus === 6) {
        this.currentRow = { ...row, flag: userStatus };
        this.showDialog = true;
      } else {
        this.userStatusHandle(row, userStatus);
      }
    },
    deleteHandle(row, userStatus) {
      this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        this.userStatusHandle(row, userStatus);
      });
    },
    handleSizeChange(val) {
      this.pageInfo.pageNum = 1;
      this.pageInfo.pageSize = val;
      this.init();
    },
    handleCurrentChange(val) {
      this.pageInfo.pageNum = val;
      this.init();
    },
    listenToChild(flag) {
      if (flag) this.init();
      this.showDialog = false;
    },
    downloadApplyFile(path) {
      const link = document.createElement('a');
      link.href = `${this.$axios.defaults.baseURL}/file/downloadApply?fileName=${path}`;
      document.body.appendChild(link);
      link.click();
      URL.revokeObjectURL(link.href); // 释放url
      document.body.removeChild(link);// 释放标签
    },
  },
};
</script>
<style lang="less">
.user-list {
  .table {
    margin-top: 20px;
  }
  .main {
    min-height: 400px;
  }
  .red {
    color: #f00;
  }
}
</style>
