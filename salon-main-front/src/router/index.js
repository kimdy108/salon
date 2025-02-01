import { createRouter, createWebHistory } from 'vue-router'

const loadPage = (path, view) => {
  return () => import(`@/${path}/${view}.vue`)
}

// const loadSubPage = (path, dir, view) => {
//   return () => import(`@/${path}/${dir}/${view}.vue`)
// }

const routes = [
  { path: '/', name: 'HomeView', component: loadPage('views', 'HomeView'), meta: { id: 'HomeView' } },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
