<template>
  <div class="data-report body-container">
    <div class="body-container_title">
      数据上报
    </div>
    <div class="main">
      <div class="notes">
        <el-row>
          <el-col :span="12">
            <el-row justify="end">
              <el-col
                :span="12"
              >
                <span class="red">请下载并使用最新数据模板上报数据</span>
              </el-col>
              <el-col :span="4">
                <el-button
                 type="text"
                 size="medium"
                 @click="downloadTemplate"
                 style="font-size: 16px; font-weight: 600;">
                  下载模板
                </el-button>
              </el-col>
              <el-col :span="4">
                <el-button
                  type="primary"
                  size="medium"
                  @click="showDialog = true"
                >数据导入</el-button>
              </el-col>
            </el-row>
          </el-col>
          <el-col :span="11" :offset="0">
            <span class="red"> 请自2010年上传数据，请于每年3月1日前上传前一年数据,
              前一年的出生分娩随访情况请暂时填“0”，次年上传数据时再补填前一年的出生随访结果</span>
          </el-col>
        </el-row>
      </div>
      <div class="table">
        <el-table
          border
          size="medium"
          style="width: 100%;margin-top:20px;"
          :data="tableData"
          max-height="500"
          :header-cell-class-name="'table-header'"
        >
          <el-table-column
            type="index"
            label="序号"
            width="50"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="name"
            label="中心名称"
            min-width="160"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="recordYear"
            label="年份"
            min-width="120"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="createTime"
            label="创建时间"
            min-width="160"
            align="center"
          >
          </el-table-column>
          <!-- <el-table-column
            label="修改时间"
            prop="updateTime"
            min-width="160"
            align="center"
          >
          </el-table-column> -->
          <el-table-column
            prop="name"
            label="编辑"
            width="180"
            align="center"
          >
            <template slot-scope="scope">
              <el-button
                type="primary"
                size="medium"
                @click="viewDetail(scope.row)"
              >查看</el-button>
              <el-button
                type="primary"
                size="medium"
                @click="deleteRecord(scope.row)"
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
      <UploadDialog
        v-if="showDialog"
        @listenToUpload="listenToUpload"
      />
    </div>
  </div>
</template>
<script>
import moment from 'moment';
import UploadDialog from './Upload.vue';
import store from '../../store';

export default {
  name: 'data-report',
  data() {
    return {
      pageInfo: {
        pageNum: 1,
        pageSize: 10,
        total: 0,
      },
      showDialog: false,
      tableData: [],
    };
  },
  components: {
    UploadDialog,
  },
  computed: {
    userInfo() {
      return store.state.userInfo;
    },
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      this.$axios.post('/report/query', { ...this.pageInfo }).then((res) => {
        if (res.state === 0) {
          res.data.list.forEach((item) => {
            // eslint-disable-next-line
            item.name = this.userInfo.userName;
            // eslint-disable-next-line no-param-reassign
            item.createTime = item.createTime ? moment(item.createTime).format('YYYY-MM-DD HH:mm:ss') : '';
            // eslint-disable-next-line no-param-reassign
            item.updateTime = item.updateTime ? moment(item.updateTime).format('YYYY-MM-DD HH:mm:ss') : '';
          });
          this.tableData = res.data.list;
          this.pageInfo.total = res.data.total;
        }
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
    viewDetail(item) {
      this.$router.push({
        name: 'dataDetail',
        params: {
          recordYear: item.recordYear,
        },
      });
    },
    deleteRecord(item) {
      this.$confirm('此操作将永久删除该条记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        this.$axios.post('/report/delete', item).then((res) => {
          if (res.state === 0) {
            this.$notify({
              title: '提示',
              message: '删除成功！',
              type: 'success',
            });
            this.init();
          }
        });
      });
    },
    listenToUpload(flag) {
      if (flag) this.init();
      this.showDialog = false;
    },
    downloadTemplate() {
      const link = document.createElement('a');
      link.href = `${this.$axios.defaults.baseURL}/report/downloadTemplate`;
      document.body.appendChild(link);
      link.click();
      URL.revokeObjectURL(link.href); // 释放url
      document.body.removeChild(link);// 释放标签
    },
  },
};
</script>
<style lang="less" scoped>
.data-report {
  .notes {
    line-height: 40px;
  }
  .report-year {
    text-align: center;
    font-size: 16px;
    line-height: 40px;
    color: #f00;
  }
  .main {
    min-height: 400px;
    .table-title {
      background-color: #eee;
      line-height: 40px;
      padding: 0 10px;
    }
    .table {
      margin-top: 0;
      margin-bottom: 10px;
    }
  }
  .red {
    color: #f00;
  }
}
</style>
