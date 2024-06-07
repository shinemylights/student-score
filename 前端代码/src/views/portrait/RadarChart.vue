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
        legend: {},
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
            radius: 160,
            axisName: {
              color: '#fff',
              backgroundColor: '#666',
              borderRadius: 3,
              padding: [3, 5]
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
                  }
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
                }
              }
            ]
          }
        ]


      });

      const barChart = echarts.init(this.$refs.barChart);
      barChart.setOption({
        xAxis: {
          type: 'category',
          data: ['数学', '语言文学', '专业基础', '专业选修', '工程实践', '思想政治']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: resData,
            type: 'bar'
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
  height: 500px; /* 雷达图高度 */
  /* 可以添加其他样式，如边框等 */
}
</style>
