<template>
  <div>
    <Dialog :visible="showModal" 
      modal 
      :closable="false" 
      :draggable="false"
      class="w-1/3"
    >
      <template #header>
        <div class="w-full flex justify-between items-center border-b border-gray-100 pb-6">
          <div class="text-xl text-gray-900">비밀번호 수정</div>
        </div>
      </template>

      <div class="grid">
        <SalonInput inputTitle="비밀번호" :isRequire="true" inputType="password" inputPlaceholder="" :isDisabled="false" :isPassword="true" v-model:inputValue="masterPassword" />
        <SalonInput inputTitle="비밀번호 확인" :isRequire="true" inputType="password" inputPlaceholder="" :isDisabled="false" :isPassword="true" v-model:inputValue="masterPasswordCheck" />
      </div>

      <template #footer>
        <div>
          <button class="text-gray-700 bg-white hover:bg-gray-100 border-gray-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="closeUpdatePasswordModal">닫기</button>
          <button class="text-teal-600 bg-teal-200 hover:bg-teal-300 border-teal-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="masterUpdatePassword">수정</button>
        </div>
      </template>
    </Dialog>

  </div>
</template>

<script setup>
import { Dialog } from 'primevue'
import { ref, watch, defineProps, defineEmits } from 'vue'
import { passwdReg } from '@/references/config'
import { encryptStringSalt } from '@/utils/common'

import SalonInput from '@/components/element/SalonInput.vue'

import ApiService from '@/services/ApiService'
import AlertService from '@/services/AlertService'

const props = defineProps(['showModal','updatePasswordGuid'])
const emit = defineEmits(['closeUpdatePasswordModal'])

const masterPassword = ref('')
const masterPasswordCheck = ref('')

const initData = () => {
  masterPassword.value = ''
  masterPasswordCheck.value = ''
}

const closeUpdatePasswordModal = () => {
  emit('closeUpdatePasswordModal')
}

const masterUpdatePassword = () => {
  if (masterPassword.value !== masterPasswordCheck.value) {
    AlertService.normalAlertAction('비밀번호가 일치하지 않습니다.', '사용자관리', '확인', 'error')
    return
  }
  if (!passwdReg.test(masterPassword.value)) {
    AlertService.normalAlertAction('비밀번호를 확인해주세요.', '사용자관리', '확인', 'error')
    return
  }

  masterUpdatePasswordAction()
}

const masterUpdatePasswordAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'masterGuid': props.updatePasswordGuid,
    'masterPassword': encryptStringSalt(masterPassword.value)
  }
  const updatePasswordResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'PUT',
    url: '/main/manage/master/update/password',
    data: reqData
  })
  if (updatePasswordResult.retStatus) {
    AlertService.normalAlertAction('수정했습니다.', '사용자관리', '확인', 'success')
    closeUpdatePasswordModal()
  } else AlertService.normalAlertAction(updatePasswordResult.retData, '사용자관리', '확인', 'error')
}

watch(() => props.showModal, (newVal) => {
    if (newVal) initData()
})
</script>