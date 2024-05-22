<template>
  <el-collapse>
    <el-collapse-item name="1">
      <template slot="title">
        <div style="margin-left: 20px">
          <i class="el-icon-search"></i><span style="margin-left: 10px;color: #33aef0">查询</span>
        </div>
      </template>
      <div style="margin-left: 20px">
        <el-form :inline="true" :model="formData">
          <el-form-item size="medium" label="用户名" v-if="showData.username">
            <el-input v-model="formData.username" placeholder="用户名"></el-input>
          </el-form-item>
          <el-form-item size="medium" label="真实姓名" v-if="showData.realName">
            <el-input v-model="formData.realName" placeholder="真实姓名"></el-input>
          </el-form-item>
          <el-form-item size="medium" label="标签名" v-if="showData.dormAddress">
            <el-input v-model="formData.dormAddress" placeholder="标签名"></el-input>
          </el-form-item>
          <el-form-item size="medium" label="描述" v-if="showData.dormNum">
            <el-input v-model="formData.dormNum" placeholder="描述"></el-input>
          </el-form-item>
          <el-form-item size="medium" label="学号" v-if="showData.stuId">
            <el-input v-model="formData.stuId" placeholder="学号"></el-input>
          </el-form-item>
          <el-form-item size="medium" label="教师编号" v-if="showData.teaId">
            <el-input v-model="formData.teaId" placeholder="教师编号"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button size="small" type="primary" @click="submitForm">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-collapse-item>
  </el-collapse>
</template>
<script>
export default {
  props:{
    queryType:'',
    showData:{
      username:false,
      realName:false,
      dormAddress:false,
      dormNum:false,
      stuId:false,
      teaId:false
    }
  },
  data() {
    return {
      formData:{}
    }
  },
  methods: {
    /*过滤表单数据*/
    filterData(formData){
      for(let item in formData){
        if(formData[item]===''){
          formData[item]=null
        }
      }
    },
    async submitForm(){
      this.filterData(this.formData)
      let res
      if(this.queryType==='teacher'){
        res= await this.$axios.post('/admin/getUserList',{
          current:0,
          userType:2,
          stUser:this.formData
        })
      }else if(this.queryType==='dorm'){
        res=await this.$axios.post('/admin/getDormList',{
          current:0,
          stDorm:this.formData
        })
      }else if(this.queryType==='student'){
        res= await this.$axios.post('/admin/getUserList',{
          current:0,
          userType:1,
          stUser:this.formData
        })
      }else if(this.queryType==='reviewGrade'){
        res=await this.$axios.post('/review/getReviewList',{
          current:0,
          stReviewGrade:this.formData
        })
      }
      if(res.data.code===200){
        this.$emit("submitData",res.data.data)
      }
    }

  }
}
</script>

<style>

</style>
