<template>
  <div class="w-full">
    <div class="text-left mb-5">
      <div class="text-xl font-bold text-gray-900">사용자 관리</div>
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
          <i class="pi pi-upload mr-2" />사용자 등록
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
        keyName="userGuid"
        noListText="등록된 사용자가 없습니다."
        @toggleActiveChange="userActive"
        @chgCurrentPage="chgCurrentPage"
        @actionPassword="showUpdatePasswordModal"
        @actionUpdate="showUpdateModal"
        @actionDelete="userDelete"
      ></SalonTable>
    </div>

    <UserRegist :showModal="isRegistModal" @closeRegistModal="closeRegistModal" ></UserRegist>
    <UserUpdate :showModal="isUpdateModal" :updateGuid="updateGuid" @closeUpdateModal="closeUpdateModal" ></UserUpdate>
    <UserUpdatePassword :showModal="isUpdatePasswordModal" :updatePasswordGuid="updatePasswordGuid" @closeUpdatePasswordModal="closeUpdatePasswordModal" ></UserUpdatePassword>

  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue';
import { useUserStore } from '@/stores/userStore';
import { decryptStringSalt } from '@/utils/common'
import SalonSearch from '@/components/element/SalonSearch.vue';
import SalonTable from '@/components/element/SalonTable.vue';
import AlertService from '@/services/AlertService';
import ApiService from '@/services/ApiService';

import UserRegist from '@/pages/manage/user/UserRegist.vue'
import UserUpdate from '@/pages/manage/user/UserUpdate.vue'
import UserUpdatePassword from '@/pages/manage/user/UserUpdatePassword.vue'

import { Select } from 'primevue';

onMounted(() => {
  getUserList()
})

const userStore = useUserStore()

const searchCategory = ref('companyName')
const searchCategoryList = ([
    { name: '고객사명', key: 'companyName' },
    { name: '사용자이름', key: 'userName' }
])
const searchValue = ref('')

const totalCount = ref(0)
const fields = computed(() => {
  return [
    { idx: 1, name: "No", id: 'seq', type: 'seq', classes: 'text-center', style: 'width:80px', clickType: '', target: '' },
    { idx: 2, name: "활성화", id: 'isActive', type: 'toggleActive', classes: 'text-center', style: 'width:80px', clickType: '', target: '' },
    { idx: 3, name: "고객사", id: 'companyName', type: 'text', classes: 'text-center', style: '', clickType: '', target: '' },
    { idx: 4, name: "아이디", id: 'userID', type: 'text', classes: 'text-center', style: '', clickType: '', target: '' },
    { idx: 4, name: "이름", id: 'userName', type: 'text', classes: 'text-center', style: '', clickType: '', target: '' },
    { idx: 5, name: "권한", id: 'userRole', type: 'text', classes: 'text-center', style: '', clickType: '', target: '' },
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
  getUserList()
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
  getUserList()
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
  getUserList()
  isUpdatePasswordModal.value = false
}

const userDelete = (guid) => {
  if (decryptStringSalt(userStore.getCurrentUser.ugd) === guid) {
    AlertService.normalAlertAction('본인 계정은 삭제할 수 없습니다.', '사용자관리', '확인', 'error')
    return
  }

  AlertService.confirmAlertAction('삭제하시겠습니까?', '사용자관리', '확인', '취소', 'info', guid, userDeleteAction)
}

const searchSubmit = () => {
  currentPage.value = 1
  getUserList()
}

const chgCurrentPage = (num) => {
  currentPage.value = num
  getUserList()

  let el = document.getElementById('salon-hair')
  el.scrollTo({ top: 0 })
}

const getUserList = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqParams = {
    searchType: searchCategory.value ? searchCategory.value : '',
    searchValue: searchValue.value ? searchValue.value : '',
    limit: limit,
    offset: currentPage.value - 1,
    companyGuid: decryptStringSalt(userStore.getCurrentUser.ucg)
  }
  const userResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/main/user/list/page`,
    params: reqParams
  })
  if (userResult.retStatus) {
    contents.value = userResult.retData.content
    totalCount.value = userResult.retData.totalElements
  }
}

const userActive = async (guid, value) => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    userGuid: guid,
    isActive: value
  }
  const activeResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'PUT',
    url: `/main/user/active`,
    data: reqData
  })
  if (!activeResult.retStatus) {
    AlertService.normalAlertAction(activeResult.retData, '사용자관리', '확인', 'error')
  }
}

const userDeleteAction = async (guid) => {
  const reqHeader = { accept: 'application/json' }
  const deleteResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'DELETE',
    url: `/main/user/delete/${guid}`
  })
  if (deleteResult.retStatus) {
    AlertService.normalAlertAction('삭제 했습니다.', '사용자관리', '확인', 'success')
    getUserList()
  }
  else AlertService.normalAlertAction(deleteResult.retData, '사용자관리', '확인', 'error')
}
</script>