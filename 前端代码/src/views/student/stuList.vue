<template>
  <div>
    <el-button size="small" style="margin-bottom: 15px" type="primary" @click="addStu">新增</el-button>
    <query-box :show-data="showData" :query-type="queryType" @submitData="submitData"></query-box>
    <el-table
      :data="pageData.records"
      style="width: 100%">
      <el-table-column
        label="头像"
        width="50">
        <template slot-scope="scope">
          <el-avatar shape="square" size="medium" :src="imgUrl+scope.row.avatar"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column
        label="学号"
        width="140">
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
        width="200">
        <template slot-scope="scope">
          <span>{{ scope.row.email }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="专业班级"
        width="110">
        <template slot-scope="scope">
          <span>{{ scope.row.majorClass[0] }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="入学日期"
        width="130">
        <template slot-scope="scope">
          <span>{{ scope.row.stuDay }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="宿舍地址"
        width="90">
        <template slot-scope="scope">
          <span>{{  scope.row.dorm.length===1?'待完善': scope.row.dorm[1]+ scope.row.dorm[2] }}</span>
        </template>
      </el-table-column>
<!--      <el-table-column-->
<!--        label="手机号"-->
<!--        width="120">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ scope.row.phoneNum }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--        label="身份证号码"-->
<!--        width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ scope.row.idCard }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column
        label="性别"
        width="90">
        <template slot-scope="scope">
          <span>{{ scope.row.sex }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="160">
        <template slot-scope="scope">
          <el-button
            size="mini"
            style="font-size: medium"
            @click="editStu(scope.row)">编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            style="font-size: medium"
            @click="delTea(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="160">
        <template slot-scope="scope">
          <el-button
            size="mini"
            style="font-size: medium"
            @click="navigateToStudentProfile">学生画像</el-button>
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

    <!--完善信息的弹出框-->
    <el-dialog :title="stuTitle" :visible.sync="stuDialog" width="30%">
      <el-form :model="userForm">
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="userForm.realName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="学号" :label-width="formLabelWidth">
          <el-input v-model="userForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号" :label-width="formLabelWidth">
          <el-input v-model="userForm.phoneNum" autocomplete="off"></el-input>
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
        <el-button @click="stuDialog = false;userForm={}">取 消</el-button>
        <el-button type="primary" @click="updateStu">确 定</el-button>
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
      imgUrl:this.$BaseUrl+this.$UserImg,
      userForm:{
        id:'',
        realName: '',
        username: '',
        phoneNum:'',
        age:'',
        sex:'',
        dorm:[],
        idCard:'',
        stuDay:'',
        majorClass:[]
      }, //学生信息完善表单
      majorData:[],  //专业班级数据
      dormData:[], //宿舍数据
      formLabelWidth:'90px',
      stuTitle:'',
      stuDialog:false,
      stuData:{},
      queryType:'student',
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
      const res=await this.$axios.post('/admin/getUserList',{current:current,userType:1})
      if(res.data.code===200){
        this.pageData=res.data.data
      }
    },
    async updateStu(){
      const res = await this.$axios.post('/stu/updateStu',this.userForm)
      if(res.data.code===200){
        this.stuDialog=false
        this.userForm={}
        await this.changePage(this.current)
        this.$message.success("信息编辑成功")
      }
    },
    async addStu(){
      this.stuDialog=true
      this.stuTitle="新增学生"
      //请求专业班级信息
      const res2 = await this.$axios.get('/stu/getMajorClass')
      this.majorData=res2.data.data;
      //请求宿舍信息
      const res3 = await this.$axios.get('/stu/getDorm')
      this.dormData=res3.data.data;
    },
    async editStu(stuData){
      this.stuDialog=true
      this.stuTitle="编辑学生"
      let user=stuData
      this.userForm.username=user.username
      this.userForm.realName=user.realName
      this.userForm.id=user.id
      this.userForm.phoneNum=user.phoneNum
      this.userForm.age=user.age
      this.userForm.sex=user.sex
      this.userForm.idCard=user.idCard
      this.userForm.stuDay=user.stuDay==='待完善'?'':user.stuDay
      this.userForm.majorClass=[user.major[1],user.majorClass[1]]
      this.userForm.dorm=[0,user.dorm[0]]
      //请求专业班级信息
      const res2 = await this.$axios.get('/stu/getMajorClass')
      this.majorData=res2.data.data;
      //请求宿舍信息
      const res3 = await this.$axios.get('/stu/getDorm')
      this.dormData=res3.data.data;
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
      const res=await this.$axios.post('/admin/saveOrUpdateTea',this.stuData)
      if(res.data.code===200){
        this.stuDialog=false
        this.stuData={}
        await this.changePage(this.current)
        this.$message.success("更改成功")
      }
    },
    async cancelEdit(){
      this.stuDialog = false;
      this.stuData={};
      await this.changePage(this.current)
    },
    navigateToStudentProfile() {
      // 假设您已经在Vue Router中定义了一个名为'studentProfile'的路由
      this.$router.push({ name: 'teaRadar' });
    }
  }
}
</script>

<style>
.pagination{
  margin-top: 15px;
  float: right;
}
.el-card {
  font-size: 19px !important; /* 使用 !important 来确保样式被应用 */
}

.el-table {
  font-size: 19px !important; /* 使用 !important 来确保样式被应用 */
}

.custom-table .el-table__body {
  font-size: 19px !important; /* 调整为你希望的字体大小 */
}
</style>
