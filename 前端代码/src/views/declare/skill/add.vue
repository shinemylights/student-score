<template>
  <div>
    <el-card>
      <div slot="title">
        <div class="edit-head">
          <el-button @click="close" type="text" icon="el-icon-arrow-left">返回</el-button>
          <div class="head-name">添加</div>
          <span></span>
          <el-button @click="close" type="text" icon="el-icon-close" class="window-close"></el-button>
        </div>
      </div>
      <el-form :model="form" label-width="100px" :rules="formValidate" label-position="left">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" clearable style="width:570px"></el-input>
        </el-form-item>
        <!-- 颁发单位 -->
        <el-form-item label="掌握程度">
          <el-input v-model="form.level"></el-input>
        </el-form-item>
        <!-- 颁发单位 -->
        <el-form-item label="类别">
          <el-input v-model="form.category"></el-input>
        </el-form-item>

        <!-- 描述 -->
        <el-form-item label="描述">
          <el-input type="textarea" v-model="form.description"></el-input>
        </el-form-item>

        <!-- 添加上传奖项证明文件的模块 -->
        <el-form-item label="上传证明" prop="awardProof">
          <!-- 这里应替换为您的上传处理URL -->
          <el-upload
            class="upload-demo"
            action="http://localhost:8083/practice/upEvidence"
            :headers="myHeaders"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :before-remove="beforeRemove"
            :on-success="handleAvatarSuccess"
          multiple
          :limit="3"
          :on-exceed="handleExceed"
          :file-list="fileList">
          <el-button size="small" type="primary">点击上传</el-button>
          <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-form-item>
        <el-form-item>
          <el-button @click="handleSubmit" :loading="submitLoading" type="primary">提交并保存</el-button>
          <el-button @click="handleReset">重置</el-button>
          <el-button type="dashed" @click="close">关闭</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import {
    addCompetition
} from "../practice/api.js";
export default {
    name: "add",
    components: {},
    data() {
        return {
          myHeaders: {token: sessionStorage.getItem('token')},
            submitLoading: false,
            form: {
                name: "",
                startDate: "",
                endDate: "",
                organization: "",
                description: "",
                proofImageUrl: ""
            },
            formValidate: {}
        };
    },
    methods: {
        init() {},
      // 头像上传成功后的处理函数
      handleAvatarSuccess(response, file) {
          console.log(response);
          console.log(file);
        this.form.proofImageUrl = this.form.proofImageUrl + ',' + response.result.proofImageUrl; // 假设返回的数据结构中包含url字段
        console.log(this.form.proofImageUrl);
      },
        handleReset() {
            this.$refs.form.resetFields();
        },
        handleSubmit() {
            // this.$refs.form.validate(valid => {
            //     if (valid) {
          console.log("进入了handle方法")
                    addCompetition(this.form).then(res => {

                        this.submitLoading = false;
                        if (res.data.success) {
                            this.$Message.success("操作成功");
                            this.submited();
                        }
                    });
                // }
            // });
        },
        close() {
            this.$emit("close", true);
        },
        submited() {
            this.$emit("submited", true);
        }
    },
    mounted() {
        this.init();
    }
};
</script>

<style lang="css">
.edit-head {
    display: flex;
    align-items: center;
    justify-content: space-between;
    position: relative;

    .back-title {
        color: #515a6e;
        display: flex;
        align-items: center;
    }

    .head-name {
        display: inline-block;
        height: 20px;
        line-height: 20px;
        font-size: 16px;
        color: #17233d;
        font-weight: 500;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    .window-close {
        z-index: 1;
        font-size: 12px;
        position: absolute;
        right: 0px;
        top: -5px;
        overflow: hidden;
        cursor: pointer;

        .ivu-icon-ios-close {
            color: #999;
            transition: color .2s ease;
        }
    }

    .window-close .ivu-icon-ios-close:hover {
        color: #444;
    }
}
</style>
