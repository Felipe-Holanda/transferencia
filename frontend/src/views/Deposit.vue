<template>
  <div class="min-h-screen bg-gray-50">
    <!-- Header -->
    <header class="bg-white shadow">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between items-center py-6">
          <div class="flex items-center">
            <button
              @click="goBack"
              class="mr-4 p-2 text-gray-600 hover:text-gray-900 transition-colors"
            >
              <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
              </svg>
            </button>
            <h1 class="text-3xl font-bold text-gray-900">Fazer Depósito</h1>
          </div>
        </div>
      </div>
    </header>

    <!-- Progress Indicator -->
    <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
      <div class="bg-white rounded-lg shadow p-6 mb-6">
        <div class="flex items-center justify-center">
          <div class="flex items-center space-x-8">
            <!-- Step 1 -->
            <div class="flex items-center">
              <div :class="[
                'w-8 h-8 rounded-full flex items-center justify-center text-sm font-medium',
                currentStep >= 1 ? 'bg-green-600 text-white' : 'bg-gray-200 text-gray-600'
              ]">
                1
              </div>
              <span :class="[
                'ml-2 text-sm font-medium',
                currentStep >= 1 ? 'text-green-600' : 'text-gray-500'
              ]">
                Informar Valor
              </span>
            </div>
            
            <!-- Divider -->
            <div :class="[
              'h-0.5 w-16',
              currentStep >= 2 ? 'bg-green-600' : 'bg-gray-200'
            ]"></div>
            
            <!-- Step 2 -->
            <div class="flex items-center">
              <div :class="[
                'w-8 h-8 rounded-full flex items-center justify-center text-sm font-medium',
                currentStep >= 2 ? 'bg-green-600 text-white' : 'bg-gray-200 text-gray-600'
              ]">
                2
              </div>
              <span :class="[
                'ml-2 text-sm font-medium',
                currentStep >= 2 ? 'text-green-600' : 'text-gray-500'
              ]">
                Confirmar
              </span>
            </div>
            
            <!-- Divider -->
            <div :class="[
              'h-0.5 w-16',
              currentStep >= 3 ? 'bg-green-600' : 'bg-gray-200'
            ]"></div>
            
            <!-- Step 3 -->
            <div class="flex items-center">
              <div :class="[
                'w-8 h-8 rounded-full flex items-center justify-center text-sm font-medium',
                currentStep >= 3 ? 'bg-green-600 text-white' : 'bg-gray-200 text-gray-600'
              ]">
                3
              </div>
              <span :class="[
                'ml-2 text-sm font-medium',
                currentStep >= 3 ? 'text-green-600' : 'text-gray-500'
              ]">
                Concluído
              </span>
            </div>
          </div>
        </div>
      </div>

      <!-- Step 1: Valor do Depósito -->
      <div v-if="currentStep === 1" class="bg-white rounded-lg shadow p-6">
        <h2 class="text-xl font-semibold text-gray-900 mb-6">Valor do Depósito</h2>
        
        <div class="max-w-md mx-auto">
          <!-- Informações da Conta -->
          <div v-if="user" class="bg-blue-50 border border-blue-200 rounded-lg p-4 mb-6">
            <h3 class="text-sm font-medium text-blue-800 mb-2">Sua Conta</h3>
            <div class="space-y-1 text-sm text-blue-700">
              <p><strong>Nome:</strong> {{ user.fullName }}</p>
              <p><strong>Conta:</strong> {{ user.accountNumber }}</p>
              <p><strong>Saldo Atual:</strong> {{ formatCurrency(user.balance) }}</p>
            </div>
          </div>

          <!-- Valor do Depósito -->
          <div class="mb-6">
            <label for="amount" class="block text-sm font-medium text-gray-700 mb-2">
              Valor do Depósito
            </label>
            <div class="relative">
              <span class="absolute left-3 top-2 text-gray-500">R$</span>
              <input
                id="amount"
                v-model="displayAmount"
                type="text"
                placeholder="0,00"
                class="w-full pl-10 pr-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-green-500 text-right font-mono"
                @input="handleAmountInput"
                @blur="formatAmountDisplay"
              />
            </div>
            <p class="mt-1 text-sm text-gray-500">Valor mínimo: R$ 0,01</p>
          </div>

          <!-- Resumo do Depósito -->
          <div v-if="form.amount" class="bg-green-50 border border-green-200 rounded-lg p-4 mb-6">
            <h3 class="text-sm font-medium text-green-800 mb-2">Resumo do Depósito</h3>
            <div class="space-y-1 text-sm text-green-700">
              <div class="flex justify-between">
                <span>Valor a depositar:</span>
                <span class="font-medium">{{ formatCurrency(parseFloat(form.amount)) }}</span>
              </div>
              <div class="flex justify-between">
                <span>Taxa:</span>
                <span class="font-medium">{{ formatCurrency(0) }}</span>
              </div>
              <div class="border-t border-green-300 pt-1 mt-2">
                <div class="flex justify-between font-medium">
                  <span>Novo saldo:</span>
                  <span>{{ formatCurrency((user?.balance || 0) + parseFloat(form.amount)) }}</span>
                </div>
              </div>
            </div>
          </div>

          <!-- Actions -->
          <div class="flex justify-between">
            <button
              @click="goBack"
              class="px-6 py-2 border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50 transition-colors"
            >
              Cancelar
            </button>
            <button
              @click="goToConfirmation"
              :disabled="!canProceed"
              :class="[
                'px-6 py-2 rounded-md font-medium transition-colors',
                canProceed 
                  ? 'bg-green-600 text-white hover:bg-green-700' 
                  : 'bg-gray-300 text-gray-500 cursor-not-allowed'
              ]"
            >
              Continuar
            </button>
          </div>
        </div>
      </div>

      <!-- Step 2: Confirmação -->
      <div v-if="currentStep === 2" class="bg-white rounded-lg shadow p-6">
        <h2 class="text-xl font-semibold text-gray-900 mb-6">Confirmar Depósito</h2>
        
        <div class="max-w-md mx-auto">
          <!-- Detalhes do Depósito -->
          <div class="bg-gray-50 rounded-lg p-4 mb-6">
            <h3 class="text-sm font-medium text-gray-700 mb-3">Detalhes do Depósito</h3>
            <div class="space-y-2 text-sm">
              <div class="flex justify-between">
                <span class="text-gray-600">Conta:</span>
                <span class="font-medium">{{ user?.accountNumber }}</span>
              </div>
              <div class="flex justify-between">
                <span class="text-gray-600">Titular:</span>
                <span class="font-medium">{{ user?.fullName }}</span>
              </div>
              <div class="flex justify-between">
                <span class="text-gray-600">Valor:</span>
                <span class="font-medium text-green-600 text-lg">{{ formatCurrency(parseFloat(form.amount)) }}</span>
              </div>
              <div class="flex justify-between">
                <span class="text-gray-600">Taxa:</span>
                <span class="font-medium">{{ formatCurrency(0) }}</span>
              </div>
              <div class="border-t border-gray-300 pt-2 mt-2">
                <div class="flex justify-between">
                  <span class="text-gray-700 font-medium">Saldo atual:</span>
                  <span class="font-medium">{{ formatCurrency(user?.balance || 0) }}</span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-700 font-medium">Novo saldo:</span>
                  <span class="font-bold text-lg text-green-600">{{ formatCurrency((user?.balance || 0) + parseFloat(form.amount)) }}</span>
                </div>
              </div>
            </div>
          </div>

          <!-- Warning -->
          <div class="bg-yellow-50 border border-yellow-200 rounded-lg p-4 mb-6">
            <div class="flex">
              <svg class="w-5 h-5 text-yellow-600 mr-3 mt-0.5" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd" d="M8.257 3.099c.765-1.36 2.722-1.36 3.486 0l5.58 9.92c.75 1.334-.213 2.98-1.742 2.98H4.42c-1.53 0-2.493-1.646-1.743-2.98l5.58-9.92zM11 13a1 1 0 11-2 0 1 1 0 012 0zm-1-8a1 1 0 00-1 1v3a1 1 0 002 0V6a1 1 0 00-1-1z" clip-rule="evenodd"/>
              </svg>
              <div>
                <h3 class="text-sm font-medium text-yellow-800">Atenção</h3>
                <p class="text-sm text-yellow-700 mt-1">
                  Confirme os dados antes de prosseguir. Esta operação será processada imediatamente.
                </p>
              </div>
            </div>
          </div>

          <!-- Actions -->
          <div class="flex justify-between">
            <button
              @click="goToPreviousStep"
              class="px-6 py-2 border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50 transition-colors"
            >
              Voltar
            </button>
            <button
              @click="confirmDeposit"
              :disabled="depositLoading"
              :class="[
                'px-6 py-2 rounded-md font-medium transition-colors',
                depositLoading 
                  ? 'bg-gray-300 text-gray-500 cursor-not-allowed' 
                  : 'bg-green-600 text-white hover:bg-green-700'
              ]"
            >
              <div v-if="depositLoading" class="flex items-center">
                <div class="animate-spin rounded-full h-4 w-4 border-2 border-white border-t-transparent mr-2"></div>
                Processando...
              </div>
              <span v-else>Confirmar Depósito</span>
            </button>
          </div>
        </div>
      </div>

      <!-- Step 3: Resultado -->
      <div v-if="currentStep === 3" class="bg-white rounded-lg shadow p-6">
        <div class="text-center max-w-md mx-auto">
          <!-- Success Icon -->
          <div class="mx-auto flex items-center justify-center h-16 w-16 rounded-full bg-green-100 mb-4">
            <svg class="h-8 w-8 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
            </svg>
          </div>
          
          <h2 class="text-2xl font-bold text-gray-900 mb-2">Depósito Realizado!</h2>
          <p class="text-gray-600 mb-6">Seu depósito foi processado com sucesso.</p>
          
          <!-- Deposit Details -->
          <div v-if="depositResult" class="bg-gray-50 rounded-lg p-6 text-left mb-6">
            <h3 class="text-lg font-medium text-gray-900 mb-4">Comprovante</h3>
            <div class="space-y-2 text-sm">
              <div class="flex justify-between">
                <span class="text-gray-600">ID da Transação:</span>
                <span class="font-mono text-xs">{{ depositResult.transactionHash?.substring(0, 16) }}...</span>
              </div>
              <div class="flex justify-between">
                <span class="text-gray-600">Valor Depositado:</span>
                <span class="font-medium text-green-600">{{ formatCurrency(depositResult.amount) }}</span>
              </div>
              <div class="flex justify-between">
                <span class="text-gray-600">Data:</span>
                <span class="font-medium">{{ formatDate(depositResult.targetDate) }}</span>
              </div>
              <div class="flex justify-between">
                <span class="text-gray-600">Status:</span>
                <span class="font-medium text-green-600">{{ depositResult.status }}</span>
              </div>
              <div class="border-t border-gray-300 pt-2 mt-2">
                <div class="flex justify-between">
                  <span class="text-gray-700 font-medium">Novo Saldo:</span>
                  <span class="font-bold text-lg text-green-600">{{ formatCurrency(depositResult.recipient?.balance || 0) }}</span>
                </div>
              </div>
            </div>
          </div>

          <!-- Actions -->
          <div class="flex flex-col sm:flex-row justify-center space-y-2 sm:space-y-0 sm:space-x-4">
            <button
              @click="goToDashboard"
              class="px-6 py-2 bg-green-600 text-white rounded-md hover:bg-green-700 transition-colors"
            >
              Voltar ao Dashboard
            </button>
            <button
              @click="startNewDeposit"
              class="px-6 py-2 border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50 transition-colors"
            >
              Novo Depósito
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import userService from '../services/userService.js'
import transactionService from '../services/transactionService.js'

export default {
  name: 'Deposit',
  setup() {
    const router = useRouter()
    
    // Estado reativo
    const currentStep = ref(1)
    const user = ref(null)
    
    // Form data
    const form = ref({
      amount: ''
    })
    
    // Display amount for currency mask
    const displayAmount = ref('')
    
    // Estados de carregamento
    const depositLoading = ref(false)
    const depositResult = ref(null)

    // Computed
    const canProceed = computed(() => {
      return form.value.amount && 
             parseFloat(form.value.amount) > 0
    })

    // Métodos
    const goBack = () => {
      router.push('/dashboard')
    }

    const goToDashboard = () => {
      router.push('/dashboard')
    }

    const goToPreviousStep = () => {
      if (currentStep.value > 1) {
        currentStep.value--
      }
    }

    const goToConfirmation = () => {
      if (canProceed.value) {
        currentStep.value = 2
      }
    }

    const startNewDeposit = () => {
      // Reset form
      currentStep.value = 1
      form.value = { amount: '' }
      displayAmount.value = ''
      depositResult.value = null
      
      // Recarrega dados do usuário
      loadUserData()
    }

    // Formata valores monetários
    const formatCurrency = (value) => {
      return transactionService.formatCurrency(value)
    }

    // Formata datas
    const formatDate = (dateString) => {
      return transactionService.formatDate(dateString)
    }

    // Handle amount input with currency mask
    const handleAmountInput = (event) => {
      let value = event.target.value
      
      // Remove todos os caracteres não numéricos
      value = value.replace(/\D/g, '')
      
      if (value === '') {
        displayAmount.value = ''
        form.value.amount = ''
        return
      }
      
      // Converte para centavos
      const cents = parseInt(value)
      const reais = cents / 100
      
      // Atualiza o valor real do form
      form.value.amount = reais.toString()
      
      // Formata para exibição
      displayAmount.value = formatCurrencyInput(reais)
    }

    // Formata o valor para exibição com máscara de moeda
    const formatCurrencyInput = (value) => {
      return new Intl.NumberFormat('pt-BR', {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
      }).format(value)
    }

    // Formata o display do amount quando o campo perde o foco
    const formatAmountDisplay = () => {
      if (form.value.amount) {
        const value = parseFloat(form.value.amount)
        displayAmount.value = formatCurrencyInput(value)
      }
    }

    // Carregar dados do usuário
    const loadUserData = async () => {
      try {
        const userData = await userService.getCurrentUser()
        user.value = userData
      } catch (error) {
        console.error('Erro ao carregar dados do usuário:', error)
      }
    }

    // Confirmar depósito
    const confirmDeposit = async () => {
      try {
        depositLoading.value = true
        
        const depositData = {
          amount: parseFloat(form.value.amount)
        }
        
        const result = await transactionService.makeDeposit(depositData)
        depositResult.value = result
        currentStep.value = 3
        
        // Atualizar dados do usuário
        await loadUserData()
        
      } catch (error) {
        console.error('Erro ao realizar depósito:', error)
        // Aqui você pode adicionar tratamento de erro
      } finally {
        depositLoading.value = false
      }
    }

    // Inicialização
    onMounted(() => {
      loadUserData()
    })

    return {
      // Estado
      currentStep,
      user,
      form,
      displayAmount,
      depositLoading,
      depositResult,
      
      // Computed
      canProceed,
      
      // Métodos
      goBack,
      goToDashboard,
      goToPreviousStep,
      goToConfirmation,
      startNewDeposit,
      formatCurrency,
      formatDate,
      handleAmountInput,
      formatAmountDisplay,
      confirmDeposit
    }
  }
}
</script>

<style scoped>
/* Animação para o loader */
@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.animate-spin {
  animation: spin 1s linear infinite;
}

/* Estilo para o campo de valor com máscara de moeda */
input[type="text"]#amount {
  font-family: monospace;
  text-align: right;
  padding-right: 12px;
}

/* Estilo para fonte monoespaçada */
.font-mono {
  font-family: ui-monospace, SFMono-Regular, "SF Mono", Consolas, "Liberation Mono", Menlo, monospace;
}
</style>
