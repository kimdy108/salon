<template>
  <div class="w-full text-left mb-3">
    <div class="pl-1">
      <span class="text-base text-gray-700 dark:text-gray-50">{{ inputTitle }}</span> <span v-if="isRequire" class="text-base mx-1 font-bold text-warning-500">*</span>
    </div>
    <div class="mt-3">
      <textarea 
        class="w-full border border-gray-300 dark:border-gray-700 rounded-lg bg-white dark:bg-gray-700 dark:text-gray-50 placeholder-gray-300 py-3 px-5 shadow-sm focus:outline-none focus:shadow-outline" 
        :placeholder="inputPlaceholder" 
        v-model="inputValue"
        :disabled="isDisabled"
        style="height: 150px;"
      ></textarea>
    </div>
    <div class="mt-2" v-if="inputHint!=''">
      <span class="text-sm">{{ inputHint }}</span>
    </div>
  </div>
</template>

<script setup>
import { defineModel, defineProps } from 'vue'

defineProps(['inputTitle','isDisabled','isRequire','inputPlaceholder','inputHint'])
const inputValue = defineModel('inputValue', { required: true })
</script>

<style lang="scss" scoped>

</style>