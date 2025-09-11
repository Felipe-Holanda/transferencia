<template>
  <div class="min-h-screen bg-gradient-to-br from-bank-secondary-50 via-white to-bank-primary-50 flex items-center justify-center p-4">
    <!-- Background pattern -->
    <div class="absolute inset-0 opacity-40">
      <div class="absolute inset-0 bg-bank-primary-100" style="background-image: radial-gradient(circle at 1px 1px, rgba(14, 165, 233, 0.15) 1px, transparent 0); background-size: 20px 20px;"></div>
    </div>
    
    <div class="relative w-full max-w-md">
      <div class="text-center mb-8">
        <div class="w-16 h-16 bg-gradient-to-br from-bank-primary-600 to-bank-primary-800 rounded-2xl flex items-center justify-center mx-auto mb-4 shadow-bank-lg">
          <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
          </svg>
        </div>
        <h1 class="text-3xl font-bold text-bank-secondary-800 mb-2">Editar Perfil</h1>
        <p class="text-bank-secondary-600">Atualize suas informações pessoais</p>
      </div>

      <div class="card backdrop-blur-sm bg-white/90 p-8">
        <form class="space-y-6" @submit.prevent="handleUpdateProfile" novalidate>
          <div>
            <label for="fullName" class="form-label">Nome Completo</label>
            <div class="relative">
              <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <svg class="h-5 w-5 text-bank-secondary-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
                </svg>
              </div>
              <input
                id="fullName"
                v-model="form.fullName"
                type="text"
                placeholder="Digite seu nome completo"
                class="form-input pl-10"
                required
                :disabled="isLoading"
              />
            </div>
          </div>

          <div>
            <label for="email" class="form-label">E-mail</label>
            <div class="relative">
              <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <svg class="h-5 w-5 text-bank-secondary-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 12a4 4 0 10-8 0 4 4 0 008 0zm0 0v1.5a2.5 2.5 0 005 0V12a9 9 0 10-9 9m4.5-1.206a8.959 8.959 0 01-4.5 1.207"/>
                </svg>
              </div>
              <input
                id="email"
                v-model="form.email"
                type="email"
                placeholder="Digite seu e-mail"
                class="form-input pl-10"
                required
                :disabled="isLoading"
              />
            </div>
          </div>

          <div>
            <label for="phone" class="form-label">Telefone</label>
            <div class="relative">
              <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <svg class="h-5 w-5 text-bank-secondary-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z"/>
                </svg>
              </div>
              <input
                id="phone"
                v-model="form.phone"
                type="tel"
                placeholder="Digite seu telefone"
                class="form-input pl-10"
                required
                :disabled="isLoading"
              />
            </div>
          </div>

          <div>
            <label for="cpf" class="form-label">CPF</label>
            <div class="relative">
              <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <svg class="h-5 w-5 text-bank-secondary-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 6H5a2 2 0 00-2 2v9a2 2 0 002 2h14a2 2 0 002-2V8a2 2 0 00-2-2h-5m-4 0V5a2 2 0 114 0v1m-4 0a2 2 0 104 0m-5 8a2 2 0 100-4 2 2 0 000 4zm0 0c1.306 0 2.417.835 2.83 2M9 14a3.001 3.001 0 00-2.83 2M15 11h3m-3 4h2"/>
                </svg>
              </div>
              <input
                id="cpf"
                v-model="form.cpf"
                type="text"
                placeholder="Digite seu CPF"
                class="form-input pl-10 bg-gray-100"
                readonly
                disabled
              />
            </div>
            <p class="mt-1 text-sm text-bank-secondary-500">CPF não pode ser alterado</p>
          </div>

          <button 
            type="submit"
            class="btn-primary w-full"
            :disabled="isLoading"
            :class="{ 'opacity-50 cursor-not-allowed': isLoading }"
          >
            <div v-if="isLoading" class="flex items-center justify-center">
              <svg class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
              </svg>
              Salvando...
            </div>
            <div v-else class="flex items-center justify-center">
              <svg class="w-5 h-5 mr-2 inline-block" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
              </svg>
              Salvar Alterações
            </div>
          </button>
        </form>

        <div class="text-center mt-6">
          <button 
            type="button" 
            @click="goBack" 
            class="font-medium text-bank-primary-600 hover:text-bank-primary-500 transition-colors underline"
          >
            Voltar ao Dashboard
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useToast } from 'vue-toastification'
import userService from '../services/userService'

const router = useRouter()
const toast = useToast()

const isLoading = ref(false)
const form = ref({
  fullName: '',
  email: '',
  phone: '',
  cpf: ''
})

const loadUserData = async () => {
  try {
    isLoading.value = true
    const userData = await userService.getCurrentUser()
    
    form.value = {
      fullName: userData.fullName || '',
      email: userData.email || '',
      phone: userData.phone || '',
      cpf: userData.cpf || ''
    }
  } catch (error) {
    toast.error('Erro ao carregar dados do usuário')
    console.error('Erro ao carregar dados:', error)
  } finally {
    isLoading.value = false
  }
}

const handleUpdateProfile = async (event) => {
  event.preventDefault()
  
  if (isLoading.value) return
  
  if (!form.value.fullName || !form.value.email || !form.value.phone) {
    toast.error('Por favor, preencha todos os campos')
    return
  }

  isLoading.value = true

  try {
    await userService.updateUser({
      fullName: form.value.fullName,
      email: form.value.email,
      phone: form.value.phone
    })
    
    toast.success('Perfil atualizado com sucesso!')
    
    // Redirecionar após um breve delay
    setTimeout(() => {
      router.push('/dashboard')
    }, 1500)
    
  } catch (error) {
    const errorMessage = error?.message || 'Erro ao atualizar perfil. Tente novamente.'
    toast.error(errorMessage)
  } finally {
    isLoading.value = false
  }
}

const goBack = () => {
  router.push('/dashboard')
}

onMounted(() => {
  loadUserData()
})
</script>
