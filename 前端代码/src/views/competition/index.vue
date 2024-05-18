<template>
  <div class="search">
<!--    <el-dialog v-if="currView=='add'" :visible.sync="isAddDialogVisible" @close="currView='index'">-->
<!--      &lt;!&ndash; Add your 'add' component content here &ndash;&gt;-->
<!--    </el-dialog>-->
<!--    <el-dialog v-if="currView=='edit'" :visible.sync="isAddDialogVisible" @close="currView='index'">-->
<!--      &lt;!&ndash; Add your 'edit' component content here &ndash;&gt;-->
<!--    </el-dialog>-->
    <add v-if="currView=='add'" @close="currView='index'" @submited="submited" />
    <edit v-if="currView=='edit'" @close="currView='index'" @submited="submited" :data="formData" />
    <el-card v-show="currView=='index'">
      <el-row v-show="openSearch" @keydown.enter.native="handleSearch">
        <el-form :model="searchForm" inline class="demo-form-inline">
          <el-form-item label="" prop="level">
            <el-select v-model="searchForm.level" placeholder="请选择">
              <el-option label="国家级" value="国家级"></el-option>
              <el-option label="省级" value="省级"></el-option>
              <el-option label="市级" value="市级"></el-option>
              <el-option label="校级" value="校级"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="" prop="title">
            <el-input v-model="searchForm.title" placeholder="请输入竞赛名称" clearable></el-input>
          </el-form-item>
          <el-form-item label="" prop="value">
            <el-input v-model="searchForm.value" placeholder="请输入竞赛奖项" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click="handleSearch" type="primary" icon="el-icon-search">搜索</el-button>
            <el-button @click="handleReset" type="warning" icon="el-icon-refresh">重置</el-button>
            <el-button @click="add" type="info" icon="el-icon-plus">添加</el-button>
            <el-button @click="delAll" type="danger" icon="el-icon-delete">删除</el-button>
            <el-button @click="excelData" type="success" icon="el-icon-download">导出</el-button>
          </el-form-item>
        </el-form>
      </el-row>
      <!-- ... 其他代码 ... -->
      <div class="operation" style="position:relative;">
        <transition>
          <div v-show="showFilterPanelFlag" class="filter-panel">
            <el-checkbox-group v-model="selected">
              <div v-for="item in mycolumns" :key="item.key">
                <el-checkbox :label="item.title" style="margin: 2px 5px"></el-checkbox>
              </div>
            </el-checkbox-group>
          </div>
        </transition>
      </div>

      <!-- Conditional Row -->
      <div v-if="openTip"></div>

      <!-- Table Row -->
      <el-table
        :data="data"
        style="width: 100%"
        border
        stripe
        size="small"
        @sort-change="changeSort"
        @selection-change="changeSelect"
        @row-click="rowClick"
        :row-class-name="rowClassName"
      >
        <el-table-column
          v-for="(column, index) in columns"
          :key="index"
          :prop="column.key"
          :label="column.title"
          :sortable="column.sortable || 'custom'"
        ></el-table-column>
      </el-table>
<!--      <el-row>-->
<!--        <el-table-->
<!--          :columns="columns"-->
<!--          :data="data"-->
<!--          style="width: 100%"-->
<!--          @selection-change="handleSelectionChange">-->
<!--          &lt;!&ndash; Define your table columns here &ndash;&gt;-->
<!--        </el-table>-->
<!--      </el-row>-->
      <el-row type="flex" justify="end" class="page">
        <el-pagination
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[15, 20, 50]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </el-row>
    </el-card>
  </div>
</template>

<script>
import {
  getCompetitionList,
  deleteCompetition
} from './api.js'
import add from "./add.vue";
import edit from "./edit.vue";
export default {
  name: "single-window",
  components: {
    add,
    edit
  },
  data() {
    return {
      tableHeight: 0,
            selected: [
                "选择",
                "序号",
                "竞赛级别",
                "竞赛名称",
                "竞赛奖项",
                "创建时间",
                "创建者",
                "修改时间",
                "修改者",
                "操作",
            ],
            modal1: false,
            openSearch: true,
            openTip: true,
            formData: {},
            currView: "index",
            loading: true,
            searchForm: {
                pageNumber: 1,
                pageSize: 15,
                sort: "createTime",
                order: "desc",
            },
            selectList: [],
            selectCount: 0,
            selectRow: 0,
            columns: [
                {
                    type: "selection",
                    width: 60,
                    title: "选择",
                    align: "center",
                    fixed: "left",
                },
                {
                    title: "序号",
                    width: 85,
                    align: "center",
                    fixed: "left",
                    sortType: true,
                    render: (h, params) => {
                        return h(
                            "span",
                            params.index +
                            (this.searchForm.pageNumber - 1) * this.searchForm.pageSize +
                            1
                        );
                    },
                },
                {
                    title: "竞赛级别",
                    key: "level",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "竞赛名称",
                    key: "title",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "竞赛奖项",
                    key: "value",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "创建时间",
                    key: "createTime",
                    sortable: true,
                    sortType: "desc",
                    minWidth: 180,
                    align: "center",
                    tooltip: true,
                },
                {
                    title: "创建者",
                    key: "createBy",
                    sortable: true,
                    sortType: "desc",
                    minWidth: 100,
                    align: "center",
                    tooltip: true,
                },
                {
                    title: "修改时间",
                    key: "updateTime",
                    minWidth: 180,
                    align: "center",
                    tooltip: true,
                },
                {
                    title: "修改者",
                    key: "updateBy",
                    minWidth: 100,
                    align: "center",
                    tooltip: true,
                },
                {
                    title: "操作",
                    key: "action",
                    align: "center",
                    width: 200,
                    render: (h, params) => {
                        return h("div", [
                            h(
                                "Button", {
                                    props: {
                                        type: "primary",
                                        size: "small",
                                        icon: "ios-create-outline",
                                        ghost: true
                                    },
                                    style: {
                                        marginRight: "5px"
                                    },
                                    on: {
                                        click: () => {
                                            this.edit(params.row);
                                        }
                                    }
                                },
                                "编辑"
                            ),
                            h(
                                "Button", {
                                    props: {
                                        type: "error",
                                        size: "small",
                                        icon: "md-trash",
                                        ghost: true
                                    },
                                    on: {
                                        click: () => {
                                            this.remove(params.row);
                                        }
                                    }
                                },
                                "删除"
                            )
                        ]);
                    }
                }
            ],
            data: [],
            pageNumber: 1,
            pageSize: 10,
            total: 0,
            showFilterPanelFlag: false,
        };
    },
    methods: {
        init() {
            this.getDataList();
        },
        submited() {
            this.currView = "index";
            this.getDataList();
        },
        changePage(v) {
            this.searchForm.pageNumber = v;
            this.getDataList();
            this.clearSelectAll();
        },
        changePageSize(v) {
            this.searchForm.pageSize = v;
            this.getDataList();
        },
        rowClick(row, index) {
            this.selectRow = row;
        },
        rowClassNmae(row, index) {
            if (row.id == this.selectRow.id) {
                return "rowClassNmaeColor";
            }
            return "";
        },
        excelData() {
            this.$refs.table.exportCsv({
                filename: "导出结果",
            });
        },
        handleSearch() {
            this.searchForm.pageNumber = 1;
            this.searchForm.pageSize = 15;
            this.getDataList();
        },
        handleReset() {
            this.$refs.searchForm.resetFields();
            this.searchForm.pageNumber = 1;
            this.searchForm.pageSize = 15;
            this.getDataList();
        },
        changeSort(e) {
            this.searchForm.sort = e.key;
            this.searchForm.order = e.order;
            if (e.order === "normal") {
                this.searchForm.order = "";
            }
            this.getDataList();
        },
        clearSelectAll() {
            this.$refs.table.selectAll(false);
        },
        changeSelect(e) {
            this.selectList = e;
            this.selectCount = e.length;
        },
        getDataList() {
            this.loading = true;
            getCompetitionList(this.searchForm).then(res => {
              console.info(res);
                this.loading = false;
                if (res.data.success) {
                    this.data = res.data.result.records;
                    this.total = res.data.result.total;
                    console.info(this.data);
                    console.info(this.total);
                }
            });
        },
        add() {
            this.currView = "add";
        },
        edit(v) {
            for (let attr in v) {
                if (v[attr] == null) {
                    v[attr] = "";
                }
            }
            let str = JSON.stringify(v);
            let data = JSON.parse(str);
            this.formData = data;
            this.currView = "edit";
        },
        remove(v) {
            this.$Modal.confirm({
                title: "确认删除",
                content: "您确认要删除 ?",
                loading: true,
                onOk: () => {
                    deleteCompetition({
                        ids: v.id
                    }).then(res => {
                        this.$Modal.remove();
                        if (res.success) {
                            this.$Message.success("操作成功");
                            this.getDataList();
                        }
                    });
                }
            });
        },
        delAll() {
            if (this.selectCount <= 0) {
                this.$Message.warning("您还未选择要删除的数据");
                return;
            }
            this.$Modal.confirm({
                title: "确认删除",
                content: "您确认要删除所选的 " + this.selectCount + " 条数据?",
                loading: true,
                onOk: () => {
                    let ids = "";
                    this.selectList.forEach(function (e) {
                        ids += e.id + ",";
                    });
                    ids = ids.substring(0, ids.length - 1);
                    deleteCompetition({
                        ids: ids
                    }).then(res => {
                        this.$Modal.remove();
                        if (res.success) {
                            this.$Message.success("操作成功");
                            this.clearSelectAll();
                            this.getDataList();
                        }
                    });
                }
            });
        }
    },
    mounted() {
        this.init();
        this.tableHeight = Number(window.innerHeight - 273);
        this.mycolumns = this.columns;
        let showcolumns = [];
        for (var i = 0; i < this.selected.length; i++) {
            var item = this.selected[i];
            for (var j = 0; j < this.columns.length; j++) {
                if (this.columns[j].title == item) {
                    showcolumns.push(this.columns[j]);
                }
            }
        }
        this.columns = showcolumns;
    },
    watch: {
        selected: function (newcolumns) {
            let showcolumns = [];
            for (var i = 0; i < this.mycolumns.length; i++) {
                var item = this.mycolumns[i];
                if (item.title == undefined) showcolumns.push(item);
                else if (newcolumns.contains(item.title)) showcolumns.push(item);
            }
            this.columns = showcolumns;
        },
    },
};
</script>

<style lang="css">
.search {
    .operation {
        margin-bottom: 2vh;
    }

    .select-count {
        font-weight: 600;
        color: #40a9ff;
    }

    .select-clear {
        margin-left: 10px;
    }

    .page {
        margin-top: 2vh;
    }

    .drop-down {
        margin-left: 5px;
    }
}

.filter-panel {
    width: 166px;
    min-height: 120px;
    height: 200px;
    position: absolute;
    background-color: white;
    z-index: 9999;
    margin-left: 1px;
    overflow-y: scroll;
    border: 1px solid blue;
    top: 35px;
    right: 10px;
}

.openSearch {
    position: absolute;
    right: 240px;
}

.openTip {
    position: absolute;
    right: 130px;
}

.showFilterPanelFlag {
    position: static !important;
    right: 10px;
    margin-right: 10px;
}

.ivu-table td {
    height: 38px !important;
}

.ivu-table-cell-with-expand {
    height: 38px !important;
    line-height: 38px !important;
}

.ivu-table .rowClassNmaeColor td {
    background-color: #b0b3b6 !important;
    color: #ffffff !important;
    font-size: 12px;
}
</style>
