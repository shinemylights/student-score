<template>
  <div>
    <el-tabs @tab-click="changeMajor">
      <el-tab-pane
        v-for="item in majorData"
        :label="item.name"
      >
        <el-transfer
          style="height:400px"
          filterable
          :filter-method="filterMethod"
          filter-placeholder="请输入课程名称"
          v-model="courseData.alyData"
          :titles="['可选课程', '已绑课程']"
          :button-texts="['弃绑', '绑定']"
          :data="courseData.allData">
          <el-button class="roomBu" slot="right-footer" size="small" @click="saveData" type="primary">保存</el-button>
        </el-transfer>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
  created() {
    this.tableInit()   //初始化选项卡
  },
  data(){
    return{
      value: [],
      majorData:null,
      courseData:{
        allData:[], //全部课程
        alyData:[]  //已选课程id集合
      },
      major:''   //当前选择的专业
    }
  },
  methods:{
    /*初始化选项卡数据*/
    async tableInit(){
      const res =await this.$axios.get('/cou/getMajor')
      this.majorData=res.data.data;
      this.major=this.majorData[0].name
      this.getCourse(this.major)
    },

    /*根据学年与专业获取课程*/
    getCourse(major){
      this.$axios.get('/cou/getCourse',{
        params: {
          major:major
        }
      }).then((res)=>{
        //未选课程
        const allData=[]
        //已选课程
        const alyData=[]
        res.data.data.noList.forEach((course, index) => {
          allData.push({
            label: course.couName,
            key: course.id,
            pinyin: course.couName
          });
        });
        res.data.data.alList.forEach((course,index)=>{
          allData.push({
            label: course.couName,
            key: course.id,
            pinyin: course.couName
          });
          alyData.push(course.id)
        })
        this.courseData.alyData=alyData
        this.courseData.allData=allData
      })
    },
    /*绑定课程*/
    saveData(){
      this.$axios.post('/cou/bindCourse',{
        couIds:this.courseData.alyData,
        major:this.major
      }).then((res)=>{
        if(res.data.code===200){
          this.getCourse(this.major)
          this.$message.success("绑定成功")
        }
      })
    },

    filterMethod(query, item) {
      return item.pinyin.indexOf(query) > -1;
    },

    /*切换专业选项卡*/
    changeMajor(tab){
      this.major=tab.label
      this.getCourse(this.major)
    }
  }
}
</script>

<style>
.roomBu{
  margin-left: 71px;
}
</style>

