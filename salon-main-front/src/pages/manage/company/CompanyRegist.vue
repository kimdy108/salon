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
          <div class="text-xl text-gray-900">고객사 등록</div>
        </div>
      </template>

      <div class="w-full px-5">
        <SalonInput inputTitle="사업자번호" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="false" :isPassword="false" v-model:inputValue="companyNumber" />

        <div class="grid grid-cols-2 gap-6">
          <SalonInput inputTitle="고객사 이름" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="false" :isPassword="false" v-model:inputValue="companyName" />
          <SalonInput inputTitle="주소" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="false" :isPassword="false" v-model:inputValue="companyAddress" />

          <SalonInput inputTitle="담당자 이름" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="false" :isPassword="false" v-model:inputValue="managerName" />
          <SalonInput inputTitle="담당자 연락처" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="false" :isPassword="false" v-model:inputValue="managerPhone" />
        </div>

        <SalonTextArea inputTitle="비고" :isDisabled="false" :isRequire="false" inputPlaceholder="" inputHint="" v-model:inputValue="descriptionNote" ></SalonTextArea>

      </div>

      <template #footer>
        <div>
          <button class="text-gray-700 bg-white hover:bg-gray-100 border-gray-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="closeRegistModal">닫기</button>
          <button class="text-sky-600 bg-sky-200 hover:bg-sky-300 border-sky-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="companyRegist">등록</button>
        </div>
      </template>
    
    
    </Dialog>
  </div>
</template>

<script setup>
import { Dialog } from 'primevue';
import { defineProps, defineEmits, ref, watch } from 'vue';

import SalonInput from '@/components/element/SalonInput.vue';
import SalonTextArea from '@/components/element/SalonTextArea.vue';

import ApiService from '@/services/ApiService'
import AlertService from '@/services/AlertService'

import { nameReg,phoneReg,companyNumberReg } from '@/references/config'

const props = defineProps(['showModal'])
const emit = defineEmits(['closeRegistModal'])

const companyNumber = ref('')
const companyName = ref('')
const companyAddress = ref('')
const managerName = ref('')
const managerPhone = ref('')
const descriptionNote = ref('')

const initData = () => {
  companyNumber.value = ''
  companyName.value = ''
  companyAddress.value = ''
  managerName.value = ''
  managerPhone.value = ''
  descriptionNote.value = ''
}

const closeRegistModal = () => {
  emit('closeRegistModal')
}

const companyRegist = () => {
  if(!companyNumberReg.test(companyNumber.value)) {
    AlertService.normalAlertAction('사업자번호를 확인해주세요.', '고객사관리', '확인', 'error')
    return
  }
  if(!nameReg.test(managerName.value)) {
    AlertService.normalAlertAction('이름을 확인해주세요.', '고객사관리', '확인', 'error')
    return
  }
  if(!phoneReg.test(managerPhone.value)) {
    AlertService.normalAlertAction('전화번호를 확인해주세요.', '고객사관리', '확인', 'error')
    return
  }
  companyRegistAction()
}

const companyRegistAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'companyNumber' : companyNumber.value,
    'companyName' : companyName.value,
    'companyAddress' : companyAddress.value,
    'managerName' : managerName.value,
    'managerPhone' : managerPhone.value,
    'descriptionNote' : descriptionNote.value,
  }
  const registResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'POST',
    url: `/main/company/regist`,
    data: reqData
  })
  if (registResult.retStatus) {
    AlertService.normalAlertAction('등록 했습니다.', '고객사관리', '확인', 'success')
    closeRegistModal()
  }
  else AlertService.normalAlertAction('등록에 실패했습니다.', '고객사관리', '확인', 'error')
}

watch(() => props.showModal, (newVal) => {
  if(newVal) initData()
})

</script>