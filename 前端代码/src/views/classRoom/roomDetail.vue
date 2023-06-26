<template>
  <div style="height: 100%">

    <div class="myCouImg"
         :style="{backgroundImage: 'url(' + this.$BaseUrl+this.$CouImg+roomDetail.bcImg + ')'}">
      <span class="pc">{{roomDetail.couName}}</span>

      <el-upload
        v-if="role==='teacher'"
        :action="action"
        :headers="myHeaders"
        :on-success="upSuccess"
        :data="upImgData"
        :before-upload="beforeUp"
        :show-file-list="false"
      >
        <el-button slot="trigger" type="text" class="editIcon">
          <svg-icon icon-class="#icon-tianxie"/>
        </el-button>
      </el-upload>

      <p class="majP">{{roomDetail.major}}</p>


      <p class="tim" >开课时间：{{roomDetail.timing===''?'待定':roomDetail.timing}}</p>

      <p class="p">课堂码：{{roomDetail.classKey}}</p>

      <div class="num">
        <el-tag size="mini">学</el-tag>
        <span> {{roomDetail.stuSum}}人</span>
      </div>
      <el-tag class="tag" type="warning" size="mini">{{roomDetail.couState}}</el-tag>

      <el-dropdown class="drop" v-if="roomDetail.couState==='待开课'&&role==='teacher'">
        <el-button type="primary" size="medium">
          开课<i class="el-icon-arrow-down el-icon--right"></i>
        </el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="changeState(1)">现在开课</el-dropdown-item>
          <el-dropdown-item @click.native="timingDia=true">定时开课</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>

      <el-button
        v-if="roomDetail.couState==='进行中'&&role==='teacher'"
        class="drop"
        @click="changeState(2)"
        type="primary"
        size="medium">
        结束课程</el-button>

<!--      <el-button-->
<!--        v-if="role==='student'"-->
<!--        class="drop"-->
<!--        @click="exitRoom"-->
<!--        type="primary"-->
<!--        size="medium">-->
<!--        退出课堂</el-button>-->

      <!--设置定时开课的时间-->
      <el-dialog :visible.sync="timingDia" title="设置时间" width="30%">
        <el-date-picker
          v-model="time"
          value-format="yyyy-MM-dd HH:mm:ss"
          type="datetime"
          placeholder="选择开课日期时间">
        </el-date-picker>
        <div slot="footer">
          <el-button @click="timingDia = false">取 消</el-button>
          <el-button type="primary" @click="timingStart">确 定</el-button>
        </div>
      </el-dialog>
    </div>

    <el-card class="stuCard">
      <div v-if="role==='teacher'">
        <el-upload
          style="display: inline-block;margin-right: 10px"
          :action=this.uploadUrl
          :headers="myHeaders"
          :data="upGradeData"
          :show-file-list="false"
          :on-success="upFileSuccess"
        >
          <el-button size="small" type="primary">上传成绩</el-button>
        </el-upload>
        <el-button type="text" @click="test">下载excel模板</el-button>
      </div>
      <el-table
        :data="stuData.records"
        style="width: 100%">
        <el-table-column
          label="头像"
          width="70">
          <template slot-scope="scope">
            <img class="usrImg" :src="userImg+scope.row.avatar" alt="暂无">
          </template>
        </el-table-column>
        <el-table-column
          prop="username"
          label="学号"
          width="180">
        </el-table-column>
        <el-table-column
          prop="realName"
          label="姓名"
          width="180">
        </el-table-column>
        <el-table-column
          label="成绩">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.grade>=60" type="success">{{scope.row.grade}}</el-tag>
            <el-tag v-else type="danger">{{scope.row.grade}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          align="right">
          <template slot-scope="scope">
            <el-button
              style="margin-right: 10px"
              v-if="role==='teacher'"
              size="mini"
              type="primary"
              @click="gradeToDia(scope)"
            >给予成绩</el-button>
            <el-popconfirm
              title="确定将该学生强制退课吗？"
              @confirm="exitRoomByTea"
            >
              <el-button
                slot="reference"
                v-if="role==='teacher'"
                size="mini"
                type="danger"
                @click="changeStuId(scope)"
              >强制退课</el-button>
            </el-popconfirm>
            <el-button
              slot="reference"
              v-if="role==='student'&&scope.row.id===userId&&reviewId===-1&&scope.row.grade!==0"
              size="mini"
              type="warning"
              @click="clickReview(scope.row.grade)"
            >申请成绩复核</el-button>
            <el-button
              slot="reference"
              v-if="role==='student'&&scope.row.id===userId&&reviewId!==-1"
              size="mini"
              type="primary"
              @click="toReviewDetail"
            >成绩复核详情</el-button>
            <el-button
              slot="reference"
              v-if="role==='student'&&scope.row.grade<60&&scope.row.id===userId&&scope.row.grade!==0"
              size="mini"
              type="danger"
              @click="rebuild"
            >申请重修</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--分页-->
      <el-pagination
        class="userPag"
        @current-change="changeCurrent"
        background
        layout="total,prev, pager, next"
        :current-page="stuData.current"
        :page-size="stuData.size"
        :total="stuData.total">
      </el-pagination>

      <!--给予成绩的弹出框-->
      <el-dialog
        title="给予成绩"
        :visible.sync="gradeDia"
        width="30%"
       >
        <span>当前课程考试成绩占总成绩的比值为：{{roomDetail.ratio}}</span>
        <el-form :model="gradeData">
          <el-form-item :label="'平时成绩: '+((1-roomDetail.ratio)*100).toFixed(0)+'%'">
            <el-input v-model="gradeData.peaGrade" placeholder="请输入平时成绩"></el-input>
          </el-form-item>
          <el-form-item :label="'考试成绩：'+roomDetail.ratio*100+'%'">
            <el-input v-model="gradeData.examGrade" placeholder="请输入考试成绩"></el-input>
          </el-form-item>
        </el-form>
        <span>总成绩为:{{totalGrade}}</span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="gradeDia = false">取 消</el-button>
          <el-button type="primary" @click="updateGrade">确 定</el-button>
        </span>
      </el-dialog>

      <!--导入失败弹框-->
      <up-grade-box :dialog-data="dialogData" @close="dialogStu=false;dialogData={}" :dialog-stu="dialogStu"></up-grade-box>

      <!--申请成绩复核弹出框-->
      <el-dialog
        title="申请成绩复核"
        :visible.sync="dialogReview"
        width="30%"
      >
        <p>请填写备注</p>
        <el-input
          type="textarea"
          :rows="4"
          placeholder="请输入备注"
          v-model="createReviewData.stuDesc">
        </el-input>
        <span slot="footer" class="dialog-footer">
        <el-button @click="dialogReview = false;createReviewData={}">取 消</el-button>
        <el-button type="primary" @click="createReview">确 定</el-button>
        </span>
      </el-dialog>
    </el-card>



  </div>
</template>

<script>
import UpGradeBox from "@/components/upGradeBox";
export default {
  components: {UpGradeBox},
  created() {
    this.isReview()
    this.getRoomById()
    this.getStuData(1)
  },
  data(){
    return{
      userId:this.$store.state.user.user.id,
      dialogReview:false,
      dialogStu:false,
      dialogData:{},
      uploadUrl:this.$BaseUrl+'/cou/upGrade',
      userImg:this.$BaseUrl+this.$UserImg,
      timingDia:false,
      gradeDia:false,
      reviewId:-1,
      stuId:0,
      current:0,
      createReviewData:{
        oldGrade:0,
        stuDesc:'',
        classRoomId:0
      },
      gradeData:{
        examGrade:'',
        peaGrade:''
      },
      time:'',
      roomDetail:'',
      upImgData:{
        roomId:this.$route.query.id
      },
      upGradeData:{
        roomId:this.$route.query.id
      },
      role:this.$store.state.user.user.roles[0],
      myHeaders: {token: sessionStorage.getItem('token')},
      action: this.$BaseUrl+"/cou/upBcImg",
      stuData: []
    }
  },
  computed:{
    totalGrade(){
      return (this.gradeData.examGrade*this.roomDetail.ratio+this.gradeData.peaGrade*(1-this.roomDetail.ratio)).toFixed(0)
    }
  },
  methods:{
    toReviewDetail(){
      this.$router.push({
        name:'reviewDetail',
        query:{
          id:this.reviewId
        }
      })
    },
    async isReview(){
      const res=await this.$axios.get('/review/getReviewByRoomId?roomId='+this.$route.query.id);
      if(res.data.code===200){
        if(res.data.data!=null){
            this.reviewId=res.data.data.id
        }
      }
    },
    clickReview(grade){
      this.createReviewData.oldGrade=grade;
      this.dialogReview=true;
      this.createReviewData.classRoomId=this.$route.query.id
    },
    /*重修告知*/
    rebuild(){
      this.$notify({
        title: '警告',
        message: '请等待重修选课时间开启，从选课页面进入',
        type: 'warning'
      });
    },
    /*成绩复核*/
    async createReview(){
      const res=await this.$axios.post('/review/createReviewByStu',this.createReviewData)
      if(res.data.code===200){
        this.createReviewData={}
        this.dialogReview=false
        this.reviewId=res.data.data
        this.$message.success("申请成功")
      }
    },
    test(){
      const link = document.createElement('a');
      link.href = require('@/assets/file/学生成绩模板.xlsx');
      link.download = '学生成绩模板.xlsx';
      link.target = '_blank';
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    },
    /*教师强制退课*/
    async exitRoomByTea(){
      const  res=await this.$axios.post('/cou/exitRoomByTea',{
        roomId:this.$route.query.id,
        stuId:this.stuId
      })
      if(res.data.code===200){
        this.$message.success("退课成功")
        await this.getStuData(this.current)
      }
    },
    /*文件上传成功*/
    handleAvatarSuccess(res) {
      if(res.code===200){
        this.userImg=res.data
        this.$message.success("修改成功")
      }else {
        this.$message.error(res.data.msg)
      }
    },
    /*成绩弹框*/
    gradeToDia(scope){
      this.gradeDia=true
      this.stuId=scope.row.id
    },
    /*提交成绩*/
    async updateGrade(){
        const res=await this.$axios.post('/cou/updateGrade',{
          grade:this.totalGrade,
          roomId:this.$route.query.id,
          stuId:this.stuId
        })
      if(res.data.code===200){
        this.$message.success("操作成功")
        this.gradeDia = false
        await this.getStuData(this.current)
      }
    },
    changeStuId(scope){
      this.stuId=scope.row.id
    },
    /*导入成绩成功*/
    async upFileSuccess(res){
      if(res.code===200){
        this.$message.success("修改成功")
        if(res.data[0].length>0||res.data[1].length>0){
          this.dialogData=res.data
          this.dialogStu=true;
          await this.getStuData(this.current)
        }
      }else {
        this.$message.error(res.msg)
      }
    },
    /*图片更改成功*/
    upSuccess(res){
      if(res.code===200){
        this.roomDetail.bcImg=res.data
        this.$message.success("修改成功")
      }else {
        this.$message.error(res.msg)
      }
    },
    /*分页获取选课的学生信息*/
    async getStuData(current){
      const res=await this.$axios.get('/comm/getStuList',{
        params:{
          roomId:this.$route.query.id,
          current:current
        }
      })
      if(res.data.code===200){
        this.stuData=res.data.data
      }
    },
    /*获取当前页数据*/
    changeCurrent(current){
      this.current=current
      this.getStuData(current)
    },
    /*退出课堂*/
    async exitRoom(){
      const res=await this.$axios.get('/stu/room/exitRoom?roomId='+this.$route.query.id)
      if(res.data.code===200){
        this.$message.success("退课成功")
        await this.$router.push('/classRoom/roomListStu')
      }
    },
    /*图片上传前的效验*/
    beforeUp(file){
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 2MB!');
      }
      return isLt2M;
    },
    /*更改课程状态*/
    async changeState(state){
      const res=await this.$axios.post('/cou/changeState',{
        state:state,
        roomId:this.$route.query.id
      })
      if(res.data.code===200){
        await this.getRoomById()
        this.$message.success("变更成功")
      }
    },
    /*定时开课*/
    async timingStart(){
      const res=await this.$axios.post('/cou/timingStart',{
        time:this.time,
        roomId:this.$route.query.id
      })
      if(res.data.code===200){
        await this.getRoomById()
        this.$message.success("变更成功")
        this.timingDia=false
      }
    },
    /*根据id获取课堂信息*/
    async getRoomById(){
      const res=await this.$axios.get('/comm/getRoomById',{
        params:{
          id:this.$route.query.id
        }
      })
      this.roomDetail=res.data.data
    }
  }
}
</script>
<style scoped>
/deep/.el-upload{
  display: inline;
}
</style>
<style scoped lang="scss">
.stuCard{
  margin-top: -20px;
}
.editIcon{
  position: relative;
  top: -45px;
  float: right;
}
.usrImg{
  width: 40px;
  height: 40px;
  border-radius: 50%;
}
.userPag{
  margin-top: 10px;
  margin-bottom: 10px;
  float: right;
}
.myCouImg{
  border-radius: 15px;
  padding-top: 10px;
  color: white;
  width: 100%;
  height: 310px;
  z-index: -1;
  background-position: center;
  overflow: hidden;
  background-size: cover;
  .pc{
    display: block;
    margin-left: 10px;
    font-size: xx-large;
  }
  .majP{
    display: inline-block;
    margin-left: 10px;
    font-size: large;
  }
  .tim{
    margin: 120px 0 0 10px;
  }
  .p{
    display: inline-block;
    margin-top: 20px;
    margin-left: 10px;
  }
  .tag{
    margin-left: 20px;
    display: inline-block;
  }
  .num{
    margin-left: 20px;
    display: inline-block;
  }
  .drop{
    margin-top: 5px;
    margin-right: 10px;
    display: inline-block;
    float: right;
  }


}
</style>

