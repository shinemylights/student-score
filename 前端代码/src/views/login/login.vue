<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">

      <div class="title-container">
        <h3 class="title">学生评价系统</h3>
      </div>

      <el-form-item prop="account">
        <span class="svg-container">
          <svg-icon icon-class="#icon-icon-user" />
        </span>
        <el-input v-model="loginForm.account" placeholder="学号或者邮箱"></el-input>
      </el-form-item>

      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="#icon-mima" />
        </span>
        <el-input v-model="loginForm.password" type="password" placeholder="密码"></el-input>
      </el-form-item>

      <div style="text-align: center">
        <el-button type="primary" @click="submitForm('loginForm')" class="loginBut">登录</el-button>
        <el-button @click="resetForm('loginForm')" class="loginBut">重置</el-button>
      </div>
      <p style="float: right;font-size: 14px">
        暂无账号？<el-link type="primary" @click="toRegister">去注册</el-link>
      </p>
    </el-form>
  </div>
</template>

<script>

import store from "@/store";

export default {
  name: 'Login',
  data() {
    return {
      loginForm: {
        account: '',
        password: ''
      },
      loginRules: {
        account: [
          { required: true, message: '请输入账号', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    toRegister () {
      this.$router.push('/register')
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    },
    //提交登录表单
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/login', this.loginForm).then(res => {
            if (res.data.code === 200) {
              this.$message.success("登录成功")
              sessionStorage.setItem('token', res.data.data.token)
              let role=res.data.data.roles
              if(role==='admin'){
                this.$router.push("/student/stuList")
              }else if(role==='teacher'){
                this.$router.push('/classRoom/roomListTea')
              }else {
                this.$router.push('/home')
              }
            }
          })
        } else {
          return false
        }
      })
    },
  }
}
</script>

<style lang="scss">

$bg:#283443;
$light_gray:#fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  background-image: url('../../assets/img/bg.jpg'); // 替换为您的图片路径
  background-size: cover; // 覆盖整个容器
  background-repeat: no-repeat; // 防止图片重复
  background-position: center; // 图片居中显示
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0;
      -webkit-appearance: none;
      border-radius: 0;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style lang="scss">
$bg:#2d3a4b;
$dark_gray: #141617;
$light_gray: #fcfafa;
.loginBut{
  width: 100px;
}
.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 80px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    margin-right: 8px;
    margin-top: 12px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>
