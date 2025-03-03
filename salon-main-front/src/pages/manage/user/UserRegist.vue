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
        
        <SalonSelect selectTitle="고객사" selectPlaceholder="고객사" :isRequire="true" :options="companyList" optionTitle="companyName" optionSub="managerName" optionValue="companyGuid" v-model:inputValue="companyGuid"></SalonSelect>

        <div class="grid grid-cols-2 gap-6">
          <SalonInput inputTitle="아이디" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="false" :isPassword="false" v-model:inputValue="userID" />
          <SalonInput inputTitle="이름" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="false" :isPassword="false" v-model:inputValue="userName" />
          
          <SalonInput inputTitle="비밀번호" :isRequire="true" inputType="password" inputPlaceholder="" :isDisabled="false" :isPassword="true" v-model:inputValue="userPassword" />
          <SalonSelectBtn inputTitle="권한" :isDisabled="false" :isRequire="true" :options="userRoleOptios" optionLabel="name" optionValue="value" :multiple="false" v-model:inputValue="userRole"></SalonSelectBtn>

          <SalonInput inputTitle="전화번호" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="false" :isPassword="false" v-model:inputValue="userPhone" />
          <SalonInput inputTitle="이메일" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="false" :isPassword="false" v-model:inputValue="userEmail" />
        </div>

        <SalonTextArea inputTitle="비고" :isDisabled="false" :isRequire="false" inputPlaceholder="" inputHint="" v-model:inputValue="descriptionNote" ></SalonTextArea>

      </div>

      <template #footer>
        <div>
          <button class="text-gray-700 bg-white hover:bg-gray-100 border-gray-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="closeRegistModal">닫기</button>
          <button class="text-sky-600 bg-sky-200 hover:bg-sky-300 border-sky-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="userRegist">등록</button>
        </div>
      </template>
    
    </Dialog>
  </div>
</template>

<script setup>
import { Dialog } from 'primevue';

import { defineProps, defineEmits, watch, ref, computed } from 'vue';

import { useUserStore } from '@/stores/userStore';
import { decryptStringSalt, encryptStringSalt } from '@/utils/common';
import { emailReg, idReg, passwdReg, nameReg, phoneReg, userRoleList } from '@/references/config';

import SalonSelect from '@/components/element/SalonSelect.vue'
import SalonInput from '@/components/element/SalonInput.vue'
import SalonTextArea from '@/components/element/SalonTextArea.vue'
import SalonSelectBtn from '@/components/element/SalonSelectBtn.vue'

import ApiService from '@/services/ApiService';
import AlertService from '@/services/AlertService';

const props = defineProps(['showModal'])
const emit = defineEmits(['closeRegistModal'])

const userStore = useUserStore()

const userRoleOptios = computed(() => {
  let idx = userRoleList.findIndex(x => x.value == decryptStringSalt(userStore.getUserRole))
  return userRoleList.filter(x => x.key > idx)
})

const companyList = ref([])
const companyGuid = ref('')
const userID = ref('')
const userName = ref('')
const userPassword = ref('')
const userRole = ref('')
const userPhone = ref('')
const userEmail = ref('')
const descriptionNote = ref('')

const initData = () => {
  companyList.value = []
  companyGuid.value = ''
  userID.value = ''
  userName.value = ''
  userPassword.value = ''
  userRole.value = 'MEMBER'
  userPhone.value = ''
  userEmail.value = ''
  descriptionNote.value = ''
  getCompanyList()
}

const closeRegistModal = () => {
  emit('closeRegistModal')
}

const userRegist = () => {
  const denyAdminID = ['master','salon']

  if (denyAdminID.indexOf(userID.value) > -1) {
    AlertService.normalAlertAction('사용할 수 없는 아이디 입니다.', '사용자관리', '확인', 'error')
    return
  }
  if(!idReg.test(userID.value)) {
    AlertService.normalAlertAction('아이디를 확인해주세요.', '사용자관리', '확인', 'error')
    return
  }
  if(!passwdReg.test(userPassword.value)) {
    AlertService.normalAlertAction('비밀번호를 확인해주세요.', '사용자관리', '확인', 'error')
    return
  }
  if(!nameReg.test(userName.value)) {
    AlertService.normalAlertAction('이름을 확인해주세요.', '사용자관리', '확인', 'error')
    return
  }
  if(!phoneReg.test(userPhone.value)) {
    AlertService.normalAlertAction('전화번호를 확인해주세요.', '사용자관리', '확인', 'error')
    return
  }
  if(!emailReg.test(userEmail.value)) {
    AlertService.normalAlertAction('이메일을 확인해줏요.', '사용자관리', '확인', 'error')
    return
  }
  userRegistAction()
}

const userRegistAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'userID' : encryptStringSalt(userID.value),
    'userPassword' : encryptStringSalt(userPassword.value),
    'companyGuid' : companyGuid.value,
    'userName' : encryptStringSalt(userName.value),
    'userPhone' : encryptStringSalt(userPhone.value),
    'userEmail' : encryptStringSalt(userEmail.value),
    'userRole' : userRole.value,
    'descriptionNote' : descriptionNote.value
  }
  const registResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'POST',
    url: '/main/manage/user/regist',
    data: reqData
  })
  if (registResult.retStatus) {
    AlertService.normalAlertAction('등록 했습니다.', '사용자관리', '확인', 'success')
    closeRegistModal()
  }
  else AlertService.normalAlertAction(registResult.retData, '사용자관리', '확인', 'error')
}

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

watch(() => props.showModal, (newVal) => {
  if(newVal) initData()
})
</script>