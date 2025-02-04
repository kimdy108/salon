<template>
  <div class="w-full text-left mb-3">
    <div class="pl-1">
      <span class="text-base text-gray-700">{{ inputTitle }}</span> <span v-if="isRequire" class="text-base mx-1 font-bold text-orange-500">*</span>
    </div>
    <div class="mt-3">
      <div>
        <div class="flex">
          <input 
            :type="isShowPassword ? 'text' : inputType" 
            class="w-full border border-gray-300 rounded-lg placeholder-gray-300 bg-white py-3 px-5 shadow-sm focus:outline-none focus:shadow-outline" 
            :placeholder="inputPlaceholder" 
            v-model="inputValue"
            :disabled="isDisabled"
          />
          <span
            v-if="isPassword"
            @click="changeShowPassword()"
            class="px-4 inline-flex items-center min-w-fit rounded-e-md border border-s-0 border-gray-300 bg-gray-50 text-sm text-gray-500 cursor-pointer"
          >
            <i class="pi text-base text-gray-600" :class="isShowPassword ? 'pi-eye' : 'pi-eye-slash'"></i>
          </span>

        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineModel, ref } from "vue"

defineProps(['inputTitle', 'isRequire', 'inputType', 'inputPlaceholder', 'isDisabled', 'isPassword'])
const inputValue = defineModel('inputValue', { required: true })

const isShowPassword = ref(false)
const changeShowPassword = () => {
  isShowPassword.value = !isShowPassword.value
}
</script>

<style lang="scss" scoped>

</style>