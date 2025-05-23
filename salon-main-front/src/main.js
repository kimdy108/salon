import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { createPinia } from 'pinia'

import '@/assets/css/main.css'
import '@/assets/css/tailwind.css'

import VueSweetalert2 from 'vue-sweetalert2'
import 'sweetalert2/dist/sweetalert2.min.css'

import PrimeVue from 'primevue/config';
import Aura from '@primevue/themes/aura'

import 'primeicons/primeicons.css'
import ToastService from 'primevue/toastservice';

import { PerfectScrollbarPlugin } from 'vue3-perfect-scrollbar';
import 'vue3-perfect-scrollbar/style.css';

import dayjs from 'dayjs'
import JsonViewer from 'vue-json-viewer'

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(PerfectScrollbarPlugin)
app.component('JsonViewer', JsonViewer)
app.use(VueSweetalert2)
app.use(PrimeVue, { 
  theme: {
    preset: Aura,
    options: {
      darkModeSelector: false,
    }
  }
})
app.use(ToastService)
app.config.globalProperties.$_dayjs = dayjs
app.mount('#app')
