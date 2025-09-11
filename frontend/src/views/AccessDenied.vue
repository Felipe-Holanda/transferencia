<template>
  <div class="min-h-screen bg-gray-50 flex flex-col justify-center py-12 sm:px-6 lg:px-8">
    <div class="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
      <div class="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10">
        <div class="text-center">
          <!-- Warning Icon -->
          <div class="mx-auto flex items-center justify-center h-16 w-16 rounded-full bg-red-100 mb-4">
            <svg class="h-8 w-8 text-red-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-2.5L13.732 4c-.77-.833-1.964-.833-2.732 0L4.082 15.5c-.77.833.192 2.5 1.732 2.5z" />
            </svg>
          </div>
          
          <h2 class="text-2xl font-bold text-gray-900 mb-2">Acesso Negado</h2>
          <p class="text-gray-600 mb-6">
            Você não tem permissão para acessar esta área. 
            Apenas administradores podem visualizar este conteúdo.
          </p>
          
          <div class="space-y-3">
            <button
              @click="goToDashboard"
              class="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
            >
              Voltar ao Dashboard
            </button>
            
            <button
              @click="logout"
              class="w-full flex justify-center py-2 px-4 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
            >
              Fazer Logout
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useRouter } from 'vue-router'
import authService from '../services/authService.js'

export default {
  name: 'AccessDenied',
  setup() {
    const router = useRouter()

    const goToDashboard = () => {
      router.push('/dashboard')
    }

    const logout = () => {
      authService.logout()
      router.push('/')
    }

    return {
      goToDashboard,
      logout
    }
  }
}
</script>
