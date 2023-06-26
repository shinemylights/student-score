<template>
  <!--成绩图片上传弹出框-->
  <el-dialog
    title="提示"
    :visible.sync="dialog"
    width="30%">
    <div v-show="isAgree">
      <p style="font-weight: bold">请填写新成绩</p>
      <el-input
        style="margin-bottom: 20px"
        placeholder="请输入新成绩"
        v-model="upData.newGrade">
      </el-input>
    </div>
    <p style="font-weight: bold">请填写备注</p>
    <el-input
      style="margin-bottom: 20px"
      type="textarea"
      :rows="4"
      placeholder="请输入备注"
      v-model="upData.teaDesc">
    </el-input>
    <p style="font-weight: bold">请上传试卷图片</p>
    <el-upload
      ref="upload"
      drag
      :http-request="uploadFile"
      :auto-upload="false"
      :file-list="fileList"
      :on-remove="handleRemove"
      multiple
      :data="upData"
      class="avatar-uploader"
      action
      :show-file-list="true"
      :before-upload="beforeAvatarUpload">
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将试卷图片拖到此处，或<em>点击上传</em></div>
      <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
    </el-upload>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialog = false">取 消</el-button>
        <el-button type="primary" @click="submitUpload">确 定</el-button>
      </span>
  </el-dialog>
</template>

<style>
</style>

<script>
export default {
  props:{
    isAgree:false,
    upData:{
      id:0,
      type:0,
      teaDesc:'',
      newGrade:0
    },
    uploadDialog:false
  },
  data() {
    return {
      formData:'',
      dialog:false,
      fileList:[],
      myHeaders: {token: sessionStorage.getItem('token')},
      action: this.$BaseUrl+"/review/updateReviewByTea",
    };
  },
  watch:{
    uploadDialog(val, valOld){
      this.dialog=val
    },
    dialog(val, valOld){
      this.$emit("updateDialog",val)
    }
  },
  methods: {
    uploadFile(file) {
      this.formData.append('files', file.file);
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    async submitUpload() {
      this.formData = new FormData;//创建FormData对象
      if(!this.upData.newGrade){
        this.upData.newGrade=0
      }
      this.formData.append("id",this.upData.id)
      this.formData.append("type",this.upData.type)
      this.formData.append("teaDesc",this.upData.teaDesc)
      this.formData.append("newGrade",this.upData.newGrade)
      this.$refs.upload.submit();
      const res= await this.$axios.post("/review/updateReviewByTea", this.formData)
      if(res.data.code===200){
        this.$message.success("审批成功")
        this.dialog=false
        this.$emit("refresh")
      }
    },
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

