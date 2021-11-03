"use strict";

import Vue from 'vue';
import axios from "axios";
import router from '../router'
import { MessageBox } from 'element-ui';
import { getToken, removeStorage} from '../utils'
import { startLoading, closeLoading } from '../utils/globalLoaing'
// Full config:  https://github.com/axios/axios#request-config
// axios.defaults.baseURL = process.env.baseURL || process.env.apiUrl || '';
// axios.defaults.headers.common['Authorization'] = AUTH_TOKEN;
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

let config = {
  // baseURL: 'http://192.168.0.102:8099' //lyf
  // baseURL: 'http://106.13.190.67:8900'
  baseURL: 'http://127.0.0.1:8900'
  // timeout: 60 * 1000, // Timeout
  // withCredentials: true, // Check cross-site Access-Control
};

const _axios = axios.create(config);

_axios.interceptors.request.use(
  function(config) {
    startLoading(config)
    config.headers.Authorization = getToken() || '';
    // Do something before request is sent
    return config;
  },
  function(error) {
    // Do something with request error
    return Promise.reject(error);
  }
);

// Add a response interceptor
_axios.interceptors.response.use(
  function(response) {
    closeLoading()
    const {data} = response
    if(data.state === -1) {
      if(data.code === 401) {
        sessionStorage.clear();
        removeStorage('token');
        MessageBox.alert('登录已失效，请重新登录！', '提示', {
          type:'error',
          callback: action => {
            router.replace('/login')
          }
        })
      } else {
        MessageBox.alert(data.msg, '提示',{
          type: 'error',
          message: data.msg
        })
      }
    } 
    // Do something with response data
    return response.data;
  },
  function(error) {
    // Do something with response error
    closeLoading()
    return Promise.reject(error);
  }
);

Plugin.install = function(Vue, options) {
  Vue.axios = _axios;
  window.axios = _axios;
  Object.defineProperties(Vue.prototype, {
    axios: {
      get() {
        return _axios;
      }
    },
    $axios: {
      get() {
        return _axios;
      }
    },
  });
};

Vue.use(Plugin)

export default Plugin;
