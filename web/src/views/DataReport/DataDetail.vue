<template>
  <div class="data-detail body-container">
    <el-page-header
      @back="goBack"
      content="数据上报详情"
      style="padding: 20px 0 0 20px;"
    />
    <div class="main">
      <div class="report-year">上报年份：{{recordYear}}</div>
      <el-tabs
        v-model="activeName"
        tab-position="left"
        style="height:100%"
      >
        <el-tab-pane
          label="治疗总情况"
          name="first"
        >
          <div class="table-title">患者基本情况</div>
          <el-table
            :data="baseInfoData"
            :span-method="objectSpanMethod"
            :header-cell-class-name="'table-header'"
            border
            size="medium"
            style="width: 100%; margin-bottom: 10px"
          >
            <el-table-column
              prop="infertilityType"
              label="不孕因素"
              width="180"
            />
            <el-table-column prop="infertilityTypeName" />
            <el-table-column
              prop="cyclesNum"
              label="周期数"
            />
          </el-table>

          <div class="table-title">总治疗周期数</div>
          <zeus-table
            size="small"
            :data="treatCyclesData"
            :colConfigs="totalTreatmentCyclesConfig"
          />

        </el-tab-pane>
        <el-tab-pane
          label="人工授精"
          name="second"
        >
          <div class="table-title">AIH统计表</div>
          <zeus-table
            size="small"
            :data="AIHData"
            :colConfigs="config"
          />

          <div class="table-title">AID统计表</div>
          <zeus-table
            size="small"
            :data="AIDData"
            :colConfigs="config"
          />
        </el-tab-pane>
        <el-tab-pane
          label="试管婴儿"
          name="third"
        >
          <div class="table-title">新鲜周期用药、获卵及并发症情况</div>
          <zeus-table
            size="small"
            :data="othersData"
            :show-header="false"
            :colConfigs="totalTreatmentCyclesConfig"
          />
          <div class="table-title">夫精IVF统计表</div>
          <zeus-table
            size="small"
            :data="FIVFData"
            :colConfigs="config"
          />
          <div class="table-title">夫精ICSI统计表</div>
          <zeus-table
            size="small"
            :data="FICSIData"
            :colConfigs="config"
          />
          <div class="table-title">夫精IVF+ICSI统计表</div>
          <zeus-table
            size="small"
            :data="FIVFICSIData"
            :colConfigs="config"
          />
          <div class="table-title">供精IVF（ICSI）统计表</div>
          <zeus-table
            size="small"
            :data="GIVFICSIData"
            :colConfigs="config"
          />
          <div class="table-title">FET统计表</div>
          <zeus-table
            size="small"
            :data="FETData"
            :colConfigs="config"
          />
          <div class="table-title">PGT-A统计表</div>
          <zeus-table
            size="small"
            :data="PGTAData"
            :colConfigs="config"
          />
          <div class="table-title">PGT-SR统计表</div>
          <zeus-table
            size="small"
            :data="PGTSRData"
            :colConfigs="config"
          />
          <div class="table-title">PGT-M统计表</div>
          <zeus-table
            size="small"
            :data="PGTMData"
            :colConfigs="config"
          />
          <div class="table-title">赠、受卵统计表</div>
          <zeus-table
            size="small"
            :show-header="false"
            :data="ZSLData"
            :colConfigs="totalTreatmentCyclesConfig"
          />
          <div class="table-title">冻卵融卵统计表</div>
          <zeus-table
            size="small"
            :show-header="false"
            :data="DRLData"
            :colConfigs="totalTreatmentCyclesConfig"
          />
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script>


import {
  totalTreatmentCyclesConfig, commonConfig, commonConfig4044,
} from './config';
import store from '../../store';

export default {
  name: 'data-detail',
  data() {
    return {
      recordYear: '',
      activeName: 'first',
      showDetail: false,
      totalTreatmentCyclesConfig,
      config: store.state.userInfo.is4044 ? commonConfig4044 : commonConfig,
      baseInfoData: [],
      treatCyclesData: [],
      othersData: [],
      FIVFData: [],
      FICSIData: [],
      FIVFICSIData: [],
      GIVFICSIData: [],
      FETData: [],
      PGTAData: [],
      PGTSRData: [],
      PGTMData: [],
      AIHData: [],
      AIDData: [],
      ZSLData: [],
      DRLData: [],
      basicSituation: [],
    };
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      this.recordYear = this.$route.params.recordYear;
      this.$axios.post('/report/queryRecordDetail', {
        recordYear: this.recordYear,
      }).then((result) => {
        if (result.state === 0) {
          // console.log(result);
          this.baseInfoData = result.data.baseInfo;
          this.treatCyclesData = result.data.treatCycles;
          this.othersData = result.data.others;
          this.FIVFData = result.data.FIVF;
          this.FICSIData = result.data.FICSI;
          this.FIVFICSIData = result.data.FIVFICSI;
          this.GIVFICSIData = result.data.GIVFICSI;
          this.FETData = result.data.FET;
          this.PGTAData = result.data.PGTA;
          this.PGTSRData = result.data.PGTSR;
          this.PGTMData = result.data.PGTM;
          this.AIHData = result.data.AIH;
          this.AIDData = result.data.AID;
          this.DRLData = result.data.DRL;
          this.ZSLData = result.data.ZSL;
        }
      });
    },
    goBack() {
      this.$router.go(-1);
    },
    // eslint-disable-next-line consistent-return
    objectSpanMethod({
      // eslint-disable-next-line no-unused-vars
      row, column, rowIndex, columnIndex,
    }) {
      if (columnIndex === 0) {
        if (rowIndex === 0) {
          return [2, 1];
        } if (rowIndex === 2) {
          return [3, 1];
        } if (rowIndex === 5) {
          return [4, 1];
        } if (rowIndex === 9) {
          return [2, 1];
        }
        return [0, 0];
      }
      return [1, 1];
    },
  },
};
</script>
<style lang="less" scoped>
.data-detail {
  .notes {
    line-height: 40px;
  }
  // .table {
  //   margin-top: 20px;
  // }
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
