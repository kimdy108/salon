<template>
  <div class="flex">
    <MainSide></MainSide>
    <div class="w-full">
      <MainHeader></MainHeader>
      <perfect-scrollbar class="overflow-hidden w-full salon-contents flex flex-col justify-between items-stretch bg-gray-50" id="salon-hair">
        <main class="px-8 py-10">
          <router-view></router-view>
        </main>
      </perfect-scrollbar>
    </div>
  </div>
</template>

<script setup>
import MainSide from '@/layouts/MainSide.vue';
import MainHeader from '@/layouts/MainHeader.vue';
</script>

<style lang="scss" scoped>
.salon-contents {
  height: calc(100vh - 72px);
}
</style>