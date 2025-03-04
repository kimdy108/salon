<template>
  <div class="w-full">
    <div class="text-left mb-5">
      <div class="text-xl font-bold text-gray-900">마스터 관리</div>
    </div>
    <div class="bg-white rounded-lg shadow-lg flex justify-between items-center px-8 py-4 mb-5">
      <div class="text-left flex justify-start items-center">
        <Select v-model="searchCategory" :options="searchCategoryList" optionLabel="name" optionValue="key" placeholder="검색조건" class="w-56 !bg-white border !border-gray-300 !text-gray-700 !rounded-lg !placeholder-gray-300 !shadow-sm !focus:outline-none !focus:shadow-outline"></Select>
        <SalonSearch :isDisabled="false" inputPlaceholder="" inputType="text" v-model:inputValue="searchValue" @searchSubmit="searchSubmit"></SalonSearch>
        <button class="min-w-fit flex justify-center items-center bg-emerald-200 text-emerald-600 hover:bg-emerald-300 px-5 py-2 rounded-lg shadow-sm" @click="searchSubmit">
          <span class="pi pi-search mr-3"></span>검색
        </button>
      </div>
      <div class="flex justify-end items-center">
        <button class="min-w-fit flex justify-center items-center text-sky-600 bg-sky-200 hover:bg-sky-300 text-base px-5 py-2 rounded-lg shadow-sm" @click="showRegistModal">
          <i class="pi pi-upload mr-2" />마스터 등록
        </button>
      </div>
    </div>

    <div>
      <SalonTable
        :totalCount="totalCount"
        :fields="fields"
        :contents="contents"
        :limit="limit"
        :currentPage="currentPage"
        keyName="masterGuid"
        noListText="등록된 마스터가 없습니다."
        @toggleActiveChange="masterActive"
        @chgCurrentPage="chgCurrentPage"
        @actionPassword="showUpdatePasswordModal"
        @actionUpdate="showUpdateModal"
        @actionDelete="masterDelete"
      ></SalonTable>
    </div>

    <MasterRegist :showModal="isRegistModal" @closeRegistModal="closeRegistModal" ></MasterRegist>
    <MasterUpdate :showModal="isUpdateModal" :updateGuid="updateGuid" @closeUpdateModal="closeUpdateModal" ></MasterUpdate>
    <UserUpdatePassword :showModal="isUpdatePasswordModal" :updatePasswordGuid="updatePasswordGuid" @closeUpdatePasswordModal="closeUpdatePasswordModal" ></UserUpdatePassword>

  </div>
</template>

<script setup>
import { defineProps, computed, onMounted, ref, watch } from 'vue';
import { useUserStore } from '@/stores/userStore';
import { decryptStringSalt } from '@/utils/common'
import SalonSearch from '@/components/element/SalonSearch.vue';
import SalonTable from '@/components/element/SalonTable.vue';
import AlertService from '@/services/AlertService';
import ApiService from '@/services/ApiService';

import MasterRegist from '@/pages/manage/user/MasterRegist.vue'
import MasterUpdate from '@/pages/manage/user/MasterUpdate.vue'
import UserUpdatePassword from '@/pages/manage/user/UserUpdatePassword.vue'

import { Select } from 'primevue';

onMounted(() => {
  getMasterList()
})

const props = defineProps(['selectedTab'])

const userStore = useUserStore()

const searchCategory = ref('userName')
const searchCategoryList = ([
    { name: '사용자이름', key: 'userName' },
    { name: '사용자아이디', key: 'userID' }
])
const searchValue = ref('')

const totalCount = ref(0)
const fields = computed(() => {
  return [
    { idx: 1, name: "No", id: 'seq', type: 'seq', classes: 'text-center', style: 'width:80px', clickType: '', target: '' },
    { idx: 2, name: "활성화", id: 'isActive', type: 'toggleActive', classes: 'text-center', style: 'width:80px', clickType: '', target: '' },
    { idx: 4, name: "아이디", id: 'masterID', type: 'text', classes: 'text-center', style: '', clickType: '', target: '' },
    { idx: 4, name: "이름", id: 'masterName', type: 'text', classes: 'text-center', style: '', clickType: '', target: '' },
    { idx: 5, name: "권한", id: 'masterRole', type: 'text', classes: 'text-center', style: '', clickType: '', target: '' },
    { idx: 6, name: "수정일", id: 'updateDate', type: 'dateTime', classes: 'text-center', style: '', clickType: '', target: '' },
    { idx: 7, name: "등록일", id: 'insertDate', type: 'dateTime', classes: 'text-center', style: '', clickType: '', target: '' },
    { idx: 8, name: "", id: '', type: 'password', classes: 'text-center px-4', style: 'width:60px', clickType: '', target: '' },
    { idx: 8, name: "", id: '', type: 'update', classes: 'text-center px-4', style: 'width:60px', clickType: '', target: '' },
    { idx: 9, name: "", id: '', type: 'delete', classes: 'text-center px-4', style: 'width:60px', clickType: '', target: '' },
  ]
})
const contents = ref([])
const currentPage = ref(1)
const limit = 15

const isRegistModal = ref(false)
const isUpdateModal = ref(false)
const isUpdatePasswordModal = ref(false)
const updateGuid = ref('')
const updatePasswordGuid = ref('')

const showRegistModal = () => {
  isRegistModal.value = true
}
const closeRegistModal = () => {
  getMasterList()
  isRegistModal.value = false
}

const showUpdateModal = (guid) => {
  if (decryptStringSalt(userStore.getCurrentUser.ugd) === guid) {
    AlertService.normalAlertAction('상단의 설정 메뉴를 이용해주세요.', '사용자관리', '확인', 'error')
    return
  }
  updateGuid.value = guid
  isUpdateModal.value = true
}
const closeUpdateModal = () => {
  getMasterList()
  isUpdateModal.value = false
}

const showUpdatePasswordModal = (guid) => {
  if (decryptStringSalt(userStore.getCurrentUser.ugd) === guid) {
    AlertService.normalAlertAction('상단의 설정 메뉴를 이용해주세요.', '사용자관리', '확인', 'error')
    return
  }

  updatePasswordGuid.value = guid
  isUpdatePasswordModal.value = true
}
const closeUpdatePasswordModal = () => {
  getMasterList()
  isUpdatePasswordModal.value = false
}

const masterDelete = (guid) => {
  if (decryptStringSalt(userStore.getCurrentUser.ugd) === guid) {
    AlertService.normalAlertAction('본인 계정은 삭제할 수 없습니다.', '사용자관리', '확인', 'error')
    return
  }

  AlertService.confirmAlertAction('삭제하시겠습니까?', '사용자관리', '확인', '취소', 'info', guid, masterDeleteAction)
}

const searchSubmit = () => {
  currentPage.value = 1
  getMasterList()
}

const chgCurrentPage = (num) => {
  currentPage.value = num
  getMasterList()

  let el = document.getElementById('salon-hair')
  el.scrollTo({ top: 0 })
}

const getMasterList = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqParams = {
    searchType: searchCategory.value ? searchCategory.value : '',
    searchValue: searchValue.value ? searchValue.value : '',
    limit: limit,
    offset: currentPage.value - 1
  }
  const masterResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/main/manage/master/list/page`,
    params: reqParams
  })
  if (masterResult.retStatus) {
    console.log(masterResult)
    contents.value = masterResult.retData.content
    totalCount.value = masterResult.retData.totalElements
  }
}

const masterActive = async (guid, value) => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    masterGuid: guid,
    isActive: value
  }
  const activeResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'PUT',
    url: `/main/manage/master/active`,
    data: reqData
  })
  if (!activeResult.retStatus) {
    AlertService.normalAlertAction(activeResult.retData, '사용자관리', '확인', 'error')
  }
}

const masterDeleteAction = async (guid) => {
  const reqHeader = { accept: 'application/json' }
  const deleteResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'DELETE',
    url: `/main/manage/master/delete/${guid}`
  })
  if (deleteResult.retStatus) {
    AlertService.normalAlertAction('삭제 했습니다.', '사용자관리', '확인', 'success')
    getMasterList()
  }
  else AlertService.normalAlertAction(deleteResult.retData, '사용자관리', '확인', 'error')
}

watch(() => props.selectedTab, (newVal) => {
  if(newVal == 0) getMasterList()
})
</script>