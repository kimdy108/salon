<template>
  <div class="w-full text-left mb-3">
    <div class="pl-1">
      <span class="text-base text-gray-700">{{ inputTitle }}</span> <span v-if="isRequire" class="text-md mx-1 font-bold text-orange-500">*</span>
    </div>
    <div class="mt-3">
      <div>
        <div class="flex py-3">
          <ToggleSwitch 
            v-model="inputValue" 
            :trueValue="trueValue" 
            :falseValue="falseValue" 
            :disabled="isDisabled"
          ></ToggleSwitch>
          <span class="mx-5" :class="inputValue == trueValue ? 'text-blue-400': 'text-orange-600'">{{ inputValue == trueValue ? trueString : falseString }}</span>
        </div>
      </div>
      
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineModel } from 'vue'
import { ToggleSwitch } from "primevue"

defineProps(['inputTitle', 'isRequire', 'trueValue', 'falseValue', 'isDisabled', 'trueString', 'falseString'])
const inputValue = defineModel('inputValue', { required: true })
</script>
