<template>
  <div class="w-full salon-main-header-wrapper bg-white shadow-lg transition-all duration-300 ease-in-out">
    <div class="flex items-center justify-end px-6 py-4">

      <div class="flex items-center space-x-4">
        <button class="salon-signout-button border rounded-lg border-gray-300 px-8 py-3 text-gray-600 hover:text-gray-800 hover:bg-gray-100 transition-colors" @click="menuToggle">
          <i class="pi pi-user mr-2"></i>
          <span>{{ userName }}</span>
        </button>
        <Menu ref="adminMenu" :model="adminItems" :popup="true">
          <template #item="{ item, props }">
            <a class="flex items-center" v-bind="props.action">
              <span>{{ item.label }}</span>
            </a>
          </template>
        </Menu>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useUserStore } from "@/stores/userStore";
import { decryptStringSalt } from "@/utils/common";
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import { Menu } from "primevue";

const userStore = useUserStore()
const router = useRouter()

const userName = ref('')

const adminMenu = ref()
const adminItems = ref([
  {
    items: [
      {
        label: '설정',
        command: () => {
          router.push({ name: 'AdminConfig' }).catch(() => {
            console.log('adminconfigerror')
          })
        }
      },
      {
        label: '로그아웃',
        command: () => {
          userStore.setUserLogout()
          router.push({ name: 'IndexPage' }).catch(() => {
            console.log('logouterror')
          })
        }
      }
    ]
  }
])

onMounted(() => {
  userName.value = decryptStringSalt(userStore.getCurrentUser.unm)
})

const menuToggle = (event) => {
  adminMenu.value.toggle(event)
}
</script>

<style lang="scss" scoped>
.salon-main-header-wrapper{
    height: 72px;
}
.salon-signout-button {
    margin-top: 5px;
}
</style>