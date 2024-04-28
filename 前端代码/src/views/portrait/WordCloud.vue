<template>
  <div class="container">
    <div ref="wordCloud" class="word-cloud"></div>
  </div>
</template>


<script>
import * as echarts from 'echarts';
import 'echarts-wordcloud'

export default {
  name: 'WordCloudChart',
  data() {
    return {
      worddata: [
        // 示例数据
        { name: '词汇1', value: 15000 },
        { name: '词汇2', value: 10081 },
        // ...
      ],
    };
  },
  mounted() {
    setTimeout(() => {
      this.initChart(); // 初始化词云图
    }, 0);
  },
  methods: {
    initChart() {
      const chartContainer = this.$refs.wordCloud;
      const myChart = echarts.init(chartContainer);

      myChart.setOption({
        series: [
          {
            type: 'wordCloud',
            left: '20%',                 // X轴偏移量
            top: '10%',                  // Y轴偏移量
            width: '100%',               // canvas宽度大小
            height: '100%',              // canvas高度大小
            sizeRange: [20, 70],         //  词典字体大小范围配置
            rotationRange: [0, 0],       // 词典字体旋转角度配置，默认不旋转
            gridSize: 25,                // 词典字体间距配置
            layoutAnimation: true,       // 为false词典过度会阻塞
            // maskImage: 'E:\\img.png',
            textStyle: {                 // 词典样式配置
              normal: {
                color() {
                  // 颜色随机渐变
                  let colors = ['#fe9a8bb3', '#fe9a8bb3', '#fe9a8b03', '#9E87FFb3', '#9E87FFb3', '#9E87FFb3', '#fe9a8bb3', '#fe9a8bb3', '#fe9a8bb3', '#73DDFF', '#58D5FF']
                  return colors[parseInt(Math.random() * 10)]
                }
              }
            },
            // 渲染词典数据
            data: [{
              value: '50',          // 词典大小配置
              name: '综合素养',    // 词典名称配置
              textStyle: {          // 单独配置某个词典样式
                shadowBlur: 4,
                shadowOffsetY: 14,
                color: '#BDBEFA'
              }
            },
              { value: '30', name: '逻辑思维' },
              { value: '29', name: '人文修养' },
              { value: '28', name: '专业能力' },
              { value: '27', name: '实践能力' },
              { value: '26', name: '创新' },
              { value: '25', name: '数学竞赛' },
              { value: '24', name: '编程竞赛' },
              { value: '23', name: '实习' },
              { value: '22', name: '动手能力' },
              { value: '21', name: '外语' },
              { value: '20', name: '口才' },
              { value: '16', name: '体育' },
              { value: '14', name: 'C++' },
              { value: '13', name: 'Java' },
              { value: '12', name: '进步' },
              { value: '10', name: '分析问题' }]
          }
        ],
      });

      // 点击事件
      myChart.on('click', (params) => {
        alert(params.name);
      });
    },
  },
};
</script>



<style>
.container {
  display: flex; /* 启用Flexbox */
  justify-content: flex-start; /* 水平居左 */
  align-items: center; /* 垂直居中 */
  height: 100vh; /* 容器高度设置为视口高度 */
}

.word-cloud {
  width: 50vw; /* 雷达图宽度设置为视口宽度的一半 */
  height: 400px; /* 雷达图高度 */
  /* 可以添加其他样式，如边框等 */
}
</style>
