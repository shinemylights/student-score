<template>
  <div>
    <el-card style="margin-bottom: 10px">
      <i class="el-icon-info" style="margin-right: 10px"></i>
      <span style="color: #909399">此表格显示{{majorName}}专业总绩点排名前50</span>
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
          label="总平均绩点"
          prop="avgGpa">
        </el-table-column>
        <el-table-column
          label="排名"
          prop="ranking">
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>
<script>
import StuList from "@/views/student/stuList";
export default {
  components: {StuList},
  data(){
    return{
      majorName:this.$store.state.user.user.major[0],
      rankList:[],
    }
  },
  created() {
    console.log(this.$store.state.user.user)
    this.getGradePoint()
  },
  methods:{
    async getGradePoint(){
      const res=await this.$axios.get('/grade/getAvgPointLimit?majorName='+this.majorName)
      if(res.data.code===200){
        this.rankList=res.data.data
      }
    }
  }
}
</script>
<style lang="scss" scoped>
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

