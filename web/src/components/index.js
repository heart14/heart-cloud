import ZeusTable from './ZeusTable.vue';

const globalComponents = {
  install(Vue) {
    Vue.component(ZeusTable.name, ZeusTable);
  },
};
export default globalComponents;
