<template>
  <div class="px-0 py-5 w-full">
    <div class="flex justify-between items-center mb-6">
      <div>
        <span class="text-base font-bold text-sky-600 mr-2">전체 : </span> 
        <span class="text-base text-gray-700">{{ totalCount }}</span>
      </div>
    </div>

    <div class="bg-white rounded-lg border border-gray-300 mb-10 shadow-lg">
      <table class="table-auto w-full">
        <thead>
          <tr>
            <th v-for="(fItem, fidx) in fields" :key="fidx" class="sticky top-0 py-5 text-center bg-gray-50 first:rounded-tl-lg last:rounded-tr-lg border-b border-gray-300 z-10">
              <div class="text-base font-bold text-gray-500 flex justify-center items-center">
                <div class="text-gray-500">{{ fItem.name }}</div>
              </div>
            </th>
          </tr>
        </thead>

        <tbody v-if="contents && contents.length == 0" class="bg-white">
          <tr>
            <td :colspan="fields.length"  class="text-center py-4 border-b border-gray-300"> 
              <span class="font-bold text-red-500">{{ noListText }}</span>
            </td>
          </tr>
        </tbody>

        <tbody class="bg-white">
          <tr v-for="(cItem, cidx) in contents" :key="cidx" class="hover:bg-sky-50">
            <td v-for="(fItem, fidx) in fields" :key="fidx" class="py-4 px-3 border-b border-gray-300 text-base" :class="fItem.classes" :style="fItem.style">
              <div v-if="fItem.type === 'normal' || fItem.type === 'text'" class="text-base text-gray-600" v-html="makeTableValue(cItem[fItem.id], fItem.type, fItem.ref)"></div>

              <div v-else-if="fItem.type === 'seq'" class="text-base">
                <div class="text-gray-600">{{ totalCount - cidx - (limit * (currentPage - 1)) }}</div>
              </div>

              <div v-else-if="fItem.type === 'title'" class="text-base text-gray-600">
                {{ cItem[fItem.id] }}
              </div>

              <div v-else-if="fItem.type === 'number'" class="text-base text-gray-600">
                {{ $_numberComma(cItem[fItem.id]) }}
              </div>

              <div v-else-if="fItem.type === 'toggleActive'" class="flex justify-center items-center">
                <ToggleSwitch v-model="cItem[fItem.id]" trueValue="YES" falseValue="NO" @change="toggleActiveChange(cItem[fItem.id], cItem[keyName])"/>
              </div>

              <div v-else-if="fItem.type === 'dateTime'" class="text-base text-gray-600">
                {{ cItem[fItem.id] ? $_dayjs(cItem[fItem.id]).format("YYYY-MM-DD HH:mm:ss") : '' }}
              </div>

              <div v-else-if="fItem.type === 'password'">
                <button
                  class="text-base text-teal-600 bg-teal-200 hover:bg-teal-300 px-2 py-1 rounded-md whitespace-nowrap cursor-pointer hover:shadow-xl"
                  @click="actionPassword(cItem[keyName])"><i class="pi pi-key mr-2" />비밀번호 수정</button>
              </div>

              <div v-else-if="fItem.type === 'update'">
                <button
                  class="text-base text-orange-600 bg-orange-200 hover:bg-orange-300 px-2 py-1 rounded-md whitespace-nowrap cursor-pointer hover:shadow-xl"
                  @click="actionUpdate(cItem[keyName])"><i class="pi pi-pen-to-square mr-2" />수정</button>
              </div>

              <div v-else-if="fItem.type === 'delete'">
                <button
                  class="text-base text-red-600 bg-red-200 hover:bg-red-300 px-2 py-1 rounded-md whitespace-nowrap cursor-pointer hover:shadow-xl"
                  @click="actionDelete(cItem[keyName])"><i class="pi pi-trash mr-2" />삭제</button>
              </div>

              <div v-else-if="fItem.type === 'detail'">
                <button
                  class="text-base text-yellow-600 bg-yellow-200 hover:bg-yellow-300 px-2 py-1 rounded-md whitespace-nowrap cursor-pointer hover:shadow-xl"
                  @click="actionDetail(cidx)">상세 보기</button>
              </div>

              <div v-else v-html="makeTableValue(cItem[fItem.id], fItem.type, fItem.ref)"></div>
            </td>
          </tr>
        </tbody>
      </table>

      <div class="flex justify-center items-center my-10 " v-if="totalCount > 0">
        <button class="border border-emerald-400 border-solid mx-1 select-none text-xs text-green-800 py-2 px-4"
          @click="chgCurrentPage(1)"
        >
          <i class="pi pi-angle-double-left"></i>
        </button>

        <button class="border border-emerald-400 border-solid mx-1 select-none text-xs text-green-800 py-2 px-4"
          @click="chgCurrentPage(currentPage - 1)"
        >
          <i class="pi pi-angle-left"></i>
        </button>

        <button class="mx-1 select-none py-2 px-4 text-xs"
          :class="currentPage == num ? 'bg-emerald-200 text-emerald-600 border-2 border-emerald-400 border-solid' : 'text-emerald-800 border border-emerald-400 border-solid'"
          v-for="(num, idx) in pageArray" :key="idx" @click="chgCurrentPage(num)"
        >
          {{ num }}
        </button>

        <button class="border border-emerald-400 border-solid mx-1 select-none text-xs text-green-800 py-2 px-4"
          @click="chgCurrentPage(currentPage + 1)"
        >
          <i class="pi pi-angle-right"></i>
        </button>

        <button class="border border-emerald-400 border-solid mx-1 select-none text-xs text-green-800 py-2 px-4"
          @click="chgCurrentPage(lastPage)"
        >
          <i class="pi pi-angle-double-right"></i>
        </button>
      </div>

    </div>

  </div>
</template>

<script setup>
import { defineProps, defineEmits, onMounted, ref, watch } from 'vue';

import { ToggleSwitch } from 'primevue';

const props = defineProps(['totalCount', 'fields','contents','limit','currentPage','keyName','noListText'])
const emit = defineEmits(['chgCurrentPage','actionPassword','actionUpdate','actionDelete','actionDetail','toggleActiveChange'])

const lastPage = ref(0)

const pageCount = 10
let pageArray = []

const actionPassword = (guid) => {
  emit('actionPassword', guid)
}
const actionUpdate = (guid) => {
  emit('actionUpdate', guid)
}
const actionDelete = (guid) => {
  emit('actionDelete', guid)
}
const actionDetail = (guid) => {
  emit('actionDetail', guid)
}
const toggleActiveChange = (value, guid) => {
  emit('toggleActiveChange', guid, value)
}

const chgCurrentPage = (num) => {
  if(num < 1) return
  if(lastPage.value < num) return
  
  emit('chgCurrentPage', num)
}

const makeTableValue = (value, type, ref) => {
  switch (type) {
    case 'text':
    case 'normal':
      if (ref) return ref[value] ? ref[value] : value
      else return value;
    default:
      return value;
  }
}

const makeShowPagination = () => {
  pageArray = [];

  lastPage.value = Math.ceil(props.totalCount / props.limit)

  let startNum = (props.currentPage - 1) - (props.currentPage - 1) % pageCount
  let lastNum = (props.currentPage - 1) - (props.currentPage - 1) % pageCount + pageCount;
  if (lastNum > lastPage.value) lastNum = lastPage.value

  for (let i = startNum; i < lastNum; i++) pageArray.push(i + 1);
}

onMounted(() => {
  makeShowPagination()
})

watch(() => props.totalCount, () => {
  makeShowPagination()
})

watch(() => props.currentPage, (newVal) => {
  if(pageArray.findIndex(x => x == newVal) < 0) makeShowPagination()
})

</script>