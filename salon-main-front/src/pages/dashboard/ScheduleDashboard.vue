<template>
  <div class="w-full">
    <div class="text-left mb-5">
      <div class="text-xl font-bold text-gray-900">스케줄 대시보드</div>
    </div>

    <div class="w-full grid grid-cols-2 gap-5 items-start mt-10 mb-5">
      <div>
        <div class="flex justify-between items-center mb-3 py-2">
          <p class="font-bold text-gray-600 text-md text-left">월별 스케줄 현황</p>
        </div>

        <div class="grid grid-cols-2 gap-5 mb-5">
          <SalonCard cardTitle="금년 스케줄" :cardContent="yearSchedule" />
          <SalonCard cardTitle="금월 스케줄" :cardContent="monthSchedule" />
        </div>

        <div class="bg-white rounded-xl shadow-md w-full py-5">
          <div>
            <SalonChart titleValue="월별 스케줄 현황" :xData="scheduleXdata" :countData="scheduleCountData" idValue="companyChart" seriesValue="고객사" />
          </div>
        </div>
      </div>

      <div>
        <div class="flex justify-between items-center mb-3 py-2">
          <p class="font-bold text-gray-600 text-md text-left">금일 사용자별 예약 현황</p>
        </div>

        <div class="grid grid-cols-2 gap-5 mb-5">
          <SalonCard cardTitle="금일 스케줄" :cardContent="daySchedule" />
          <SalonCard cardTitle="금일 근무 인원" :cardContent="employmentUser" />
        </div>

        <div class="bg-white rounded-xl shadow-md w-full py-5">
          <div>
            <SalonChart titleValue="사용자별 예약 현황" :xData="userScheduleXdata" :countData="userScheduleCountData" idValue="userChart" seriesValue="사용자" />
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useUserStore } from '@/stores/userStore';
import { decryptStringSalt, makeCurrentByMonth } from '@/utils/common';

import SalonChart from '@/components/element/SalonChart.vue';
import SalonCard from '@/components/element/SalonCard.vue';

import ApiService from '@/services/ApiService';

onMounted(() => {
  getCurrentSchedule()
  getCurrentScheduleByMonth()
  getCurrentScheduleByDay()
  getCurrentScheduleByUser()
})

const userStore = useUserStore()

const yearSchedule = ref(0)
const monthSchedule = ref(0)
const scheduleXdata = ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
const scheduleCountData = ref([0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0])

const daySchedule = ref(0)
const employmentUser = ref(0)
const userScheduleXdata = ref([])
const userScheduleCountData = ref([])

const getCurrentSchedule = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqParams = { 'companyGuid': decryptStringSalt(userStore.getCurrentUser.ucg) }
  const currentResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: '/main/dashboard/schedule/current',
    params: reqParams
  })
  if (currentResult.retStatus) {
    yearSchedule.value = currentResult.retData.yearSchedule
    monthSchedule.value = currentResult.retData.monthSchedule
  }
}

const getCurrentScheduleByMonth = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqParams = { 'companyGuid': decryptStringSalt(userStore.getCurrentUser.ucg) }
  const currentByMonthResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: '/main/dashboard/schedule/current/month',
    params: reqParams
  })
  if (currentByMonthResult.retStatus) scheduleCountData.value = makeCurrentByMonth(currentByMonthResult.retData)
}

const getCurrentScheduleByDay = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqParams = { 'companyGuid': decryptStringSalt(userStore.getCurrentUser.ucg) }
  const currentByDayResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: '/main/dashboard/schedule/current/day',
    params: reqParams
  })
  if (currentByDayResult.retStatus) {
    daySchedule.value = currentByDayResult.retData.daySchedule
    employmentUser.value = currentByDayResult.retData.employmentUser
  }
}

const getCurrentScheduleByUser = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqParams = { 'companyGuid': decryptStringSalt(userStore.getCurrentUser.ucg) }
  const currentByUserResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: '/main/dashboard/schedule/current/user',
    params: reqParams
  })
  if (currentByUserResult.retStatus) {
    console.log(currentByUserResult.retData)
    let userXData = []
    let userCountData = []
    for (let i = 0; i < currentByUserResult.retData.length; i++) {
      userXData.push(currentByUserResult.retData[i].companyName + '-' + currentByUserResult.retData[i].userName)
      userCountData.push(currentByUserResult.retData[i].scheduleCount)
    }
    userScheduleXdata.value = userXData
    userScheduleCountData.value = userCountData
  }
}
</script>