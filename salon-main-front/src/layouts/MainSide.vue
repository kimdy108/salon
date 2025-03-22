<template>
  <div class="flex min-h-screen bg-gradient-to-br from-gray-50 to-gray-100">
    <div class="hidden lg:block w-64 bg-white shadow-lg transition-all duration-300 ease-in-out">
      <div class="flex flex-col h-full p-6">
        <h1 class="salon-main-logo text-3xl font-bold mb-8 text-gray-800">SALON HAIR</h1>
        <nav class="space-y-2 flex-1">
          <div
            v-for="item in navItems"
            :key="item.id"
          > 
            <div v-if="isShowMenu(item.role)" class="w-full flex flex-col items-start space-x-3">
              <div class="p-3 text-xl font-bold">
                <i :class="['pi', item.icon, 'text-xl']"></i>
                <span class="mb-5">{{ item.label }}</span>
              </div>
              <div
                v-for="childItem in item.child"
                :key="childItem.id"
                class="w-5/6"
              > 
                <div
                class="flex items-start p-3 rounded-lg transition-all duration-300 ease-in-out cursor-pointer"
                :class="[ 
                route.path === childItem.path 
                  ? 'text-sky-600 bg-sky-200' 
                  : 'text-gray-600 hover:bg-gray-100' 
                ]"
                v-if="isShowMenu(childItem.role)"
                @click="goMenu(childItem)"
                >
                  <i :class="['pi', childItem.icon, 'text-xl']"></i>
                  <span>{{ childItem.label }}</span>
                </div>
              </div>
            </div>
          </div>
        </nav>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useUserStore } from '@/stores/userStore'
import { decryptStringSalt } from '@/utils/common'
import { onBeforeMount, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const userRole = ref('')

onBeforeMount(() => {
  userRole.value = decryptStringSalt(userStore.getUserRole)
})

const isShowMenu = (roleList) => {
  return roleList.includes(userRole.value)
}

const dashBoardItems = [
  { id: '6433015e-8aad-4a27-8b65-f2d577eba5ef', path: '/salon/dashboard/company', label: '고객사 대시보드', name: 'CompanyDashboard', role: ['MASTER'] },
  { id: 'c8f136b1-e63d-49f0-83e4-b5dcfc191e16', path: '/salon/dashboard/schedule', label: '스케줄 대시보드', name: 'ScheduleDashboard', role: ['MASTER', 'ADMIN','MANAGER','MEMBER'] },
]

const manageItem = [
  { id: '5242b009-d22c-440f-ac6d-4e24a11311dc', path: '/salon/manage/company', label: '고객사 관리', name: 'CompanyAdmin', role: ['MASTER'] },
  { id: '151fea10-9564-4508-bb1a-917f51d43ee8', path: '/salon/manage/user', label: '사용자 관리', name: 'UserAdmin', role: ['MASTER', 'ADMIN','MANAGER'] },
]

const scheduleItem = [
  { id: 'efaf63cb-b99c-4a73-b5ad-9da3dc03bb24', path: '/salon/schedule/employment', label: '근태 관리', name: 'EmploymentAdmin', role: ['MASTER', 'ADMIN','MANAGER','MEMBER'] },
  { id: '3f9c78a8-c66e-4b57-98f4-d009035b4cc6', path: '/salon/schedule/reservation', label: '예약 관리', name: 'ReservationAdmin', role: ['MASTER', 'ADMIN','MANAGER','MEMBER'] },
]

const settingItem = [
  { id: 'ebf38c16-e4a6-4b91-a2fe-b2be07376992', path: '/salon/setting/style', label: '스타일', name: 'StyleAdmin', role: ['MASTER', 'ADMIN','MANAGER','MEMBER'] },
  { id: '4f0f2429-8822-4386-a437-ac8a0aa944a1', path: '/salon/setting/system', label: '시스템설정', name: 'SystemAdmin', role: ['MASTER', 'ADMIN','MANAGER'] },
]

const reportItem = [
  { id: '5adbc1ae-fb8a-4609-b87b-4c6566dce054', path: '/salon/report/audit', label: '감사 로그', name: 'AuditLog', role: ['MASTER'] },
  { id: '730a33e8-4f06-4759-b6a9-0ee2ceaaebc6', path: '/salon/report/login', label: '로그인 로그', name: 'LoginLog', role: ['MASTER', 'ADMIN','MANAGER'] },
]

const navItems = [
  { id: '729d5b14-af2c-4833-a535-efc4913518dd', path: '/salon/dashboard', label: 'DashBoard', child: dashBoardItems, role: ['MASTER', 'ADMIN','MANAGER','MEMBER'] },
  { id: '1bf7978a-b5e6-4a63-a85d-5a88126e2386', path: '/salon/manage', label: 'Manage', child: manageItem, role: ['MASTER', 'ADMIN','MANAGER'] },
  { id: '82a7454f-2a99-4dbe-acb3-592350305afc', path: '/salon/schedule', label: 'Schedule', child: scheduleItem, role: ['MASTER', 'ADMIN','MANAGER','MEMBER'] },
  { id: '89ce841e-29d1-4f6e-927a-687bd3a78e95', path: '/salon/setting', label: 'Setting', child: settingItem, role: ['MASTER', 'ADMIN','MANAGER','MEMBER'] },
  { id: 'dd3270ff-cf5b-4a74-8141-3789ef0194f5', path: '/salon/report', label: 'Report', child: reportItem, role: ['MASTER', 'ADMIN','MANAGER'] },
]

const goMenu = (item) => {
  router.push({ name: item.name }).catch(() => {
    console.log('goMenuError')
  })
}
</script>

<style scoped>
.salon-main-logo {
    margin-top: 10px;
    margin-bottom: 30px;
}
</style>