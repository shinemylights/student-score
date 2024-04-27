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
            left: '-5%',                 // X轴偏移量
            top: '20%',                  // Y轴偏移量
            width: '100%',               // canvas宽度大小
            height: '100%',              // canvas高度大小
            sizeRange: [12, 50],         //  词典字体大小范围配置
            rotationRange: [0, 0],       // 词典字体旋转角度配置，默认不旋转
            gridSize: 25,                // 词典字体间距配置
            layoutAnimation: true,       // 为false词典过度会阻塞
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
              name: 'iPhone 13',    // 词典名称配置
              textStyle: {          // 单独配置某个词典样式
                shadowBlur: 4,
                shadowOffsetY: 14,
                color: '#BDBEFA'
              }
            },
              { value: '30', name: 'VIVO' },
              { value: '29', name: 'OPPO' },
              { value: '28', name: 'HONOR' },
              { value: '27', name: 'iPhone 12 pro max' },
              { value: '26', name: 'iPhone 12 pro max' },
              { value: '25', name: 'HUAWEI MATE 10' },
              { value: '24', name: 'ONEPLUS' },
              { value: '23', name: 'Lenova T470' },
              { value: '22', name: 'MacBook Air ' },
              { value: '21', name: 'SAMSUNG' },
              { value: '20', name: 'iPad mini' },
              { value: '16', name: 'BLACKBERRY' },
              { value: '14', name: 'OPPO' },
              { value: '13', name: 'SAMSUNG' },
              { value: '12', name: '361' },
              { value: '10', name: 'Lenova' }]
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
