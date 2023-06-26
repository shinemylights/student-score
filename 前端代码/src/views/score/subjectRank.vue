<template>
  <div>
    <el-card style="margin-bottom: 10px">
      <i class="el-icon-info" style="margin-right: 10px"></i>
      <span style="color: #909399">此表格显示{{couName}}课程成绩排名前50</span>
    </el-card>
    <el-card>
      <el-table
        :data="rankList"
        style="width: 100%">
        <el-table-column
          label="学号"
          prop="username">
        </el-table-column>
        <el-table-column
          label="姓名"
          prop="realName">
        </el-table-column>
        <el-table-column
          label="专业"
          prop="majorName">
        </el-table-column>
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
      </el-table>
    </el-card>
  </div>
</template>
<script>
export default {
  data(){
    return{
      rankList:[],
      couName:this.$route.query.couName
    }
  },
  created() {
    this.getGradePoint()
  },
  methods:{
    async getGradePoint(){
      const res=await this.$axios.get('/grade/getGradePoint?couId='+this.$route.query.couId+'&stuYear='+this.$route.query.stuYear)
      if(res.data.code===200){
        this.rankList=res.data.data
      }
    }
  }
}
</script>
<style lang="scss" scoped>

</style>

