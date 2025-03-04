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
          <div class="text-xl text-gray-900">사용자 등록</div>
        </div>
      </template>

      <div class="w-full px-5">
        <div class="grid grid-cols-2 gap-6">
          <SalonInput inputTitle="아이디" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="false" :isPassword="false" v-model:inputValue="masterID" />
          <SalonInput inputTitle="이름" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="false" :isPassword="false" v-model:inputValue="masterName" />
          
          <SalonInput inputTitle="비밀번호" :isRequire="true" inputType="password" inputPlaceholder="" :isDisabled="false" :isPassword="true" v-model:inputValue="masterPassword" />

          <SalonInput inputTitle="전화번호" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="false" :isPassword="false" v-model:inputValue="masterPhone" />
          <SalonInput inputTitle="이메일" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="false" :isPassword="false" v-model:inputValue="masterEmail" />
        </div>

        <SalonTextArea inputTitle="비고" :isDisabled="false" :isRequire="false" inputPlaceholder="" inputHint="" v-model:inputValue="descriptionNote" ></SalonTextArea>

      </div>

      <template #footer>
        <div>
          <button class="text-gray-700 bg-white hover:bg-gray-100 border-gray-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="closeRegistModal">닫기</button>
          <button class="text-sky-600 bg-sky-200 hover:bg-sky-300 border-sky-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="masterRegist">등록</button>
        </div>
      </template>
    
    </Dialog>
  </div>
</template>

<script setup>
import { Dialog } from 'primevue';

import { defineProps, defineEmits, watch, ref } from 'vue';

import { encryptStringSalt } from '@/utils/common';
import { emailReg, idReg, passwdReg, nameReg, phoneReg } from '@/references/config';

import SalonInput from '@/components/element/SalonInput.vue'
import SalonTextArea from '@/components/element/SalonTextArea.vue'

import ApiService from '@/services/ApiService';
import AlertService from '@/services/AlertService';

const props = defineProps(['showModal'])
const emit = defineEmits(['closeRegistModal'])

const masterID = ref('')
const masterName = ref('')
const masterPassword = ref('')
const masterPhone = ref('')
const masterEmail = ref('')
const descriptionNote = ref('')

const initData = () => {
  masterID.value = ''
  masterName.value = ''
  masterPassword.value = ''
  masterPhone.value = ''
  masterEmail.value = ''
  descriptionNote.value = ''
}

const closeRegistModal = () => {
  emit('closeRegistModal')
}

const masterRegist = () => {
  const denyAdminID = ['master','salon']

  if (denyAdminID.indexOf(masterID.value) > -1) {
    AlertService.normalAlertAction('사용할 수 없는 아이디 입니다.', '사용자관리', '확인', 'error')
    return
  }
  if(!idReg.test(masterID.value)) {
    AlertService.normalAlertAction('아이디를 확인해주세요.', '사용자관리', '확인', 'error')
    return
  }
  if(!passwdReg.test(masterPassword.value)) {
    AlertService.normalAlertAction('비밀번호를 확인해주세요.', '사용자관리', '확인', 'error')
    return
  }
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
  masterRegistAction()
}

const masterRegistAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'masterID' : encryptStringSalt(masterID.value),
    'masterPassword' : encryptStringSalt(masterPassword.value),
    'masterName' : encryptStringSalt(masterName.value),
    'masterPhone' : encryptStringSalt(masterPhone.value),
    'masterEmail' : encryptStringSalt(masterEmail.value),
    'descriptionNote' : descriptionNote.value
  }
  const registResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'POST',
    url: '/main/manage/master/regist',
    data: reqData
  })
  if (registResult.retStatus) {
    AlertService.normalAlertAction('등록 했습니다.', '사용자관리', '확인', 'success')
    closeRegistModal()
  }
  else AlertService.normalAlertAction(registResult.retData, '사용자관리', '확인', 'error')
}

watch(() => props.showModal, (newVal) => {
  if(newVal) initData()
})
</script>