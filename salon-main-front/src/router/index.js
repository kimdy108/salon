import { createRouter, createWebHistory } from 'vue-router'

const loadPage = (path, view) => {
  return () => import(`@/${path}/${view}.vue`)
}

const loadSubPage = (path, dir, view) => {
  return () => import(`@/${path}/${dir}/${view}.vue`)
}

const dashboardChild = [
  { path: '/salon/dashboard/company', name: 'CompanyDashboard', component: loadSubPage('pages', 'dashboard', 'CompanyDashboard'), meta: { id: 'CompanyDashboard' } },
  { path: '/salon/dashboard/schedule', name: 'ScheduleDashboard', component: loadSubPage('pages', 'dashboard', 'ScheduleDashboard'), meta: { id: 'ScheduleDashboard' } },
]

const manageChild = [
  { path: '/salon/manage/company', name: 'CompanyAdmin', component: loadSubPage('pages', 'manage/company', 'CompanyAdmin'), meta: { id: 'CompanyAdmin' } },
  { path: '/salon/manage/user', name: 'UserAdmin', component: loadSubPage('pages', 'manage/user', 'UserAdmin'), meta: { id: 'UserAdmin' } },
]

const scheduleChild = [
  { path: '/salon/schedule/employment', name: 'EmploymentAdmin', component: loadSubPage('pages', 'schedule/employment', 'EmploymentAdmin'), meta: { id: 'EmploymentAdmin' } },
  { path: '/salon/schedule/reservation', name: 'ReservationAdmin', component: loadSubPage('pages', 'schedule/reservation', 'ReservationAdmin'), meta: { id: 'ReservationAdmin' } },
]

const settingChild = [
  { path: '/salon/setting/style', name: 'StyleAdmin', component: loadSubPage('pages', 'setting/style', 'StyleAdmin'), meta: { id: 'StyleAdmin' } },
  { path: '/salon/setting/system', name: 'SystemAdmin', component: loadSubPage('pages', 'setting/system', 'SystemAdmin'), meta: { id: 'SystemAdmin' } },
]

const reportChild = [
  { path: '/salon/report/audit', name: 'AuditLog', component: loadSubPage('pages', 'report/audit', 'AuditLog'), meta: { id: 'AuditLog' } },
  { path: '/salon/report/login', name: 'LoginLog', component: loadSubPage('pages', 'report/login', 'LoginLog'), meta: { id: 'LoginLog' } },
]

const systemChild = [
  { path: '/salon/system/admin', name: 'AdminConfig', component: loadSubPage('pages', 'system', 'AdminConfig'), meta: { id: 'AdminConfig' } }
]

const mainChild = [
  { path: '/salon/dashboard', name: 'DashboardMain', children: dashboardChild, redirect: '/salon/dashboard/company' },
  { path: '/salon/manage', name: 'ManageMain', children: manageChild, redirect: '/salon/manage/user' },
  { path: '/salon/schedule', name: 'ScheduleMain', children: scheduleChild, redirect: '/salon/schedule/employment' },
  { path: '/salon/setting', name: 'SettingMain', children: settingChild, redirect: '/salon/setting/style' },
  { path: '/salon/report', name: 'ReportMain', children: reportChild, redirect: '/salon/report/audit' },

  { path: '/salon/system', name: 'SystemMain', children: systemChild, redirect: '/salon/system/admin' },
]

const routes = [
  { path: '/', name: 'IndexPage', component: loadPage('views', 'IndexPage'), meta: { id: 'IndexPage' } },
  { path: '/install', name: 'InstallPage', component: loadPage('views', 'InstallPage'), meta: { id: 'InstallPage' } },
  { path: '/login', name: 'LoginPage', component: loadPage('views', 'LoginPage'), meta: { id: 'LoginPage' } },
  { path: '/main', name: 'MainPage', component: loadPage('views', 'MainPage'), meta: { id: 'MainPage' }, children: mainChild, redirect: '/salon/dashboard/company' },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
