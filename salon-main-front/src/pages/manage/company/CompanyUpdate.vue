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
          <div class="text-xl text-gray-900">고객사 수정</div>
        </div>
      </template>

      <div class="w-full px-5">
        <SalonInput inputTitle="사업자번호" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="true" :isPassword="false" v-model:inputValue="companyNumber" />

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
          <button class="text-gray-700 bg-white hover:bg-gray-100 border-gray-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="closeUpdateModal">닫기</button>
          <button class="text-orange-600 bg-orange-200 hover:bg-orange-300 border-orange-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="companyUpdate">수정</button>
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

import { nameReg,phoneReg } from '@/references/config'

const props = defineProps(['showModal','updateGuid'])
const emit = defineEmits(['closeUpdateModal'])

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

  getCompanyInfo()
}

const closeUpdateModal = () => {
  emit('closeUpdateModal')
}

const companyUpdate = () => {
  if(!nameReg.test(managerName.value)) {
    AlertService.normalAlertAction('이름을 확인해주세요.', '고객사관리', '확인', 'error')
    return
  }
  if(!phoneReg.test(managerPhone.value)) {
    AlertService.normalAlertAction('전화번호를 확인해주세요.', '고객사관리', '확인', 'error')
    return
  }
  companyUpdateAction()
}

const getCompanyInfo = async () => {
  const reqHeader = { accept: 'application/json' }
  const companyResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/main/company/info/${props.updateGuid}`
  })
  if (companyResult.retStatus) {
    companyNumber.value = companyResult.retData.companyNumber
    companyName.value = companyResult.retData.companyName
    companyAddress.value = companyResult.retData.companyAddress
    managerName.value = companyResult.retData.managerName
    managerPhone.value = companyResult.retData.managerPhone
    descriptionNote.value = companyResult.retData.descriptionNote
  } else AlertService.normalAlertAction('조회 실패했습니다.', '고객사관리', '확인', 'error')
}

const companyUpdateAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'companyGuid' : props.updateGuid,
    'companyName' : companyName.value,
    'companyAddress' : companyAddress.value,
    'managerName' : managerName.value,
    'managerPhone' : managerPhone.value,
    'descriptionNote' : descriptionNote.value,
  }
  const updateResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'PUT',
    url: `/main/company/update`,
    data: reqData
  })
  if (updateResult.retStatus) {
    AlertService.normalAlertAction('수정 했습니다.', '고객사관리', '확인', 'success')
    closeUpdateModal()
  }
  else AlertService.normalAlertAction('수정에 실패했습니다.', '고객사관리', '확인', 'error')
}

watch(() => props.showModal, (newVal) => {
  if(newVal) initData()
})

</script>