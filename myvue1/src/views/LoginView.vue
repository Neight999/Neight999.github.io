<template>
    <div>
        <div
            style="width: 400px; height: 350px; margin: 150px auto; background-color: rgba(107, 149, 224, 0.5); border-radius: 10px;">
            <div
                style="widows: 100%; height: 100px; font-size: 30px; line-height: 100px; text-align: center; color: #4a5ed0;">
                欢迎登录</div>
            <div style="margin-top: 25px; text-align: center; height: 320px;">
                <el-form :model="user">
                    <el-form-item>
                        <el-input v-model="user.name" prefix-icon="el-icon-user-solid" style="width: 80%;"
                            placeholder="请输入用户名"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-input v-model="user.password" prefix-icon="el-icon-lock" show-password style="width: 80%;"
                            placeholder="请输入密码"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button style="width: 80%; margin-top: 10px;" type="primary"
                        v-loading.fullscreen.lock="fullscreenLoading" @click="login()">登录</el-button>
                        <el-link :underline="false" href="http://localhost:7070/register" style="width: 30%; margin-top: 10px;
                        margin-left: 50%;" type="primary">没有账户?去注册</el-link>
                    </el-form-item>
                </el-form>
            </div>

        </div>
    </div>
</template>

<script>
import request from '@/utils/request';
export default {
    data() {
        return {
            user: {},
            fullscreenLoading: false
        }
    },
    //页面加载的时候，做一些事情，在create里面
    created() {

    },
    //定义一些页面上控件触发的事件调用的方法
    methods: {
        login() {
            request.post('/user/login', this.user).then(res => {
                if (res.code === '0') {
                    const loading = this.$loading({
                        lock: true,
                        text: 'Loading',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });
                    setTimeout(() => {
                        loading.close();
                        //登录成功
                    this.$message({
                        message: '登录成功',
                        type: 'success'
                    });
                    localStorage.setItem('user', JSON.stringify(res.data));
                    this.$router.push('/');
                    }, 1500);
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            });
        }
    }
};
</script>
