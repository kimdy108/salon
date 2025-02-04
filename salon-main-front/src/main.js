import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { createPinia } from 'pinia'

import '@/assets/css/main.css'
import '@/assets/css/tailwind.css'

import PrimeVue from 'primevue/config';
import Aura from '@primevue/themes/aura'

import 'primeicons/primeicons.css'

import VueSweetalert2 from 'vue-sweetalert2'
import 'sweetalert2/dist/sweetalert2.min.css'

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(VueSweetalert2)
app.use(PrimeVue, { 
  theme: {
    preset: Aura,
  }
})
app.mount('#app')
