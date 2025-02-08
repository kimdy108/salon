<template>
  <div>
    CompanyDashboard
    <div>
      <Button @click="apiTest">Test</Button>
    </div>
  </div>
</template>

<script setup>
import ApiService from '@/services/ApiService';
import { Button } from 'primevue';

const apiTest = async () => {
  const reqHeader = { 'accept':'application/json', 'Content-Type': 'application/json' }
  const result = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: '/main/test/test'
  })
  console.log(result)
}
</script>

<style lang="scss" scoped>

</style>