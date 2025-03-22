<template>
  <div class="w-full">
    <div class="text-left mb-5">
      <div class="text-xl font-bold text-gray-900">사용자 설정</div>
    </div>

    <div class="w-full bg-white rounded-lg shadow-lg px-8 py-4 mb-5">

      <div class="grid grid-cols-2 gap-6">
        <SalonInput inputTitle="아이디" :isRequire="false" inputType="text" inputPlaceholder="" :isDisabled="true" :isPassword="false" v-model:inputValue="adminID" />
        <SalonInput inputTitle="이름" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="false" :isPassword="false" v-model:inputValue="adminName" />
      </div>

      <SalonSelectBtn inputTitle="권한" :isDisabled="true" :isRequire="true" :options="userRoleOptios" optionLabel="name" optionValue="value" :multiple="false" v-model:inputValue="adminRole"></SalonSelectBtn>

      <div class="grid grid-cols-2 gap-6">
        <SalonInput inputTitle="전화번호" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="false" :isPassword="false" v-model:inputValue="adminPhone" />
        <SalonInput inputTitle="이메일" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="false" :isPassword="false" v-model:inputValue="adminEmail" />
      </div>
      
      <SalonTextArea inputTitle="비고" :isDisabled="false" :isRequire="false" inputPlaceholder="" inputHint="" v-model:inputValue="descriptionNote" ></SalonTextArea>

      <div class="mt-5 flex justify-end">
        <button class="text-emerald-600 bg-emerald-200 hover:bg-emerald-300 border-emerald-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="passwordUpdate">비밀번호 변경</button>
        <button class="text-orange-600 bg-orange-200 hover:bg-orange-300 border-orange-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="adminUpdate">수정</button>
      </div>  

    </div>

    <AdminUpdatePassword :showModal="isUpdatePasswordModal" :adminGuid="adminGuid" @closeUpdatePasswordModal="closeUpdatePasswordModal" ></AdminUpdatePassword>

  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue';
import { useUserStore } from '@/stores/userStore';
import { decryptStringSalt, encryptStringSalt } from '@/utils/common';
import { emailReg, nameReg, phoneReg, userRoleList } from '@/references/config';

import ApiService from '@/services/ApiService';
import AlertService from '@/services/AlertService';

import SalonInput from '@/components/element/SalonInput.vue';
import SalonTextArea from '@/components/element/SalonTextArea.vue';
import SalonSelectBtn from '@/components/element/SalonSelectBtn.vue';

import AdminUpdatePassword from './AdminUpdatePassword.vue';

onMounted(() => {
  initData()
})

const userStore = useUserStore()
const adminGuid = decryptStringSalt(userStore.getCurrentUser.ugd)

const adminRole = ref('')
const adminID = ref('')
const adminName = ref('')
const adminPhone = ref('')
const adminEmail = ref('')
const descriptionNote = ref('')

const isUpdatePasswordModal = ref(false)

const initData = () => {
  adminRole.value = ''
  adminID.value = ''
  adminName.value = ''
  adminPhone.value = ''
  adminEmail.value = ''
  descriptionNote.value = ''

  isUpdatePasswordModal.value = false

  getAdminInfo()
}

const userRoleOptios = computed(() => {
  let idx = userRoleList.findIndex(x => x.value == decryptStringSalt(userStore.getUserRole))
  return userRoleList.filter(x => x.key === idx)
})

const passwordUpdate = () => {
  isUpdatePasswordModal.value = true
}
const closeUpdatePasswordModal = () => {
  isUpdatePasswordModal.value = false
}

const adminUpdate = () => {
  if(!nameReg.test(adminName.value)) {
    AlertService.normalAlertAction('이름을 확인해주세요.', '사용자 설정', '확인', 'error')
    return
  }
  if(!phoneReg.test(adminPhone.value)) {
    AlertService.normalAlertAction('전화번호를 확인해주세요.', '사용자 설정', '확인', 'error')
    return
  }
  if(!emailReg.test(adminEmail.value)) {
    AlertService.normalAlertAction('이메일을 확인해주세요.', '사용자 설정', '확인', 'error')
    return
  }

  adminUpdateAction()
}

const getAdminInfo = async () => {
  const reqHeader = { accept: 'application/json' }
  const infoResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/main/setting/admin/info/${adminGuid}`
  })
  if (infoResult.retStatus) {
    adminRole.value = infoResult.retData.adminRole
    adminID.value = decryptStringSalt(infoResult.retData.adminID)
    adminName.value = decryptStringSalt(infoResult.retData.adminName)
    adminPhone.value = decryptStringSalt(infoResult.retData.adminPhone)
    adminEmail.value = decryptStringSalt(infoResult.retData.adminEmail)
    descriptionNote.value = infoResult.retData.descriptionNote
  }
}

const adminUpdateAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'adminGuid' : adminGuid,
    'adminName' : encryptStringSalt(adminName.value),
    'adminPhone' : encryptStringSalt(adminPhone.value),
    'adminEmail' : encryptStringSalt(adminEmail.value),
    'adminRole' : adminRole.value,
    'descriptionNote' : descriptionNote.value,
  }
  const updateResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'PUT',
    url: `/main/setting/admin/update`,
    data: reqData    
  })
  if (updateResult.retStatus) {
    AlertService.normalAlertAction('수정에 성공했습니다.', '사용자 설정', '확인', 'success')
    getAdminInfo()
  }
  else AlertService.normalAlertAction(updateResult.retData, '사용자 설정', '확인', 'error')
}
</script>

<style lang="scss" scoped>

</style>