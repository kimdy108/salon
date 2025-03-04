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
          <div class="text-xl text-gray-900">관리자 수정</div>
        </div>
      </template>

      <div class="w-full px-5">

        <div class="grid grid-cols-2 gap-6">
          <SalonInput inputTitle="아이디" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="true" :isPassword="false" v-model:inputValue="masterID" />
          <SalonInput inputTitle="이름" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="false" :isPassword="false" v-model:inputValue="masterName" />
          <SalonInput inputTitle="전화번호" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="false" :isPassword="false" v-model:inputValue="masterPhone" />
          <SalonInput inputTitle="이메일" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="false" :isPassword="false" v-model:inputValue="masterEmail" />
        </div>

        <SalonTextArea inputTitle="비고" :isDisabled="false" :isRequire="false" inputPlaceholder="" inputHint="" v-model:inputValue="descriptionNote" ></SalonTextArea>

      </div>

      <template #footer>
        <div>
          <button class="text-gray-700 bg-white hover:bg-gray-100 border-gray-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="closeUpdateModal">닫기</button>
          <button class="text-orange-600 bg-orange-200 hover:bg-orange-300 border-orange-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="masterUpdate">수정</button>
        </div>
      </template>
    </Dialog>

  </div>
</template>

<script setup>
import { Dialog } from 'primevue';

import { defineProps, defineEmits, watch, ref } from 'vue';
import { nameReg, phoneReg, emailReg } from '@/references/config';
import { decryptStringSalt, encryptStringSalt } from '@/utils/common'

import SalonInput from '@/components/element/SalonInput.vue'
import SalonTextArea from '@/components/element/SalonTextArea.vue'

import ApiService from '@/services/ApiService';
import AlertService from '@/services/AlertService';

const props = defineProps(['showModal','updateGuid'])
const emit = defineEmits(['closeUpdateModal'])

const masterGuid = ref('')
const masterID = ref('')
const masterName = ref('')
const masterPhone = ref('')
const masterEmail = ref('')
const descriptionNote = ref('')

const initData = () => {
  masterGuid.value = ''
  masterID.value = ''
  masterName.value = ''
  masterPhone.value = ''
  masterEmail.value = ''
  descriptionNote.value = ''
  getMasterInfo()
}

const closeUpdateModal = () => {
  emit('closeUpdateModal')
}

const masterUpdate = () => {
  if(!nameReg.test(masterName.value)) {
    AlertService.normalAlertAction('이름을 확인해주세요.', '사용자관리', '확인', 'error')
    return
  }
  if(!phoneReg.test(masterPhone.value)) {
    AlertService.normalAlertAction('전화번호를 확인해주세요.', '사용자관리', '확인', 'error')
    return
  }
  if(!emailReg.test(masterEmail.value)) {
    AlertService.normalAlertAction('이메일을 확인해줏요.', '사용자관리', '확인', 'error')
    return
  }

  masterUpdateAction()
}

const getMasterInfo = async () => {
  const reqHeader = { accept: 'application/json' }
  const infoResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/main/manage/master/info/${props.updateGuid}`
  })
  if (infoResult.retStatus) {
    masterGuid.value = infoResult.retData.masterGuid
    masterID.value = decryptStringSalt(infoResult.retData.masterID)
    masterName.value = decryptStringSalt(infoResult.retData.masterName)
    masterPhone.value = decryptStringSalt(infoResult.retData.masterPhone)
    masterEmail.value = decryptStringSalt(infoResult.retData.masterEmail)
    descriptionNote.value = infoResult.retData.descriptionNote
  }
}

const masterUpdateAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'masterGuid' : masterGuid.value,
    'masterName' : encryptStringSalt(masterName.value),
    'masterPhone' : encryptStringSalt(masterPhone.value),
    'masterEmail' : encryptStringSalt(masterEmail.value),
    'descriptionNote' : descriptionNote.value
  }
  const updateResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'PUT',
    url: '/main/manage/master/update',
    data: reqData
  })
  if (updateResult.retStatus) {
    AlertService.normalAlertAction('수정했습니다.', '사용자관리', '확인', 'success')
    closeUpdateModal()
  } else AlertService.normalAlertAction(updateResult.retData, '사용자관리', '확인', 'error')
}

watch(() => props.showModal, (newVal) => {
  if(newVal) initData()
})
</script>