import { MessageBox } from 'element-ui';
import router from './router';
import { getToken } from './utils';
import { PATH_LOGIN, PATH_REGISTERED, PATH_FORGET_PASSWORD } from './configs';
import store from './store';

if (getToken()) {
  store.dispatch('getUserInfo');
}
router.beforeEach((to, from, next) => {
  const token = getToken();
  const { path, meta, matched } = to;
  if (token) {
    if (matched.length === 0) {
      MessageBox({
        title: '提示',
        message: '页面不存在',
        type: 'warning',
      });
      next('/');
    } else {
      const { userStatus } = store.state.userInfo;
      if (meta.role.includes(userStatus) || meta.role.includes('all')) {
        next();
      } else {
        MessageBox({
          title: '提示',
          message: userStatus === 3 ? '账户已封停，暂无权限访问' : '未完成审核，暂无权限访问',
          type: 'warning',
        });
        next(false);
      }
    }
  } else if ([PATH_FORGET_PASSWORD, PATH_LOGIN, PATH_REGISTERED].includes(path)) {
    next();
  } else {
    next('/login');
  }
});
