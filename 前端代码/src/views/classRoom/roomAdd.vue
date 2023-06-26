<template>
  <div>

    <el-input placeholder="请输入课程码" v-model="classKey" class="couSelect">
      <el-button slot="append" icon="el-icon-search" @click="getRoomByKey"></el-button>
    </el-input>

    <div class="couDiv">
      <!--课堂组件-->
      <class-box
        v-if="roomListPage!=null"
        v-for="item in roomListPage.records"
        :cou-data="item"
        :tip="tip"
        @iconClick="bindRoom"
      ></class-box>
    </div>

    <el-pagination
      v-if="roomListPage.records.length>1"
      class="userPag"
      @current-change="current"
      background
      layout="total,prev, pager, next"
      :current-page="roomListPage.current"
      :page-size="roomListPage.size"
      :total="roomListPage.total">
    </el-pagination>
  </div>
</template>

<script>
import ClassBox from "@/components/classBox";
export default {
  components: {ClassBox},
  created() {
    this.getRooms(1)
  },
  data(){
    return{
      tip:{
        type:'add',
        title:'确定要加入课堂吗？',
        icon:'#icon-tianjia'
      },
      roomData:null,
      roomListPage:null,
      classKey:''
    }
  },
  methods:{
    /*绑定课堂*/
    bindRoom(roomId){
      this.$axios.get('/stu/room/bindRoom?roomId='+roomId+'&type='+this.$route.query.type).then((res)=>{
        if(res.data.code===200){
          this.getRooms(1);
          this.$message.success("绑定成功");
        }
      })

    },
    /*查询全部课程*/
    async getRooms(current){
      const res = await this.$axios.get('/stu/room/getRooms?current='+current+'&id='+this.$route.query.id)
      this.roomListPage=res.data.data
    },
    /*当前页变化*/
    current(current){
      this.getRooms(current)
    },
    /*根据课堂码查询课堂*/
    async getRoomByKey(){
      const res=await this.$axios.get('/stu/room/getRoomByKey?classKey='+this.classKey+'&id='+this.$route.query.id)
      let roomData=[]
      roomData.push(res.data.data);
      this.roomListPage.records=roomData
    }
  }
}
</script>

<style scoped>
.couDiv{
  display: flex;
  flex-wrap:wrap;
  justify-content:flex-start;
}
.couSelect{
  margin-bottom: 20px;
}
.userPag{
  margin-top: 20px;
  float: right;
}
</style>

