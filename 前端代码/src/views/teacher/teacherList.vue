<template>
  <div>
    <el-button size="small" style="margin-bottom: 15px" type="primary" @click="teaDialog=true;teaTitle='新增教师'">新增</el-button>
    <query-box :show-data="showData" :query-type="queryType" @submitData="submitData"></query-box>
    <el-table
      :data="pageData.records"
      style="width: 100%">
      <el-table-column
        label="用户名"
        width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.username }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="真实姓名"
        width="90">
        <template slot-scope="scope">
          <span>{{ scope.row.realName }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="邮箱"
        width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.email }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="手机号"
        width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.phoneNum }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="身份证号码"
        width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.idCard }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="性别"
        width="90">
        <template slot-scope="scope">
          <span>{{ scope.row.sex }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="teaDialog=true;teaData=scope.row;teaTitle='编辑教师信息'">编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="delTea(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      class="pagination"
      background
      @current-change="changePage"
      layout="total,prev, pager, next"
      :page-size="pageData.size"
      :total="pageData.total">
    </el-pagination>

    <!--教师操作弹出框-->
    <el-dialog :title="teaTitle" :visible.sync="teaDialog" width="30%" @close="teaData={}">
      <el-form :model="teaData">
        <el-form-item size="medium" label="用户名">
          <el-input v-model="teaData.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item size="medium" label="真实姓名">
          <el-input v-model="teaData.realName" placeholder="真实姓名"></el-input>
        </el-form-item>
        <el-form-item size="medium" label="邮箱">
          <el-input v-model="teaData.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <el-form-item size="medium" label="手机号">
          <el-input v-model="teaData.phoneNum" placeholder="手机号"></el-input>
        </el-form-item>
        <el-form-item size="medium" label="身份证号">
          <el-input v-model="teaData.idCard" placeholder="身份证号"></el-input>
        </el-form-item>
        <el-form-item size="medium" label="性别">
          <el-input v-model="teaData.sex" placeholder="性别"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelEdit">取 消</el-button>
        <el-button type="primary" @click="editTea">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import queryBox from "@/components/queryBox";
export default {
  components: {
    queryBox
  },
  data() {
    return {
      teaTitle:'',
      teaDialog:false,
      teaData:{},
      queryType:'teacher',
      showData:{
        username:true,
        realName:true
      },
      pageData:{},
      currentPage:0,
    }
  },
  created() {
    this.changePage(0);
  },
  methods: {
    async changePage(current){
      const res=await this.$axios.post('/admin/getUserList',{current:current,userType:2})
      if(res.data.code===200){
        this.pageData=res.data.data
      }
    },
    submitData(data){
      this.pageData=data;
    },
    async delTea(teaId){
      const res=await this.$axios.post('/admin/delTea',{id:teaId})
      if(res.data.code===200){
        await this.changePage(this.current)
        this.$message.success("删除成功")
      }
    },
    async editTea(){
      const res=await this.$axios.post('/admin/saveOrUpdateTea',this.teaData)
      if(res.data.code===200){
        this.teaDialog=false
        this.teaData={}
        await this.changePage(this.current)
        this.$message.success("更改成功")
      }
    },
    async cancelEdit(){
      this.teaDialog = false;
      this.teaData={};
      await this.changePage(this.current)
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
