<template>
  <div class="data-statistics body-container" v-loading="loading">
    <div class="body-container_title">
      生殖中心数据统计
    </div>
    <div class="main">
      <div style="overflow:hidden"  v-if="showTable">
        <el-button
          size="medium"
          style="float:right"
          v-print="'#printDom'"
        >打印</el-button>
      </div>
      <div class="search-form">
        <el-form
          style="text-align: center"
          :inline="true"
          :modal="queryForm"
        >
          <el-form-item label="统计范围" />

          <el-form-item>
            <el-select
              v-model="queryForm.startYear"
              style="width: 100px"
            >
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
            <el-select
              v-model="queryForm.endYear"
              style="width: 100px"
            >
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
            <el-select
              v-model="queryForm.centerId"
              style="width: 120px"
            >
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
            <el-button
              type="primary"
              @click="search"
            > 统 计 </el-button>
          </el-form-item>
        </el-form>
      </div>
      <div
        class="printDom"
        id="printDom"
        v-if="showTable"
      >
        <div class="title">
          {{ tableTitle }}
        </div>
        <div class="data-list" v-for="item in tableData" :key="item.tableName">
          <div class="label">{{item.tableName}}</div>
          <div class="item">
            <div  v-for="item1 in item.tableData" :key="item1.title">
              <el-row v-if="item.type === 'normal'">
                <el-col :span="12">{{item1.title}}</el-col>
                <el-col :span="12">{{item1.value}}</el-col>
              </el-row>
              <el-row v-else-if="is4044">
                <el-col :span="9">{{item1.title}}</el-col>
                <el-col :span="3">{{item1.value35}}</el-col>
                <el-col :span="3">{{item1.value3539}}</el-col>
                <el-col :span="3">{{item1.value4044}}</el-col>
                <el-col :span="3">{{item1.value4550}}</el-col>
                <el-col :span="3">{{item1.valueAll}}</el-col>
              </el-row>
              <el-row v-else>
                <el-col :span="6">{{item1.title}}</el-col>
                <el-col :span="3">{{item1.value35}}</el-col>
                <el-col :span="3">{{item1.value3539}}</el-col>
                <el-col :span="3">{{item1.value4042}}</el-col>
                <el-col :span="3">{{item1.value4344}}</el-col>
                <el-col :span="3">{{item1.value4550}}</el-col>
                <el-col :span="3">{{item1.valueAll}}</el-col>
              </el-row>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { getYears, getNowYear } from '../../utils/enum';
import store from '../../store';

export default {
  name: 'data-statistics',
  data() {
    return {
      YAER_MAP: getYears(),
      center_map: [],
      queryForm: {
        startYear: getNowYear(),
        endYear: getNowYear(),
        centerId: 1,
      },
      showTable: false,
      loading: false,
      tableTitle: '',
      tableData: [],
    };
  },
  computed: {
    userInfo() {
      return store.state.userInfo;
    },
    is4044() {
      return store.state.userInfo.is4044;
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
        const s = this.center_map.find(e => e.value === this.queryForm.centerId);
        let name;
        if (s.label === '本中心') {
          name = this.userInfo.userName;
        } else {
          name = s.label;
        }
        this.loading = true;
        this.tableTitle = '';
        this.showTable = false;
        this.$axios.post('/report/queryDataStatistics', {
          ...this.queryForm,
        }).then((result) => {
          // console.log(result);
          if (result.state === 0) {
            if (this.queryForm.endYear - this.queryForm.startYear === 0) {
              this.tableTitle = `${name} 生殖中心${this.queryForm.startYear}年统计报表`;
            } else {
              this.tableTitle = `${name} ${this.queryForm.startYear}~${this.queryForm.endYear}年统计报表`;
            }
            this.tableData = result.data;
            this.showTable = true;
          }
          this.loading = false;
        });
      }
    },
  },
};
</script>
<style lang="less">
@page {
  size: A4;
  margin: 0mm;
  @top-left {
    display: none;
  }
  @bottom-center {
    display: none;
  }
}
.data-statistics {
  .main {
    min-height: 400px;
  }
}
#printDom {
  .title {
    margin-top: 20px;
    text-align: center;
    font-size: 18px;
  }
  .data-list {
    width: 70%;
    margin: 20px auto;
    padding-left: 0;
    line-height: 28px;
    font-size: 15px;
    .label {
      font-weight: bold;
      font-size: 17px;
    }
    .item {
      border-left: 2px solid #232323;
      border-top: 2px solid #232323;
      margin-bottom: 20px;
      .el-row {
        .el-col {
          padding-left: 5px;
          border-right: 2px solid #232323;
          border-bottom: 2px solid #232323;
        }
      }
    }
  }
}
</style>
