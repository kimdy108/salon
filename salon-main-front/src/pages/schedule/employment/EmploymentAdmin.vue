<template>
  <div class="w-full">
    <div class="text-left mb-5">
      <div class="text-xl font-bold text-gray-900">근태 관리</div>
    </div>

    <div class="bg-white rounded-lg shadow-lg flex justify-between items-center px-8 py-4 mb-5">
      <FullCalendar class="w-full" defaultView="dayGridMonth" :options="calendarOptions" />
    </div>


  </div>
</template>

<script setup>
import { ref } from 'vue';
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import koLocale from '@fullcalendar/core/locales/ko';

const isRegistModal = ref(false)
const isUpdateModal = ref(false)
const updateGuid = ref('')
const targetDate = ref('')

const handleDateClick = (val) => {
  targetDate.value = val.dateStr
  isRegistModal.value = true
  console.log(targetDate.value)
  console.log(isRegistModal.value)
}
const handleEventClick = (val) => {
  updateGuid.value = val.event.id
  isUpdateModal.value = true
  console.log(updateGuid.value)
  console.log(isUpdateModal.value)
}

const calendarOptions = ref({
  plugins: [ dayGridPlugin, interactionPlugin ],
  initialView: 'dayGridMonth',
  events: [],
  eventClick: handleEventClick,
  dateClick: handleDateClick,
  headerToolbar: {
    left: 'prevYear,prev today next,nextYear',
    center: 'title',
    right: 'dayGridMonth'
  },
  locale: koLocale,
  contentHeight: 700
})

</script>

<style lang="scss" scoped>

</style>