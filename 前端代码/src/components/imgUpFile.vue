<template>
  <el-upload
    class="avatar-uploader"
    :action="action"
    :headers="myHeaders"
    :show-file-list="false"
    :on-success="handleAvatarSuccess"
    :before-upload="beforeAvatarUpload">
    <img :src="imageUrl" class="userImg" alt="无法显示"/>
  </el-upload>
</template>

<style scoped>
.avatar-uploader{
  width: 170px;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.userImg {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  margin-right: 20px;
}
</style>

<script>
export default {
  data() {
    return {
      myHeaders: {token: sessionStorage.getItem('token')},
      action: this.$BaseUrl+"/stu/upAvatar",
      userImg:this.$store.state.user.user.avatar
    };
  },
  computed:{
    imageUrl(){
      return this.$BaseUrl+this.$UserImg+this.userImg
    }
  },
  methods: {
    handleAvatarSuccess(res) {
      if(res.code===200){
        this.userImg=res.data
        this.$message.success("修改成功")
      }else {
        this.$message.error(res.data.msg)
      }
    },
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isLt2M;
    }
  }
}
</script>

