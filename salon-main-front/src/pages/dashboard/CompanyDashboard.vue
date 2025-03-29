<template>
  <div class="w-full">
    <div class="text-left mb-5">
      <div class="text-xl font-bold text-gray-900">고객사 대시보드</div>
    </div>

    <div class="w-full grid grid-cols-2 gap-5 items-start mt-10 mb-5">
      <div>
        <div class="flex justify-between items-center mb-3 py-2">
          <p class="font-bold text-gray-600 text-md text-left">월별 고객사 현황</p>
        </div>

        <div class="grid grid-cols-3 gap-5 mb-5">
          <SalonCard cardTitle="총 고객사" :cardContent="allCompany" />
          <SalonCard cardTitle="금월 추가 고객사" :cardContent="addCompany" />
          <SalonCard cardTitle="활성 고객사" :cardContent="activeCompany" />
        </div>

        <div class="bg-white rounded-xl shadow-md w-full py-5">
          <div>
            <SalonChart titleValue="월별 고객사 현황" :xData="companyXdata" :countData="companyCountData" idValue="companyChart" seriesValue="고객사" />
          </div>
        </div>
      </div>

      <div>
        <div class="flex justify-between items-center mb-3 py-2">
          <p class="font-bold text-gray-600 text-md text-left">월별 사용자 현황</p>
        </div>

        <div class="grid grid-cols-3 gap-5 mb-5">
          <SalonCard cardTitle="총 사용자" :cardContent="allUser" />
          <SalonCard cardTitle="금월 추가 사용자" :cardContent="addUser" />
          <SalonCard cardTitle="활성 사용자" :cardContent="activeUser" />
        </div>

        <div class="bg-white rounded-xl shadow-md w-full py-5">
          <div>
            <SalonChart titleValue="월별 사용자 현황" :xData="userXdata" :countData="userCountData" idValue="userChart" seriesValue="사용자" />
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script setup>
import { decryptStringSalt, makeCurrentByMonth } from '@/utils/common';
import { useUserStore } from '@/stores/userStore';
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router'

import SalonChart from '@/components/element/SalonChart.vue';
import SalonCard from '@/components/element/SalonCard.vue';

import ApiService from '@/services/ApiService';

onMounted(() => {
  if (decryptStringSalt(userStore.getUserRole) !== 'MASTER') {
    router.push({ name: 'ScheduleDashboard' }).catch(() => {
      console.log('ScheduleDashboardError')
    })
  } else {
    getCurrentCompany()
    getCurrentCompanyByMonth()
    getCurrentUser()
    getCurrentUserByMonth()
  }
})

const router = useRouter()
const userStore = useUserStore()

const allCompany = ref(0)
const addCompany = ref(0)
const activeCompany = ref(0)
const companyXdata = ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
const companyCountData = ref([0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0])

const allUser = ref(0)
const addUser = ref(0)
const activeUser = ref(0)
const userXdata = ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
const userCountData = ref([0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0])

const getCurrentCompany = async () => {
  const reqHeader = { accept: 'application/json' }
  const currentResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: '/main/dashboard/company/current'
  })
  if (currentResult.retStatus) {
    allCompany.value = currentResult.retData.allCompany
    addCompany.value = currentResult.retData.addCompany
    activeCompany.value = currentResult.retData.activeCompany
  }
}

const getCurrentCompanyByMonth = async () => {
  const reqHeader = { accept: 'application/json' }
  const currentByMonthResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: '/main/dashboard/company/current/month'
  })
  if (currentByMonthResult.retStatus) companyCountData.value = makeCurrentByMonth(currentByMonthResult.retData)
}

const getCurrentUser = async () => {
  const reqHeader = { accept: 'application/json' }
  const currentResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: '/main/dashboard/user/current'
  })
  if (currentResult.retStatus) {
    allUser.value = currentResult.retData.allUser
    addUser.value = currentResult.retData.addUser
    activeUser.value = currentResult.retData.activeUser
  }
}

const getCurrentUserByMonth = async () => {
  const reqHeader = { accept: 'application/json' }
  const currentByMonthResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: '/main/dashboard/user/current/month'
  })
  if (currentByMonthResult.retStatus) userCountData.value = makeCurrentByMonth(currentByMonthResult.retData)
}
</script>