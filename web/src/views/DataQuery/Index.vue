<template>
  <div class="data-query body-container">
    <div class="body-container_title">
      生殖中心数据查询
    </div>
    <div class="main">
      <div class="search-form">
        <el-form
          style="text-align: center"
          :inline="true"
          :modal="queryForm"
        >
          <el-form-item>
            <el-select v-model="queryForm.startYear" style="width: 100px">
              <el-option
                v-for="item in YAER_MAP"
                :key="item"
                :label="item"
                :value="item"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-select v-model="queryForm.endYear" style="width: 100px">
              <el-option
                v-for="item in YAER_MAP"
                :key="item"
                :label="item"
                :value="item"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-select v-model="queryForm.centerId" style="width: 120px">
              <el-option
                v-for="item in center_map"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-select v-model="queryForm.tableIndex" @change="tableSelectChanged" >
              <el-option
                v-for="item in REPORT_TYPE_LIST"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-select v-model="queryForm.ageArea" style="width: 150px">
              <el-option
                v-for="item in ageAreaMay"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="search">检 索</el-button>
          </el-form-item>
        </el-form>
      </div>
      <zeus-table
        v-if='showTable'
        size="medium"
        :data="tableData"
        :colConfigs="colConfig"
        :loading="loading"
      ></zeus-table>
    </div>
  </div>
</template>
<script>
import {
  getYears,
  getNowYear,
  REPORT_TYPE_LIST,
  AGE_RANGE_MAY,
  AGE_RANGE_MAY_4044,
} from '../../utils/enum';
import { COL_CONIFGS } from './configs';
import store from '../../store';

export default {
  name: 'data-query',
  data() {
    return {
      YAER_MAP: getYears(),
      center_map: [],
      REPORT_TYPE_LIST,
      ageAreaMay: [
        { label: '各年龄段汇总', value: '合计' },
      ],
      queryForm: {
        startYear: getNowYear(),
        endYear: getNowYear(),
        centerId: 1,
        tableIndex: 'TotalInfo',
        ageArea: '合计',
      },
      tableData: [],
      colConfig: [],
      showTable: false,
      loading: false,
    };
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
      let i;
      this.$axios.post('/usr/queryAllUser').then((result) => {
        if (result.state === 0) {
          this.center_map = [];
          this.center_map.push({ label: '本中心', value: this.userInfo.userId });
          this.queryForm.centerId = this.userInfo.userId;
          for (i = 0; i < result.data.length; i += 1) {
            this.center_map.push({ label: result.data[i].userName, value: result.data[i].userId });
          }
          this.center_map.push({ label: '上海市', value: null });
        }
      });
    },
    search() {
      if (this.queryForm.startYear > this.queryForm.endYear) {
        this.$message.error('开始年份不能小于结束年份');
      } else {
        this.colConfig = COL_CONIFGS[this.queryForm.tableIndex];
        this.showTable = true;
        this.loading = true;
        this.$axios.post('/report/queryTable', {
          ...this.queryForm,
        }).then((result) => {
          if (result.state === 0) {
            this.tableData = result.data;
            this.loading = false;
          }
          this.loading = false;
        });
      }
    },
    tableSelectChanged(value) {
      if (value === 'TotalInfo' || value === 'ZengShouLuan' || value === 'DongRongLuan') {
        this.ageAreaMay = [
          { label: '各年龄段汇总', value: '合计' },
        ];
        this.queryForm.ageArea = '合计';
      } else {
        this.ageAreaMay = store.state.userInfo.is4044 ? AGE_RANGE_MAY_4044 : AGE_RANGE_MAY;
      }
    },
  },
};
</script>
<style lang="less">
.data-query {
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
