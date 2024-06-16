<template>
  <div id="download-element" class="pdfItem">
    <el-card style="text-align: center;margin-bottom: 10px">
      <span>{{userInfo.realName}}的可视化成绩分析报告 <el-link :underline="false" @click="downloadPDF">下载为pdf</el-link></span>
    </el-card>
    <el-empty v-if="gapAndRank.gpa==='暂无数据'">暂无数据</el-empty>
    <div v-else>
      <el-row :gutter="20">
        <el-col :span="10">
          <el-card>
            <el-row :gutter="10">
              <el-col :span="11">
                <div>
                  <img-up-file></img-up-file>
                  <p>姓名：{{userInfo.realName}}</p>
                  <p>学号：{{userInfo.username}}</p>
                </div>
              </el-col>
              <el-col :span="2">
                <el-divider direction="vertical" style="width: 30px"></el-divider>
              </el-col>
              <el-col :span="11">
                <div>
                  <p>总学分：{{gapAndRank.sumGrade}}</p>
                  <p>平均绩点：{{gapAndRank.gpa}} / {{gapAndRank.sumGpa}}</p>
                  <p>专业排名：{{gapAndRank.rank}} / {{gapAndRank.rankSum}}</p>
                </div>
              </el-col>
            </el-row>
          </el-card>
          <el-card style="margin-top: 10px;height: 320px" >
            <!--成绩区间饼状图-->
            <div class="gradeCard" id="gradeCard"></div>
          </el-card>
        </el-col>
        <el-col :span="14">
          <el-card>
            <el-table
              border
              :data="gpaYearList"
              style="width: 100%">
              <el-table-column
                prop="stuYear"
                label="学年"
                width="250">
              </el-table-column>
              <el-table-column
                prop="avgGpa"
                label="平均绩点"
                width="180">
              </el-table-column>
              <el-table-column
                prop="rank"
                label="排名"
                width="180">
              </el-table-column>
              <el-table-column
                prop="sumGrade"
                label="总学分">
              </el-table-column>
            </el-table>
          </el-card>
          <el-card style="height: 115px;margin-top: 10px">
          <span class="text">
            <span>{{userInfo.realName}}同学在校期间平均绩点为{{gapAndRank.gpa}},在该专业{{gapAndRank.rankSum}}人中排名第{{gapAndRank.rank}}。</span>
            <span v-if="scoreAnalysis.couData!=null&&scoreAnalysis.couData.length>0">
              他的挂科数目为{{scoreAnalysis.couData.length}},分别是
              <span v-for="item in scoreAnalysis.couData">
              {{item.couName}},</span>
            </span>
            <span>他的平均成绩为{{scoreAnalysis.avgGrade}},最高分为<span v-for="item in scoreAnalysis.maxCourse">{{item}}</span>
              课程的{{scoreAnalysis.maxGrade}}，最低分为<span v-for="item in scoreAnalysis.minCourse">{{item}} </span>的{{scoreAnalysis.minGrade}}</span>
          </span>
          </el-card>
        </el-col>
      </el-row>
      <!--绩点折线图-->
      <el-card class="gpaCard" id="gpaCard" style="margin-right: 2%"></el-card>
      <!--绩点排名折线图-->
      <el-card class="gpaCard" id="gpaRankCard"></el-card>
      <el-row :gutter="20" style="margin-top: 10px">
        <el-col :span="7">
          <el-card>
            <el-table
              border
              :data="basicsCourse.couData"
              style="width: 100%">
              <el-table-column
                prop="couName"
                label="课程名"
                width="155">
              </el-table-column>
              <el-table-column
                prop="grade"
                label="成绩"
                width="60">
              </el-table-column>
              <el-table-column
                prop="ranking"
                label="排名"
                width="60">
              </el-table-column>
            </el-table>
          </el-card>
        </el-col>
        <el-col :span="5">
          <el-card>
          <span>
            {{userInfo.realName}}同学的专业基础课成绩如左表所示。在这些成绩中，他的最高分为{{basicsCourse.maxCourse.couName}}，得分为{{basicsCourse.maxCourse.grade}}分，而该课程在同一学期的平均分为{{basicsCourse.maxAvg.toFixed(1)}}分。
            同时，他在{{basicsCourse.minCourse.couName}}课程中获得了最低分，得分为{{basicsCourse.minCourse.grade}}分，而该课程在同一学期的平均分为{{basicsCourse.minAvg.toFixed(1)}}分。
          </span>
          </el-card>
        </el-col>
        <el-col :span="7">
          <el-card>
            <el-table
              border
              :data="coreCourse.couData"
              style="width: 100%">
              <el-table-column
                prop="couName"
                label="课程名"
                width="155">
              </el-table-column>
              <el-table-column
                prop="grade"
                label="成绩"
                width="60">
              </el-table-column>
              <el-table-column
                prop="ranking"
                label="排名"
                width="60">
              </el-table-column>
            </el-table>
          </el-card>
        </el-col>
        <el-col :span="5">
          <el-card>
          <span>
            {{userInfo.realName}}同学的专业核心课成绩如右表所示。在这些成绩中，他的最高分为{{coreCourse.maxCourse.couName}}，得分为{{coreCourse.maxCourse.grade}}分，而该课程在同一学期的平均分为{{coreCourse.maxAvg.toFixed(1)}}分。
            同时，他在{{coreCourse.minCourse.couName}}课程中获得了最低分，得分为{{coreCourse.minCourse.grade}}分，而该课程在同一学期的平均分为{{coreCourse.minAvg.toFixed(1)}}分。
          </span>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
import html2canvas from 'html2canvas'
import * as echarts from 'echarts';
import jsPDF from 'jspdf';
import imgUpFile from "@/components/imgUpFile";
export default {
  components:{imgUpFile},
  created() {
    this.getRankAndGpaByYear()
    this.getRankAndGpa()
    this.getScoreAnalysis()
    this.getCourseByType(1)
    this.getCourseByType(2)
  },
  mounted() {
    this.gpaInit()
    this.gpaRankInit()
    this.gradeCardInit()
    this.initLineChartPoint(0)
    this.initLineChartPoint(1)
  },
  data(){
    return{
      gapAndRank:{},
      gpaYearList:[],
      scoreAnalysis:{},
      basicsCourse:{
        couData:[],
        maxCourse:{},
        minCourse:{},
        maxAvg:0,
        minAvg:0
      },
      coreCourse:{
        couData:[],
        maxCourse:{},
        minCourse:{},
        maxAvg:0,
        minAvg:0
      },
      userInfo:this.$store.state.user.user,
    }
  },
  methods:{
    /*获取折线图数据*/
    async initLineChartPoint(type){
      const res=await this.$axios.get('/grade/initLineChartPoint?type='+type)
      if(res.data.code===200){
        return res.data.data
      }
    },
    async getRankAndGpa(){
      const res=await this.$axios.get('/scoreAnalysis/getRankAndGpa')
      if(res.data.code===200){
        this.gapAndRank=res.data.data
      }
    },
    async getCourseByType(type){
      const res=await this.$axios.get('/scoreAnalysis/getCourseByType?type='+type)
      if(res.data.code===200){
        if(type===1){
          this.basicsCourse=res.data.data
        }else this.coreCourse=res.data.data
      }
    },
    async getGradeInterval(){
      const res=await this.$axios.get('/scoreAnalysis/getGradeInterval')
      if(res.data.code===200){
        return res.data.data
      }
    },
    async getScoreAnalysis(){
      const res=await this.$axios.get('/scoreAnalysis/getScoreAnalysis')
      if(res.data.code===200){
        this.scoreAnalysis=res.data.data
      }
    },
    async getRankAndGpaByYear(){
      const res=await this.$axios.get('/scoreAnalysis/getRankAndGpaByYear')
      if(res.data.code===200){
        this.gpaYearList=res.data.data
      }
    },
    /*构造绩点折线图*/
    async gpaInit() {
      let myChart = echarts.init(document.getElementById('gpaCard'));
      const  resData=await this.initLineChartPoint(0)
      myChart.setOption({
        title: {
          text: '绩点',
          textStyle: {
            fontSize: 27 // 调整标题文字大小
          }
        },
        legend: {
          data: ['绩点'],
          textStyle: {
            fontSize: 24 // 调整图例文字大小
          }
        },
        tooltip: {
          trigger: 'axis',
          textStyle: {
            fontSize: 22 // 调整提示框文字大小
          }
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: resData.xData,
          axisLabel: {
            fontSize: 22 // 调整x轴标签文字大小
          }
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            fontSize: 22 // 调整y轴标签文字大小
          }
        },
        series: [
          {
            name: '绩点',
            type: 'line',
            stack: 'Total',
            data: resData.yData,
            label: {
              normal: {
                show: true,
                position: 'top',
                textStyle: {
                  fontSize: 22 // 调整系列标签文字大小
                }
              }
            }
          },
        ]
      })
    },
    /*构造绩点排名折线图*/
    async gpaRankInit() {
      let myChart = echarts.init(document.getElementById('gpaRankCard'));
      const  resData=await this.initLineChartPoint(1)
      myChart.setOption({
        title: {
          text: '绩点排名',
          textStyle: {
            fontSize: 27 // 调整标题文字大小
          }
        },
        tooltip: {
          trigger: 'axis',
          textStyle: {
            fontSize: 22 // 调整提示框文字大小
          }
        },
        legend: {
          data: ['绩点排名'],
          textStyle: {
            fontSize: 24 // 调整图例文字大小
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          axisLine: {
            onZero: false
          },
          type: 'category',
          boundaryGap: false,
          data: resData.xData,
          axisLabel: {
            fontSize: 22 // 调整x轴标签文字大小
          }
        },
        yAxis: {
          type: 'value',
          inverse: true,
          axisLabel: {
            fontSize: 22 // 调整y轴标签文字大小
          }
        },
        series: [
          {
            name: '绩点排名',
            type: 'line',
            stack: 'Total',
            data: resData.yData,
            label: {
              normal: {
                show: true,
                position: 'top',
                textStyle: {
                  fontSize: 22 // 调整系列标签文字大小
                }
              }
            }
          },
        ]
      })
    },
    /*构造饼状图数据*/
    async gradeCardInit(){
      let myChart = echarts.init(document.getElementById('gradeCard'));
      const  resData=await this.getGradeInterval()
      myChart.setOption({
          title: {
            text: '在校成绩区间图',
            textStyle: {
              fontSize: 28 // 调整标题文字大小
            }
          },
          tooltip: {
            trigger: 'item'
          },
          legend: {
            orient: 'vertical',
            left: "left",
            top: 25,
            textStyle: {
              fontSize: 23 // 调整图例文字大小
            }
          },
          toolbox: {
            show: true,
            feature: {
              mark: { show: true },
              dataView: { show: true, readOnly: false },
              restore: { show: true },
              saveAsImage: { show: true }
            },
            iconStyle: {
              fontSize: 24 // 调整工具箱图标旁边文字的大小
            }
          },
          series: [
            {
              name: '门数',
              type: 'pie',
              radius: [20, 100],
              center: ['50%', '50%'],
              roseType: 'area',
              itemStyle: {
                borderRadius: 8
              },
              label: {
                normal: {
                  textStyle: {
                    fontSize: 24 // 调整饼图扇区中的文字大小
                  }
                }
              },
              data: [
                { value: resData[0], name: '不及格(重修后仍不及格)' },
                { value: resData[1], name: '60-70' },
                { value: resData[2], name: '70-80' },
                { value: resData[3], name: '80-90' },
                { value: resData[4], name: '90-100' },
              ]
            }
          ]
        })
      },
    async downloadPDF() {
      const fileName=this.userInfo.username+this.userInfo.realName+'的成绩分析报告'
      const element = document.getElementById("download-element");
      html2canvas(element, { useCORS: true }).then(function (canvas) {
        const imgData = canvas.toDataURL("image/jpeg");
        const pdf = new jsPDF();
        const imgProps = pdf.getImageProperties(imgData);
        const pdfWidth = pdf.internal.pageSize.getWidth();
        const pdfHeight = (imgProps.height * pdfWidth) / imgProps.width;
        pdf.addImage(imgData, "JPEG", 0, 0, pdfWidth, pdfHeight);
        pdf.save(fileName+'.pdf');
      });
    }
  }
}
</script>
<style scoped lang="scss">
html {
  font-size: 16px !important; /* 这将设置基础字体大小为16px */
}
.gpaCard {
  display: inline-block;
  padding: 10px;
  margin-top: 20px;
  width: 49%;
  height: 300px;
}
.gradeCard{
  height: 320px;
}
.text {
    span{
      margin-bottom: 10px;
    }
}

.el-card {
  font-size: 24px !important; /* 使用 !important 来确保样式被应用 */
}

.el-table {
  font-size: 24px !important; /* 使用 !important 来确保样式被应用 */
}

.custom-table .el-table__body {
  font-size: 24px !important; /* 调整为你希望的字体大小 */
}

</style>

