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
          <div class="text-xl text-gray-900">로그 상세</div>
        </div>
      </template>

      <div class="w-full px-5 py-3 text-left bg-white">
        <JsonViewer :value="targetData" class="!bg-gray-50"></JsonViewer>
      </div>

      <template #footer>
        <div>
          <button class="text-gray-700 bg-white hover:bg-gray-100 border-gray-200 rounded-lg shadow-md py-3 px-6 text-base mx-2" @click="closeDetailModal">닫기</button>
        </div>
      </template>

    </Dialog>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue'
import { Dialog } from 'primevue';

defineProps(['showModal', 'targetData'])
const emit = defineEmits(['closeDetailModal'])

const closeDetailModal = () => {
  emit('closeDetailModal')
}
</script>

<style lang="scss" scoped>

</style>