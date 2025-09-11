import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Cadastro from '../views/Cadastro.vue'
import Dashboard from '../views/Dashboard.vue'
import Transfer from '../views/Transfer.vue'
import Deposit from '../views/Deposit.vue'
import AdminDashboard from '../views/AdminDashboard.vue'
import AccessDenied from '../views/AccessDenied.vue'
import ForgotPassword from '../views/ForgotPassword.vue'
import ResetPassword from '../views/ResetPassword.vue'
import EditProfile from '../views/EditProfile.vue'
import ChangePassword from '../views/ChangePassword.vue'
import authService from '../services/authService.js'

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login,
    meta: { requiresGuest: true }
  },
  {
    path: '/cadastro',
    name: 'Cadastro',
    component: Cadastro,
    meta: { requiresGuest: true }
  },
  {
    path: '/forgot-password',
    name: 'ForgotPassword',
    component: ForgotPassword,
    meta: { requiresGuest: true }
  },
  {
    path: '/reset-password',
    name: 'ResetPassword',
    component: ResetPassword,
    meta: { requiresGuest: true }
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: Dashboard,
    meta: { requiresAuth: true }
  },
  {
    path: '/transfer',
    name: 'Transfer',
    component: Transfer,
    meta: { requiresAuth: true }
  },
  {
    path: '/deposit',
    name: 'Deposit',
    component: Deposit,
    meta: { requiresAuth: true }
  },
  {
    path: '/admin',
    name: 'AdminDashboard',
    component: AdminDashboard,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  {
    path: '/edit-profile',
    name: 'EditProfile',
    component: EditProfile,
    meta: { requiresAuth: true }
  },
  {
    path: '/change-password',
    name: 'ChangePassword',
    component: ChangePassword,
    meta: { requiresAuth: true }
  },
  {
    path: '/access-denied',
    name: 'AccessDenied',
    component: AccessDenied,
    meta: { requiresAuth: true }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// Guard de navegação
router.beforeEach((to, from, next) => {
  const isAuthenticated = authService.isAuthenticated()
  
  if (to.meta.requiresAuth && !isAuthenticated) {
    next('/')
    return
  }
  
  // Se a rota requer admin e o usuário não é admin
  if (to.meta.requiresAdmin && isAuthenticated) {
    const user = authService.getCurrentUser()
    
    if (!user || user.role !== 'ADMIN') {
      next('/access-denied')
      return
    }
  }
  
  // Se a rota é para visitantes e o usuário está autenticado
  if (to.meta.requiresGuest && isAuthenticated) {
    next('/dashboard')
    return
  }
  
  next()
})

export default router
