<template>
  <div>
    <div class="salon-chart-style" :id="idValue" />
  </div>
</template>

<script setup>
import { defineProps, onMounted, watch } from 'vue'
import * as echarts from 'echarts'

const props = defineProps(['idValue','titleValue','countData','seriesValue','xData'])

const setChart = () => {
  const chartDom = document.getElementById(props.idValue)
  const myChart = echarts.init(chartDom)

  const option = {
    tooltip: {},
    title: {
        text: props.titleValue,
        textStyle: {
          color : '#1f2937'
        }
    },
    xAxis: {
      type: 'category',
      data: props.xData,
      axisLabel: {
        color: ''
      }
    },
    yAxis: {
      type: 'value',
      min: 0,
      max: Math.max(...props.countData) + 2,
      interval: 1,
      axisLabel: {
        color: ''
      }
    },
    series: [{
      name: props.seriesValue,
      type: 'line',
      data: props.countData,
      itemStyle: {
        color: '#0284c7'
      }
    }]
  }
  myChart.setOption(option)
}

onMounted(() => {
  setChart()
})

watch(() => props.countData, (newVal) => {
  if (newVal) setChart()
})
</script>

<style lang="css" scoped>
.salon-chart-style {
    width: 100%;
    height: 400px;
    margin: 20px;
}
</style>