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
      </div>
      
      <SalonInput inputTitle="스타일" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="true" :isPassword="false" v-model:inputValue="styleName" />

      <div class="grid grid-cols-2 gap-4">
        <SalonInput inputTitle="예약자 이름" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="true" :isPassword="false" v-model:inputValue="clientName" />
        <SalonInput inputTitle="예약자 번호" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="true" :isPassword="false" v-model:inputValue="clientNumber" />
      </div>

      <SalonTextArea inputTitle="비고" :isDisabled="false" :isRequire="false" inputPlaceholder="" inputHint="" v-model:inputValue="descriptionNote" ></SalonTextArea>

      <template #footer>
        <div>
          <button class="text-gray-700 bg-white hover:bg-gray-100 border-gray-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="closeUpdateModal">닫기</button>
          <button v-if="checkUserRole" class="text-red-600 bg-red-200 hover:bg-red-300 border-red-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="reservationDelete">삭제</button>
        </div>
      </template>


    </Dialog>
  </div>
</template>

<script setup>
import { Dialog } from 'primevue'
import { defineProps, defineEmits, watch, ref } from 'vue'

import SalonInput from '@/components/element/SalonInput.vue'
import SalonTextArea from '@/components/element/SalonTextArea.vue'

const props = defineProps(['showModal', 'targetDate', 'userGuid', 'updateGuid'])
const emit = defineEmits(['closeUpdateModal'])

const closeUpdateModal = () => {
  emit('closeUpdateModal')
}

const reservationDate = ref('')
const reservationTime = ref('')
const styleName = ref('')
const clientName = ref('')
const clientNumber = ref('')
const descriptionNote = ref('')

const initData = () => {
  reservationDate.value = ''
  reservationTime.value = ''
  styleName.value = ''
  clientName.value = ''
  clientNumber.value = ''
  descriptionNote.value = ''
}

const reservationDelete = () => {
  console.log('reservationDelete : ', props.updateGuid)
  closeUpdateModal()
}

watch(() => props.showModal, (newVal) => {
  if(newVal) {
    initData()
  }
})
</script>

<style lang="scss" scoped>

</style>