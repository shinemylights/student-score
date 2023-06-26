
<template>
  <div>
    <el-collapse
      accordion
      @change="changeYear"
      v-model="couFormData.stuYear">
      <el-collapse-item
        v-for="item in yearData"
        :name="item">
        <template slot="title">
          <svg-icon icon-class="#icon-dierxueqi"/>
          <p class="myTitle">{{item}}</p>
        </template>
        <el-tabs @tab-click="changeMajor">
          <el-tab-pane
            v-for="item in majorData"
            :label="item.name"
          >
            <!--教师创建课程按钮-->
            <el-button
              type="primary"
              size="mini"
              style="margin:0 0 10px 0"
              @click="roomFormInit"
              v-if="role==='teacher'">
              创建课堂</el-button>
            <el-empty description="暂无内容" v-if="couData.length===0"></el-empty>
            <div class="couDiv">
              <!--课堂组件-->
              <class-box
                v-for="item in couData"
                :cou-data="item"
                :tip="tip"
              ></class-box>
            </div>
          </el-tab-pane>
        </el-tabs>
      </el-collapse-item>
    </el-collapse>

    <!--创建课堂的弹出框-->
    <el-dialog title="创建课堂" :visible.sync="roomFormDia" width="40%">
      <el-form :model="couFormData">
        <el-form-item label="所属学年" :label-width="formLabelWidth">
          <el-input v-model="couFormData.stuYear" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="所属专业" :label-width="formLabelWidth">
          <el-input v-model="couFormData.major" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="班级描述" :label-width="formLabelWidth">
          <el-input v-model="couFormData.classDesc" autocomplete="off" placeholder="例如:软件19级 3-4班"></el-input>
        </el-form-item>
        <el-form-item label="所属课程" :label-width="formLabelWidth">
          <el-select v-model="couFormData.courseId" placeholder="仅展示该专业的绑定课程">
            <el-option
              v-for="item in courseData"
              :key="item.id"
              :label="item.couName"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="roomFormDia = false">取 消</el-button>
        <el-button type="primary" @click="createRoom">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import ClassBox from "@/components/classBox";
import imgUpFile from "@/components/imgUpFile";
export default {
  components: {ClassBox,imgUpFile},
  created() {
    this.getMajor()
    this.getStuYear()
    //this.getTeaRoom()
  },
  data(){
    return{
      tip:'',
      couData:[],
      couFormData:{
        classDesc:'',   //班级描述
        major:'',     //所属专业
        stuYear:'',     //学年
        courseId:'',    //课程id
        couState:'',
      },
      myHeaders: {token: sessionStorage.getItem('token')},
      action: this.$BaseUrl+"/stu/upAvatar",
      majorData:[],
      yearData:[],
      courseData:[],
      roomFormDia:false,
      formLabelWidth:'90px',
      role:this.$store.state.user.user.roles[0],
      visible:false,
      couImg: require("@/assets/img/cou.jpg")
    }
  },

  methods:{
    /*初始化课程创建菜单*/
    roomFormInit(){
      this.roomFormDia=true
      this.getCourse()
    },
    /*初始化学年信息*/
    async getStuYear(){
      const res=await this.$axios.get('/cou/labelInit')
      this.yearData=res.data.data;
    },
    /*根据学年，专业获取教师已创课程*/
    getTeaRoom(){
      this.$axios.get('/cou/getTeaRoom',{
        params:{
          stuYear:this.couFormData.stuYear.substr(0,4)+","+this.couFormData.stuYear.substr(10,1),
          major:this.couFormData.major
        }
      }).then((res)=>{
        if(res.data.code===200){
          this.couData=res.data.data;
        }
      })
    },
    /*获取全部专业信息*/
    async getMajor(){
      const res=await this.$axios.get('cou/getMajor')
      this.majorData=res.data.data
      //赋予选定专业的默认值
      this.couFormData.major=this.majorData[0].name
    },
    /*获取本教师已选课程信息*/
    async getCourse(){
      const res=await this.$axios.get('cou/getCourse',{
        params:{
          major:this.couFormData.major
        }
      })
      this.courseData=res.data.data.alList
    },
    /*切换当前选定专业*/
    changeMajor(tab){
      this.couFormData.major=tab.label
      this.getTeaRoom()
    },
    /*切换学年*/
    changeYear(){
      this.getTeaRoom()
    },
    /*创建课程*/
    async createRoom(){
      const res=await this.$axios.post('/cou/createRoom',this.couFormData)
      if(res.data.code===200){
        this.$message.success("创建成功")
        this.roomFormDia=false
        this.getTeaRoom()
      }
    }
  }
}
</script>

<style scoped>
/deep/.el-collapse-item__content{
  padding-left: 20px;
}

</style>

<style scoped lang="scss">
.myTitle{
  display: inline-block;
  color: #20a0ff;
  font-size: medium;
}
.couDiv{
  display: flex;
  flex-wrap:wrap;
  justify-content:flex-start;
}

</style>

