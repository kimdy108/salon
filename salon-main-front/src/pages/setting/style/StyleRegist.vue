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
          <div class="text-xl text-gray-900">스타일 등록</div>
        </div>
      </template>

      <div class="w-full px-5">
        <SalonSelect v-if="decryptStringSalt(userStore.getUserRole) === 'MASTER'" selectTitle="고객사" selectPlaceholder="고객사" :isRequire="true" :options="companyList" optionTitle="companyName" optionSub="managerName" optionValue="companyGuid" v-model:inputValue="companyGuid"></SalonSelect>
        <SalonSelect selectTitle="담당자" selectPlaceholder="담당자" :isRequire="true" :options="userList" optionTitle="userName" optionSub="companyName" optionValue="userGuid" v-model:inputValue="userGuid"></SalonSelect>

        <SalonInput inputTitle="스타일 이름" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="false" :isPassword="false" v-model:inputValue="styleName" />
        <SalonTextArea inputTitle="스타일 설명" :isDisabled="false" :isRequire="false" inputPlaceholder="" inputHint="" v-model:inputValue="styleDetail" ></SalonTextArea>

        <div class="grid grid-cols-3 gap-4">
          <SalonInput inputTitle="소요시간" :isRequire="true" inputType="text" inputPlaceholder="30분 단위로 입력해주세요." :isDisabled="false" :isPassword="false" v-model:inputValue="styleDuration" />
          <SalonToggleSw inputTitle="중간 시간 여부" :isRequire="true" :isDisabled="styleDuration >= 90 ? false : true" trueValue="YES" falseValue="NO" trueString="중간 시간 사용" falseString="중간 시간 사용 안함"  v-model:inputValue="isMiddleTime"></SalonToggleSw>
          <SalonSelectBtn v-if="isMiddleTime === 'YES'" inputTitle="중간 시간" :isDisabled="false" :isRequire="true" :options="middleTimeOptions" optionLabel="value" optionValue="value" :multiple="true" v-model:inputValue="middleTime"></SalonSelectBtn>
        </div>
        
        <SalonTextArea inputTitle="비고" :isDisabled="false" :isRequire="false" inputPlaceholder="" inputHint="" v-model:inputValue="descriptionNote" ></SalonTextArea>

      </div>
  
  
      <template #footer>
        <div>
          <button class="text-gray-700 bg-white hover:bg-gray-100 border-gray-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="closeRegistModal">닫기</button>
          <button class="text-sky-600 bg-sky-200 hover:bg-sky-300 border-sky-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="styleRegist">등록</button>
        </div>
      </template>
  
    </Dialog>

  </div>
</template>

<script setup>
import { defineProps, defineEmits, ref, watch, computed } from 'vue'
import { Dialog } from 'primevue'
import { decryptStringSalt } from '@/utils/common'
import { useUserStore } from '@/stores/userStore'

import SalonSelect from '@/components/element/SalonSelect.vue'
import SalonInput from '@/components/element/SalonInput.vue'
import SalonTextArea from '@/components/element/SalonTextArea.vue'
import SalonToggleSw from '@/components/element/SalonToggleSw.vue'
import SalonSelectBtn from '@/components/element/SalonSelectBtn.vue'

import ApiService from '@/services/ApiService'
import AlertService from '@/services/AlertService'

const userStore = useUserStore()

const props = defineProps(['showModal'])
const emit = defineEmits(['closeRegistModal'])

const closeRegistModal = () => {
  emit('closeRegistModal')
}

const companyList = ref([])
const companyGuid = ref('')
const userList = ref([])
const userGuid = ref('')
const styleName = ref('')
const styleDetail = ref('')
const styleDuration = ref('')
const isMiddleTime = ref('NO')
const middleTime = ref([])
const descriptionNote = ref('')

const initData = () => {
  companyList.value = []
  companyGuid.value = ''
  userList.value = []
  userGuid.value = ''
  styleName.value = ''
  styleDetail.value = ''
  styleDuration.value = ''
  isMiddleTime.value = 'NO'
  middleTime.value = []
  descriptionNote.value = ''

  getCompanyList()
}

const styleRegist = () => {
  if (companyGuid.value === '') {
    AlertService.normalAlertAction('고객사를 확인해주세요.', '스타일', '확인', 'error')
    return
  }
  if (userGuid.value === '') {
    AlertService.normalAlertAction('담당자를 확인해주세요.', '스타일', '확인', 'error')
    return
  }
  if (styleName.value === '') {
    AlertService.normalAlertAction('스타일 이름을 확인해주세요.', '스타일', '확인', 'error')
    return
  }
  if (styleDetail.value === '') {
    AlertService.normalAlertAction('스타일 설명을 확인해주세요.', '스타일', '확인', 'error')
    return
  }
  if (styleDuration.value === '' || styleDuration.value === '0' || styleDuration.value % 30 !== 0) {
    AlertService.normalAlertAction('소요시간을 확인해주세요.', '스타일', '확인', 'error')
    return
  }
  if (isMiddleTime.value === 'YES' && (middleTime.value.length === 0 || (middleTime.value.includes(middleTimeOptions.value[0].value) || middleTime.value.includes(middleTimeOptions.value[middleTimeOptions.value.length - 1].value)))) {
    AlertService.normalAlertAction('중간 시간을 확인해주세요.', '스타일', '확인', 'error')
    return
  }

  styleRegistAction()
}

const middleTimeOptions = computed(() => {
  const durationCount = styleDuration.value / 30
  const options = []
  for (let i = 1; i <= durationCount; i++) {
    options.push({ key: i, value: `${30 * i}` })
  }
  return options
})

const styleRegistAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'styleName' : styleName.value,
    'adminGuid' : userGuid.value,
    'styleDetail' : styleDetail.value,
    'styleDuration' : styleDuration.value,
    'isMiddleTime' : isMiddleTime.value,
    'middleTime' : isMiddleTime.value === 'YES' ? middleTime.value : [],
    'descriptionNote' : descriptionNote.value
  }
  const registResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'POST',
    url: '/main/setting/style/regist',
    data: reqData
  })
  if (registResult.retStatus) {
    AlertService.normalAlertAction('등록 했습니다.', '스타일관리', '확인', 'success')
    closeRegistModal()
  }
  else AlertService.normalAlertAction(registResult.retData, '스타일관리', '확인', 'error')
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

<style lang="scss" scoped>

</style>