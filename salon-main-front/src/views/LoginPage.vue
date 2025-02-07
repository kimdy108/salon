<template>
  <div class="min-h-screen transition-all duration-300 ease-in-out login-wrapper">
    <div class="w-full h-screen flex items-center justify-center">
      <div class="w-1/4 bg-white border rounded-lg shadow-lg">
        <div class="flex items-center justify-center mt-10">
          <img class="max-h-10" :src="require('@/assets/images/logo.png')" alt="logo" @dblclick="changeIsMaster()" />
        </div>
        <div class="px-12 py-5 w-full text-gray-600">
          <SalonInput v-if="!isMaster" inputTitle="" :isRequire="false" inputType="text" inputPlaceholder="사업자번호" :isDisabled="false" :isPassword="false" v-model:inputValue="companyNumber" @keyup.enter="userLogin()" />
          <SalonInput inputTitle="" :isRequire="false" inputType="text" inputPlaceholder="ID" :isDisabled="false" :isPassword="false" v-model:inputValue="userID" @keyup.enter="userLogin()" />
          <SalonInput inputTitle="" :isRequire="false" inputType="password" inputPlaceholder="Password" :isDisabled="false" :isPassword="false" v-model:inputValue="userPassword" @keyup.enter="userLogin()" />
          <button class="w-full bg-sky-500 hover:bg-sky-600 py-5 rounded-lg focus:outline-none focus:shadow-outline transition-all duration-300 ease-in-out mt-3" @click="userLogin()">
            <span class="text-white font-bold text-md">로그인</span>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import SalonInput from "@/components/element/SalonInput.vue";
import AlertService from "@/services/AlertService";
import AuthService from "@/services/AuthService";
import { decryptString, encryptStringSalt } from "@/utils/common";
import { useUserStore } from "@/stores/userStore";
import { useRouter } from "vue-router";
import { ref } from "vue";
import { divisionChar } from "@/references/config";

const userStore = useUserStore()
const router = useRouter()

const isMaster = ref(false)
const companyNumber = ref('')
const userID = ref('')
const userPassword = ref('')

const changeIsMaster = () => {
  isMaster.value = !isMaster.value
}

const userLogin = () => {
  if (isMaster.value) {
    if (userID.value === '' || userPassword.value === '') {
      AlertService.normalAlertAction('아이디 또는 비밀번호를 확인해주세요.', '로그인실패', '확인', 'error')
      return
    }
  } else {
    if (userID.value === '' || userPassword.value === '' || companyNumber.value === '') {
      AlertService.normalAlertAction('아이디 또는 비밀번호를 확인해주세요.', '로그인실패', '확인', 'error')
      return
    }
  }
  userLoginAction()
}

const userLoginAction = async () => {
  const reqData = {
    companyNumber: isMaster.value ? encryptStringSalt('0') : encryptStringSalt(companyNumber.value),
    userID: encryptStringSalt(userID.value),
    userPassword: encryptStringSalt(userPassword.value)
  }
  const loginResult = await AuthService.requestLogin({
    data: reqData
  })
  if (loginResult.retStatus) {
    const userInfo = decryptString(loginResult.retData.user).split(divisionChar)
    userStore.setCurrentUser({
      at: loginResult.retData.at,
      rt: loginResult.retData.rt,
      uid: encryptStringSalt(userID.value),
      unm: encryptStringSalt(userInfo[0]),
      ugd: encryptStringSalt(userInfo[1]),
      uro: encryptStringSalt(userInfo[2]),
      ucg: encryptStringSalt(userInfo[3]),
      sgd: encryptStringSalt(userInfo[4]),
    })
    userStore.setUserRole(encryptStringSalt(userInfo[2]))
    router.push({ name: 'IndexPage' }).catch(() => {
      console.log('indexpage')
    })
    console.log(userStore.getUserRole)
    console.log(userStore.getCurrentUser)
  } else {
    console.log('error: ', loginResult)
    AlertService.normalAlertAction('로그인에 실패했습니다.', '로그인실패', '확인', 'error')
  }
}
</script>

<style lang="scss">
.login-wrapper {
  background-image: url('@/assets/images/bg.png')
}
</style>