<template>
  <div>
    loading
  </div>
</template>

<script setup>
import { useUserStore } from '@/stores/userStore'
import { computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import ApiService from '@/services/ApiService'

const router = useRouter()
const userStore = useUserStore()

const currentUser = computed(() => userStore.getCurrentUser)

let isInstallSystem = false

onMounted (async () => {
  isInstallSystem = await getInstallCheck()

  if (!isInstallSystem) {
    router.push({ name: 'InstallPage' }).catch(() => {
      console.log('installerror')
    })
    return
  }

  if (currentUser.value.at) {
    setTimeout(() => {
      router.push({ name: 'MainPage' }).catch(() => {
        console.log('mainerror')
      })
    }, 2000)
  }
  else {
    setTimeout(() => {
      router.push({ name: 'LoginPage' }).catch(() => {
        console.log('loginerror')
      })
    }, 2000)
  }
})

const getInstallCheck = async () => {
  const reqHeader = { accept: 'application/json' }
  const checkResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/main/setting/install/check`,
  })
  let returnResult = false
  if (checkResult.retStatus) {
    returnResult = checkResult.retStatus
  }
  return returnResult
}
</script>
