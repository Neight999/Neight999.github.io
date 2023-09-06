<template>
  <div>
    <el-container>
      <el-header>
        <img src="@/assets/logo.png" style="width: 30px; position: relative; top: 10px;">
        <span style="font-size: 20px; margin-left: 15px; color: white;">后台管理系统</span>



        <el-dropdown style="float: right;">
          <span class="el-dropdown-link" style="color: white; font-size: 13px;">
            <el-image style="width: 35px; height: 35px; border-radius: 50%; top: 15px"
              :src="'http://localhost:8080/api/files/' + user.img">
            </el-image>
            {{ user.name }}
            <i class="el-icon-arrow-down el-icon--right "></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>
              <div @click="logout()">退出登录</div>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>

    </el-container>

    <el-container>
      <el-aside width="200px">
        <el-menu default-active="/" router class="el-menu-vertical-demo" background-color="#545c64" text-color="#fff"
          active-text-color="#ffd04b">
          <el-menu-item index="/">
            <i class="el-icon-s-home"></i>
            <span slot="title">系统首页</span>
          </el-menu-item>
          <el-submenu index="2" v-if="user.role === 'ROLE_ADMIN'">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>用户管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/admin">用户信息</el-menu-item>
            </el-menu-item-group>
          </el-submenu>

          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-reading"></i>
              <span>图书管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/type">图书分类</el-menu-item>
              <el-menu-item index="/book">图书信息</el-menu-item>
              <el-menu-item index="/audit">请假审核</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user: localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : {}
    }
  },
  methods: {
    logout() {
      this.$router.push('/login');
      localStorage.removeItem('user');
    }
  }
}
</script>
<style>
.el-header {
  background-color: #373838;
  color: #333;
  line-height: 60px;
}

.el-aside {
  background-color: #787878;
  color: #333;
  text-align: center;
  line-height: 200px;
  min-height: 100vh;
}

.el-main {
  background-color: #ffffff;
  color: #333;
  line-height: 50px;
  padding: 10px;
}

.el-menu {
  border-right: none !important;
}
</style>
