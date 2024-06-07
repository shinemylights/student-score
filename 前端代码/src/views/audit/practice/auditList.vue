<template>
  <div>
    <query-box :show-data="showData" :query-type="queryType" @submitData="submitData"></query-box>
    <el-table
      :data="pageData.records"
      stripe
      style="width: 100%">
      <el-table-column
        prop="studentId"
        label="学号"
        width="180">
      </el-table-column>
      <el-table-column
        prop="studentName"
        label="学生姓名"
        width="180">
      </el-table-column>
      <el-table-column
        prop="name"
        label="竞赛名称"
        width="180">
      </el-table-column>
      <el-table-column
        prop="submissionDate"
        label="创建时间">
      </el-table-column>
      <el-table-column label="状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.approvalStatus === '0'">待审批</el-tag>
          <el-tag v-else-if="scope.row.approvalStatus === '1'">已同意</el-tag>
          <el-tag v-else-if="scope.row.approvalStatus === '2'">已驳回</el-tag>
          <el-tag v-else>未知状态</el-tag> <!-- 处理非预期的state值 -->
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="mini"
            @click="showDetail(scope.row.id)">查看详情</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      class="pagination"
      background
      :current-page="current"
      @current-change="changePage"
      layout="total,prev, pager, next"
      :page-size="pageData.size"
      :total="pageData.total">
    </el-pagination>

  </div>
</template>
<script>
import queryBox from "@/components/queryBox.vue";
export default {
  components: {
    queryBox
  },
  data(){
    return{
      pageData:{},
      current:0,
      queryType: 'reviewGrade',
      showData:{
        stuId:true,
        teaId:true
      },
    }
  },
  created() {
    this.changePage(0);
  },
  methods:{
    async changePage(current){
      const res=await this.$axios.post('/audit/prac/getAuditList',{current:current})
      if(res.data.code===200){
        this.pageData=res.data.data
        console.log(this.pageData)
      }
    },
    submitData(data){
      this.pageData=data;
    },
    showDetail(id){
      this.$router.push({
        name:'pracauditDetail',
        query: {
          id: id
        }
      })
    }

  }

}
</script>
<style>
.pagination{
  margin-top: 15px;
  float: right;
}
</style>
