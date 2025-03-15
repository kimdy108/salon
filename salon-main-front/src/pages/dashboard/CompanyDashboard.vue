<template>
  <div>
    CompanyDashboard
  </div>
</template>

<script setup>
import { decryptStringSalt } from '@/utils/common';
import { useUserStore } from '@/stores/userStore';
import { onMounted } from 'vue';
import { useRouter } from 'vue-router'

onMounted(() => {
  if (decryptStringSalt(userStore.getUserRole) !== 'MASTER') {
    router.push({ name: 'ScheduleDashboard' }).catch(() => {
      console.log('ScheduleDashboardError')
    })
  }
})

const router = useRouter()
const userStore = useUserStore()
</script>

<style lang="scss" scoped>

</style>