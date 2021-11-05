/* eslint-disable no-unused-vars */
<template>
  <div class="quality-control-chart body-container" v-loading="loading">
    <div class="body-container_title">
      生殖中心质控图
    </div>
    <div class="main">
      <div class="search-form">
        <el-form
          style="text-align: center"
          :inline="true"
          :modal="queryForm"
        >
          <el-form-item label="范围" />
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
            <el-select v-model="queryForm.tableIndex" @change="tableSelectChanged">
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
            <el-select v-model="queryForm.situation" style="width: 180px">
              <el-option
                v-for="item in situation_may"
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
      <div class="chart" v-if="showChart">
        <div class="chart-title">{{ chartTitle }}</div>
        <div class="chart-container">
          <ve-line v-if="chartType === 'line'"
          height="500px"
          :data="chartData"
          :settings="chartSettings"
          :extend="extendLine"
          :legend="legendLine"></ve-line>
          <ve-histogram v-else-if="chartType === 'histogram'"
            height="500px"
           :data="chartData"
           :settings="chartSettings"
           :extend="extendHistogram"
           :mark-line="markLine"
           :legend="legendHistogram"
           ></ve-histogram>
          <ve-pie v-else
          height="500px"
          :data="chartData"
          :settings="piechartsetting"
          :extend="extend"
          :legend="pielegend"></ve-pie>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import VeHistogram from 'v-charts/lib/histogram.common';
import VeLine from 'v-charts/lib/line.common';
import VePie from 'v-charts/lib/pie.common';
import 'echarts/lib/component/markLine';
import {
  getYears,
  getNowYear,
  REPORT_TYPE_MAP,
  REPORT_TYPE_LIST,
  AGE_RANGE_MAY,
  AGE_RANGE_MAY_4044,
} from '../../utils/enum';
import {
  SITUATION_LIST,
} from './configs';
import store from '../../store';

export default {
  name: 'quality-control-chart',
  data() {
    return {
      YAER_MAP: getYears(),
      REPORT_TYPE_LIST: REPORT_TYPE_LIST.filter(item => item.value !== 'TotalInfo'),
      REPORT_TYPE_MAP,
      ageAreaMay: store.state.userInfo.is4044 ? AGE_RANGE_MAY_4044 : AGE_RANGE_MAY,
      situation_may: SITUATION_LIST.FuJingIvf,
      queryForm: {
        startYear: getNowYear(),
        endYear: getNowYear(),
        situation: 'pjhls',
        tableIndex: 'FuJingIvf',
        ageArea: '合计',
      },
      loading: false,
      chartData: {
        columns: ['name', 'ownCenterValue', 'shanghaiAverageValue', 'warningValue'],
        rows: [],
      },
      chartType: false,
      showChart: false,
      chartTitle: '',
      chartSettings: {
      // yAxisType: ['percent'],
        yAxisType: ['normal'],
        labelMap: {
          name: '日期',
          ownCenterValue: '本中心值',
          shanghaiAverageValue: '上海平均值',
          warningValue: '警戒值',
        },
      },
      extendLine: {
        series: {
          barMaxWidth: 70,
          label: {
            normal: {
              show: true,
            },
          },
        },
        color: ['#E7C324', '#438FB3', '#D54B19'],
      },
      extend: {
        series: {
          barMaxWidth: 70,
          label: {
            normal: {
              show: true,
            },
          },
        },
        color: ['#E7C324', '#438FB3', '#D54B19'],
      },
      extendHistogram: {
        series: {
          barMaxWidth: 100,
          label: { show: true, position: 'top' },
        },
        color: ['#E7C324', '#438FB3', '#D54B19'],
      },
      markLine: {
        data: [
          {
            yAxis: 100,
            label: { show: true, position: 'middle' },
          },
        ],
        symbol: ['none', 'none'],
        precision: 4,
        lineStyle: {
          type: 'solid',
          color: '#D54B19',
        },
      },
      legendHistogram: {
        selectedMode: false,
        inactiveColor: '#D54B19',
        selected: {
          本中心值: true,
          上海平均值: true,
          警戒值: false,
        },
        data: [
          {
            name: '本中心值',
            textStyle: {
              color: '#E7C324',
            },
          },
          {
            name: '上海平均值',
            textStyle: {
              color: '#438FB3',
            },
          },
          {
            name: '警戒值',
            textStyle: {
              color: 'black',
            },
          },
        ],
      },
      legendLine: {
        data: [
          {
            name: '本中心值',
            textStyle: {
              color: '#E7C324',
            },
          },
          {
            name: '上海平均值',
            textStyle: {
              color: '#438FB3',
            },
          },
          {
            name: '警戒值',
            textStyle: {
              color: '#D54B19',
            },
          },
        ],
      },
      pielegend: {
        orient: 'vertical',
        right: 20,
        top: 30,
        bottom: 30,
      },
      piechartsetting: {
        radius: 200,
        offsetY: 250,
      },
    };
  },
  components: {
    [VeHistogram.name]: VeHistogram,
    [VeLine.name]: VeLine,
    [VePie.name]: VePie,
  },
  methods: {
    defineProperty(obj, key, val) {
      // 返回object对象
      return Object.defineProperty(obj, key,
        {
          value: val,
          writable: true,
          configurable: true,
          enumerable: true,
        });
    },
    search() {
      if (this.queryForm.startYear > this.queryForm.endYear) {
        this.$message.error('开始年份不能小于结束年份');
      } else {
        const s = this.situation_may.find(e => e.value === this.queryForm.situation);
        this.chartSettings.yAxisType = [s.dataType];
        if (this.queryForm.endYear - this.queryForm.startYear === 0) {
          this.chartTitle = `${this.queryForm.endYear}年本中心${this.REPORT_TYPE_MAP[this.queryForm.tableIndex]} ${s.label}质控分析`;
        } else {
          this.chartTitle = `${this.queryForm.startYear}~${this.queryForm.endYear}年本中心${this.REPORT_TYPE_MAP[this.queryForm.tableIndex]} ${s.label}质控分析`;
        }
        if (this.queryForm.situation === 'csnnbl' || this.queryForm.situation === 'csqxzlzb') {
          this.chartType = 'pie';
        } else if (this.queryForm.endYear === this.queryForm.startYear) {
          this.chartType = 'histogram';
        } else {
          this.chartType = 'line';
        }
        this.loading = true;
        this.$axios.post('/report/queryQualityChart', {
          ...this.queryForm,
        }).then((result) => {
          // console.log(result);
          if (result.state === 0) {
            const { data } = result;
            if (data === null) {
              this.markLine.data[0].yAxis = '';
              this.chartData.rows = [];
              this.loading = false;
              this.showChart = true;
            } else {
              if (s.dataType === 'percent') {
                data.forEach((item) => {
                  this.defineProperty(item, 'warningValue', item.warningValue / 100);
                  this.defineProperty(item, 'ownCenterValue', item.ownCenterValue / 100);
                  this.defineProperty(item, 'shanghaiAverageValue', item.shanghaiAverageValue / 100);
                });
                this.extendHistogram.series.label.formatter = (label => (
                  label.value * 100).toFixed(2));
                this.extendLine.series.label.normal.formatter = (label => (
                  label.value[1] * 100).toFixed(2));
                this.markLine.data[0].label.formatter = (label => (
                  label.value * 100).toFixed(2));
              } else {
                this.extendHistogram.series.label.formatter = (label => label.value);
                this.extendLine.series.label.normal.formatter = (label => label.value[1]);
                this.markLine.data[0].label.formatter = (label => label.value);
              }
              if (this.chartType === 'histogram') {
                this.markLine.data[0].yAxis = data[0].warningValue;
                this.chartData.rows = [
                  {
                    name: data[0].name,
                    ownCenterValue: data[0].ownCenterValue,
                    shanghaiAverageValue: data[0].shanghaiAverageValue,
                  },
                ];
              } else {
                this.chartData.rows = data;
              }
            }
            this.loading = false;
            this.showChart = true;
          }
        // eslint-disable-next-line no-unused-vars
        }).catch((e) => {
          this.markLine.data[0].yAxis = '';
          this.chartData.rows = [];
          this.loading = false;
          this.showChart = true;
        });
      }
    },
    tableSelectChanged(value) {
      switch (value) {
        case 'FuJingIvf':
        case 'FuJingIcsi':
        case 'FuJingIvfIcsi':
        case 'GongJingIvf':
        case 'Pgta':
        case 'Pgtsr':
        case 'Pgtm':
          this.ageAreaMay = store.state.userInfo.is4044 ? AGE_RANGE_MAY_4044 : AGE_RANGE_MAY;
          this.queryForm.situation = 'pjhls';
          break;
        case 'Fet':
          this.ageAreaMay = store.state.userInfo.is4044 ? AGE_RANGE_MAY_4044 : AGE_RANGE_MAY;
          this.queryForm.situation = 'ptfsl';
          break;
        case 'Aih':
        case 'Aid':
          this.ageAreaMay = store.state.userInfo.is4044 ? AGE_RANGE_MAY_4044 : AGE_RANGE_MAY;
          this.queryForm.situation = 'lcrsl';
          break;
        case 'ZengShouLuan':
          this.ageAreaMay = [
            { label: '各年龄段汇总', value: '合计' },
          ];
          this.queryForm.ageArea = '合计';
          this.queryForm.situation = 'pjyzpts';
          break;
        case 'DongRongLuan':
          this.ageAreaMay = [
            { label: '各年龄段汇总', value: '合计' },
          ];
          this.queryForm.ageArea = '合计';
          this.queryForm.situation = 'lzfsl';
          break;
        default:
          break;
      }
      this.situation_may = SITUATION_LIST[this.queryForm.tableIndex];
      this.situation = 0;
    },
  },
};
</script>
<style lang="less">
.quality-control-chart {
  .main {
    min-height: 500px;
    .chart-title {
      text-align: center;
      font-size: 24px;
      padding: 10px 0
    }
  }
}
</style>
