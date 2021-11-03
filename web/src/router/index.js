import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '../views/Home.vue';
import Login from '../views/Login.vue';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home,
    children: [
      {
        path: '/',
        redirect: '/main',
        meta: {
          role: ['all'],
        },
        component: () => import('../views/Main/Main.vue'),
      },
      {
        path: '/main',
        meta: {
          role: ['all'],
        },
        component: () => import('../views/Main/Main.vue'),
      },
      {
        path: '/userInfo',
        meta: {
          role: [0, 1],
        },
        component: () => import('../views/UserInfo/Index.vue'),
      },
      {
        path: '/dataReport',
        meta: {
          role: [0, 3],
        },
        component: () => import('../views/DataReport/Index.vue'),
      },
      {
        path: '/dataDetail/:recordYear',
        name: 'dataDetail',
        meta: {
          role: [0, 3],
        },
        component: () => import('../views/DataReport/DataDetail.vue'),
      },
      {
        path: '/dataQuery',
        meta: {
          role: [0, 3],
        },
        component: () => import('../views/DataQuery/Index.vue'),
      },
      {
        path: '/qualityControlChart',
        meta: {
          role: [0, 3],
        },
        component: () => import('../views/QualityControlChart/Index.vue'),
      },
      {
        path: '/dataStatistics',
        meta: {
          role: [0, 3],
        },
        component: () => import('../views/DataStatistics/Index.vue'),
      },
      {
        path: '/password',
        meta: {
          role: ['all'],
        },
        component: () => import('../views/Password/Index.vue'),
      },
      {
        path: '/changeTel',
        meta: {
          role: ['all'],
        },
        component: () => import('../views/ChangeTel/Index.vue'),
      },
      {
        path: '/userList',
        meta: {
          role: [0],
        },
        component: () => import('../views/UserList/Index.vue'),
      },

    ],
  },
  {
    path: '/login',
    name: 'login',
    component: Login,
    meta: {
      role: ['all'],
    },
  },
  {
    path: '/registered',
    name: 'registered',
    meta: {
      role: ['all'],
    },
    component: () => import('../views/Registered.vue'),
  },
  {
    path: '/forgetPassword',
    name: 'forgetPassword',
    meta: {
      role: ['all'],
    },
    component: () => import('../views/ForgetPassword.vue'),
  },
];

const router = new VueRouter({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes,
});

export default router;
