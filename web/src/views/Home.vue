<template>
  <div class="main-container">
    <div class="header">
      <div class="banner">
        <img
          src="./../assets/images/banner3.jpg"
          alt="banner"
        >
      </div>
      <div class="nav">
        <el-menu
          router
          class="el-menu-demo"
          mode="horizontal"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b"
          :default-active="activeIndex"
        >
          <el-menu-item index="/main">首页</el-menu-item>
          <el-menu-item index="/dataReport">数据上报</el-menu-item>
          <el-menu-item index="/dataQuery">数据查询</el-menu-item>
          <el-menu-item index="/qualityControlChart">质控图</el-menu-item>
          <el-menu-item index="/dataStatistics">数据统计</el-menu-item>
          <el-menu-item index="/userList" v-show="userInfo.userStatus === 0">用户列表</el-menu-item>
          <el-menu-item index="/password">修改密码</el-menu-item>
          <el-menu-item index="/changeTel">换绑手机</el-menu-item>
          <div class="username">
            <el-dropdown @command="handleCommand">
              <span class="el-dropdown-link">
                {{ userInfo.userName }}({{ USER_STATUS_MAP[userInfo.userStatus] }})
                <i class="el-icon-caret-bottom el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="logout">退出</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </el-menu>
      </div>
    </div>
    <el-main>
      <router-view />
    </el-main>
  </div>
</template>

<script>
import { removeStorage } from '../utils';
import { USER_STATUS_MAP } from '../utils/enum';
import store from '../store';

export default {
  name: 'home',
  data() {
    return {
      USER_STATUS_MAP,
    };
  },
  methods: {
    handleCommand(command) {
      if (command === 'logout') {
        this.$axios.post('/usr/logout').then((res) => {
          if (res.state === 0) {
            sessionStorage.clear();
            removeStorage('token');
            this.$router.push('/login');
          } else {
            this.$notify({
              title: '提示',
              message: '退出登录失败',
              type: 'error',
            });
          }
        }).catch(() => {
          this.$notify({
            title: '提示',
            message: '退出登录失败',
            type: 'error',
          });
        });
      }
    },
  },
  computed: {
    activeIndex() {
      // return this.$route.path.substring(1);
      return this.$route.path;
    },
    userInfo() {
      return store.state.userInfo;
    },
  },
};
</script>
<style lang="less">
.main-container {
  // height: 100%;
  display: flex;
  flex-direction: column;
  .header {
    background: #545c64;
    .banner {
      background-color: #087279;
      img {
        display: block;
        width: 100%;
        height: 110px;
        margin: 0 auto;
      }
    }
    .nav {
      width: 1300px;
      margin: 0 auto;
      height: 40px;
      overflow: hidden;
      .el-menu--horizontal > .el-menu-item {
        height: 40px;
        line-height: 40px;
      }
      .username {
        float: right;
        padding-right: 40px;
        height: 40px;
        font-size: 14px;
        .el-dropdown {
          color: #ffffff;
          height: 40px;
          line-height: 40px;
          cursor: pointer;
        }
      }
    }
  }
  .el-main {
    width: 1300px;
    margin: 0 auto;
  }
}
</style>
