<template>
  <div class="border border-blue-100 rounded-lg w-full">
    <div class="bg-blue-200 py-2 rounded-t-lg">
      <div class="text-base text-gray-600">{{ props.cardTitle }}</div>
    </div>
    <div class="py-6 bg-white rounded-b-lg">
      <div class="text-xl text-gray-900">{{ props.cardContent }}</div>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from 'vue'

const props = defineProps(['cardTitle','cardContent'])
</script>