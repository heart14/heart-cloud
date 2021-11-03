import Vue from 'vue';
import Vuex from 'vuex';
import { getToken } from '../utils';

Vue.use(Vuex);
const store = new Vuex.Store({
  state: sessionStorage.getItem('state') ? JSON.parse(sessionStorage.getItem('state')) : {
    userInfo: {},
  },
  mutations: {
    setUserInfo(state, data) {
      state.userInfo = data;
    },
  },
  actions: {
    getUserInfo({ commit }) {
      if (getToken()) {
        Vue.axios.post('/usr/userInfo').then((res) => {
          if (res.state === 0) {
            commit('setUserInfo', res.data);
          }
        });
      }
    },
  },
});
export default store;
