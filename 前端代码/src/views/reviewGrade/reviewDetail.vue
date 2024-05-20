<template>
  <div>
    <el-steps :active="reviewData.length" simple finish-status="success" class="steps">
      <el-step title="学生申请" ></el-step>
      <el-step title="教师审核"></el-step>
      <el-step title="管理员审核" ></el-step>
    </el-steps>

    <el-timeline :reverse="true">
      <el-timeline-item v-for="item in reviewData" :timestamp="item.date" placement="top" :size="timeline.size" :color="timeline.color">
        <el-card>

          <div v-if="item.type==='student'">
            <h4>学生申请奖项审核</h4>
            <h4 style="float: right">{{item.state}}</h4>
            <p><el-tag style="margin-right: 10px">学生</el-tag>{{item.stuId}} 提交于 {{item.date}}</p>
            <el-collapse>
              <el-collapse-item title="详细信息" name="1">
                <el-tag type="warning"style="margin-right: 10px">学生备注</el-tag>
                <span>{{item.desc}}</span>
              </el-collapse-item>
            </el-collapse>
            <div v-show="role==='teacher'&&reviewData.length===1" style="float: right;margin-top: 10px;margin-bottom: 10px">
              <el-button type="primary" size="mini" round @click="updateByTea(0)">同意</el-button>
              <el-button type="warning" size="mini" round @click="updateByTea(1)">拒绝</el-button>
            </div>
          </div>

          <div v-else-if="item.type==='teacher'">
            <h4>教师审批</h4>
            <h4 style="float: right">{{item.state}}</h4>
            <p><el-tag style="margin-right: 10px">教师</el-tag>{{item.teaId}} 审批于 {{item.date}}</p>
            <el-collapse>
              <el-collapse-item title="详细信息" name="1">
                <div v-show="item.state==='教师审核通过'">
<!--                  <el-tag type="warning" style="margin-right: 10px">成绩更改</el-tag>-->
<!--                  <span><el-tag>{{item.oldGrade}}</el-tag> -&#45;&#45;&#45;&#45; 变更为 -&#45;&#45;&#45;&#45; <el-tag>{{item.newGrade}}</el-tag></span>-->
                </div>
                <el-tag type="warning" style="margin-right: 10px;margin-top: 10px">教师备注</el-tag>
                <span>{{item.desc}}</span>
                <br>
<!--                <el-tag type="warning" style="margin-right: 10px;margin-top: 10px">试卷图片</el-tag>-->
                <div class="imageFlex">
                  <el-image v-for="url in item.examPic.split(',')" :src="getExamUrl(url)"></el-image>
                </div>
              </el-collapse-item>
<!--              <div v-show="role==='admin'&&item.finalState==='教师审核通过'" style="float: right;margin-top: 10px;margin-bottom: 10px">-->
<!--                <el-button type="primary" size="mini" round @click="adminData.type=0;adminDialog=true;">同意</el-button>-->
<!--                <el-button type="warning" size="mini" round @click="adminData.type=1;adminDialog=true;">拒绝</el-button>-->
<!--              </div>-->
              <div v-show="role==='admin'" style="float: right;margin-top: 10px;margin-bottom: 10px">
                <el-button type="primary" size="mini" round @click="adminData.type=0;adminDialog=true;">同意</el-button>
                <el-button type="warning" size="mini" round @click="adminData.type=1;adminDialog=true;">拒绝</el-button>
              </div>
            </el-collapse>
          </div>

          <div v-else>
            <h4>管理员审核</h4>
            <h4 style="float: right">{{item.state}}</h4>
            <p><el-tag style="margin-right: 10px">管理员</el-tag>审批于 {{item.date}}</p>
            <el-collapse>
              <el-collapse-item title="详细信息" name="1">
                <el-tag type="warning" style="margin-right: 10px">管理员备注</el-tag>
                <span>{{item.desc}}</span>
              </el-collapse-item>
            </el-collapse>
          </div>

        </el-card>
      </el-timeline-item>
    </el-timeline>

    <!--教师审批弹出框-->
    <exam-up-file
      :is-agree="isAgree"
      :upload-dialog="uploadDialog"
      :up-data="upData"
      @refresh="getReviewById"
      @updateDialog="updateDialog">
    </exam-up-file>

    <!--管理员审批弹出框-->
    <el-dialog
      title="管理员审批"
      :visible.sync="adminDialog"
      width="30%"
    >
      <p>请填写备注</p>
      <el-input
        type="textarea"
        :rows="4"
        placeholder="请输入备注"
        v-model="adminData.adminDesc">
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="adminDialog = false;adminData={}">取 消</el-button>
        <el-button type="primary" @click="updateByAdmin">确 定</el-button>
        </span>
    </el-dialog>


  </div>
</template>
<script>
import examUpFile from "@/components/examUpFile";
export default {
  components:{examUpFile},
  data(){
    return{
      isAgree:false,
      fileList:[],
      uploadDialog:false,
      adminDialog:false,
      examUrl:this.$BaseUrl+this.$ExamImg,
      upData:{
        id:0,
        type:0
      },
      adminData:{
        id:this.$route.query.id,
        type:0,
        adminDesc:""
      },
      role:this.$store.state.user.user.roles[0],
      reviewData:[],
      timeline:{
        color: '#409EFF',
        size: 'large'
      }

    }
  },
  created() {
    this.getReviewById()
  },
  methods:{
    async updateByAdmin(){
      const res=await this.$axios.post('/review/updateByAdmin',this.adminData)
      if(res.data.code===200){
        this.adminData={}
        this.adminDialog=false
        await this.getReviewById()
        this.$message.success("审批成功")
      }
    },
    getExamUrl(url){
      return this.$BaseUrl+this.$ExamImg+url
    },
    updateDialog(val){
      this.uploadDialog=val
    },
    updateByTea(type){
      this.isAgree = type === 0;
      this.upData.id=this.$route.query.id
      this.upData.type=type
      this.uploadDialog=true
    },
    async getReviewById(){
      const res= await this.$axios.get('/review/getReviewById?id='+this.$route.query.id)
      if(res.data.code===200){
        this.reviewData=res.data.data
      }
    }
  }

}
</script>
<style scoped lang="scss">
.imageFlex{
  display: flex;
  flex-wrap:wrap;
  justify-content:space-around;
}
.steps{
  background-color: #909399;
  margin-bottom: 20px;
}
</style>
