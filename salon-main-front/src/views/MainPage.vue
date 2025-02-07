<template>
  <div>
    MainPage
  </div>
  <div class="mt-10">
    <Button @click="logout">LogOut</Button>
  </div>
</template>

<script setup>
import { useUserStore } from '@/stores/userStore';
import { useRouter } from 'vue-router';
import { Button } from 'primevue';

const userStore = useUserStore()
const router = useRouter()
const logout = () => {
  userStore.setUserLogout()
  router.push({ name: 'IndexPage' }).catch(() => {
    console.log('error')
  })
  console.log(userStore.getUserRole)
  console.log(userStore.getCurrentUser)
}
</script>

<style lang="scss" scoped>

</style>