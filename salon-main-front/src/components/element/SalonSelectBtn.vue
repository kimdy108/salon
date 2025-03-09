<template>
  <div class="w-full text-left mb-3">
    <div class="pl-1">
      <span class="text-base text-gray-700">{{ inputTitle }}</span> <span v-if="isRequire" class="text-base mx-1 font-bold text-orange-500">*</span>
    </div>
    <div class="mt-3">
      <div>
        <div class="flex">
          <SelectButton 
            v-model="inputValue" 
            :options="options" 
            :optionLabel="optionLabel" 
            :optionValue="optionValue"
            :multiple="multiple"
            :disabled="isDisabled"
          ></SelectButton>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { SelectButton } from 'primevue'

import { defineProps, defineModel } from "vue"

defineProps(['inputTitle', 'isRequire', 'options', 'optionLabel', 'optionValue', 'multiple', 'isDisabled'])
const inputValue = defineModel('inputValue', { required: true })
</script>