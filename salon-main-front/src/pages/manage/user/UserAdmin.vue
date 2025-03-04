<template>
  <div>
    <div class="card" v-if="userRole == 'MASTER'">
      <Tabs value="0">
        <TabList class="shadow-sm">
          <Tab value="0" @click="selectTab(0)">사용자 관리</Tab>
          <Tab value="1" @click="selectTab(1)">마스터 관리</Tab>
        </TabList>
        <TabPanels>
          <TabPanel value="0">
            <div class="pt-3">
              <UserList :selectedTab="selectedTab" ></UserList>
            </div>
          </TabPanel>

          <TabPanel value="1">
            <div class="pt-3">
              <MasterList :selectedTab="selectedTab" ></MasterList>
            </div>
          </TabPanel>
        </TabPanels>
      </Tabs>
    </div>

    <div v-else>
      <UserList :selectedTab="selectedTab" ></UserList>
    </div>

  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useUserStore } from '@/stores/userStore'

import { Tabs } from 'primevue'
import { TabList } from 'primevue'
import { Tab } from 'primevue'
import { TabPanels } from 'primevue'
import { TabPanel } from 'primevue'

import UserList from './UserList.vue'
import MasterList from './MasterList.vue'
import { decryptStringSalt } from '@/utils/common'

onMounted(() => {
  userRole.value = decryptStringSalt(userStore.getUserRole)
})

const userStore = useUserStore()

const userRole = ref('MEMBER')
const selectedTab = ref(0)

const selectTab = (selectedNum) => {
  if(selectedTab.value!=selectedNum) {
    selectedTab.value = selectedNum
  }
}
</script>

<style lang="scss" scoped>

</style>