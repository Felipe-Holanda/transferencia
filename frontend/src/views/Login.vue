<template>
  <div class="min-h-screen bg-gradient-to-br from-bank-secondary-50 via-white to-bank-primary-50 flex items-center justify-center p-4">
    <!-- Background pattern -->
    <div class="absolute inset-0 opacity-40">
      <div class="absolute inset-0 bg-bank-primary-100" style="background-image: radial-gradient(circle at 1px 1px, rgba(14, 165, 233, 0.15) 1px, transparent 0); background-size: 20px 20px;"></div>
    </div>
    
    <div class="relative w-full max-w-md">
      <div class="text-center mb-8">
        <div class="w-16 h-16 bg-gradient-to-br from-bank-primary-600 to-bank-primary-800 rounded-2xl flex items-center justify-center mx-auto mb-4 shadow-bank-lg">
          <svg class="w-8 h-8 text-white" fill="currentColor" viewBox="0 0 20 20">
            <path d="M4 4a2 2 0 00-2 2v1h16V6a2 2 0 00-2-2H4z"/>
            <path fill-rule="evenodd" d="M18 9H2v5a2 2 0 002 2h12a2 2 0 002-2V9zM4 13a1 1 0 011-1h1a1 1 0 110 2H5a1 1 0 01-1-1zm5-1a1 1 0 100 2h1a1 1 0 100-2H9z" clip-rule="evenodd"/>
          </svg>
        </div>
        <h1 class="text-3xl font-bold text-bank-secondary-800 mb-2">BankVue</h1>
        <p class="text-bank-secondary-600">Acesse sua conta com segurança</p>
      </div>

      <div class="card backdrop-blur-sm bg-white/90 p-8">
        <form class="space-y-6" @submit.prevent="handleLogin" novalidate>
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
              />
            </div>
          </div>

          <div>
            <label for="password" class="form-label">Senha</label>
            <div class="relative">
              <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <svg class="h-5 w-5 text-bank-secondary-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"/>
                </svg>
              </div>
              <input
                id="password"
                v-model="form.password"
                :type="showPassword ? 'text' : 'password'"
                placeholder="Digite sua senha"
                class="form-input pl-10 pr-10"
                required
              />
              <button
                type="button"
                @click="togglePasswordVisibility"
                class="absolute inset-y-0 right-0 pr-3 flex items-center"
              >
                <svg v-if="showPassword" class="h-5 w-5 text-bank-secondary-400 hover:text-bank-secondary-600 transition-colors" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.543-7a9.97 9.97 0 011.563-3.029m5.858.908a3 3 0 114.243 4.243M9.878 9.878l4.242 4.242M9.878 9.878L3 3m6.878 6.878L21 21"/>
                </svg>
                <svg v-else class="h-5 w-5 text-bank-secondary-400 hover:text-bank-secondary-600 transition-colors" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"/>
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"/>
                </svg>
              </button>
            </div>
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
              Carregando...
            </div>
            <div v-else class="flex items-center justify-center">
              <svg class="w-5 h-5 mr-2 inline-block" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 16l-4-4m0 0l4-4m-4 4h14m-5 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h7a3 3 0 013 3v1"/>
              </svg>
              Entrar na Conta
            </div>
          </button>

          <div class="text-center">
            <span class="text-bank-secondary-600">Não tem uma conta?</span>
            <button 
              type="button" 
              @click="goToCadastro" 
              class="ml-1 font-medium text-bank-primary-600 hover:text-bank-primary-500 transition-colors underline"
            >
              Cadastre-se agora
            </button>
          </div>
        </form>

        <div class="mt-6 p-4 bg-bank-accent-50 border border-bank-accent-200 rounded-lg">
          <div class="flex items-start">
            <svg class="w-5 h-5 text-bank-accent-600 mt-0.5 mr-3 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
              <path fill-rule="evenodd" d="M8.257 3.099c.765-1.36 2.722-1.36 3.486 0l5.58 9.92c.75 1.334-.213 2.98-1.742 2.98H4.42c-1.53 0-2.493-1.646-1.743-2.98l5.58-9.92zM11 13a1 1 0 11-2 0 1 1 0 012 0zm-1-8a1 1 0 00-1 1v3a1 1 0 002 0V6a1 1 0 00-1-1z" clip-rule="evenodd"/>
            </svg>
            <div>
              <h3 class="text-sm font-medium text-bank-accent-800">Segurança em primeiro lugar</h3>
              <p class="text-sm text-bank-accent-700 mt-1">Nunca compartilhe suas credenciais. Nossa equipe nunca solicitará sua senha por e-mail ou telefone.</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useToast } from 'vue-toastification'
import authService from '../services/authService'

const router = useRouter()
const toast = useToast()

const form = ref({
  email: '',
  password: ''
})

const isLoading = ref(false)
const showPassword = ref(false)

const togglePasswordVisibility = () => {
  showPassword.value = !showPassword.value
}

const handleLogin = async (event) => {
  // Prevenir comportamento padrão do formulário
  event.preventDefault()
  
  if (isLoading.value) return
  
  if (!form.value.email || !form.value.password) {
    toast.error('Por favor, preencha todos os campos')
    return
  }

  isLoading.value = true

  try {
    await authService.login({
      email: form.value.email,
      password: form.value.password
    })

    toast.success('Login realizado com sucesso!')
    
    // Aguardar um breve momento para o toast aparecer antes de redirecionar
    setTimeout(() => {
      router.push('/dashboard') // ou rota apropriada após login
    }, 500)
    
  } catch (error) {
    const errorMessage = error?.message || error?.response?.data?.message || 'Erro interno do servidor'
    toast.error(errorMessage)
  } finally {
    isLoading.value = false
  }
}

const goToCadastro = () => {
  router.push('/cadastro')
}
</script>
