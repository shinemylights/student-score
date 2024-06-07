<template>
  <div>
        <el-card >

          <el-form label-position="top">
            <el-row :gutter="8" class="custom-row-spacing">
              <el-col :span="12">
                <span style="font-weight: bold;">学生学号</span>
              </el-col>
              <el-col :span="12">
                <span style="vertical-align: middle;">{{ reviewData.studentId }}</span>
              </el-col>
            </el-row>
            <el-row :gutter="8" class="custom-row-spacing">
              <el-col :span="12">
                <span style="font-weight: bold;">学生姓名</span>
              </el-col>
              <el-col :span="12">
                <span style="vertical-align: middle;">{{ reviewData.studentName }}</span>
              </el-col>
            </el-row>
          <el-row :gutter="8" class="custom-row-spacing">
            <el-col :span="12">
                <span style="font-weight: bold;">名称</span>
            </el-col>
            <el-col :span="12">
              <span style="vertical-align: middle;">{{ reviewData.name }}</span>
            </el-col>
          </el-row>
          <el-row :gutter="8" class="custom-row-spacing">
            <el-col :span="12">
              <span style="font-weight: bold;">起始日期</span>
            </el-col>
            <el-col :span="12">
              <span style="vertical-align: middle;">{{ reviewData.startDate }}</span>
            </el-col>
          </el-row>
          <el-row :gutter="8" class="custom-row-spacing">
            <el-col :span="12">
              <span style="font-weight: bold;">结束日期</span>
            </el-col>
            <el-col :span="12">
              <span style="vertical-align: middle;">{{ reviewData.endDate }}</span>
            </el-col>
          </el-row>
          <el-row :gutter="8" class="custom-row-spacing">
            <el-col :span="12">
              <span style="font-weight: bold;">实践单位</span>
            </el-col>
            <el-col :span="12">
              <span style="vertical-align: middle;">{{ reviewData.organization }}</span>
            </el-col>
          </el-row>
            <el-row class="custom-row-spacing">
              <span style="font-weight: bold;">描述</span>
            </el-row>
            <el-row class="custom-row-spacing">
              <span style="vertical-align: middle;">{{ reviewData.description }}</span>
            </el-row>

            <!-- 其他信息项类似添加 -->

            <!-- 证明图片缩略图 -->
            <el-row>
              <el-col :span="6" v-for="(image, index) in reviewData.images" :key="index">
                <el-image
                  :src="image"
                  style="width: 100px; height: auto; object-fit: cover;"
                  :preview-src-list="[image]"
                ></el-image>
              </el-col>
            </el-row>
          </el-form>

<!--             操作按钮 -->
            <el-row type="flex" justify="end">
              <el-button type="primary" @click="approveAward">同意</el-button>
              <el-button @click="rejectAward">驳回</el-button>
            </el-row>

<!--          <div v-else-if="item.type==='teacher'">-->
<!--            <h4>管理员审批</h4>-->
<!--            <h4 style="float: right">{{item.state}}</h4>-->
<!--            <p><el-tag style="margin-right: 10px">管理员</el-tag>{{item.teaId}} 审批于 {{item.date}}</p>-->
<!--            <el-collapse>-->
<!--              <el-collapse-item title="详细信息" name="1">-->
<!--                <div v-show="item.state==='管理员审核通过'">-->
<!--&lt;!&ndash;                  <el-tag type="warning" style="margin-right: 10px">成绩更改</el-tag>&ndash;&gt;-->
<!--&lt;!&ndash;                  <span><el-tag>{{item.oldGrade}}</el-tag> -&#45;&#45;&#45;&#45; 变更为 -&#45;&#45;&#45;&#45; <el-tag>{{item.newGrade}}</el-tag></span>&ndash;&gt;-->
<!--                </div>-->
<!--                <el-tag type="warning" style="margin-right: 10px;margin-top: 10px">教师备注</el-tag>-->
<!--                <span>{{item.desc}}</span>-->
<!--                <br>-->
<!--&lt;!&ndash;                <el-tag type="warning" style="margin-right: 10px;margin-top: 10px">试卷图片</el-tag>&ndash;&gt;-->
<!--                <div class="imageFlex">-->
<!--                  <el-image v-for="url in item.examPic.split(',')" :src="getExamUrl(url)"></el-image>-->
<!--                </div>-->
<!--              </el-collapse-item>-->
<!--&lt;!&ndash;              <div v-show="role==='admin'&&item.finalState==='教师审核通过'" style="float: right;margin-top: 10px;margin-bottom: 10px">&ndash;&gt;-->
<!--&lt;!&ndash;                <el-button type="primary" size="mini" round @click="adminData.type=0;adminDialog=true;">同意</el-button>&ndash;&gt;-->
<!--&lt;!&ndash;                <el-button type="warning" size="mini" round @click="adminData.type=1;adminDialog=true;">拒绝</el-button>&ndash;&gt;-->
<!--&lt;!&ndash;              </div>&ndash;&gt;-->
<!--              <div v-show="role==='admin'" style="float: right;margin-top: 10px;margin-bottom: 10px">-->
<!--                <el-button type="primary" size="mini" round @click="adminData.type=0;adminDialog=true;">同意</el-button>-->
<!--                <el-button type="warning" size="mini" round @click="adminData.type=1;adminDialog=true;">拒绝</el-button>-->
<!--              </div>-->
<!--            </el-collapse>-->
<!--          </div>-->

<!--          <div v-else>-->
<!--            <h4>管理员审核</h4>-->
<!--            <h4 style="float: right">{{item.state}}</h4>-->
<!--            <p><el-tag style="margin-right: 10px">管理员</el-tag>审批于 {{item.date}}</p>-->
<!--            <el-collapse>-->
<!--              <el-collapse-item title="详细信息" name="1">-->
<!--                <el-tag type="warning" style="margin-right: 10px">管理员备注</el-tag>-->
<!--                <span>{{item.desc}}</span>-->
<!--              </el-collapse-item>-->
<!--            </el-collapse>-->
<!--          </div>-->

        </el-card>
<!--      </el-timeline-item>-->
<!--    </el-timeline>-->

<!--    &lt;!&ndash;教师审批弹出框&ndash;&gt;-->
<!--    <exam-up-file-->
<!--      :is-agree="isAgree"-->
<!--      :upload-dialog="uploadDialog"-->
<!--      :up-data="upData"-->
<!--      @refresh="getReviewById"-->
<!--      @updateDialog="updateDialog">-->
<!--    </exam-up-file>-->

<!--    &lt;!&ndash;管理员审批弹出框&ndash;&gt;-->
<!--    <el-dialog-->
<!--      title="管理员审批"-->
<!--      :visible.sync="adminDialog"-->
<!--      width="30%"-->
<!--    >-->
<!--      <p>请填写备注</p>-->
<!--      <el-input-->
<!--        type="textarea"-->
<!--        :rows="4"-->
<!--        placeholder="请输入备注"-->
<!--        v-model="adminData.adminDesc">-->
<!--      </el-input>-->
<!--      <span slot="footer" class="dialog-footer">-->
<!--        <el-button @click="adminDialog = false;adminData={}">取 消</el-button>-->
<!--        <el-button type="primary" @click="updateByAdmin">确 定</el-button>-->
<!--        </span>-->
<!--    </el-dialog>-->


  </div>
</template>
<script>
import examUpFile from "@/components/examUpFile.vue";
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
    async approveAward(){
      const res= await this.$axios.get('/audit/prac/approveAward?id=' + this.$route.query.id)
      if(res.data.code===200){
        // this.adminData={}
        // this.adminDialog=false
        // await this.getReviewById()
        this.$message.success("审批成功")
        this.$router.go(-1);
      }
    },
    async rejectAward(){
      try {
        const res= await this.$axios.get('/audit/prac/rejectAward?id=' + this.$route.query.id)
        if(res.data.code===200){
          // this.adminData={}
          // this.adminDialog=false
          // await this.getReviewById()
          this.$message.success("驳回成功")
          this.$router.go(-1);
        }
      } catch (error) {
        console.error("发生错误:", error);
      }
    },
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
      const res= await this.$axios.get('/audit/prac/getAuditById?id=' + this.$route.query.id)
      // const res= await this.$axios.get('/audit/getAuditById?id=' + '5')
      if(res.data.code===200){
        this.reviewData=res.data.data
        console.log(this.reviewData)
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
.custom-row-spacing {
  margin-bottom: 24px; /* 或者使用padding-bottom根据需要调整 */
}
</style>
