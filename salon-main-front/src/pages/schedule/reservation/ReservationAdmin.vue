<template>
  <div class="w-full">
    <div class="text-left mb-5">
      <div class="text-xl font-bold text-gray-900">예약 관리</div>
    </div>

    <div class="bg-white rounded-lg shadow-lg flex justify-between items-center px-8 py-4 mb-5">
      <FullCalendar class="w-full" defaultView="dayGridMonth" :options="calendarOptions" />
    </div>


  </div>
</template>

<script setup>
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
import timeGridPlugin from '@fullcalendar/timegrid'
import interactionPlugin from '@fullcalendar/interaction'
import koLocale from '@fullcalendar/core/locales/ko';
import { ref } from 'vue';

const isRegistModal = ref(false)
const isUpdateModal = ref(false)
const updateGuid = ref('')
const targetDate = ref('')

const handleDateClick = (ev) => {
  targetDate.value = ev.dateStr
  isRegistModal.value = true
  console.log(targetDate.value)
  console.log(isRegistModal.value)
}
const handleEventClick = (ev) => {
  updateGuid.value = ev.event.id
  isUpdateModal.value = true
}

const calendarOptions = ref({
  plugins: [ dayGridPlugin, timeGridPlugin, interactionPlugin ],
  initialView: 'dayGridMonth',
  events: [],
  eventClick: handleEventClick,
  dateClick: handleDateClick,
  headerToolbar: {
    left: '',
    center: 'title',
    right: 'dayGridMonth,timeGridWeek,timeGridDay'
  },
  locale: koLocale,
  contentHeight: 700
})

</script>

<style lang="scss" scoped>

</style>