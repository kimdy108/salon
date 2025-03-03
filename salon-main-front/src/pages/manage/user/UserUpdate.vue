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
          <div class="text-xl text-gray-900">사용자 수정</div>
        </div>
      </template>

      <div class="w-full px-5">

        <div class="grid grid-cols-2 gap-6">
          <SalonInput inputTitle="고객사" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="true" :isPassword="false" v-model:inputValue="companyName" />
          <SalonInput inputTitle="아이디" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="true" :isPassword="false" v-model:inputValue="userID" />

          <SalonInput inputTitle="이름" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="false" :isPassword="false" v-model:inputValue="userName" />
          <SalonSelectBtn inputTitle="권한" :isDisabled="false" :isRequire="true" :options="userRoleOptios" optionLabel="name" optionValue="value" :multiple="false" v-model:inputValue="userRole"></SalonSelectBtn>

          <SalonInput inputTitle="전화번호" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="false" :isPassword="false" v-model:inputValue="userPhone" />
          <SalonInput inputTitle="이메일" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="false" :isPassword="false" v-model:inputValue="userEmail" />
        </div>

        <SalonTextArea inputTitle="비고" :isDisabled="false" :isRequire="false" inputPlaceholder="" inputHint="" v-model:inputValue="descriptionNote" ></SalonTextArea>

      </div>

      <template #footer>
        <div>
          <button class="text-gray-700 bg-white hover:bg-gray-100 border-gray-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="closeUpdateModal">닫기</button>
          <button class="text-orange-600 bg-orange-200 hover:bg-orange-300 border-orange-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="userUpdate">수정</button>
        </div>
      </template>
    </Dialog>

  </div>
</template>

<script setup>
import { Dialog } from 'primevue';

import { useUserStore } from '@/stores/userStore'
import { defineProps, defineEmits, watch, ref, computed } from 'vue';
import { nameReg, phoneReg, emailReg, userRoleList } from '@/references/config';
import { decryptStringSalt, encryptStringSalt } from '@/utils/common'

import SalonInput from '@/components/element/SalonInput.vue'
import SalonTextArea from '@/components/element/SalonTextArea.vue'
import SalonSelectBtn from '@/components/element/SalonSelectBtn.vue'

import ApiService from '@/services/ApiService';
import AlertService from '@/services/AlertService';

const props = defineProps(['showModal','updateGuid'])
const emit = defineEmits(['closeUpdateModal'])

const userStore = useUserStore()

const userRoleOptios = computed(() => {
  let idx = userRoleList.findIndex(x => x.value == decryptStringSalt(userStore.getUserRole))
  return userRoleList.filter(x => x.key > idx)
})

const companyName = ref('')
const userGuid = ref('')
const userID = ref('')
const userName = ref('')
const userRole = ref('MEMBER')
const userPhone = ref('')
const userEmail = ref('')
const descriptionNote = ref('')

const initData = () => {
  companyName.value = ''
  userGuid.value = ''
  userID.value = ''
  userName.value = ''
  userRole.value = 'MEMBER'
  userPhone.value = ''
  userEmail.value = ''
  descriptionNote.value = ''
  getUserInfo()
}

const closeUpdateModal = () => {
  emit('closeUpdateModal')
}

const userUpdate = () => {
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

  userUpdateAction()
}

const getUserInfo = async () => {
  const reqHeader = { accept: 'application/json' }
  const infoResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/main/manage/user/info/${props.updateGuid}`
  })
  if (infoResult.retStatus) {
    companyName.value = infoResult.retData.companyName
    userGuid.value = infoResult.retData.userGuid
    userID.value = decryptStringSalt(infoResult.retData.userID)
    userName.value = decryptStringSalt(infoResult.retData.userName)
    userRole.value = infoResult.retData.userRole
    userPhone.value = decryptStringSalt(infoResult.retData.userPhone)
    userEmail.value = decryptStringSalt(infoResult.retData.userEmail)
    descriptionNote.value = infoResult.retData.descriptionNote
  }
}

const userUpdateAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'userGuid' : userGuid.value,
    'userName' : encryptStringSalt(userName.value),
    'userPhone' : encryptStringSalt(userPhone.value),
    'userEmail' : encryptStringSalt(userEmail.value),
    'userRole' : userRole.value,
    'descriptionNote' : descriptionNote.value
  }
  const updateResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'PUT',
    url: '/main/manage/user/update',
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