<template>
  <div class="w-full">
    <div class="text-left mb-5">
      <div class="text-xl font-bold text-gray-900">고객사 관리</div>
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
          <i class="pi pi-upload mr-2" />고객사 등록
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
        keyName="companyGuid"
        noListText="등록된 고객사가 없습니다."
        @toggleActiveChange="companyActive"
        @chgCurrentPage="chgCurrentPage"
        @actionUpdate="showUpdateModal"
        @actionDelete="companyDelete"
      ></SalonTable>
    </div>

    <CompanyRegist :showModal="isRegistModal" @closeRegistModal="closeRegistModal" ></CompanyRegist>
    <CompanyUpdate :showModal="isUpdateModal" :updateGuid="updateGuid" @closeUpdateModal="closeUpdateModal" ></CompanyUpdate>

  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue';
import SalonSearch from '@/components/element/SalonSearch.vue';
import SalonTable from '@/components/element/SalonTable.vue';
import AlertService from "@/services/AlertService";
import ApiService from '@/services/ApiService';

import CompanyRegist from '@/pages/manage/company/CompanyRegist.vue';
import CompanyUpdate from '@/pages/manage/company/CompanyUpdate.vue';

import { Select } from 'primevue';

onMounted(() => {
  getCompanyList()
})

const searchCategory = ref('companyName')
const searchCategoryList = ([
    { name: '고객사명', key: 'companyName' },
    { name: '담당자이름', key: 'managerName' }
])
const searchValue = ref('')

const totalCount = ref(0)
const fields = computed(() => {
  return [
    { idx: 1, name: "No", id: 'seq', type: 'seq', classes: 'text-center', style: 'width:80px', clickType: '', target: '' },
    { idx: 2, name: "활성화", id: 'isActive', type: 'toggleActive', classes: 'text-center', style: 'width:80px', clickType: '', target: '' },
    { idx: 3, name: "고객사", id: 'companyName', type: 'text', classes: 'text-center', style: '', clickType: '', target: '' },
    { idx: 4, name: "담당자 이름", id: 'managerName', type: 'text', classes: 'text-center', style: '', clickType: '', target: '' },
    { idx: 5, name: "담당자 연락처", id: 'managerPhone', type: 'text', classes: 'text-center', style: '', clickType: '', target: '' },
    { idx: 6, name: "수정일", id: 'updateDate', type: 'dateTime', classes: 'text-center', style: '', clickType: '', target: '' },
    { idx: 7, name: "등록일", id: 'insertDate', type: 'dateTime', classes: 'text-center', style: '', clickType: '', target: '' },
    { idx: 8, name: "", id: '', type: 'update', classes: 'text-center px-4', style: 'width:60px', clickType: '', target: '' },
    { idx: 9, name: "", id: '', type: 'delete', classes: 'text-center px-4', style: 'width:60px', clickType: '', target: '' },
  ]
})
const contents = ref([])
const currentPage = ref(1)
const limit = 15

const isRegistModal = ref(false)
const isUpdateModal = ref(false)
const updateGuid = ref('')

const showRegistModal = () => {
  isRegistModal.value = true
}
const closeRegistModal = () => {
  getCompanyList()
  isRegistModal.value = false
}

const showUpdateModal = (guid) => {
  updateGuid.value = guid
  isUpdateModal.value = true
}
const closeUpdateModal = () => {
  getCompanyList()
  isUpdateModal.value = false
}

const companyDelete = (guid) => {
  AlertService.confirmAlertAction('삭제하시겠습니까?', '고객사관리', '확인', '취소', 'info', guid, companyDeleteAction)
}

const searchSubmit = () => {
  currentPage.value = 1
  getCompanyList()
}

const chgCurrentPage = (num) => {
  currentPage.value = num
  getCompanyList()

  let el = document.getElementById('salon-hair')
  el.scrollTo({ top: 0 })
}

const getCompanyList = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqParams = {
    searchType: searchCategory.value ? searchCategory.value : '',
    searchValue: searchValue.value ? searchValue.value : '',
    limit: limit,
    offset: currentPage.value - 1
  }
  const companyResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/main/company/list/page`,
    params: reqParams
  })
  if (companyResult.retStatus) {
    contents.value = companyResult.retData.content
    totalCount.value = companyResult.retData.totalElements
  }
}

const companyActive = async (guid, value) => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    companyGuid: guid,
    isActive: value
  }
  const activeResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'PUT',
    url: `/main/company/active`,
    data: reqData
  })
  if (!activeResult.retStatus) {
    AlertService.normalAlertAction(activeResult.retData, '고객사관리', '확인', 'error')
  }
}

const companyDeleteAction = async (guid) => {
  const reqHeader = { accept: 'application/json' }
  const deleteResult = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'DELETE',
    url: `/main/company/delete/${guid}`
  })
  if (deleteResult.retStatus) {
    AlertService.normalAlertAction('삭제 했습니다.', '고객사관리', '확인', 'success')
    getCompanyList()
  }
  else AlertService.normalAlertAction(deleteResult.retData, '고객사관리', '확인', 'error')
}

</script>

<style lang="scss" scoped>

</style>