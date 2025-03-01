<template>
  <div class="w-full text-left mb-3">
    <div class="pl-1">
      <span class="text-base text-gray-700">{{ selectTitle }}</span> <span v-if="isRequire" class="text-base mx-1 font-bold text-orange-500">*</span>
    </div>

    <div class="mb-3">
      <div>
        <div class="flex">
          <Select
              v-model="inputValue"
              class="w-full border !bg-white rounded-lg placeholder-gray-300 py-1 px-2 shadow-sm focus:outline-none focus:shadow-outline"
              :options="options" 
              :optionLabel="optionTitle" 
              :optionValue="optionValue" 
              :placeholder="selectPlaceholder" 
              emptyMessage="선택할 수 있는 항목이 없습니다."
              @change="changeSelectValue"
          >
            <template #option="slotProps">
              <div>
                <div class="flex items-center">
                  <div class="text-md font-bold text-gray-600">{{ slotProps.option[optionTitle] }}</div>
                </div>

                <div class="flex items-center">
                  <div class="text-sm font-semibold text-gray-400">
                    {{ slotProps.option[optionSub] }}
                  </div>
                </div>
              </div>
            </template>

            <template #empty>
              <div class="text-gray-600">선택할 수 있는 항목이 없습니다.</div>
            </template>

          </Select>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { Select } from 'primevue'
import { defineProps, defineModel, defineEmits } from "vue"

defineProps(['selectTitle', 'isRequire', 'options', 'optionTitle', 'optionValue', 'optionSub', 'selectPlaceholder'])
const inputValue = defineModel('inputValue', { required: true })

const emit = defineEmits(['changeSelectValue'])

const changeSelectValue = () => {
  emit('changeSelectValue')
}
</script>