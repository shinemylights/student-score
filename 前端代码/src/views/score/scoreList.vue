<template>
  <div>
    <el-card style="margin-bottom: 20px">
      <el-row :gutter="20">
        <el-col :span="12"><p>学号：<el-tag type="info" style="font-size: large">{{username}}</el-tag></p></el-col>
        <el-col :span="12">
          <p>总平均绩点：
            <el-tooltip class="item" effect="dark" content="平均学分绩点（GPA）=（每门课程的学分数*对应课程的绩点）的总和/学分的总和" placement="top">
              <el-tag style="font-size: large">{{avgPoint.avgGpa}}</el-tag>
            </el-tooltip>
          </p>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-select v-model="stuYear" placeholder="请选择学年">
            <el-option
              v-for="item in yearData"
              :key="item"
              :label="item"
              :value="getStuYear(item)">
            </el-option>
          </el-select>
          <el-button type="primary" style="margin-left: 30px" @click="getGrade">查询</el-button>
        </el-col>
        <el-col :span="12"><p>总学分：<el-tag style="font-size: large">{{sumAllGrade}}</el-tag></p></el-col>
      </el-row>
    </el-card>
    <el-card>
      <el-row :gutter="20" v-show="gradeList.length>0">
        <el-col :span="8"><p>学年总学分：<el-tag type="success" style="font-size: large">{{sumGrade}}</el-tag></p></el-col>
        <el-col :span="8"><p>学年平均绩点：<el-tag type="success" style="font-size: large">{{avgYearPoint.avgGpa}}</el-tag></p></el-col>
        <el-col :span="8"><p>学年平均绩点排名：<el-tag type="success" style="font-size: large">{{avgYearPoint.ranking}}</el-tag></p></el-col>
      </el-row>
      <el-table
        :data="gradeList"
        style="width: 100%">
        <el-table-column
          label="课程名"
          prop="couName">
        </el-table-column>
        <el-table-column
          label="成绩"
          prop="grade">
        </el-table-column>
        <el-table-column
          label="专业排名"
          prop="ranking">
        </el-table-column>
        <el-table-column
          align="right">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              style="font-size: large"
              @click="toRankList(scope.row.couId,scope.row.couName)">大神排名</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>
<script>
export default {
  data(){
    return{
      username:this.$store.state.user.user.username,
      sumGrade:0,
      sumAllGrade:'暂无数据',
      gradeList:[],
      yearData:[],
      stuYear:'',
      avgPoint:{
        avgGpa:'暂无数据',
        ranking:'暂无数据'
      },
      avgYearPoint:{
        avgGpa:'暂无数据',
        ranking:'暂无数据'
      }
    }
  },
  created() {
    this.menuInit()
    this.getPointInStuYear()
    this.getSumGrade()
  },
  methods:{
    toRankList(couId,couName){
      this.$router.push({
        name:'subjectRank',
        query:{
          couName:couName,
          couId:couId,
          stuYear:this.stuYear
        }
      })
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
    /*获取总学分*/
    async getSumGrade(){
      const res=await this.$axios.get('/grade/getSumGrade')
      if(res.data.code===200){
        if(res.data.data!=null){
          this.sumAllGrade=res.data.data
        }
      }
    },
    /*获取学年平均绩点*/
    async getPoint(){
      const res=await this.$axios.get('/grade/getPoint?stuYear='+this.stuYear)
      if(res.data.code===200){
        if(res.data.data!=null){
          this.avgYearPoint=res.data.data
        }
      }
    },
    /*获取个人成绩*/
    async getGrade(){
      let sum=0;
      const res=await this.$axios.get('/grade/getGrade?stuYear='+this.stuYear)
      if(res.data.code===200){
        this.gradeList=res.data.data
        for(let item in this.gradeList){
          sum+=this.gradeList[item].couGrade
        }
        this.sumGrade=sum
        await this.getPoint()
      }
    },
    getStuYear(stuYear){
      return stuYear.substr(0,4)+","+stuYear.substr(10,1)
    },
    /*初始化学年选择框*/
    async menuInit(){
      const res=await this.$axios.get('/stu/room/menuInit')
      this.yearData=res.data.data
    }
  }
}
</script>
<style lang="scss" scoped>
html {
  font-size: 16px !important; /* 这将设置基础字体大小为16px */
}
.el-card {
  font-size: 20px !important; /* 使用 !important 来确保样式被应用 */
}

.el-table {
  font-size: 22px !important; /* 使用 !important 来确保样式被应用 */
}

.custom-table .el-table__body {
  font-size: 20px !important; /* 调整为你希望的字体大小 */
}

</style>
