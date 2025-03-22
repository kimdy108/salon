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
          <div class="text-xl text-gray-900">예약 정보</div>
        </div>
      </template>

      <div class="grid grid-cols-2 gap-4">
        <SalonInput inputTitle="예약일" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="true" :isPassword="false" v-model:inputValue="reservationDate" />
        <SalonInput inputTitle="예약시간" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="true" :isPassword="false" v-model:inputValue="reservationTime" />
      
        <SalonInput inputTitle="스타일" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="true" :isPassword="false" v-model:inputValue="styleName" />
        <SalonInput inputTitle="소요시간" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="true" :isPassword="false" v-model:inputValue="styleDuration" />

        <SalonInput inputTitle="예약자 이름" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="true" :isPassword="false" v-model:inputValue="clientName" />
        <SalonInput inputTitle="예약자 번호" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="true" :isPassword="false" v-model:inputValue="clientNumber" />
      </div>

      <SalonTextArea inputTitle="비고" :isDisabled="false" :isRequire="false" inputPlaceholder="" inputHint="" v-model:inputValue="descriptionNote" ></SalonTextArea>

      <template #footer>
        <div>
          <button class="text-gray-700 bg-white hover:bg-gray-100 border-gray-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="closeInfoModal">닫기</button>
          <button v-if="checkUserRole" class="text-red-600 bg-red-200 hover:bg-red-300 border-red-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="reservationDelete">취소</button>
        </div>
      </template>


    </Dialog>
  </div>
</template>

<script setup>
import { Dialog } from 'primevue'
import { defineProps, defineEmits, watch, ref } from 'vue'
import { useUserStore } from '@/stores/userStore'
import { decryptStringSalt } from '@/utils/common'

import ApiService from '@/services/ApiService'
import AlertService from '@/services/AlertService'

import SalonInput from '@/components/element/SalonInput.vue'
import SalonTextArea from '@/components/element/SalonTextArea.vue'

const props = defineProps(['showModal', 'infoGuid'])
const emit = defineEmits(['closeInfoModal'])

const closeInfoModal = () => {
  emit('closeInfoModal')
}

const userStore = useUserStore()
const checkUserRole = decryptStringSalt(userStore.getUserRole) === 'MASTER' || decryptStringSalt(userStore.getUserRole) === 'ADMIN'

const reservationGuid = ref('')
const reservationDate = ref('')
const reservationTime = ref('')
const styleName = ref('')
const styleDuration = ref('')
const clientName = ref('')
const clientNumber = ref('')
const descriptionNote = ref('')

const initData = () => {
  reservationGuid.value = ''
  reservationDate.value = ''
  reservationTime.value = ''
  styleName.value = ''
  styleDuration.value = ''
  clientName.value = ''
  clientNumber.value = ''
  descriptionNote.value = ''

  getReservationInfo()
}

const reservationDelete = () => {
  reservationDeleteAction()
}

const getReservationInfo = async () => {
  const reqHeader = { accept: 'application/json' }
  const infoResult = await ApiService.requestAPI({
    headers : reqHeader,
    method : 'GET',
    url : `/main/schedule/reservation/info/${props.infoGuid}`
  })
  if (infoResult.retStatus) {
    reservationGuid.value = infoResult.retData.reservationGuid
    reservationDate.value = infoResult.retData.reservationYear + '-' + infoResult.retData.reservationMonth + '-' + infoResult.retData.reservationDay
    reservationTime.value = infoResult.retData.reservationHour + ':' + infoResult.retData.reservationMinute + ':00'
    styleName.value = infoResult.retData.styleName
    styleDuration.value = infoResult.retData.styleDuration
    clientName.value = infoResult.retData.clientName
    clientNumber.value = infoResult.retData.clientNumber
    descriptionNote.value = infoResult.retData.descriptionNote
  }
}

const reservationDeleteAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const deleteResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'DELETE',
    url: `/main/schedule/reservation/delete/${reservationGuid.value}`
  })
  if (deleteResult.retStatus) {
    AlertService.normalAlertAction('삭제 했습니다.', '예약관리', '확인', 'success')
    closeInfoModal()
  }
  else AlertService.normalAlertAction(deleteResult.retData, '예약관리', '확인', 'error')
}

watch(() => props.showModal, (newVal) => {
  if(newVal) {
    initData()
  }
})
</script>

<style lang="scss" scoped>

</style>