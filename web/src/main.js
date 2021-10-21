import Vue from 'vue';
import './plugins/axios';
import Print from 'vue-print-nb';
import App from './App.vue';
import router from './router';
import store from './store';
import 'normalize.css';
import './plugins/element';
import './assets/style/index.less';
import './permission';
import 'nprogress/nprogress.css';
import globalComponents from './components';

window.store = store;
Vue.use(globalComponents);

Vue.config.productionTip = false;
Vue.use(Print); // 注册

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app');
