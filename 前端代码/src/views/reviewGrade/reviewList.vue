<template>
  <div>
    <query-box :show-data="showData" :query-type="queryType" @submitData="submitData"></query-box>
    <el-table
      :data="pageData.records"
      stripe
      style="width: 100%">
      <el-table-column
        prop="stuId"
        label="学号"
        width="180">
      </el-table-column>
      <el-table-column
        prop="teaId"
        label="学生姓名"
        width="180">
      </el-table-column>
      <el-table-column
        prop="stuDate"
        label="创建时间">
      </el-table-column>
      <el-table-column
        label="状态">
        <template slot-scope="scope">
          <el-tag>{{scope.row.state.split(',')[scope.row.state.split(',').length-2]}}</el-tag>
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
import queryBox from "@/components/queryBox";
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
      const res=await this.$axios.post('/review/getReviewList',{current:current})
      if(res.data.code===200){
        this.pageData=res.data.data
      }
    },
    submitData(data){
      this.pageData=data;
    },
    showDetail(id){
      this.$router.push({
        name:'reviewDetail',
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
