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
          <div class="text-xl text-gray-900">스타일 수정</div>
        </div>
      </template>


      <div class="w-full px-5">
        <SalonInput v-if="decryptStringSalt(userStore.getUserRole) === 'MASTER'" inputTitle="고객사" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="true" :isPassword="false" v-model:inputValue="companyName" />
        <SalonInput inputTitle="담당자 이름" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="true" :isPassword="false" v-model:inputValue="userName" />

        <SalonInput inputTitle="스타일 이름" :isRequire="true" inputType="text" inputPlaceholder="" :isDisabled="false" :isPassword="false" v-model:inputValue="styleName" />
        <SalonTextArea inputTitle="스타일 설명" :isDisabled="false" :isRequire="false" inputPlaceholder="" inputHint="" v-model:inputValue="styleDetail" ></SalonTextArea>

        <div class="grid grid-cols-3 gap-4">
          <SalonInput inputTitle="소요시간" :isRequire="true" inputType="text" inputPlaceholder="30분 단위로 입력해주세요." :isDisabled="false" :isPassword="false" v-model:inputValue="styleDuration" />
          <SalonToggleSw inputTitle="중간 시간 여부" :isRequire="true" :isDisabled="styleDuration >= 90 ? false : true" trueValue="YES" falseValue="NO" trueString="중간 시간 사용" falseString="중간 시간 사용 안함"  v-model:inputValue="isMiddleTime"></SalonToggleSw>
          <SalonSelectBtn v-if="isMiddleTime === 'YES'" inputTitle="중간 시간" :isDisabled="isMiddleTime === 'YES' ? false : true" :isRequire="true" :options="middleTimeOptions" optionLabel="value" optionValue="value" :multiple="true" v-model:inputValue="middleTime"></SalonSelectBtn>
        </div>
        
        <SalonTextArea inputTitle="비고" :isDisabled="false" :isRequire="false" inputPlaceholder="" inputHint="" v-model:inputValue="descriptionNote" ></SalonTextArea>

      </div>
  
  
      <template #footer>
        <div>
          <button class="text-gray-700 bg-white hover:bg-gray-100 border-gray-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="closeUpdateModal">닫기</button>
          <button class="text-orange-600 bg-orange-200 hover:bg-orange-300 border-orange-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="styleUpdate">수정</button>
        </div>
      </template>
  
    </Dialog>
  </div>
</template>

<script setup>
import { Dialog } from 'primevue';

import { defineProps, defineEmits, watch, ref, computed } from 'vue';
import { useUserStore } from '@/stores/userStore';
import { decryptStringSalt } from '@/utils/common';

import SalonInput from '@/components/element/SalonInput.vue';
import SalonToggleSw from '@/components/element/SalonToggleSw.vue';
import SalonSelectBtn from '@/components/element/SalonSelectBtn.vue';
import SalonTextArea from '@/components/element/SalonTextArea.vue';

import ApiService from '@/services/ApiService';
import AlertService from '@/services/AlertService';

const props = defineProps(['showModal','updateGuid'])
const emit = defineEmits(['closeUpdateModal'])

const userStore = useUserStore()

const closeUpdateModal = () => {
  emit('closeUpdateModal')
}

const styleGuid = ref('')
const companyName = ref('')
const userName = ref('')
const styleName = ref('')
const styleDetail = ref('')
const styleDuration = ref('')
const isMiddleTime = ref('NO')
const middleTime = ref('')
const descriptionNote = ref('')

const initData = () => {
  styleGuid.value = ''
  companyName.value = ''
  userName.value = ''
  styleName.value = ''
  styleDetail.value = ''
  styleDuration.value = ''
  isMiddleTime.value = 'NO'
  middleTime.value = ''
  descriptionNote.value = ''

  getStyleInfo()
}

const middleTimeOptions = computed(() => {
  const durationCount = styleDuration.value / 30
  const options = []
  for (let i = 1; i <= durationCount; i++) {
    options.push({ key: i, value: `${30 * i}` })
  }
  return options
})

const styleUpdate = () => {
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

  styleUpdateAction()
}

const styleUpdateAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'styleGuid' : styleGuid.value,
    'styleName' : styleName.value,
    'styleDetail' : styleDetail.value,
    'styleDuration' : styleDuration.value,
    'isMiddleTime' : isMiddleTime.value,
    'middleTime' : isMiddleTime.value === 'YES' ? middleTime.value : [],
    'descriptionNote' : descriptionNote.value
  }
  const updateResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'PUT',
    url: '/main/setting/style/update',
    data: reqData
  })
  if (updateResult.retStatus) {
    AlertService.normalAlertAction('등록 했습니다.', '스타일관리', '확인', 'success')
    closeUpdateModal()
  }
  else AlertService.normalAlertAction(updateResult.retData, '스타일관리', '확인', 'error')
}

const getStyleInfo = async () => {
  const reqHeader = { accept: 'application/json' }
  const infoResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/main/setting/style/info/${props.updateGuid}`
  })
  if (infoResult.retStatus) {
    console.log(infoResult)
    styleGuid.value = infoResult.retData.styleGuid,
    companyName.value = infoResult.retData.companyName
    userName.value = decryptStringSalt(infoResult.retData.userName)
    styleName.value = infoResult.retData.styleName
    styleDetail.value = infoResult.retData.styleDetail
    styleDuration.value = infoResult.retData.styleDuration
    isMiddleTime.value = infoResult.retData.isMiddleTime
    middleTime.value = infoResult.retData.middleTime.map(String)
    descriptionNote.value = infoResult.retData.descriptionNote
  }
}

watch(() => props.showModal, (newVal) => {
  if(newVal) initData()
})
</script>

<style lang="scss" scoped>

</style>