<template>
  <div>
    <Dialog :visible="showModal" 
      modal 
      :closable="false" 
      :draggable="false"
      class="w-2/3"
    >

      <template #header>
        <div class="w-full flex justify-between items-center border-b border-gray-100 pb-6">
          <div class="text-xl text-gray-900">예약 등록</div>
        </div>
      </template>

      <div class="grid grid-cols-2 gap-4">
        <SalonInput inputTitle="예약일" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="true" :isPassword="false" v-model:inputValue="reservationDate" />
        <SalonInput inputTitle="예약시간" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="true" :isPassword="false" v-model:inputValue="reservationTime" />
      </div>
      
      <SalonSelect selectTitle="스타일" selectPlaceholder="스타일" :isRequire="true" :options="styleList" optionTitle="styleName" optionSub="styleDuration" optionValue="styleGuid" v-model:inputValue="styleGuid"></SalonSelect>

      <div class="grid grid-cols-2 gap-4">
        <SalonInput inputTitle="예약자 이름" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="false" :isPassword="false" v-model:inputValue="clientName" />
        <SalonInput inputTitle="예약자 번호" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="false" :isPassword="false" v-model:inputValue="clientNumber" />
      </div>

      <SalonTextArea inputTitle="비고" :isDisabled="false" :isRequire="false" inputPlaceholder="" inputHint="" v-model:inputValue="descriptionNote" ></SalonTextArea>

      <template #footer>
        <div>
          <button class="text-gray-700 bg-white hover:bg-gray-100 border-gray-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="closeRegistModal">닫기</button>
          <button class="text-sky-600 bg-sky-200 hover:bg-sky-300 border-sky-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="reservationRegist">등록</button>
        </div>
      </template>

    </Dialog>
  </div>
</template>

<script setup>
import { Dialog } from 'primevue'
import { defineProps, defineEmits, watch, ref } from 'vue'

import AlertService from '@/services/AlertService'
import ApiService from '@/services/ApiService'

import SalonInput from '@/components/element/SalonInput.vue'
import SalonSelect from '@/components/element/SalonSelect.vue'
import SalonTextArea from '@/components/element/SalonTextArea.vue'
import { nameReg, phoneReg } from '@/references/config'

const props = defineProps(['showModal', 'targetDate', 'userGuid'])
const emit = defineEmits(['closeRegistModal'])

const closeRegistModal = () => {
  emit('closeRegistModal')
}

const reservationDate = ref('')
const reservationTime = ref('')
const styleList = ref([])
const styleGuid = ref('')
const clientName = ref('')
const clientNumber = ref('')
const descriptionNote = ref('')

const initData = () => {
  reservationDate.value = props.targetDate.split('T')[0]
  reservationTime.value = props.targetDate.split('T')[1].split('+')[0]
  styleList.value = []
  styleGuid.value = ''
  clientName.value = ''
  clientNumber.value = ''
  descriptionNote.value = ''

  getStyleList()
}

const reservationRegist = () => {
  if (styleGuid.value === '') {
    AlertService.normalAlertAction('스타일을 확인해주세요.', '예약관리', '확인', 'error')
    return
  }
  if (!nameReg.test(clientName.value)) {
    AlertService.normalAlertAction('예약자 성함을 확인해주세요.', '예약관리', '확인', 'error')
    return
  }
  if (!phoneReg.test(clientNumber.value)) {
    AlertService.normalAlertAction('예약자 번호을 확인해주세요.', '예약관리', '확인', 'error')
    return
  }

  reservationRegistAction()
}

const getStyleList = async () => {
  const reqHeader = { accept: 'application/json' }
  const styleListResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/main/setting/style/list/${props.userGuid}`
  })
  if (styleListResult.retStatus) {
    styleList.value = styleListResult.retData
  }
}

const reservationRegistAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'userGuid' : props.userGuid,
    'styleGuid' : styleGuid.value,
    'reservationDate' : props.targetDate,
    'clientName' : clientName.value,
    'clientNumber' : clientNumber.value,
    'descriptionNote' : descriptionNote.value
  }
  const registResult = await ApiService.requestAPI({
    headers: reqHeader,
    data: reqData,
    method: 'POST',
    url: '/main/schedule/reservation/regist',
  })
  if (registResult.retStatus) {
    AlertService.normalAlertAction('등록 했습니다.', '예약관리', '확인', 'success')
    closeRegistModal()
  }
  else AlertService.normalAlertAction(registResult.retData, '예약관리', '확인', 'error')
}

watch(() => props.showModal, (newVal) => {
  if(newVal) {
    initData()
  }
})
</script>

<style lang="scss" scoped>

</style>