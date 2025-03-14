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
          <div class="text-xl text-gray-900">근태 수정</div>
        </div>
      </template>

      <div class="w-full px-5">
        <SalonInput inputTitle="고객사" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="true" :isPassword="false" v-model:inputValue="companyName" />
        <SalonInput inputTitle="담당자" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="true" :isPassword="false" v-model:inputValue="userName" />
        <SalonSelectBtn inputTitle="근태 종류" :isDisabled="checkUserRole ? false : true" :isRequire="true" :options="employmentOptions" optionLabel="value" optionValue="key" :multiple="false" v-model:inputValue="employmentOption"></SalonSelectBtn>
        <SalonTextArea inputTitle="비고" :isDisabled="checkUserRole ? false : true" :isRequire="false" inputPlaceholder="" inputHint="" v-model:inputValue="descriptionNote" ></SalonTextArea>
      </div>

      <template #footer>
        <div>
          <button class="text-gray-700 bg-white hover:bg-gray-100 border-gray-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="closeUpdateModal">닫기</button>
          <button v-if="checkUserRole" class="text-orange-600 bg-orange-200 hover:bg-orange-300 border-orange-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="employmentUpdate">수정</button>
        </div>
      </template>
    
    </Dialog>
  </div>
</template>

<script setup>
import { Dialog } from 'primevue';
import { defineProps, defineEmits, watch, ref } from 'vue';
import { decryptStringSalt } from '@/utils/common';
import { useUserStore } from '@/stores/userStore';

import SalonInput from '@/components/element/SalonInput.vue'
import SalonSelectBtn from '@/components/element/SalonSelectBtn.vue'
import SalonTextArea from '@/components/element/SalonTextArea.vue'

import ApiService from '@/services/ApiService';
import AlertService from '@/services/AlertService';

const props = defineProps(['showModal','updateGuid'])
const emit = defineEmits(['closeUpdateModal'])

const closeUpdateModal = () => {
  emit('closeUpdateModal')
}

const userStore = useUserStore()
const checkUserRole = decryptStringSalt(userStore.getUserRole) === 'MASTER' || decryptStringSalt(userStore.getUserRole) === 'ADMIN'

const employmentGuid = ref('')
const companyName = ref('')
const userName = ref('')
const employmentOption = ref('')
const descriptionNote = ref('')

const initData = () => {
  companyName.value = ''
  userName.value = ''
  employmentOption.value = ''
  descriptionNote.value = ''

  getEmploymentInfo()
}

const employmentOptions = [
  { key : 'EMPOLYMENT', value : '근무'},
  { key : 'HOLIDAY', value : '휴무'},
  { key : 'VACATION', value : '연차'},
  { key : 'SICKLEAVE', value : '병가'},
  { key : 'OFFICIALVACATION', value : '공가'}
]

const employmentUpdate = () => {
    employmentUpdateAction()
}

const getEmploymentInfo = async () => {
  const reqHeader = { accept: 'application/json' }
  const employmentResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/main/schedule/employment/info/${props.updateGuid}`
  })
  if (employmentResult.retStatus) {
    employmentGuid.value = employmentResult.retData.employmentGuid
    companyName.value = employmentResult.retData.companyName
    userName.value = employmentResult.retData.userName
    employmentOption.value = employmentResult.retData.employmentCategory
    descriptionNote.value = employmentResult.retData.descriptionNote
  } else AlertService.normalAlertAction(employmentResult.retData, '근태관리', '확인', 'error')
}

const employmentUpdateAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'employmentGuid' : employmentGuid.value,
    'employmentCategory' : employmentOption.value,
    'descriptionNote' : descriptionNote.value
  }
  const updateResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'PUT',
    url: `/main/schedule/employment/update`,
    data: reqData
  })
  if (updateResult.retStatus) {
    AlertService.normalAlertAction('수정 했습니다.', '근태관리', '확인', 'success')
    closeUpdateModal()
  }
  else AlertService.normalAlertAction(updateResult.retData, '근태관리', '확인', 'error')
}

watch(() => props.showModal, (newVal) => {
  if(newVal) initData()
})
</script>

<style lang="scss" scoped>

</style>