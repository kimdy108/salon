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
          <div class="text-xl text-gray-900">근태 등록</div>
        </div>
      </template>

      <div class="w-full px-5">
        <SalonSelect v-if="decryptStringSalt(userStore.getUserRole) === 'MASTER'" selectTitle="고객사" selectPlaceholder="고객사" :isRequire="true" :options="companyList" optionTitle="companyName" optionSub="managerName" optionValue="companyGuid" v-model:inputValue="companyGuid"></SalonSelect>
        <SalonSelect selectTitle="담당자" selectPlaceholder="담당자" :isRequire="true" :options="userList" optionTitle="userName" optionSub="companyName" optionValue="userGuid" v-model:inputValue="userGuid"></SalonSelect>
        <SalonSelectBtn inputTitle="근태 종류" :isDisabled="false" :isRequire="true" :options="employmentOptions" optionLabel="value" optionValue="key" :multiple="false" v-model:inputValue="employmentOption"></SalonSelectBtn>
        <SalonTextArea inputTitle="비고" :isDisabled="false" :isRequire="false" inputPlaceholder="" inputHint="" v-model:inputValue="descriptionNote" ></SalonTextArea>
      </div>

      <template #footer>
        <div>
          <button class="text-gray-700 bg-white hover:bg-gray-100 border-gray-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="closeRegistModal">닫기</button>
          <button class="text-sky-600 bg-sky-200 hover:bg-sky-300 border-sky-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="employmentRegist">등록</button>
        </div>
      </template>

    </Dialog>
  </div>
</template>

<script setup>
import { defineProps, defineEmits, watch, ref } from 'vue'
import { Dialog } from 'primevue'
import { useUserStore } from '@/stores/userStore'
import { decryptStringSalt } from '@/utils/common'

import ApiService from '@/services/ApiService'
import AlertService from '@/services/AlertService'

import SalonSelect from '@/components/element/SalonSelect.vue'
import SalonSelectBtn from '@/components/element/SalonSelectBtn.vue'
import SalonTextArea from '@/components/element/SalonTextArea.vue'

const userStore = useUserStore()

const props = defineProps(['showModal', 'targetDate'])
const emit = defineEmits(['closeRegistModal'])

const closeRegistModal = () => {
  emit('closeRegistModal')
}

const companyList = ref([])
const companyGuid = ref('')
const userList = ref([])
const userGuid = ref('')
const employmentOption = ref('')
const descriptionNote = ref('')

const initData = () => {
  companyList.value = []
  companyGuid.value = ''
  userList.value = []
  userGuid.value = ''
  employmentOption.value = ''
  descriptionNote.value = ''

  getCompanyList()
}

const employmentOptions = [
    { key : 'EMPOLYMENT', value : '근무'},
    { key : 'HOLIDAY', value : '휴무'},
    { key : 'VACATION', value : '연차'},
    { key : 'SICKLEAVE', value : '병가'},
    { key : 'OFFICIALVACATION', value : '공가'}
]

const employmentRegist = () => {
    employmentRegistAction()
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

const getUserList = async (companyGuid) => {
  const reqHeader = { accept: 'application/json' }
  const userListResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/main/manage/user/list/${companyGuid}`
  })
  if (userListResult.retStatus) {
    userList.value = userListResult.retData
    for (let i = 0; i < userList.value.length; i++) {
      userList.value[i].userName = decryptStringSalt(userList.value[i].userName)
    }
  }
}

const employmentRegistAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'companyGuid': companyGuid.value,
    'userGuid': userGuid.value,
    'employmentDate': props.targetDate,
    'employmentCategory': employmentOption.value,
    'descriptionNote': descriptionNote.value
  }
  const registResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'POST',
    url: '/main/schedule/employment/regist',
    data: reqData
  })
  if (registResult.retStatus) {
    AlertService.normalAlertAction('등록 했습니다.', '근태관리', '확인', 'success')
    closeRegistModal()
  }
  else AlertService.normalAlertAction(registResult.retData, '근태관리', '확인', 'error')
}

watch(() => props.showModal, (newVal) => {
  if(newVal) {
    initData()

    if (decryptStringSalt(userStore.getUserRole) !== 'MASTER') {
      companyGuid.value = decryptStringSalt(userStore.getCurrentUser.ucg)
      getUserList(companyGuid.value)
    }
  }
})
watch (() => companyGuid.value, (newVal) => {
  if (companyGuid.value !== '') getUserList(newVal)
})
</script>
