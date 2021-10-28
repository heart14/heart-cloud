<template>
  <div class="table">
    <el-table
      ref="ZeusTable"
      v-bind="$attrs"
      v-loading="loading"
      border
      stripe
      size="medium"
      :data="data"
      :header-cell-class-name="'table-header'"
    >
      <el-table-column
        v-if="showIndex"
        type="index"
        label="庝坷"
        width="50"
        align="center"
      >
      </el-table-column>
      <template v-for="(item,index) in colConfigs">
        <slot
          v-if="item.slot"
          :name="item.slot"
        />
        <!-- <el-table-column
          v-if="item.slot"
          v-bind="item"
          :key="index"
        >
          <template slot-scope="scope">
            <slot
              :name="item.prop"
              :row="scope.row"
              :index="scope.$index"
            ></slot>
          </template>
        </el-table-column> -->
        <el-table-column
          v-else
          v-bind="item"
          width="auto"
          :key="index"
          :resizable="false"
          :formatter="dataFormatter"
        />
      </template>
    </el-table>
  </div>
</template>

<script>
import { NUM_COL } from '../views/DataQuery/configs';

export default {
  name: 'ZeusTable',
  props: {
    data: {
      type: Array,
      default() {
        return [];
      },
    },
    colConfigs: {
      type: Array,
      default() {
        return [];
      },
    },
    showIndex: {
      type: Boolean,
      default: false,
    },
    loading: {
      type: Boolean,
      default: false,
    },
  },
  methods: {
    // eslint-disable-next-line no-unused-vars
    dataFormatter(row, column, cellValue, index) {
      if (cellValue == null) {
        return '/';
      }
      if (NUM_COL.indexOf(column.property) !== -1) {
        return cellValue;
      }
      const f = Math.round(cellValue * 100) / 100;
      let s = f.toString();
      let rs = s.indexOf('.');
      if (rs < 0) {
        rs = s.length;
        s += '.';
      }
      while (s.length <= rs + 2) {
        s += '0';
      }
      return s;
    },
  },
};
</script>
<style lang="less" scoped>
.table {
  margin-top: 20px;
  padding-bottom: 20px;
}
</style>
