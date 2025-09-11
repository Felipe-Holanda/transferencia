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
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 7a2 2 0 012 2m4 0a6 6 0 01-7.743 5.743L11 17H9v2H7v2H4a1 1 0 01-1-1v-2.586a1 1 0 01.293-.707l5.964-5.964A6 6 0 1121 9z"/>
          </svg>
        </div>
        <h1 class="text-3xl font-bold text-bank-secondary-800 mb-2">Redefinir Senha</h1>
        <p class="text-bank-secondary-600">Digite seu e-mail para receber as instruções</p>
      </div>

      <div class="card backdrop-blur-sm bg-white/90 p-8">
        <form class="space-y-6" @submit.prevent="handleSubmit" novalidate>
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
                v-model="email"
                type="email"
                placeholder="Digite seu e-mail cadastrado"
                class="form-input pl-10"
                required
                :disabled="isLoading"
              />
            </div>
            <p class="mt-2 text-sm text-bank-secondary-600">
              Enviaremos um código de verificação de 6 dígitos para este e-mail.
            </p>
          </div>

          <button 
            type="submit"
            class="btn-primary w-full"
            :disabled="isLoading || !email"
            :class="{ 'opacity-50 cursor-not-allowed': isLoading || !email }"
          >
            <div v-if="isLoading" class="flex items-center justify-center">
              <svg class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
              </svg>
              Enviando...
            </div>
            <div v-else class="flex items-center justify-center">
              <svg class="w-5 h-5 mr-2 inline-block" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 4.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"/>
              </svg>
              Enviar Código
            </div>
          </button>

          <div class="text-center space-y-2">
            <button 
              type="button" 
              @click="goToLogin" 
              class="font-medium text-bank-primary-600 hover:text-bank-primary-500 transition-colors underline"
            >
              Voltar para o Login
            </button>
          </div>
        </form>

        <div class="mt-6 p-4 bg-bank-accent-50 border border-bank-accent-200 rounded-lg">
          <div class="flex items-start">
            <svg class="w-5 h-5 text-bank-accent-600 mt-0.5 mr-3 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
              <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z" clip-rule="evenodd"/>
            </svg>
            <div>
              <h3 class="text-sm font-medium text-bank-accent-800">Importante</h3>
              <p class="text-sm text-bank-accent-700 mt-1">Verifique sua caixa de entrada e pasta de spam. O código tem validade limitada.</p>
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

const email = ref('')
const isLoading = ref(false)

const handleSubmit = async (event) => {
  event.preventDefault()
  
  if (isLoading.value) return
  
  if (!email.value) {
    toast.error('Por favor, digite seu e-mail')
    return
  }

  // Validação básica de email
  const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if (!emailPattern.test(email.value)) {
    toast.error('Por favor, digite um e-mail válido')
    return
  }

  isLoading.value = true

  try {
    await authService.requestPasswordReset(email.value)
    
    toast.success('Código enviado com sucesso! Verifique seu e-mail.')
    
    // Redirecionar para a tela de verificação OTP
    setTimeout(() => {
      router.push({ 
        name: 'ResetPassword', 
        query: { email: email.value }
      })
    }, 1000)
    
  } catch (error) {
    const errorMessage = error?.message || 'Erro ao enviar código. Tente novamente.'
    toast.error(errorMessage)
  } finally {
    isLoading.value = false
  }
}

const goToLogin = () => {
  router.push('/')
}
</script>
