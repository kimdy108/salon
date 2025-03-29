<template>
  <div class="w-full">
    <div class="text-left mb-5">
      <div class="text-xl font-bold text-gray-900">스타일</div>
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
          <i class="pi pi-upload mr-2" />스타일 등록
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
        keyName="styleGuid"
        noListText="등록된 스타일이 없습니다."
        @toggleActiveChange="styleActive"
        @chgCurrentPage="chgCurrentPage"
        @actionUpdate="showUpdateModal"
        @actionDelete="styleDelete"
      ></SalonTable>
    </div>

    <StyleRegist :showModal="isRegistModal" @closeRegistModal="closeRegistModal" ></StyleRegist>
    <StyleUpdate :showModal="isUpdateModal" :updateGuid="updateGuid" @closeUpdateModal="closeUpdateModal" ></StyleUpdate>

  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue';
import { decryptStringSalt } from '@/utils/common';
import { useUserStore } from '@/stores/userStore';

import { Select } from 'primevue';

import SalonSearch from '@/components/element/SalonSearch.vue';
import SalonTable from '@/components/element/SalonTable.vue';
import StyleRegist from './StyleRegist.vue';
import StyleUpdate from './StyleUpdate.vue';

import ApiService from '@/services/ApiService';
import AlertService from '@/services/AlertService';

onMounted(() => {
  getStyleList()

  let el = document.getElementById('salon-hair')
  el.scrollTo({ top: 0 })
})

const userStore = useUserStore()

const searchCategory = ref(decryptStringSalt(userStore.getUserRole) === 'MASTER' ? 'companyName' : 'managerName')
const searchCategoryList = decryptStringSalt(userStore.getUserRole) === 'MASTER' ?
([
    { name: '고객사명', key: 'companyName' },
    { name: '담당자이름', key: 'managerName' },
    { name: '스타일이름', key: 'styleName' }
]) :
([
    { name: '담당자이름', key: 'managerName' },
    { name: '스타일이름', key: 'styleName' }
])
const searchValue = ref('')

const totalCount = ref(0)
const fields = computed(() => {
  return [
    { idx: 1, name: "No", id: 'seq', type: 'seq', classes: 'text-center', style: 'width:80px', clickType: '', target: '' },
    { idx: 2, name: "활성화", id: 'isActive', type: 'toggleActive', classes: 'text-center', style: 'width:80px', clickType: '', target: '' },
    { idx: 3, name: "고객사", id: 'companyName', type: 'text', classes: 'text-center', style: '', clickType: '', target: '' },
    { idx: 4, name: "담당자 이름", id: 'userName', type: 'text', classes: 'text-center', style: '', clickType: '', target: '' },
    { idx: 5, name: "스타일", id: 'styleName', type: 'text', classes: 'text-center', style: '', clickType: '', target: '' },
    { idx: 5, name: "소요시간", id: 'styleDuration', type: 'text', classes: 'text-center', style: '', clickType: '', target: '' },
    { idx: 5, name: "중간예약 사용여부", id: 'isMiddleTime', type: 'text', classes: 'text-center', style: '', clickType: '', target: '' },
    { idx: 6, name: "수정일", id: 'updateDate', type: 'dateTime', classes: 'text-center', style: '', clickType: '', target: '' },
    { idx: 7, name: "등록일", id: 'insertDate', type: 'dateTime', classes: 'text-center', style: '', clickType: '', target: '' },
    { idx: 8, name: "", id: '', type: 'update', classes: 'text-center px-4', style: 'width:60px', clickType: '', target: '' },
    { idx: 9, name: "", id: '', type: 'delete', classes: 'text-center px-4', style: 'width:60px', clickType: '', target: '' },
  ]
})
const contents = ref([])
const currentPage = ref(1)
const limit = 15

const searchSubmit = () => {
  currentPage.value = 1
  getStyleList()
}

const isRegistModal = ref(false)
const isUpdateModal = ref(false)
const updateGuid = ref('')

const showRegistModal = () => {
  isRegistModal.value = true
}
const closeRegistModal = () => {
  getStyleList()
  isRegistModal.value = false
}

const showUpdateModal = (guid) => {
  updateGuid.value = guid
  isUpdateModal.value = true
}
const closeUpdateModal = () => {
  getStyleList()
  isUpdateModal.value = false
}

const styleDelete = (guid) => {
  AlertService.confirmAlertAction('삭제하시겠습니까?', '스타일', '확인', '취소', 'info', guid, styleDeleteAction)
  console.log('delete', guid)
}

const chgCurrentPage = (num) => {
  currentPage.value = num
  getStyleList()

  let el = document.getElementById('salon-hair')
  el.scrollTo({ top: 0 })
}

const getStyleList = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqParams = {
    searchType: searchCategory.value ? searchCategory.value : '',
    searchValue: searchValue.value ? searchValue.value : '',
    limit: limit,
    offset: currentPage.value - 1,
    companyGuid: decryptStringSalt(userStore.getCurrentUser.ucg)
  }
  const styleResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/main/setting/style/list/page`,
    params: reqParams
  })
  if (styleResult.retStatus) {
    contents.value = styleResult.retData.content
    totalCount.value = styleResult.retData.totalElements
  }
}

const styleActive = async (guid, value) => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    styleGuid: guid,
    isActive: value
  }
  const activeResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'PUT',
    url: `/main/setting/style/active`,
    data: reqData
  })
  if (!activeResult.retStatus) {
    AlertService.normalAlertAction(activeResult.retData, '스타일', '확인', 'error')
  }
}

const styleDeleteAction = async (guid) => {
  const reqHeader = { accept: 'application/json' }
  const deleteResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'DELETE',
    url: `/main/setting/style/delete/${guid}`
  })
  if (deleteResult.retStatus) {
    AlertService.normalAlertAction('삭제 했습니다.', '스타일', '확인', 'success')
    getStyleList()
  }
  else AlertService.normalAlertAction(deleteResult.retData, '스타일', '확인', 'error')
}
</script>

<style lang="scss" scoped>

</style>