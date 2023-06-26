<template>
  <div class="navbar">
    <hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <breadcrumb class="breadcrumb-container" />

   <div class="myTag">
     <el-tag v-text="role"></el-tag>
   </div>

    <div class="right-menu">
      <el-dropdown class="avatar-container" trigger="click">
        <div class="avatar-wrapper">
         <span>
            <img class="usrImg" :src="this.$BaseUrl+this.$UserImg+userInfo.avatar" alt="暂无">
         </span>
<!--          <i class="el-icon-caret-bottom" />-->
        </div>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="logout" icon="el-icon-switch-button">
           退出登录
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'

export default {
  components: {
    Breadcrumb,
    Hamburger
  },
  data(){
    return {
      userInfo:this.$store.state.user.user,
      role:'',
    }
  },
  computed: {
    ...mapGetters([
      'sidebar',
    ])
  },
  created() {
    this.getRole()
  },
  methods: {
    /*判断身份*/
    getRole(){
      let role=this.userInfo.roles
      if(role[0]==='admin') this.role='admin'
      if(role[0]==='student') this.role= '学生'
      if(role[0]==='teacher') this.role= '教师'
    },
    /*清除本地存储,退出登录*/
    logout(){
      this.$store.dispatch('user/logout')
    },
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
  }
}
</script>

<style lang="scss" scoped>
.myTag {
  margin-right: 10px;
  display: inline-block;
  line-height: 50px;
  float: right;
}
.usrImg{
  width: 35px;
  height: 35px;
  border-radius: 50%;
  margin-top: 8.4px;
  margin-bottom: 8.4px;
}
.userShow{
  display: inline-block;
  margin-left: 20px;
  margin-right: 10px;
}
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color:transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .right-menu {
    margin-right: 10px;
    width: 35px;
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        //margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
