<template>
  <div class="w-full">
    <div class="text-left mb-5">
      <div class="text-xl font-bold text-gray-900">로그인 로그</div>
    </div>

    <div class="bg-white rounded-lg shadow-lg flex justify-between items-center px-8 py-4 mb-5">
      <div class="text-left flex justify-start items-center">
        <DatePicker 
          v-model="searchDateRange" 
          selectionMode="range" 
          :manualInput="false" 
          showIcon 
          fluid 
          iconDisplay="input" 
          dateFormat="yy-mm-dd" 
          style="width:350px" 
          class="mr-5" 
          inputClass="!bg-white border !border-gray-300 !text-gray-700 !rounded-lg"
        >
        </DatePicker>

        <Select v-model="searchCategory" :options="searchCategoryList" optionLabel="name" optionValue="key" placeholder="검색조건" class="w-56 !bg-white border !border-gray-300 !text-gray-700 !rounded-lg !placeholder-gray-300 !shadow-sm !focus:outline-none !focus:shadow-outline"></Select>
        <SalonSearch :isDisabled="false" inputPlaceholder="" inputType="text" v-model:inputValue="searchValue" @searchSubmit="searchSubmit"></SalonSearch>
        <button class="min-w-fit flex justify-center items-center bg-emerald-200 text-emerald-600 hover:bg-emerald-300 px-5 py-2 rounded-lg shadow-sm" @click="searchSubmit">
          <span class="pi pi-search mr-3"></span>검색
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
        keyName="loginGuid"
        noListText="등록된 로그인 로그가 없습니다."
        @chgCurrentPage="chgCurrentPage"
      ></SalonTable>
    </div>

  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { DatePicker } from 'primevue';
import { Select } from 'primevue';
import { decryptStringSalt } from '@/utils/common';
import { useUserStore } from '@/stores/userStore';

import dayjs from 'dayjs'

import ApiService from '@/services/ApiService'

import SalonSearch from '@/components/element/SalonSearch.vue';
import SalonTable from '@/components/element/SalonTable.vue';

onMounted(() => {
  getLoginLogList()
})

const userStore = useUserStore()

const searchCategory = ref('userName')
const searchCategoryList = ([
    { name: '사용자이름', key: 'userName' },
    { name: '사용자아이디', key: 'userID' },
    { name: '고객사이름', key: 'companyName' }
])
const searchValue = ref('')
const searchDateRange = ref([new Date(), new Date()])

const totalCount = ref(0)
const fields = computed(() => {
  return [
    { idx: 1, name: "No", id: 'seq', type: 'seq', classes: 'text-center', style: 'width:80px', clickType: '', target: '' },
    { idx: 2, name: "로그인아이디", id: 'loginID', type: 'text', classes: 'text-center', style: '', clickType: '', target: '' },
    { idx: 3, name: "사용자이름", id: 'userName', type: 'text', classes: 'text-center', style: '', clickType: '', target: '' },
    { idx: 4, name: "사업자번호", id: 'companyNumber', type: 'text', classes: 'text-center', style: '', clickType: '', target: '' },
    { idx: 5, name: "고객사이름", id: 'companyName', type: 'text', classes: 'text-center', style: '', clickType: '', target: '' },
    { idx: 6, name: "로그인결과", id: 'loginResult', type: 'text', classes: 'text-center', style: '', clickType: '', target: '' },
    { idx: 8, name: "시간", id: 'insertDate', type: 'dateTime', classes: 'text-center', style: '', clickType: '', target: '' }
  ]
})
const contents = ref([])
const currentPage = ref(1)
const limit = 15

const searchSubmit = () => {
  currentPage.value = 1
  getLoginLogList()
}

const chgCurrentPage = (num) => {
  currentPage.value = num
  getLoginLogList()

  let el = document.getElementById('salon-hair')
  el.scrollTo({ top: 0 })
}

const getLoginLogList = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqParams = {
    startTimestamp: dayjs(searchDateRange.value[0]).startOf('day').valueOf(),
    endTimestamp: dayjs(searchDateRange.value[1]).endOf('day').valueOf(),
    searchType: searchCategory.value ? searchCategory.value : '',
    searchValue: searchValue.value ? searchValue.value : '',
    limit: limit,
    offset: currentPage.value - 1,
    companyGuid: decryptStringSalt(userStore.getCurrentUser.ucg)
  }
  const loginLogResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/main/report/login/list/page`,
    params: reqParams
  })
  if (loginLogResult.retStatus) {
    contents.value = loginLogResult.retData.content
    totalCount.value = loginLogResult.retData.totalElements
  }
}
</script>