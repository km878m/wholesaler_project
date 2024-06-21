<template>
  <div style="height: 100vh; overflow: hidden; position: relative;">
    <!--滑块设置-->
    <el-card class="cover" v-if="loginAdmin.id">
      <slide-verify :imgs="imgs"
                    :l="42"
                    :r="10"
                    :w="310"
                    :h="155"
                    slider-text="向右滑动"
                    @success="onSuccess"
                    @fail="onFail"
                    @refresh="onRefresh"
      ></slide-verify>
    </el-card>  

    <div
      style="
        width: 500px;
        height: 400px;
        background-color: white;
        border-radius: 10px;
        margin: 150px auto;
        padding: 50px;
      "
    >
      <div
        style="
          margin: 30px;
          text-align: center;
          font-size: 25px;
          font-weight: bold;
          color: dodgerblue;
        "
      >
        实体商行管理系统
      </div>
      <el-form :model="admin" :rules="rules" ref="loginForm">
        <el-form-item prop="username">
          <el-input
            placeholder="请输入账号"
            prefix-icon="el-icon-user"
            size="medium"
            v-model="admin.username"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            placeholder="请输入密码"
            show-password
            prefix-icon="el-icon-key"
            size="medium"
            v-model="admin.password"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            style="width: 100%"
            size="medium"
            type="primary"
            @click="login"
            >登录</el-button
          >
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from 'js-cookie';
import img0 from '@/assets/1.png'
import img1 from '@/assets/2.png'

export default {
  name: "LOGIN",
  data() {
    return {
      imgs:[img0,img1,],
      loginAdmin:{},
      admin: {},
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 3, max: 10, message: "长度在3-10个字符", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 3, max: 10, message: "长度在3-10个字符", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    // 登录方法（将账号和密码传给后端进行校验）
    login() {
      this.$refs["loginForm"].validate((valid) => {
        if (valid) {
          request.post("/admin/login", this.admin).then((res) => {
            if (res.code === "200") {
              this.loginAdmin = res.data //loginAdmin被赋值，滑块组件出现
            } else {
              this.$notify.error(res.msg);
            }
          });
        }
      });
    },
    //滑块方法（后端验证通过后换出滑块进行验证）
    onSuccess(){ // 滑块验证通过后触发
      Cookies.set('user',JSON.stringify(this.loginAdmin)) // 将管理员信息存储到Cookie中
      this.$notify.success("登录成功");
      this.$router.push("/");
    },
    //滑块滑动失败
    onFail(){
      console.log('fail')
    },
    //重新刷新滑块
    onRefresh(){
      console.log('refresh')
    }
  },
};
</script>

<style>
.cover {
  width: fit-content;
  background-color: white;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 1000;
}
</style>