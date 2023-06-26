<template>
  <el-dialog title="以下为导入失败的数据" :visible.sync="dialogStu">
    <div v-show="dialogData[0]&&dialogData[0].length>0">
      <p style="color: red">以下学生不存在系统中或者未选该课程</p>
      <el-table :data="dialogData[0]">
        <el-table-column property="username" label="学号" width="150"></el-table-column>
        <el-table-column property="realName" label="姓名" width="200"></el-table-column>
        <el-table-column property="grade" label="成绩"></el-table-column>
      </el-table>
    </div>
    <div v-show="dialogData[1]&&dialogData[1].length>0">
      <p style="color: red">以下学生学号重复</p>
      <el-table :data="dialogData[1]">
        <el-table-column property="username" label="学号" width="150"></el-table-column>
        <el-table-column property="realName" label="姓名" width="200"></el-table-column>
        <el-table-column property="grade" label="成绩"></el-table-column>
      </el-table>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="close">知道了</el-button>
    </span>
  </el-dialog>

</template>
<script>
export default {
  props: {
    dialogStu: false,
    dialogData: {}
  },
  data() {
    return {}
  },
  methods: {
    close(){
      this.dialogData={}
      this.$emit('close')
    }
  }
}
</script>

<style>

</style>
