<template>
  <div class="w-full">
    <div class="text-left mb-5">
      <div class="text-xl font-bold text-gray-900">예약 관리</div>
    </div>

    <div class="bg-white rounded-lg shadow-lg flex justify-between items-center px-8 py-4 mb-5">
      <div class="w-1/2 text-left flex justify-start items-center">
        <SalonSelect v-if="decryptStringSalt(userStore.getUserRole) === 'MASTER'" class="mr-5" selectTitle="고객사" selectPlaceholder="고객사" :isRequire="false" :options="companyList" optionTitle="companyName" optionSub="managerName" optionValue="companyGuid" v-model:inputValue="companyGuid"></SalonSelect>
        <SalonSelect selectTitle="담당자" selectPlaceholder="담당자" :isRequire="false" :options="userList" optionTitle="userName" optionSub="" optionValue="userGuid" v-model:inputValue="userGuid"></SalonSelect>
        <button class="w-1/3 py-3 ml-5 flex justify-center items-center bg-violet-200 text-violet-600 hover:bg-violet-300 px-5 py-2 rounded-lg shadow-sm" @click="refreshAll">
          <span class="pi pi-refresh mr-3"></span>새로고침
        </button>
      </div>
    </div>

    <div class="bg-white rounded-lg shadow-lg flex justify-between items-center px-8 py-4 mb-5">
      <FullCalendar class="w-full" ref="fullCalendar" :options="calendarOptions" />
    </div>

    <ReservatoinRegist :showModal="isRegistModal" :targetDate="targetDate" :userGuid="userGuid" @closeRegistModal="closeRegistModal"></ReservatoinRegist>

  </div>
</template>

<script setup>
import dayjs from 'dayjs'
import { onMounted, ref, watch } from 'vue'

import { decryptStringSalt } from '@/utils/common'
import { useUserStore } from '@/stores/userStore'
import ApiService from '@/services/ApiService'

import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
import timeGridPlugin from '@fullcalendar/timegrid'
import interactionPlugin from '@fullcalendar/interaction'
import koLocale from '@fullcalendar/core/locales/ko'

import SalonSelect from '@/components/element/SalonSelect.vue'

import ReservatoinRegist from '@/pages/schedule/reservation/ReservatoinRegist.vue'
import { emptyUUID } from '@/references/config'
import AlertService from '@/services/AlertService'

onMounted(() => {
  if (decryptStringSalt(userStore.getUserRole) === 'MASTER') getCompanyList()
  if (decryptStringSalt(userStore.getUserRole) !== 'MASTER') getUserList(companyGuid.value)
})

const userStore = useUserStore()

const isRegistModal = ref(false)
const isUpdateModal = ref(false)
const updateGuid = ref('')
const targetDate = ref('')
const companyGuid = ref(decryptStringSalt(userStore.getCurrentUser.ucg))
const companyList = ref([])
const userGuid = ref('')
const userList = ref([])

const fullCalendar = ref(null)
const calendarEvents = ref([])

const refreshAll = () => {
  updateGuid.value = ''
  targetDate.value = ''
  companyGuid.value = decryptStringSalt(userStore.getCurrentUser.ucg)
  companyList.value = []
  userGuid.value = ''
  userList.value = []

  fullCalendar.value = null
  calendarEvents.value = []

  if (decryptStringSalt(userStore.getUserRole) === 'MASTER') getCompanyList()
  if (decryptStringSalt(userStore.getUserRole) !== 'MASTER') getUserList(companyGuid.value)
}

const handleDateClick = (ev) => {
  if (ev.view.type === 'dayGridMonth') {
    fullCalendar.value.getApi().changeView('timeGridDay', ev.dateStr)
    return
  }
  if (userGuid.value === '') {
    AlertService.normalAlertAction('담당자를 선택해주세요.', '예약관리', '확인', 'error')
    return
  }
  targetDate.value = ev.dateStr
  isRegistModal.value = true
}
const handleEventClick = (ev) => {
  if (ev.view.type === 'dayGridMonth') {
    fullCalendar.value.getApi().changeView('timeGridDay', ev.event.start)
    return
  }
  updateGuid.value = ev.event.id
  isUpdateModal.value = true
}

const closeRegistModal = () => {
  isRegistModal.value = false
}

const calendarOptions = ref({
  plugins: [ dayGridPlugin, timeGridPlugin, interactionPlugin ],
  initialView: 'dayGridMonth',
  events: calendarEvents.value,
  eventClick: handleEventClick,
  dateClick: handleDateClick,
  headerToolbar: {
    left: 'prevYear,prev today next,nextYear',
    center: 'title',
    right: 'dayGridMonth,timeGridWeek,timeGridDay'
  },
  locale: koLocale,
  contentHeight: 700,
  datesSet: function (info) {
    const currentStart = dayjs(info.view.currentStart)
    const currentEnd = dayjs(info.view.currentEnd).subtract(1, 'day')
    console.log('currentStart : ' + currentStart.format('YYYY-MM-DD'))
    console.log('currentEnd : ' + currentEnd.format('YYYY-MM-DD'))

    if (info.view.type === 'dayGridMonth') {
      calendarOptions.value.events = calendarEvents.value.filter(calendarEvents => calendarEvents.date)
    } else calendarOptions.value.events = calendarEvents.value
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

const getUserList = async (companyGuid) => {
  const reqHeader = { accept: 'application/json' }
  const userListResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/main/manage/user/list/${companyGuid}`
  })
  if (userListResult.retStatus) {
    userList.value = userListResult.retData
    for (let i = 0; i < userList.value.length; i++) {
      userList.value[i].userName = decryptStringSalt(userList.value[i].userName)
    }
  }
}
watch (() => companyGuid.value, (newVal) => {
  if (companyGuid.value !== emptyUUID) getUserList(newVal)
})

</script>

<style lang="scss" scoped>

</style>
