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
          <div class="text-xl text-gray-900">사용자 수정</div>
        </div>
      </template>

      <div></div>

      <template #footer>
        <div>
          <button class="text-gray-700 bg-white hover:bg-gray-100 border-gray-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="closeUpdateModal">닫기</button>
          <button class="text-orange-600 bg-orange-200 hover:bg-orange-300 border-orange-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="userUpdate">수정</button>
        </div>
      </template>
    
    
    </Dialog>

  </div>
</template>

<script setup>
import { Dialog } from 'primevue';
import { defineProps, defineEmits, watch } from 'vue';

const props = defineProps(['showModal','updateGuid'])
const emit = defineEmits(['closeUpdateModal'])

const initData = () => {
  console.log('init')
}

const closeUpdateModal = () => {
  emit('closeUpdateModal')
}

const userUpdate = () => {
  userUpdateAction()
}

const userUpdateAction = () => {
  console.log('userUpdate')
}

watch(() => props.showModal, (newVal) => {
  if(newVal) initData()
})
</script>

<style lang="scss" scoped>

</style>