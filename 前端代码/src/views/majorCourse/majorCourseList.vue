<template>
  <div>
    <el-button size="small" type="primary" @click="addMajorDialog=true; majorTitle='添加专业'">添加专业</el-button>
    <el-button size="small" type="danger" @click="addMajorDialog=true; majorTitle='删除专业'">删除专业</el-button>
    <el-tabs v-model="majorName" @tab-click="changeMajor">
      <el-tab-pane v-for="item in majorData" :label="item.name" :name="item.name">
        <el-button size="small" type="primary" style="margin-bottom: 10px" @click="addCouDialog=true; couTitle='添加课程'; couData.major=majorName">添加课程</el-button>
        <el-table
          :data="tableData"
          style="width: 100%">
          <el-table-column
            label="课程名称"
            width="150">
            <template slot-scope="scope">
              <span>{{scope.row.couName}}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="学分"
            width="90">
            <template slot-scope="scope">
              <el-tag size="medium">{{ scope.row.couGrade }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            label="学时"
            width="90">
            <template slot-scope="scope">
              <span>{{ scope.row.couTime }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="所属专业"
            width="180">
            <template slot-scope="scope">
              <span>{{ scope.row.major }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="学时比"
            width="180">
            <template slot-scope="scope">
              <el-tag size="medium">{{ scope.row.ratio }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            label="课程类别"
            width="120">
            <template slot-scope="scope">
              <span>{{ scope.row.couType }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="课程属性"
            width="90">
            <template slot-scope="scope">
              <span>{{ scope.row.couAttribute }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="180">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="addCouDialog=true; couData=scope.row; couTitle='编辑课程'">编辑</el-button>
              <el-button
                size="mini"
                type="danger"
                @click="delCourse(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>

    <!--专业操作弹出框-->
    <el-dialog :title="majorTitle" :visible.sync="addMajorDialog" width="30%">
      <el-form :model="addMajorName">
        <el-form-item label="专业名称" label-width="80px">
          <el-input v-model="addMajorName" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addMajorDialog = false;addMajorName=''">取 消</el-button>
        <el-button type="primary" @click="editMajor">确 定</el-button>
      </div>
    </el-dialog>

    <!--课程操作弹出框-->
    <el-dialog :title="couTitle" :visible.sync="addCouDialog" width="30%" @close="couData={}">
      <el-form :model="couData">
        <el-form-item label="课程名称" label-width="80px">
          <el-input v-model="couData.couName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="学分" label-width="80px">
          <el-input v-model="couData.couGrade" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="学时" label-width="80px">
          <el-input v-model="couData.couTime" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="所属专业" label-width="80px">
          <el-input :disabled="true" v-model="couData.major" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="学时比" label-width="80px">
          <el-input v-model="couData.ratio" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="课程类别" label-width="80px">
          <el-select v-model="couData.couType" placeholder="请选择课程类别">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课程属性" label-width="80px">
          <el-radio v-model="couData.couAttribute" label="选修">选修</el-radio>
          <el-radio v-model="couData.couAttribute" label="必修">必修</el-radio>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addCouDialog = false;couData={}">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdateCourse">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>
<script>
export default {
  data(){
    return{
      type:0,
      addCouDialog:false,
      addMajorDialog:false,
      couData:{},
      majorData:[],
      majorName:'',
      addMajorName:'',
      majorTitle:'',
      couTitle:'',
      tableData: [],
      options: [{
        value: 0,
        label: '通识教育'
      }, {
        value: 1,
        label: '专业基础课'
      }, {
        value: 2,
        label: '专业核心课'
      }, {
        value: 3,
        label: '专业实践课'
      }],
    }
  },
  created() {
    this.getMajor();
  },
  methods:{
    /*操作课程*/
    async saveOrUpdateCourse(){
      if(!Number.isInteger(this.couData.couType)){
        this.couData.couType=this.options.find(option => option.label === this.couData.couType).value
      }
      const  res = await this.$axios.post('/major/saveOrUpdateCourse',this.couData)
      if(res.data.code===200){
        this.$message.success("操作成功")
        this.addCouDialog=false
        await this.changeMajor()
      }
    },
    /*删除课程*/
    async delCourse(couId){
      const  res=await this.$axios.post('/major/delCourse',{id:couId})
      if(res.data.code===200){
        this.$message.success("删除成功")
        this.addCouDialog=false
        await this.changeMajor()
      }
    },
    /*获取全部专业信息*/
    async getMajor(){
      const res=await this.$axios.get('cou/getMajor')
      this.majorData=res.data.data
      this.majorName=this.majorData[0].name
      await this.changeMajor()
    },
    /*切换专业*/
    async changeMajor(){
      const res=await this.$axios.get('major/getCourseByMajor?majorName='+this.majorName)
      this.tableData=res.data.data
    },
    /*操作专业*/
    async editMajor(){
      let res
      if(this.majorTitle==='删除专业'){
        res=await this.$axios.post('/major/delMajor',{name:this.addMajorName})
      }else {
        res=await this.$axios.post('/major/addMajor',{name:this.addMajorName})
      }
      if(res.data.code===200){
        this.majorData=res.data.data
        this.majorName=this.majorData[0].name
        await this.changeMajor()
        this.addMajorName=''
        this.addMajorDialog=false;
        this.$message.success("操作成功")
      }
    }
  }

}
</script>
<style>

</style>
