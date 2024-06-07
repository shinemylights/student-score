<template>
  <div class="library-stats">
    <el-row :gutter="20">
      <el-col :span="12">
        <h2>图书馆访问次数排名</h2>
        <el-table
          :data="studentsData.slice(0, 10)"
          style="width: 100%"
          stripe
          border
          :row-class-name="highlightCurrentStudent"
        >
          <el-table-column
            prop="rank"
            label="排名"
            width="50"
          />
          <el-table-column
            prop="name"
            label="姓名"
          />
          <el-table-column
            prop="visits"
            label="访问次数"
          />
        </el-table>
        <p v-if="currentStudent">
          当前学生（{{ currentStudent.name }}）排名：{{ currentStudent.rank }}
        </p>
      </el-col>
      <el-col :span="12">
        <h2>每月去图书馆的频次</h2>
        <el-card shadow="always">
          <div ref="chartMonthlyVisits" style="width: 100%; height: 400px;"></div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="12">
        <h2>每月在图书馆的平均时间</h2>
        <el-card shadow="always">
          <div ref="chartAvgTimePerMonth" style="width: 100%; height: 400px;"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <h2>各类书籍借阅统计</h2>
        <el-card shadow="always">
          <div ref="chartBookCategories" style="width: 100%; height: 400px;"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import { ElMessage } from 'element-ui';

const monthlyVisits = [
  { month: 'Jan', visits: Math.floor(Math.random() * 100) + 50 },
  { month: 'Feb', visits: Math.floor(Math.random() * 100) + 50 },
  { month: 'Mar', visits: Math.floor(Math.random() * 100) + 50 },
  { month: 'Apr', visits: Math.floor(Math.random() * 100) + 50 },
  { month: 'May', visits: Math.floor(Math.random() * 100) + 50 },
  { month: 'Jun', visits: Math.floor(Math.random() * 100) + 50 },
  { month: 'Jul', visits: Math.floor(Math.random() * 100) + 50 },
  { month: 'Aug', visits: Math.floor(Math.random() * 100) + 50 },
  { month: 'Sep', visits: Math.floor(Math.random() * 100) + 50 },
  { month: 'Oct', visits: Math.floor(Math.random() * 100) + 50 },
  { month: 'Nov', visits: Math.floor(Math.random() * 100) + 50 },
  { month: 'Dec', visits: Math.floor(Math.random() * 100) + 50 },
];

// 使用时，仅需每月的访问次数，因此提取'visits'字段
const monthlyVisitsData = monthlyVisits.map(item => item.visits);

const avgTimePerMonth = [
  { month: 'Jan', avgTime: (Math.random() * 5) + 2 }, // 假设范围在2到7小时之间
  { month: 'Feb', avgTime: (Math.random() * 5) + 2 },
  { month: 'Mar', avgTime: (Math.random() * 5) + 2 },
  { month: 'Apr', avgTime: (Math.random() * 5) + 2 },
  { month: 'May', avgTime: (Math.random() * 5) + 2 },
  { month: 'Jun', avgTime: (Math.random() * 5) + 2 },
  { month: 'Jul', avgTime: (Math.random() * 5) + 2 },
  { month: 'Aug', avgTime: (Math.random() * 5) + 2 },
  { month: 'Sep', avgTime: (Math.random() * 5) + 2 },
  { month: 'Oct', avgTime: (Math.random() * 5) + 2 },
  { month: 'Nov', avgTime: (Math.random() * 5) + 2 },
  { month: 'Dec', avgTime: (Math.random() * 5) + 2 },
];

// 提取'avgTime'字段
const avgTimePerMonthData = avgTimePerMonth.map(item => item.avgTime);

const categories = ['科幻', '现实', '科学', '历史', '童话', '神秘', '奇幻'];
const bookCategories = categories.map((category, index) => ({
  category,
  count: Math.floor(Math.random() * 100) + 10, // 假设每种类别至少有10本被借阅
}));

// 假设这是从后端获取的数据
const mockData = {
  activityDays: {}, // 假设这是一个对象，键为日期，值为当天的活动度
  longestStay: 8, // 最长在馆时间，单位小时
  monthlyVisits: monthlyVisitsData, // 每月访问次数数组
  avgTimePerMonth: avgTimePerMonthData, // 每月平均在馆时间数组
  bookCategories: bookCategories, // 图书分类借阅统计
};


export default {
  name: 'LibraryStats',
  data() {
    return {
      studentsData: [
        { id: 1, name: '张三', visits: 85, rank: 1 },
        { id: 2, name: '李四', visits: 76, rank: 2 },
        { id: 3, name: '王五', visits: 92, rank: 3 },
        { id: 4, name: '赵六', visits: 60, rank: 4 },
        { id: 5, name: '陈七', visits: 88, rank: 5 },
        { id: 6, name: '周八', visits: 70, rank: 6 },
        { id: 7, name: '吴九', visits: 90, rank: 7 },
        { id: 8, name: '郑十', visits: 65, rank: 8 },
        { id: 9, name: '孙十一', visits: 80, rank: 9 },
        { id: 10, name: '朱十二', visits: 72, rank: 10 }
      ],
      currentStudent: { id: 5, name: '陈七', visits: 88, rank: 5 },
      longestStay: mockData.longestStay,
    };
  },
  mounted() {
    this.initCharts();
  },
  methods: {
    initCharts() {
      // 活跃天数（使用heatmap近似表示）
      this.initActivityDaysChart(mockData.activityDays);

      // 每月去图书馆的频次图表
      this.initMonthlyVisitsChart(mockData.monthlyVisits);

      // 每月在图书馆的平均时间图表
      this.initAvgTimePerMonthChart(mockData.avgTimePerMonth);

      // 各类书籍借阅统计图表
      this.initBookCategoriesChart(mockData.bookCategories);
    },
    // 初始化活跃天数图表的逻辑
    initActivityDaysChart(data) {
      // 此处省略具体实现，因为heatmap直接展示活跃度较为复杂，需要根据实际数据格式调整
    },
    // 其他图表初始化逻辑类似上面的initTimeFrequencyChart，根据数据类型和需求调整
    // 每月去图书馆的频次图表
    initMonthlyVisitsChart(monthlyVisits) {
      const chart = echarts.init(this.$refs.chartMonthlyVisits);
      const option = {
        xAxis: {
          type: 'category',
          data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
        },
        yAxis: {
          type: 'value',
        },
        series: [{
          data: monthlyVisits,
          type: 'bar',
          showBackground: true,
        }],
      };
      chart.setOption(option);
    },

    // 每月在图书馆的平均时间图表
    initAvgTimePerMonthChart(avgTimePerMonth) {
      const chart = echarts.init(this.$refs.chartAvgTimePerMonth);
      const option = {
        xAxis: {
          type: 'category',
          data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
        },
        yAxis: {
          type: 'value',
          name: 'Average Time (hours)',
        },
        series: [{
          data: avgTimePerMonth,
          type: 'bar',
        }],
      };
      chart.setOption(option);
    },

    // 各类书籍借阅统计图表
    initBookCategoriesChart(bookCategories) {
      const chart = echarts.init(this.$refs.chartBookCategories);
      const option = {
        tooltip: {
          trigger: 'item',
        },
        legend: {
          orient: 'vertical',
          left: 'left',
        },
        series: [
          {
            name: 'Book Categories',
            type: 'pie',
            radius: '75%',
            data: bookCategories.map(item => ({ value: item.count, name: item.category })),
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)',
              },
            },
          },
        ],
      };
      chart.setOption(option);
    },
    highlightCurrentStudent({ row, rowIndex }) {
      return row.id === this.currentStudent.id ? 'current-student-row' : '';
    },
  },
};
</script>

<style scoped>
.library-stats {
  padding: 20px;
}

.current-student-row {
  background-color: #f30a3c;
}
</style>
