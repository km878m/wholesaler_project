<template>
  <div>
    <div style="margin: 20px 0;">
      <el-select class="input" v-model="timeRange" placeholder="请选择" @change="load">
        <el-option
           v-for="item in options"
           :key="item.value"
           :label="item.label"
           :value="item.value">
        </el-option>
      </el-select>
    </div>
    <el-card>
      <div id="line" style="width: 100%; height: 400px;"></div>
    </el-card>
  </div>
</template>

<script>
import Cookies from 'js-cookie';
import request from "@/utils/request";
import * as echarts from 'echarts';

const option = {
  title: {
    text: '销售额和采购额统计'
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    data: ['销售额', '采购额']
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
    type: 'category',
    boundaryGap: false,
    data: []
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '销售额',
      type: 'line',
      data: []
    },
    {
      name: '采购额',
      type: 'line',
      data: []
    },
  ]
};

export default {
  name: "HomeView.vue",
  data() {
    return {
      user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
      lineBox: null,
      timeRange:'week',
      options: [
        {label:'最近一周',value:'week'},
        {label:'最近一个月',value:'month'},
        {label:'最近两个月',value:'month2'},
        {label:'最近三个月',value:'month3'},
      ]
    };
  },
  mounted() {
    this.load()
  },
  methods: {
    // 折线图加载方法
    load() {
      if(!this.lineBox) {
        this.lineBox = echarts.init(document.getElementById('line'))//初始化图形容器
      }
      //从后台获取数据
      request.get('/sell/lineCharts/' + this.timeRange).then(res => {
        option.xAxis.data = res.data.date
        option.series[0].data = res.data.price
        option.series[1].data = res.data.priceI
        this.lineBox.setOption(option) //设置容器的属性值，当你的数据发送变化的时候，一定要重新setOption
      })
    }
  }
};
</script>

<style>
.input {
  width: 300px;
}
</style>