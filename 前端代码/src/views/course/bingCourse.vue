<template>
  <div>
    <el-empty v-if="bingCourseList.length===0" description="暂无选课任务">
      <el-button v-show="role==='admin'" ype="primary" @click="dialogBingCourse=true">新增选课任务</el-button>
    </el-empty>
    <div v-else>
      <el-button v-show="role==='admin'" style="margin-bottom: 20px" type="primary" @click="dialogBingCourse=true">新增选课任务</el-button>
      <el-card v-for="item in bingCourseList"  style="margin-bottom: 20px">
        <el-button v-show="role==='admin'" type="danger" style="float: right" icon="el-icon-delete" circle @click="delBingCourse(item.id)"></el-button>
        <p v-if="item.type===0" class="title">{{getStuYear(item.stuYear)}}学生选课</p>
        <p v-else-if="item.type===1" class="title">{{getStuYear(item.stuYear)}}学生重修选课</p>
        <p v-else class="title">{{getStuYear(item.stuYear)}}学生补选选课</p>
        <p style="text-align: center">开始选课时间：{{item.startTime}}-----{{item.endTime}}</p>
        <div class="flex">
          <p style="margin-left: 10%"><el-tag>选课对象</el-tag></p>
          <p style="margin-left: 10%;">{{item.target}}</p>
        </div>
        <div class="flex">
          <p style="margin-left: 10%"><el-tag type="warning">注意事项</el-tag></p>
          <p style="margin-left: 10%">{{item.suggest}}</p>
        </div>
        <el-link v-show="isStart(item.startTime,item.endTime)===0" type="warning" style="float: right;margin-bottom: 20px">等待开始</el-link>
        <el-link v-show="isStart(item.startTime,item.endTime)===1" type="primary" style="float: right;margin-bottom: 20px" @click.native="toCourseList(item.id,item.type)">进入选课</el-link>
        <el-link v-show="isStart(item.startTime,item.endTime)===2" type="danger" style="float: right;margin-bottom: 20px">已结束</el-link>
      </el-card>
    </div>

    <el-dialog title="新增选课任务" :visible.sync="dialogBingCourse" >
      <el-form :model="formData" :rules="rules" ref="ruleForm">
        <el-form-item label="所属学年">
          <el-date-picker
            value-format="yyyy"
            v-model="formData.stuYear"
            type="year"
            placeholder="选择年">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="所属学期">
          <el-radio-group v-model="formData.stuYearNum">
            <el-radio label="一" border>第一学年</el-radio>
            <el-radio label="二" border>第二学年</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="起止时间">
          <el-date-picker
            value-format="yyyy-MM-dd HH:mm:ss"
            v-model="formData.time"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="面向群体">
          <el-input v-model="formData.target"></el-input>
        </el-form-item>
        <el-form-item label="注意事项">
          <el-input v-model="formData.suggest"></el-input>
        </el-form-item>
        <el-form-item label="类型">
          <el-radio-group v-model="formData.type">
            <el-radio :label="0">普通</el-radio>
            <el-radio :label="1">重修</el-radio>
            <el-radio :label="2">补选</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogBingCourse = false;formData={}">取 消</el-button>
        <el-button type="primary" @click="submitData('ruleForm')">确 定</el-button>
      </div>
    </el-dialog>

  </div>

</template>

<script>
import moment from 'moment'
export default {
  data(){
    return{
      role:this.$store.state.user.user.roles[0],
      bingCourseList:[],
      dialogBingCourse:false,
      formData:{
        stuYear:'',
        stuYearNum:'一',
        target:'',
        suggest:'',
        type:0,
        time:''
      },
      rules:{
        stuYear: [{ required: true, message: '请填写学年', trigger: 'blur' }],
        suggest: [{ required: true, message: '请填写注意事项', trigger: 'blur' }],
        time: [{ required: true, message: '请填写起止时间', trigger: 'blur' }],
        target: [{ required: true, message: '请填写面向群体', trigger: 'blur' }],
      }
    }
  },
  created() {
    this.getBingCourseList()
  },
  methods:{
    /*跳转选课页面*/
    toCourseList(id,type){
      this.$router.push({
        name: 'roomList',
        query: {
          id: id,
          type:type
        }
      })
    },
    /*构造学年*/
    getStuYear(stuYear){
      let stuYearArr=stuYear.split(',')
      let year=stuYearArr[0]
      let stuYearNum=stuYearArr[1]
      return year+'-'+(parseInt(year)+1)+'第'+stuYearNum+'学期'
    },
    /*初始化list*/
    async getBingCourseList(){
      const res = await this.$axios.get("/admin/getBingCourseList")
      if(res.data.code===200){
        this.bingCourseList=res.data.data
      }
    },
    /*删除任务*/
    async delBingCourse(id){
      const res = await this.$axios.post("/admin/delBingCourse",{
        id:id
      })
      if(res.data.code===200){
        this.$message.success("删除成功")
        await this.getBingCourseList()
      }
    },
    /*判断任务状态*/
    isStart(startTime,endTime){
      const time = moment().format('YYYY-MM-DD HH:mm:ss');
      if(moment(time).isBefore(startTime)){
        return 0
      }else if(moment(time).isBetween(startTime,endTime)){
        return 1
      }else return 2
    },
    /*提交数据*/
    submitData(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          const res = await this.$axios.post('/admin/addBingCourse',{
            stuYear:this.formData.stuYear+","+this.formData.stuYearNum,
            startTime:this.formData.time[0],
            endTime:this.formData.time[1],
            target:this.formData.target,
            suggest:this.formData.suggest,
            type:this.formData.type
          })
          if(res.data.code===200){
            this.$message.success("新增成功")
            this.dialogBingCourse=false;
            await this.getBingCourseList()
            this.formData={}
          }
        } else {
          return false;
        }
      });
    },
  }
}
</script>

<style scoped>
.title{
  font-weight: bold;
  text-align: center;
  font-size: 20px;
}
.flex{
  display: flex;
  align-items:baseline;
}
</style>

