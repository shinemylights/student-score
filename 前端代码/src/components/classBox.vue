<template>
  <el-card class="couCard">
    <div class="myCouImg"
         :style="{backgroundImage: 'url(' + this.$BaseUrl+this.$CouImg+couData.bcImg + ')', backgroundSize:'100% 100%', backgroundRepeat: 'no-repeat'}">
      <el-popconfirm
        v-if="tip!==''&&role!=='admin'"
        :title=tip.title
        @confirm="iconClick(tip.type)"
      >
        <el-button slot="reference" type="text" class="myIcon">
          <svg-icon :icon-class="tip.icon"/>
        </el-button>
      </el-popconfirm>
      <span class="pc">课程码：{{couData.classKey}}</span>
    </div>
    <div @click.prevent="toDetail">
      <el-divider></el-divider>
      <p>{{couData.couName}}</p>
      <p class="p1">{{couData.classDesc}}</p>
      <p class="p1">{{couData.timing}}开课</p>
      <el-tag size="mini" class="tag" type="warning">{{couData.couState}}</el-tag>
      <div class="num">
        <el-tag size="mini">学</el-tag>
        <span>{{couData.stuSum}}人</span>
      </div>
    </div>
  </el-card>
</template>
<script>

export default {
  props:{
    couData:{
      //id
      id:'',
      //背景图片
      bcImg:'',
      //课程名称
      couName:'',
      //班级描述
      classDesc:'',
      //课堂码
      classKey:'',
      //学生人数
      stuSum:'',
      //开课时间
      timing:'',
      //课程状态
      couState:''
    },
    tip:{
      type:'',
      title:'',
      icon:''
    }  //错误提示
  },
  data(){
    return{
      role:this.$store.state.user.user.roles[0],
    }
  },
  methods:{
    /*点击图标*/
    iconClick(type){
      if(type==='add'){
        this.$emit("iconClick",this.couData.id)
      }else {
        this.$emit("exitRoom",this.couData.id)
      }
    },
    /*点击进入详情页*/
    toDetail(){
      if(this.$router.currentRoute.name!=='roomList'){
        if(this.couData.couState==='待开课'&&this.role!=='teacher'){
          this.$message.warning("课程暂未开始")
        }else {
          this.$router.push({
            path: '/classRoom/roomDetail',
            query: {
              id: this.couData.id
            }
          })
        }
      }
    }
  }

}
</script>


<style scoped>
/deep/.el-card__body{
  padding: 0;
}
/*/deep/.el-button{*/
/*  padding: 0;*/
/*}*/
</style>
<style scoped lang="scss">
.el-divider--horizontal{
  margin: 0;
}
.myCouImg{
  width: 180px;
  height: 100px;
  padding: 75px 0 5px 10px;
}
.svg-icon{
  height: 1.5em;
  width: 1.5em;
}
.couCard{
  margin-right: 20px;
  margin-bottom: 20px;
  width: 180px;
  line-height: 1.769230769230769;
  p{
    font-size: 15px;
    margin:10px 0 0 10px;
  }
  .p1{
    margin: 0 0 0 10px;
    font-size: small;
    color:#ccc
  }
  .pc{
    font-size: 13px;
    display: block;
    color: white;
  }
  .myIcon{
    padding: 0;
    float: right;
    color: white;
  }
  .tag{
    //display: inline-block;
    float: left;
    margin-left: 10px;
  }
  .num{
    //display: inline-block;
    float: right;
    margin: 0 10px 10px 0;
  }
}
</style>
