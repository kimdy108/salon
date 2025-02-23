<template>
  <div>
    <Dialog :visible="showModal" 
      modal 
      :closable="false" 
      :draggable="false"
      class="w-2/3"
    >
      <template #header>
        <div class="w-full flex justify-between items-center border-b border-gray-100 pb-6">
          <div class="text-xl text-gray-900">사용자 등록</div>
        </div>
      </template>

      <template #footer>
        <div>
          <button class="text-gray-700 bg-white hover:bg-gray-100 border-gray-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="closeRegistModal">닫기</button>
          <button class="text-sky-600 bg-sky-200 hover:bg-sky-300 border-sky-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="userRegist">등록</button>
        </div>
      </template>
    
    
    </Dialog>
  </div>
</template>

<script setup>
import { Dialog } from 'primevue';
import { defineProps, defineEmits, watch } from 'vue';

const props = defineProps(['showModal'])
const emit = defineEmits(['closeRegistModal'])

const initData = () => {
  console.log('init')
}

const closeRegistModal = () => {
  emit('closeRegistModal')
}

const userRegist = () => {
  userRegistAction()
}

const userRegistAction = () => {
  console.log('userRegist')
}

watch(() => props.showModal, (newVal) => {
  if(newVal) initData()
})
</script>

<style lang="scss" scoped>

</style>