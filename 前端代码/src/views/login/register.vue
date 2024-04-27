<template>
  <div class="login-container">
    <el-form ref="registerForm" :model="registerForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">

      <div class="title-container">
        <h3 class="title">学生评价系统</h3>
      </div>

      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="#icon-icon-user" />
        </span>
        <el-input v-model="registerForm.username" placeholder="学号"></el-input>
      </el-form-item>

      <el-form-item prop="realName">
        <span class="svg-container">
          <svg-icon icon-class="#icon-icon-user" />
        </span>
        <el-input v-model="registerForm.realName" placeholder="姓名"></el-input>
      </el-form-item>

      <el-form-item prop="email">
        <span class="svg-container">
          <svg-icon icon-class="#icon-youxiang" />
        </span>
        <el-input v-model="registerForm.email" placeholder="邮箱"></el-input>
      </el-form-item>

      <el-form-item prop="confirm">
        <span class="svg-container">
          <svg-icon icon-class="#icon-yanzhengma" />
        </span>
        <el-input v-model="registerForm.confirm" style="width: 60%" placeholder="验证码"></el-input>
        <el-button size="mini" type="primary" style="float: right;margin: 12.5px 10px 0 0" v-text="isSend.content" @click="sendEmail"></el-button>
      </el-form-item>

      <el-form-item prop="phoneNum">
        <span class="svg-container">
          <svg-icon icon-class="#icon-shoujihao" />
        </span>
        <el-input v-model="registerForm.phoneNum" placeholder="手机号"></el-input>
      </el-form-item>

      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="#icon-mima" />
        </span>
        <el-input type="password" v-model="registerForm.password" autocomplete="off" placeholder="密码"></el-input>
      </el-form-item>

      <el-form-item prop="isTeacher">
        <span class="svg-container">
          <svg-icon icon-class="#icon-xuanzehuiyuan" />
        </span>
        <el-radio-group v-model="registerForm.isTeacher" style="margin-left: 15px">
          <el-radio :label="0">注册为学生</el-radio>
          <el-radio :label="1">注册为教师</el-radio>
        </el-radio-group>
      </el-form-item>

      <div style="text-align: center">
        <el-button type="primary" @click="submitForm('registerForm')" class="loginBut">注册</el-button>
        <el-button @click="resetForm('registerForm')" class="loginBut">重置</el-button>
      </div>
      <p style="float: right;font-size: 14px">
        已有账号？<el-link type="primary" @click="toLogin">去登录</el-link>
      </p>
    </el-form>
  </div>
</template>

<script>

export default {
  name: 'Login',
  data() {
    return {
      registerForm: {
        username: '',
        realName: '',
        password: '',
        email:'',
        phoneNum:'',
        confirm:'',
        isTeacher:0,
      },
      isSend:{
        content: '发送验证码',
        totalTime: 60, //验证码有效期60s
        canClick: true
      },   //验证码按钮处理
      //输入验证
      loginRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        realName: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        confirm: [
          { required: true, message: '请输入验证码', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' }
        ],
        phoneNum: [
          { required: true, message: '请输入手机号', trigger: 'blur' }
        ]
      },
    }
  },
  methods: {
    //发送邮件
    sendEmail(){
      if(this.registerForm.email==='') this.$message.info("请填写邮箱信息")
      else {
        this.$axios.get("/sendEmail?email="+this.registerForm.email).then((res)=>{
            this.getNum();
        })
      }
    },
    //验证码倒计时
    getNum(){
      if (!this.isSend.canClick) return  //改动的是这两行代码
      this.isSend.canClick = false
      this.isSend.content = this.isSend.totalTime + 's后重新发送'
      let clock = window.setInterval(() => {
        this.isSend.totalTime--
        this.isSend.content = this.isSend.totalTime + 's后重新发送'
        if (this.isSend.totalTime < 0) {
          window.clearInterval(clock)
          this.isSend.content = '重新发送验证码'
          this.isSend.totalTime = 10
          this.isSend.canClick = true  //这里重新开启
        }
      },1000)
    },
    toLogin () {
      this.$router.push('/login')
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    },
    //提交注册表单
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/register', this.registerForm).then((res) => {
            if (res.data.code === 200) {
              this.$message.success("注册成功")
              this.$router.push("/login")
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
$dark_gray: #131313;
$light_gray: #fcfcfc;
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
