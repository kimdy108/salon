<template>
  <div class="w-full">
    <div class="text-left mb-5">
      <div class="text-xl font-bold text-gray-900">시스템 설정</div>
    </div>

    <div class="bg-white rounded-lg shadow-lg px-8 py-6 mb-5">
      <div class="text-left mb-5">
        <div class="text-xl font-bold text-gray-900">영업 시간</div>
      </div>

      <div class="card">
        <Accordion :value="['0']" multiple>
          <AccordionPanel v-for="val in weekArrays" :key="val.id" :value="val.id" >
            <AccordionHeader class="!text-lg">{{ val.dayKr }}</AccordionHeader>
            <AccordionContent>
              <SalonSelectBtn inputTitle="근무 / 휴무" :isDisabled="false" :isRequire="true" :options="hoursOptions" optionLabel="value" optionValue="key" :multiple="false" v-model:inputValue="val.hoursOption"></SalonSelectBtn>
              
              <div v-if="val.hoursOption === 'EMPLOYMENT'" class="text-left mt-5">
                <div class="text-sm font-bold text-gray-900 mb-5">근무 시간</div>
                <div class="w-1/3 grid grid-cols-2 gap-4">
                  <Select class="w-full !bg-white" placeholder="영업 시작 시간" :options="timeRange" v-model="val.startTime" />
                  <Select class="w-full !bg-white" placeholder="영업 마감 시간" :options="timeRange" v-model="val.endTime" />
                </div>
              </div>
              <div class="flex justify-end">
                <button class="text-sky-600 bg-sky-200 hover:bg-sky-300 border-sky-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="hoursRegist(val)">등록</button>
              </div>
            </AccordionContent>
          </AccordionPanel>
        </Accordion>
      </div>
    </div>



  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { timeRange } from '@/references/config';
import { decryptStringSalt } from '@/utils/common';
import { useUserStore } from '@/stores/userStore';

import ApiService from '@/services/ApiService';
import AlertService from '@/services/AlertService';

import { Accordion } from 'primevue';
import AccordionPanel from 'primevue/accordionpanel'
import AccordionHeader from 'primevue/accordionheader'
import AccordionContent from 'primevue/accordioncontent'
import Select from 'primevue/select';

import SalonSelectBtn from '@/components/element/SalonSelectBtn.vue';

onMounted(() => {
  getHoursInfoList()

  let el = document.getElementById('salon-hair')
  el.scrollTo({ top: 0 })
})

const userStore = useUserStore()

const hoursOptions = [
    { key : 'EMPLOYMENT', value : '근무'},
    { key : 'HOLIDAY', value : '휴무'}
]

const weekArrays = ref([
  { id : 0, dayEng : 'SUNDAY', dayKr : '일요일', hoursOption : 'HOLIDAY', startTime : '00:00', endTime : '00:00' },
  { id : 1, dayEng : 'MONDAY', dayKr : '월요일', hoursOption : 'HOLIDAY', startTime : '00:00', endTime : '00:00' },
  { id : 2, dayEng : 'TUESDAY', dayKr : '화요일', hoursOption : 'HOLIDAY', startTime : '00:00', endTime : '00:00' },
  { id : 3, dayEng : 'WEDNESDAY', dayKr : '수요일', hoursOption : 'HOLIDAY', startTime : '00:00', endTime : '00:00' },
  { id : 4, dayEng : 'THURSDAY', dayKr : '목요일', hoursOption : 'HOLIDAY', startTime : '00:00', endTime : '00:00' },
  { id : 5, dayEng : 'FRIDAY', dayKr : '금요일', hoursOption : 'HOLIDAY', startTime : '00:00', endTime : '00:00' },
  { id : 6, dayEng : 'SATURDAY', dayKr : '토요일', hoursOption : 'HOLIDAY', startTime : '00:00', endTime : '00:00' },
])

const hoursRegist = (val) => {
  if (val.hoursOption === null || val.hoursOption === '') {
    AlertService.normalAlertAction('근무 여부를 확인해주세요.', '시스템 설정', '확인', 'error')
    return
  }
  if (val.hoursOption === 'EMPLOYMENT' && timeRange.indexOf(val.startTime) >= timeRange.indexOf(val.endTime)) {
    AlertService.normalAlertAction('근무 시간을 확인해주세요.', '시스템 설정', '확인', 'error')
    return
  }
  if (val.startTime === null || val.startTime === '' || val.endTime === null || val.endTime === '') {
    AlertService.normalAlertAction('근무 시간을 확인해주세요.', '시스템 설정', '확인', 'error')
    return
  }
  hoursRegistAction(val)
}

const hoursRegistAction = async (val) => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'companyGuid' : decryptStringSalt(userStore.getCurrentUser.ucg),
    'dayOfWeek' : val.dayEng,
    'hoursCategory' : val.hoursOption,
    'startTime' : val.startTime,
    'endTime' : val.endTime,
  }
  const registResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'POST',
    url: `/main/setting/system/regist`,
    data: reqData
  })
  if (registResult.retStatus) AlertService.normalAlertAction('등록에 성공했습니다.', '영업 시간', '확인', 'success')
}

const getHoursInfoList = async () => {
  const reqHeader = { accept: 'application/json' }
  const infoListResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/main/setting/system/info/list/${decryptStringSalt(userStore.getCurrentUser.ucg)}`
  })
  if (infoListResult.retStatus) {
    for (let i = 0; i < infoListResult.retData.length; i++) {
      const valueOfResult = infoListResult.retData[i]
      const valueOfWeek = weekArrays.value.filter(item => item.dayEng === valueOfResult.dayOfWeek)[0]

      let resultStartTime = valueOfResult.startTimeHour + ':' + valueOfResult.startTimeMinute
      let resultEndTime = valueOfResult.endTimeHour + ':' + valueOfResult.endTimeMinute

      valueOfWeek.hoursOption = valueOfResult.hoursCategory
      valueOfWeek.startTime = resultStartTime
      valueOfWeek.endTime = resultEndTime
    }
  }
}
</script>

<style lang="scss" scoped>

</style>