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
        <SalonInput inputTitle="이전 비밀번호" :isRequire="true" inputType="password" inputPlaceholder="" :isDisabled="false" :isPassword="true" v-model:inputValue="oldPassword" />
        <SalonInput inputTitle="새로운 비밀번호" :isRequire="true" inputType="password" inputPlaceholder="" :isDisabled="false" :isPassword="true" v-model:inputValue="newPassword" />
        <SalonInput inputTitle="새로운 비밀번호 확인" :isRequire="true" inputType="password" inputPlaceholder="" :isDisabled="false" :isPassword="true" v-model:inputValue="newPasswordCheck" />
      </div>

      <template #footer>
        <div>
          <button class="text-gray-700 bg-white hover:bg-gray-100 border-gray-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="closeUpdatePasswordModal">닫기</button>
          <button class="text-teal-600 bg-teal-200 hover:bg-teal-300 border-teal-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="adminUpdatePassword">수정</button>
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

const props = defineProps(['showModal','adminGuid'])
const emit = defineEmits(['closeUpdatePasswordModal'])

const oldPassword = ref('')
const newPassword = ref('')
const newPasswordCheck = ref('')

const initData = () => {
  newPassword.value = ''
  newPasswordCheck.value = ''
}

const closeUpdatePasswordModal = () => {
  emit('closeUpdatePasswordModal')
}

const adminUpdatePassword = () => {
  if (newPassword.value !== newPasswordCheck.value) {
    AlertService.normalAlertAction('새로운 비밀번호가 일치하지 않습니다.', '사용자관리', '확인', 'error')
    return
  }
  if (!passwdReg.test(newPassword.value)) {
    AlertService.normalAlertAction('비밀번호를 확인해주세요.', '사용자관리', '확인', 'error')
    return
  }

  adminUpdatePasswordAction()
}

const adminUpdatePasswordAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'adminGuid' : props.adminGuid,
    'oldAdminPassword' : encryptStringSalt(oldPassword.value),
    'newAdminPassword' : encryptStringSalt(newPassword.value)
  }
  const updateResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'PUT',
    url: `/main/setting/admin/update/password`,
    data: reqData
  })
  if (updateResult.retStatus) {
    AlertService.normalAlertAction('비밀번호 변경에 성공했습니다.', '사용자 설정', '확인', 'success')
    closeUpdatePasswordModal()
  } else AlertService.normalAlertAction(updateResult.retData, '사용자 설정', '확인', 'error')
}

watch(() => props.showModal, (newVal) => {
    if (newVal) initData()
})
</script>

<style lang="scss" scoped>

</style>