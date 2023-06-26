<template>
  <div>
    <el-collapse v-model="stuYear" accordion @change="getRoomsByYear">
      <el-collapse-item
        v-for="item in yearData"
        :name="item">
        <template slot="title">
          <svg-icon icon-class="#icon-dierxueqi"/>
          <p class="myTitle">{{item}}</p>
        </template>
        <el-tabs v-model="couType" @tab-click="getRoomsByYear" :stretch="true">
          <el-tab-pane label="普通课程" name="0">
            <div class="couDiv">
              <!--课堂组件-->
              <class-box
                v-for="item in couData"
                :cou-data="item"
                :tip="tip"
                @exitRoom="exitRoom"
              ></class-box>
            </div>
          </el-tab-pane>
          <el-tab-pane label="重修课程" name="1">
            <div class="couDiv">
              <!--课堂组件-->
              <class-box
                v-for="item in couData"
                :cou-data="item"
                :tip="tip"
                @exitRoom="exitRoom"
              ></class-box>
            </div>
          </el-tab-pane>
        </el-tabs>
        <el-empty description="暂无内容" v-if="couData.length===0"></el-empty>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
import ClassBox from "@/components/classBox";
export default {
  components:{ClassBox},
  created() {
    this.menuInit()   //初始化选项卡
  },
  data(){
    return{
      tip:{
        type:'delete',
        title:'确定要退出课堂吗？',
        icon:'#icon-shanchu'
      },
      couType:0,
      stuYear:'',
      couData:'',
      yearData:[]
    }
  },
  methods:{
    /*退出课堂*/
    async exitRoom(couId){
      const res=await this.$axios.get('/stu/room/exitRoom?roomId='+couId+'&couType='+this.couType)
      if(res.data.code===200){
        this.$message.success("退课成功")
        await this.getRoomsByYear()
      }
    },
    /*根据学年获取已选课堂*/
    async getRoomsByYear(){
      let stuYear=this.stuYear.substr(0,4)+","+this.stuYear.substr(10,1)
      const res=await this.$axios.get('/stu/room/getRoomsByYear?stuYear='+stuYear+'&couType='+this.couType)
      this.couData=res.data.data
    },
    /*初始化学年菜单*/
    async menuInit(){
      const res=await this.$axios.get('/stu/room/menuInit')
      this.yearData=res.data.data
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
.roomBu{
  float: right;
}
</style>

