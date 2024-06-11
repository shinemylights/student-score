<template>
  <div class="container">
    <div ref="radarChart" class="radar-chart"></div>
    <div ref="barChart" class="bar-chart" style="width: 600px; height: 400px;"></div>
  </div>
<!--  <div ref="radarChart" style="width: 600px; height: 400px;"></div>-->
</template>

<script>
import * as echarts from 'echarts';

export default {
  data() {
    return {

    };
  },
  mounted() {
    this.initRadarChart();
  },
  methods: { /*获取折线图数据*/
    async getKMeansResult(id){
      const res=await this.$axios.get('/portrait/kmeans?id='+id)
      if(res.data.code===200){
        return res.data.data
      }
    },
    async initRadarChart() {
      // 基于准备好的DOM，初始化echarts实例
      const myChart = echarts.init(this.$refs.radarChart);
      const  resData =await this.getKMeansResult(122)
      // 绘制图表
      myChart.setOption({
        // ...您的雷达图配置

        color: ['#67F9D8', '#FFE434', '#56A3F1', '#FF917C'],
        // title: {
        //   text: 'Customized Radar Chart'
        // },
        legend: {
          // 调整图例项的大小
          itemHeight: 30,
          itemWidth: 30, // 调整图例的位置，这里假设是将其上移，具体数值可能需要根据实际情况调整
          top: '5%', // 或者使用像素值，例如 '100px'
          left: 'center', // 保持图例水平居中，可以根据需要调整
          // orient: 'vertical', // 设置为垂直布局，以便在右侧排列
          // 确保字体大小与其它配置保持一致
          textStyle: {
            fontSize: 28
          }
        },
        radar: [
          {
            indicator: [
              { text: '数学与自然科学', max: 10 },
              { text: '语言文学', max: 10 },
              { text: '专业基础', max: 10 },
              { text: '专业选修', max: 10 },
              { text: '工程实践', max: 10 },
              { text: '思想政治', max: 10 }
            ],
            center: ['45%', '50%'],
            radius: 200,
            axisName: {
              color: '#fff',
              backgroundColor: '#666',
              borderRadius: 3,
              padding: [3, 5],
              fontSize: 28
            },
            splitLine: {
              lineStyle: {
                width: 2 // 可选：增加分割线宽度，使雷达图更清晰
              }
            }
          }
        ],
        series: [
          // 确保 series 中的雷达图索引与您保留的雷达图配置相匹配
          {
            type: 'radar',
            radarIndex: 0, // 这里的索引从0开始，因为现在只有一个雷达图配置
            data: [
              {
                value: resData,
                name: '个人值',
                symbol: 'rect',
                symbolSize: 12,
                lineStyle: {
                  type: 'dashed'
                },
                label: {
                  show: true,
                  formatter: function (params) {
                    return params.value;
                  },
                  fontSize: 28 // 增加数据标签的字体大小
                }
              },
              {
                value: [5, 6, 7, 6.5, 7.2, 4],
                name: '平均值',
                areaStyle: {
                  color: new echarts.graphic.RadialGradient(0.1, 0.6, 1, [
                    {
                      color: 'rgba(255, 145, 124, 0.1)',
                      offset: 0
                    },
                    {
                      color: 'rgba(255, 145, 124, 0.9)',
                      offset: 1
                    }
                  ])
                },
                label: { // 为第二个系列也添加或调整字体大小
                  show: true,
                  fontSize: 28
                }
              }
            ]
          }
        ]


      });

      const barChart = echarts.init(this.$refs.barChart);
      barChart.setOption({
        grid: {
          left: '1%', // 调整左侧间距
          right: '1%', // 调整右侧间距
          bottom: '5%', // 调整底部间距，给X轴留出更多空间
          containLabel: true // 确保标签不被裁剪
        },
        xAxis: {
          type: 'category',
          data: ['数学', '语言文学', '专业基础', '专业选修', '工程实践', '思想政治'],
          axisLabel: {
            fontSize: 21, // 设置X轴标签字体大小，与雷达图中的字体大小匹配
            interval: 0 // 确保显示所有标签，根据实际情况决定是否启用
          }
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            fontSize: 23 // 设置Y轴标签字体大小，与雷达图中的字体大小匹配
          }
        },
        series: [
          {
            data: resData,
            type: 'bar',
            barWidth: '50%', // 调整柱状图的宽度，可根据需要调整
            label: {
              show: true,
              position: 'top', // 标签位置设为柱子上方
              fontSize: 23 // 设置柱状图标签字体大小，与雷达图中的字体大小匹配
            }
          }
        ]
      });
    }
  }
};
</script>


<style>
.container {
  display: flex; /* 启用Flexbox */
  justify-content: flex-start; /* 水平居左 */
  align-items: center; /* 垂直居中 */
  height: 100vh; /* 容器高度设置为视口高度 */
}

.radar-chart {
  width: 50vw; /* 雷达图宽度设置为视口宽度的一半 */
  height: 700px; /* 雷达图高度 */

  /* 可以添加其他样式，如边框等 */
}
</style>
