<template>
  <div class="w-full">
    <div class="text-left mb-5">
      <div class="text-xl font-bold text-gray-900">근태 관리</div>
    </div>

    <div v-if="decryptStringSalt(userStore.getUserRole) === 'MASTER'" class="bg-white rounded-lg shadow-lg flex justify-between items-center px-8 py-4 mb-5">
      <div class="w-1/3 text-left flex justify-start items-center">
        <SalonSelect selectTitle="고객사" selectPlaceholder="고객사" :isRequire="false" :options="companyList" optionTitle="companyName" optionSub="managerName" optionValue="companyGuid" v-model:inputValue="companyGuid"></SalonSelect>
        <button class="w-1/3 py-3 ml-5 flex justify-center items-center bg-violet-200 text-violet-600 hover:bg-violet-300 px-5 py-2 rounded-lg shadow-sm" @click="refreshCompanyGuid">
          <span class="pi pi-refresh mr-3"></span>새로고침
        </button>
      </div>
    </div>

    <div class="bg-white rounded-lg shadow-lg flex justify-between items-center px-8 py-4 mb-5">
      <FullCalendar class="w-full" initialView="dayGridMonth" :options="calendarOptions" />
    </div>

    <EmploymentRegist :showModal="isRegistModal" :targetDate="targetDate" @closeRegistModal="closeRegistModal"></EmploymentRegist>
    <EmploymentUpdate :showModal="isUpdateModal" :updateGuid="updateGuid" @closeUpdateModal="closeUpdateModal"></EmploymentUpdate>

  </div>
</template>

<script setup>
import dayjs from 'dayjs'
import { onMounted, ref, watch } from 'vue';

import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import koLocale from '@fullcalendar/core/locales/ko';

import ApiService from '@/services/ApiService';
import { decryptStringSalt } from '@/utils/common';
import { useUserStore } from '@/stores/userStore';

import SalonSelect from '@/components/element/SalonSelect.vue';

import EmploymentRegist from '@/pages/schedule/employment/EmploymentRegist.vue'
import EmploymentUpdate from '@/pages/schedule/employment/EmploymentUpdate.vue'
import { emptyUUID } from '@/references/config';

onMounted(() => {
  getEmploymentList(currentDate)
  if (decryptStringSalt(userStore.getUserRole) === 'MASTER') getCompanyList()
})

const userStore = useUserStore()

const currentDate = dayjs().format('YYYY-MM')
const searchDate = ref('')
const isRegistModal = ref(false)
const isUpdateModal = ref(false)
const updateGuid = ref('')
const targetDate = ref('')
const companyGuid = ref(decryptStringSalt(userStore.getCurrentUser.ucg))
const companyList = ref([])

const eventBgColor = (val) => {
  if (val === 'EMPOLYMENT') return '#22d3ee'
  if (val === 'HOLIDAY') return '#f87171'
  if (val === 'VACATION') return '#c084fc'
  if (val === 'SICKLEAVE') return '#4ade80'
  if (val === 'OFFICIALVACATION') return '#fde047'
}

const eventCategory = (val) => {
  if (val === 'EMPOLYMENT') return '근무'
  if (val === 'HOLIDAY') return '휴무'
  if (val === 'VACATION') return '연차'
  if (val === 'SICKLEAVE') return '병가'
  if (val === 'OFFICIALVACATION') return '공가'
}

const handleDateClick = (val) => {
  if (decryptStringSalt(userStore.getUserRole) === 'MASTER' || decryptStringSalt(userStore.getUserRole) === 'ADMIN') {
    targetDate.value = val.dateStr
    isRegistModal.value = true
  }
}
const handleEventClick = (val) => {
  updateGuid.value = val.event.id
  isUpdateModal.value = true
}

const closeRegistModal = () => {
  isRegistModal.value = false
  getEmploymentList()
}
const closeUpdateModal = () => {
  isUpdateModal.value = false
  getEmploymentList()
}

const refreshCompanyGuid = () => {
  companyGuid.value = emptyUUID
}

const calendarOptions = ref({
  plugins: [ dayGridPlugin, interactionPlugin ],
  initialView: 'dayGridMonth',
  events: [],
  eventClick: handleEventClick,
  dateClick: handleDateClick,
  headerToolbar: {
    left: 'prevYear,prev today next,nextYear',
    center: 'title',
    right: 'dayGridMonth'
  },
  locale: koLocale,
  contentHeight: 700,
  datesSet: function (info) {
    const currentStart = dayjs(info.view.currentStart);
    searchDate.value = currentStart.format('YYYY-MM')
    getEmploymentList()
  }
})

const getCompanyList = async () => {
  const reqHeader = { accept: 'application/json' }
  const companyListResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: '/main/manage/company/list',
  })
  if (companyListResult.retStatus) {
    companyList.value = companyListResult.retData
  }
}

const getEmploymentList = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqParams = {
    employmentDate: searchDate.value === '' ? currentDate : searchDate.value,
    companyGuid: companyGuid.value
  }
  const employmentList = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/main/schedule/employment/list`,
    params: reqParams
  })
  if (employmentList.retStatus) {
    calendarOptions.value.events = []
    for(const value of employmentList.retData) {
      let bgColor = eventBgColor(value.employmentCategory)
      let category = eventCategory(value.employmentCategory)
      let inputDate = value.employmentYear + '-' + value.employmentMonth + '-' + value.employmentDay
      let inputTitle = value.companyName + '-' + value.userName + '-' + category
      
      calendarOptions.value.events.push(
        {id: value.employmentGuid, date: inputDate, title: inputTitle, backgroundColor: bgColor, borderColor: bgColor}
      )
    }
  }
}

watch (() => companyGuid.value, () => {
  getEmploymentList()
})

</script>
