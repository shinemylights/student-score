<template>
  <div>
    <div  class="myCard">

      <!--个人头像，成绩card-->
      <el-card style="margin-right: 20px">
        <div class="userInfo">
          <div>
            <img-up-file></img-up-file>
            <p>姓名：{{userInfo.realName}}</p>
            <p>学号：{{userInfo.username}}</p>
          </div>
          <!--分割线-->
          <el-divider direction="vertical" style="width: 30px"></el-divider>
          <div style="width: 100%">
            <p>平均绩点：<el-tag>{{avgPoint.avgGpa}}</el-tag></p>
            <p>专业排名：<el-tag type="warning">{{avgPoint.ranking}}</el-tag></p>
          </div>
        </div>
        <el-button type="text" class="myButton" @click="toRank">详细排名</el-button>
      </el-card>

      <!--个人信息card-->
      <el-card>
        <el-descriptions title="个人信息" :column="3" border>
          <el-descriptions-item label="手机号">{{userInfo.phoneNum}}</el-descriptions-item>
          <el-descriptions-item label="邮箱">{{userInfo.email}}</el-descriptions-item>
          <el-descriptions-item label="年龄">{{userInfo.age}}</el-descriptions-item>
          <el-descriptions-item label="宿舍地址">{{userInfo.dorm.length===1?'待完善':userInfo.dorm[1]+userInfo.dorm[2]}}</el-descriptions-item>
          <el-descriptions-item label="证件号">{{userInfo.idCard}}</el-descriptions-item>
          <el-descriptions-item label="性别">{{userInfo.sex}}</el-descriptions-item>
          <el-descriptions-item label="专业班级">
            <el-tag size="small">{{userInfo.majorClass[0]}}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="入学日期">{{userInfo.stuDay}}</el-descriptions-item>
          <el-descriptions-item label="专业">
            <el-tag size="small">{{userInfo.major[0]}}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="详细住址">郑州轻工业大学7#223</el-descriptions-item>
        </el-descriptions>
        <el-button type="text" class="myButton" style="margin-top: 10px" @click="addStuForm">完善信息</el-button>
      </el-card>
    </div>

    <!--绩点折线图-->
    <el-card class="gpaCard" id="gpaCard" style="margin-right: 2%"></el-card>
    <!--绩点排名折线图-->
    <el-card class="gpaCard" id="gpaRankCard"></el-card>

    <!--完善信息的弹出框-->
    <el-dialog title="完善信息" :visible.sync="stuFormDia" width="30%">
      <el-form :model="userForm">
        <el-form-item label="手机号" :label-width="formLabelWidth">
          <el-input v-model="userForm.phoneNum" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" :label-width="formLabelWidth">
          <el-input v-model="userForm.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="年龄" :label-width="formLabelWidth">
          <el-input v-model="userForm.age" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" :label-width="formLabelWidth">
          <el-input v-model="userForm.sex" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="证件号" :label-width="formLabelWidth">
          <el-input v-model="userForm.idCard" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="宿舍地址" :label-width="formLabelWidth">
          <el-cascader
            style="width: 80%"
            v-model="userForm.dorm"
            :options="dormData"
          ></el-cascader>
        </el-form-item>
        <el-form-item label="入学日期" :label-width="formLabelWidth">
          <el-date-picker
            style="width: 80%"
            v-model="userForm.stuDay"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="专业班级" :label-width="formLabelWidth">
          <el-cascader
            style="width: 80%"
            v-model="userForm.majorClass"
            :options="majorData"
          ></el-cascader>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="stuFormDia = false">取 消</el-button>
        <el-button type="primary" @click="updateStu">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import imgUpFile from "@/components/imgUpFile";
import * as echarts from 'echarts';
export default {
  data(){
    return{
      avgPoint:{
        avgGpa:'暂无数据',
        ranking:'暂无数据'
      },
      stuFormDia:false,
      userInfo:this.$store.state.user.user,
      userForm:{
        id:'',
        email:'',
        phoneNum:'',
        age:'',
        sex:'',
        dorm:[],
        idCard:'',
        stuDay:'',
        majorClass:[]
      }, //学生信息完善表单
      formLabelWidth:'90px',
      majorData:[],  //专业班级数据
      dormData:[], //宿舍数据
    }
  },
  created() {
    this.getPointInStuYear()
  },
  mounted() {
    //初始化绩点折线图
    this.gpaInit()
    this.gpaRankInit()
    this.initLineChartPoint(0)
    this.initLineChartPoint(1)
  },
  methods:{
    toRank(){
      this.$router.push("/score/gpaRank")
    },
    /*获取折线图数据*/
    async initLineChartPoint(type){
      const res=await this.$axios.get('/grade/initLineChartPoint?type='+type)
      if(res.data.code===200){
        return res.data.data
      }
    },
    /*获取总平均绩点*/
    async getPointInStuYear(){
      const res=await this.$axios.get('/grade/getPointInStuYear')
      if(res.data.code===200){
        if(res.data.data!=null){
          this.avgPoint=res.data.data
        }
      }
    },
    /*构造完善信息表单的初始数据*/
    async addStuForm(){
      this.stuFormDia=true
      //刷新用户信息
      const res1 = await this.$axios.get('/getInfo')
      let user=res1.data.data
      this.userForm.id=user.id
      this.userForm.email=user.email===null?'待完善':user.email
      this.userForm.phoneNum=user.phoneNum
      this.userForm.age=user.age
      this.userForm.sex=user.sex
      this.userForm.idCard=user.idCard
      this.userForm.stuDay=user.stuDay==='待完善'?'':user.stuDay
      this.userForm.majorClass=[this.userInfo.major[1],this.userInfo.majorClass[1]]
      this.userForm.dorm=[0,this.userInfo.dorm[0]]
      //请求专业班级信息
      const res2 = await this.$axios.get('/stu/getMajorClass')
      this.majorData=res2.data.data;
      //请求宿舍信息
      const res3 = await this.$axios.get('/stu/getDorm')
      this.dormData=res3.data.data;
    },
    /*完善用户信息*/
    async updateStu(){
      const res = await this.$axios.post('/stu/updateStu',this.userForm)
      if(res.data.code===200){
        this.$message.success("信息完善成功")
        //刷新用户信息
        const res1 = await this.$axios.get('/getInfo')
        this.userInfo=res1.data.data
        this.stuFormDia = false
      }
    },
    /*构造绩点折线图*/
    async gpaInit() {
      let myChart = echarts.init(document.getElementById('gpaCard'));
      const  resData=await this.initLineChartPoint(0)
      myChart.setOption({
        title: {
          text: '绩点'
        },
        legend: {
          data: ['绩点']
        },
        tooltip: {
          trigger: 'axis'
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: resData.xData
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '绩点',
            type: 'line',
            stack: 'Total',
            data: resData.yData
          },
        ]
      })
    },
    /*构造绩点排名折线图*/
    async gpaRankInit() {
      let myChart = echarts.init(document.getElementById('gpaRankCard'));
      const  resData=await this.initLineChartPoint(1)
      myChart.setOption({
        title: {
          text: '绩点排名'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['绩点排名']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          axisLine: {
            onZero:false
          },
          type: 'category',
          boundaryGap: false,
          data: resData.xData
        },
        yAxis: {
          type: 'value',
          inverse:true
        },
        series: [
          {
            name: '绩点排名',
            type: 'line',
            stack: 'Total',
            data: resData.yData
          },
        ]
      })
    }
  },
  components:{imgUpFile}
}
</script>

<style scoped>
.myButton{
  float: right;
  margin-bottom: 0;
}
.myCard{
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
}
.gpaCard {
  display: inline-block;
  padding: 10px;
  margin-top: 20px;
  width: 49%;
  height: 300px;
}
.el-divider--vertical{
  height: auto !important;
}
.userInfo{
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
}
</style>


