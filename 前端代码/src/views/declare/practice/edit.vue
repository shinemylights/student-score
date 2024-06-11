<template>
  <div>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <span class="edit-head">
        <el-button type="text" @click="close">
          <i class="el-icon-arrow-left"></i> 返回
        </el-button>
        <div class="head-name">编辑</div>
        <el-button type="text" class="window-close" @click="close">
          <i class="el-icon-close" style="font-size: 31px;"></i>
        </el-button>
      </span>
    </div>
    <el-form ref="form" :model="form" :rules="formValidate" label-width="100px" label-position="left">
      <el-form-item label="名称" prop="level">
        <el-input v-model="form.name" clearable style="width: 570px;"></el-input>
      </el-form-item>
      <el-form-item label="实践单位" prop="title">
        <el-input v-model="form.organization" clearable style="width: 570px;"></el-input>
      </el-form-item>
      <el-form-item label="起始时间" prop="value">
        <el-input v-model="form.startDate" clearable style="width: 570px;"></el-input>
      </el-form-item>
      <el-form-item label="结束时间" prop="value">
        <el-input v-model="form.endDate" clearable style="width: 570px;"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSubmit" :loading="submitLoading">提交并保存</el-button>
        <el-button @click="handleReset">重置</el-button>
        <el-button type="dashed" @click="close">关闭</el-button>
      </el-form-item>
    </el-form>
  </el-card>
  </div>
</template>

<script>
import {
    editCompetition
} from "./api.js";
export default {
    name: "edit",
    components: {},
    props: {
        data: Object
    },
    data() {
        return {
            submitLoading: false,
            form: {
                level: "",
                title: "",
                value: "",
            },
            formValidate: {}
        };
    },
    methods: {
        init() {
            this.handleReset();
            this.form = this.data;
        },
        handleReset() {
            this.$refs.form.resetFields();
        },
        handleSubmit() {
            this.$refs.form.validate(valid => {
                if (valid) {
                    editCompetition(this.form).then(res => {
                        this.submitLoading = false;
                        if (res.success) {
                            this.$Message.success("操作成功");
                            this.submited();
                        }
                    });
                }
            });
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
