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
      image: 'data:image/png;base64,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'

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
      var maskImage = new Image();
      maskImage.src = this.image

      myChart.setOption({
        series: [
          {
            type: 'wordCloud',
            shape: 'circle',
            keepAspect: false,
            //此处添加图片的base64格式
            maskImage: maskImage,
            left: 'center',
            top: '-15px',
            width: '98%',
            height: '100%',
            right: null,
            bottom: null,
            sizeRange: [18, 60],
            rotationRange: [-90, 90],
            rotationStep: 45,
            gridSize: 10,
            drawOutOfBound: false,
            layoutAnimation: true,
            textStyle: {
              fontFamily: 'sans-serif',
              fontWeight: 'bold',
              color: function () {
                return 'rgb(' + [
                  Math.round(Math.random() * 160),
                  Math.round(Math.random() * 160),
                  Math.round(Math.random() * 160)
                ].join(',') + ')';
              }
            },
            emphasis: {
              // focus: 'self',
              textStyle: {
                textShadowBlur: 5,
                textShadowColor: '#333'
              }
            },
            // 渲染词典数据
            data: [
              { value: '30', name: '逻辑思维' },
              { value: '29', name: '人文修养' },
              { value: '28', name: '专业能力' },
              { value: '27', name: '实践能力' },
              { value: '15', name: '创新' },
              { value: '25', name: '数学竞赛' },
              { value: '24', name: '编程竞赛' },
              { value: '6', name: '实习' },
              { value: '22', name: '动手能力' },
              { value: '21', name: '外语' },
              { value: '7', name: '口才' },
              { value: '16', name: '体育' },
              { value: '14', name: 'C++' },
              { value: '13', name: 'Java' },
              { value: '12', name: '进步' },
              { value: '10', name: '分析问题' },
              { value: '4', name: 'Spring' },
              { value: '6', name: 'python' },
              { value: '9', name: 'Excel' },
              { value: '4', name: 'PhotoShop' },
              { value: '17', name: '歌唱' },
              { value: '14', name: '演讲' },
              { value: '25', name: 'Vue' },
              { value: '11', name: '算法' }
            ]
          }
        ],
      });

      //随着屏幕大小调节图表
      window.addEventListener("resize", () => {
        myChart.resize();
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
  /*justify-content: center; !* 水平居中 *!*/
  align-items: center; /* 垂直居中 */
  height: 100vh; /* 容器高度设置为视口高度 */
}

.word-cloud {
  width: 50vw; /* 雷达图宽度设置为视口宽度的一半 */
  height: 80vh; /* 雷达图高度 */
  /* 可以添加其他样式，如边框等 */
}
</style>
