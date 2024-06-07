<template>
  <div>
    <el-button size="small" style="margin-bottom: 15px" type="primary" @click="dormDialog=true;dormTitle='新增标签'">新增</el-button>
    <query-box :show-data="showData" :query-type="queryType" @submitData="submitData"></query-box>
    <el-table
      :data="pageData.records"
      style="width: 100%">
      <el-table-column
        label="标签类别"
        width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.category }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="一级标签"
        width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.basic }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="二级标签"
        width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.middle }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="三级标签"
        width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.advanced }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="dormDialog=true;dormData=scope.row;dormTitle='编辑标签'">编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="delDorm(scope.row.id)">删除</el-button>
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

    <!--标签操作弹出框-->
    <el-dialog :title="dormTitle" :visible.sync="dormDialog" width="30%" @close="dormData={}">
      <el-form :model="dormData">
        <el-form-item size="medium" label="标签类别">
          <el-input v-model="tagForm.category" placeholder="标签名"></el-input>
        </el-form-item>
        <el-form-item size="medium" label="一级标签">
          <el-input v-model="tagForm.basic" placeholder=""></el-input>
        </el-form-item>
        <el-form-item size="medium" label="二级标签">
          <el-input v-model="tagForm.middle" placeholder=""></el-input>
        </el-form-item>
        <el-form-item size="medium" label="三级标签">
          <el-input v-model="tagForm.advanced" placeholder=""></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dormDialog = false;dormData={}">取 消</el-button>
        <el-button type="primary" @click="editDorm">确 定</el-button>
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
      showData:{
        dormAddress: true,
        dormNum:true
      },
      dormTitle:'',
      current:0,
      queryType: 'dorm',
      pageData:{},
      currentPage:0,
      dormDialog:false,
      tagData:{},
      tagForm:{}
    }
  },
  created() {
    this.changePage(0);
  },
  methods: {
    async changePage(current){
      const res=await this.$axios.post('/tag/getTagList',{current:current})
      if(res.data.code===200){
        this.pageData=res.data.data
      }
    },
    async editDorm(){
      const res=await this.$axios.post('/tag/saveOrUpdateTag',this.tagForm)
      if(res.data.code===200){
        this.dormDialog=false
        this.dormData={}
        await this.changePage(this.current)
        this.$message.success("更改成功")
      }
    },
    async delDorm(id){
      const res=await this.$axios.post('/admin/delDorm',{id:id})
      if(res.data.code===200){
        await this.changePage(this.current);
        this.$message.success("删除成功")
      }
    },
    submitData(data){
      this.pageData=data;
    },
  }
}
</script>

<style>
.pagination{
  margin-top: 15px;
  float: right;
}
</style>
