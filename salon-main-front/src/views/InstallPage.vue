<template>
  <div class="min-h-screen transition-all duration-300 ease-in-out">
    <div class="w-full h-screen flex items-center justify-center">
      <div class="w-1/2 border rounded-lg shadow-lg">
        <div class="px-12 py-10 w-full text-gray-600">
          <SalonInput inputTitle="인증키" :isRequire="true" inputType="text" inputPlaceholder="0x000..." :isDisabled="false" :isPassword="false" v-model:inputValue="authKey" />
          <SalonInput inputTitle="아이디" :isRequire="true" inputType="text" inputPlaceholder="ID" :isDisabled="false" :isPassword="false" v-model:inputValue="adminID" />
          <SalonInput inputTitle="비밀번호" :isRequire="true" inputType="password" inputPlaceholder="Password" :isDisabled="false" :isPassword="true" v-model:inputValue="adminPassword" />
          <SalonInput inputTitle="이름" :isRequire="true" inputType="text" inputPlaceholder="Name" :isDisabled="false" :isPassword="false" v-model:inputValue="adminName" />
          <SalonInput inputTitle="전화번호" :isRequire="true" inputType="text" inputPlaceholder="Phone" :isDisabled="false" :isPassword="false" v-model:inputValue="adminPhone" />
          <SalonInput inputTitle="이메일" :isRequire="true" inputType="text" inputPlaceholder="Email" :isDisabled="false" :isPassword="false" v-model:inputValue="adminEmail" />
          <button class="w-full bg-sky-600 hover:bg-sky-700 py-5 rounded-lg focus:outline-none focus:shadow-outline transition-all duration-300 ease-in-out mt-3" @click="installSalon()">
            <span class="text-white font-bold text-md">설치</span>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { useRouter } from 'vue-router';
import { idReg, passwdReg, nameReg, phoneReg, emailReg } from "@/references/config";

import SalonInput from "@/components/element/SalonInput.vue";

import ApiService from '@/services/ApiService';
import AlertService from '@/services/AlertService'
import { encryptStringSalt } from "@/utils/common";

const router = useRouter()

const authKey = ref('')
const adminID = ref('')
const adminPassword = ref('')
const adminName = ref('')
const adminPhone = ref('')
const adminEmail = ref('')

onMounted(() => {
  authKey.value = process.env.VUE_APP_MODE === 'PROD' ? '' : '0x7C141E7bD107fF294788Acd6A9d5a9e334b46E27'
  adminID.value = process.env.VUE_APP_MODE === 'PROD' ? '' : 'master'
  adminPassword.value = process.env.VUE_APP_MODE === 'PROD' ? '' : 'rhksflwk1!'
  adminName.value = process.env.VUE_APP_MODE === 'PROD' ? '' : '최고관리자'
  adminPhone.value = process.env.VUE_APP_MODE === 'PROD' ? '' : '01012341234'
  adminEmail.value = process.env.VUE_APP_MODE === 'PROD' ? '' : 'master@salon.co.kr'
})

const installSalon = () => {
  if (authKey.value === '') {
    AlertService.normalAlertAction('인증키를 확인해주세요.', '설치입력확인', '확인', 'error')
    return;
  }
  if (!idReg.test(adminID.value)) {
    AlertService.normalAlertAction('아이디를 확인해주세요.', '설치입력확인', '확인', 'error')
    return;
  }
  if (!passwdReg.test(adminPassword.value)) {
    AlertService.normalAlertAction('비밀번호를 확인해주세요.', '설치입력확인', '확인', 'error')
    return;
  }
  if (!nameReg.test(adminName.value)) {
    AlertService.normalAlertAction('이름을 확인해주세요.', '설치입력확인', '확인', 'error')
    return;
  }
  if (!phoneReg.test(adminPhone.value)) {
    AlertService.normalAlertAction('전화번호를 확인해주세요.', '설치입력확인', '확인', 'error')
    return;
  }
  if (!emailReg.test(adminEmail.value)) {
    AlertService.normalAlertAction('이메일을 확인해주세요.', '설치입력확인', '확인', 'error')
    return;
  }
  installSalonFunction()
}

const installSalonFunction = async () => {
  const reqHeader = { 'accept':'application/json', 'Content-Type': 'application/json' }
  const reqData = {
    'authKey' : authKey.value,
    'adminID' : encryptStringSalt(adminID.value),
    'adminPassword' : encryptStringSalt(adminPassword.value),
    'adminName' : encryptStringSalt(adminName.value),
    'adminPhone' : encryptStringSalt(adminPhone.value),
    'adminEmail' : encryptStringSalt(adminEmail.value),
  }
  const installResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'POST',
    url: '/main/setting/install/salon',
    data: reqData
  })
  if (installResult.retStatus) {
    AlertService.normalAlertAction('Salon 설치 완료했습니다.', '설치성공', '확인', 'success')
    router.push({ name: 'IndexPage' }).catch(() => {
      console.log('indexerror')
    })
  } else {
    AlertService.normalAlertAction(installResult.retData, '설치실패', '확인', 'error')
  }
}
</script>

<style lang="scss" scoped>

</style>