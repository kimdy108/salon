import { createRouter, createWebHistory } from 'vue-router'

const loadPage = (path, view) => {
  return () => import(`@/${path}/${view}.vue`)
}

// const loadSubPage = (path, dir, view) => {
//   return () => import(`@/${path}/${dir}/${view}.vue`)
// }

const routes = [
  { path: '/', name: 'IndexPage', component: loadPage('views', 'IndexPage'), meta: { id: 'IndexPage' } },
  { path: '/install', name: 'InstallPage', component: loadPage('views', 'InstallPage'), meta: { id: 'InstallPage' } },
  { path: '/login', name: 'LoginPage', component: loadPage('views', 'LoginPage'), meta: { id: 'LoginPage' } },
  { path: '/main', name: 'MainPage', component: loadPage('views', 'MainPage'), meta: { id: 'MainPage' } },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
