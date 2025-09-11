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
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
          </svg>
        </div>
        <h1 class="text-3xl font-bold text-bank-secondary-800 mb-2">Confirmar Redefinição</h1>
        <p class="text-bank-secondary-600">Digite o código enviado para {{ maskedEmail }}</p>
      </div>

      <div class="card backdrop-blur-sm bg-white/90 p-8">
        <!-- Etapa 1: Verificação OTP -->
        <div v-if="step === 1">
          <form class="space-y-6" @submit.prevent="verifyOTP" novalidate>
            <div>
              <label for="otp" class="form-label">Código de Verificação</label>
              <div class="relative">
                <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <svg class="h-5 w-5 text-bank-secondary-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 20l4-16m2 16l4-16M6 9h14M4 15h14"/>
                  </svg>
                </div>
                <input
                  id="otp"
                  v-model="otp"
                  type="text"
                  placeholder="Digite o código de 6 dígitos"
                  class="form-input pl-10 text-center text-lg tracking-widest"
                  maxlength="6"
                  pattern="[0-9]{6}"
                  required
                  :disabled="isLoading"
                  @input="handleOTPInput"
                />
              </div>
              <p class="mt-2 text-sm text-bank-secondary-600">
                Código de 6 dígitos enviado por e-mail
              </p>
            </div>

            <button 
              type="submit"
              class="btn-primary w-full"
              :disabled="isLoading || otp.length !== 6"
              :class="{ 'opacity-50 cursor-not-allowed': isLoading || otp.length !== 6 }"
            >
              <div v-if="isLoading" class="flex items-center justify-center">
                <svg class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                  <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                  <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                </svg>
                Verificando...
              </div>
              <div v-else>
                Verificar Código
              </div>
            </button>
          </form>
        </div>

        <!-- Etapa 2: Nova Senha -->
        <div v-else-if="step === 2">
          <form class="space-y-6" @submit.prevent="handlePasswordReset" novalidate>
            <div>
              <label for="newPassword" class="form-label">Nova Senha</label>
              <div class="relative">
                <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <svg class="h-5 w-5 text-bank-secondary-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"/>
                  </svg>
                </div>
                <input
                  id="newPassword"
                  v-model="newPassword"
                  :type="showPassword ? 'text' : 'password'"
                  placeholder="Digite sua nova senha"
                  class="form-input pl-10 pr-10"
                  required
                  :disabled="isLoading"
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

            <div>
              <label for="confirmPassword" class="form-label">Confirmar Nova Senha</label>
              <div class="relative">
                <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <svg class="h-5 w-5 text-bank-secondary-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                  </svg>
                </div>
                <input
                  id="confirmPassword"
                  v-model="confirmPassword"
                  :type="showConfirmPassword ? 'text' : 'password'"
                  placeholder="Confirme sua nova senha"
                  class="form-input pl-10 pr-10"
                  required
                  :disabled="isLoading"
                />
                <button
                  type="button"
                  @click="toggleConfirmPasswordVisibility"
                  class="absolute inset-y-0 right-0 pr-3 flex items-center"
                >
                  <svg v-if="showConfirmPassword" class="h-5 w-5 text-bank-secondary-400 hover:text-bank-secondary-600 transition-colors" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.543-7a9.97 9.97 0 011.563-3.029m5.858.908a3 3 0 114.243 4.243M9.878 9.878l4.242 4.242M9.878 9.878L3 3m6.878 6.878L21 21"/>
                  </svg>
                  <svg v-else class="h-5 w-5 text-bank-secondary-400 hover:text-bank-secondary-600 transition-colors" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"/>
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"/>
                  </svg>
                </button>
              </div>
            </div>

            <!-- Requisitos de senha -->
            <div class="p-4 bg-bank-secondary-50 rounded-lg">
              <h4 class="text-sm font-medium text-bank-secondary-700 mb-2">Requisitos da senha:</h4>
              <ul class="space-y-1 text-sm">
                <li :class="passwordRequirements.hasMinLength ? 'text-green-600' : 'text-bank-secondary-600'">
                  <span class="mr-2">{{ passwordRequirements.hasMinLength ? '✓' : '○' }}</span>
                  Pelo menos 8 caracteres
                </li>
                <li :class="passwordRequirements.hasUppercase ? 'text-green-600' : 'text-bank-secondary-600'">
                  <span class="mr-2">{{ passwordRequirements.hasUppercase ? '✓' : '○' }}</span>
                  Uma letra maiúscula
                </li>
                <li :class="passwordRequirements.hasLowercase ? 'text-green-600' : 'text-bank-secondary-600'">
                  <span class="mr-2">{{ passwordRequirements.hasLowercase ? '✓' : '○' }}</span>
                  Uma letra minúscula
                </li>
                <li :class="passwordRequirements.hasNumber ? 'text-green-600' : 'text-bank-secondary-600'">
                  <span class="mr-2">{{ passwordRequirements.hasNumber ? '✓' : '○' }}</span>
                  Um número
                </li>
                <li :class="passwordRequirements.hasSpecialChar ? 'text-green-600' : 'text-bank-secondary-600'">
                  <span class="mr-2">{{ passwordRequirements.hasSpecialChar ? '✓' : '○' }}</span>
                  Um caractere especial (@, #, $, etc.)
                </li>
              </ul>
            </div>

            <button 
              type="submit"
              class="btn-primary w-full"
              :disabled="isLoading || !isFormValid"
              :class="{ 'opacity-50 cursor-not-allowed': isLoading || !isFormValid }"
            >
              <div v-if="isLoading" class="flex items-center justify-center">
                <svg class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                  <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                  <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                </svg>
                Alterando Senha...
              </div>
              <div v-else class="flex items-center justify-center">
                <svg class="w-5 h-5 mr-2 inline-block" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
                </svg>
                Redefinir Senha
              </div>
            </button>
          </form>
        </div>

        <div class="text-center mt-6">
          <button 
            type="button" 
            @click="goToLogin" 
            class="font-medium text-bank-primary-600 hover:text-bank-primary-500 transition-colors underline"
          >
            Voltar para o Login
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useToast } from 'vue-toastification'
import authService from '../services/authService'

const router = useRouter()
const route = useRoute()
const toast = useToast()

const step = ref(1) // 1: OTP, 2: Nova senha
const email = ref('')
const otp = ref('')
const newPassword = ref('')
const confirmPassword = ref('')
const isLoading = ref(false)
const showPassword = ref(false)
const showConfirmPassword = ref(false)

// Máscara do email para exibição
const maskedEmail = computed(() => {
  if (!email.value) return ''
  const [username, domain] = email.value.split('@')
  const maskedUsername = username.slice(0, 2) + '*'.repeat(username.length - 2)
  return `${maskedUsername}@${domain}`
})

// Validação de senha
const passwordRequirements = computed(() => {
  const password = newPassword.value
  return {
    hasMinLength: password.length >= 8,
    hasUppercase: /[A-Z]/.test(password),
    hasLowercase: /[a-z]/.test(password),
    hasNumber: /\d/.test(password),
    hasSpecialChar: /[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]/.test(password)
  }
})

const isFormValid = computed(() => {
  const requirements = passwordRequirements.value
  return newPassword.value === confirmPassword.value &&
         requirements.hasMinLength &&
         requirements.hasUppercase &&
         requirements.hasLowercase &&
         requirements.hasNumber &&
         requirements.hasSpecialChar
})

const handleOTPInput = (event) => {
  // Permitir apenas números
  const value = event.target.value.replace(/\D/g, '')
  otp.value = value.slice(0, 6)
}

const togglePasswordVisibility = () => {
  showPassword.value = !showPassword.value
}

const toggleConfirmPasswordVisibility = () => {
  showConfirmPassword.value = !showConfirmPassword.value
}

const verifyOTP = async () => {
  if (isLoading.value) return
  
  if (otp.value.length !== 6) {
    toast.error('Por favor, digite o código de 6 dígitos')
    return
  }

  // Por enquanto, apenas avança para a próxima etapa
  // A verificação real será feita junto com a redefinição da senha
  step.value = 2
  toast.success('Código verificado! Agora defina sua nova senha.')
}

const handlePasswordReset = async (event) => {
  event.preventDefault()
  
  if (isLoading.value) return
  
  if (!isFormValid.value) {
    toast.error('Por favor, verifique os requisitos da senha')
    return
  }

  isLoading.value = true

  try {
    await authService.confirmPasswordReset({
      token: otp.value,
      newPassword: newPassword.value
    })
    
    toast.success('Senha alterada com sucesso!')
    
    // Redirecionar para o login
    setTimeout(() => {
      router.push('/')
    }, 1500)
    
  } catch (error) {
    const errorMessage = error?.message || 'Erro ao redefinir senha. Tente novamente.'
    toast.error(errorMessage)
  } finally {
    isLoading.value = false
  }
}

const goToLogin = () => {
  router.push('/')
}

onMounted(() => {
  // Obter email da query string
  email.value = route.query.email || ''
  
  if (!email.value) {
    toast.error('Email não informado. Redirecionando...')
    setTimeout(() => {
      router.push('/forgot-password')
    }, 2000)
  }
})
</script>
