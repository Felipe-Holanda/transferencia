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
            <h1 class="text-3xl font-bold text-gray-900">Nova Transferência</h1>
          </div>
        </div>
      </div>
    </header>

    <!-- Progress Indicator -->
    <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
      <div class="bg-white rounded-lg shadow p-6 mb-6">
        <div class="flex items-center justify-between">
          <div class="flex items-center space-x-8">
            <!-- Step 1 -->
            <div class="flex items-center">
              <div :class="[
                'w-8 h-8 rounded-full flex items-center justify-center text-sm font-medium',
                currentStep >= 1 ? 'bg-blue-600 text-white' : 'bg-gray-200 text-gray-600'
              ]">
                1
              </div>
              <span :class="[
                'ml-2 text-sm font-medium',
                currentStep >= 1 ? 'text-blue-600' : 'text-gray-500'
              ]">
                Preencher
              </span>
            </div>
            
            <!-- Divider -->
            <div :class="[
              'h-0.5 w-16',
              currentStep >= 2 ? 'bg-blue-600' : 'bg-gray-200'
            ]"></div>
            
            <!-- Step 2 -->
            <div class="flex items-center">
              <div :class="[
                'w-8 h-8 rounded-full flex items-center justify-center text-sm font-medium',
                currentStep >= 2 ? 'bg-blue-600 text-white' : 'bg-gray-200 text-gray-600'
              ]">
                2
              </div>
              <span :class="[
                'ml-2 text-sm font-medium',
                currentStep >= 2 ? 'text-blue-600' : 'text-gray-500'
              ]">
                Confirmar
              </span>
            </div>
            
            <!-- Divider -->
            <div :class="[
              'h-0.5 w-16',
              currentStep >= 3 ? 'bg-blue-600' : 'bg-gray-200'
            ]"></div>
            
            <!-- Step 3 -->
            <div class="flex items-center">
              <div :class="[
                'w-8 h-8 rounded-full flex items-center justify-center text-sm font-medium',
                currentStep >= 3 ? 'bg-blue-600 text-white' : 'bg-gray-200 text-gray-600'
              ]">
                3
              </div>
              <span :class="[
                'ml-2 text-sm font-medium',
                currentStep >= 3 ? 'text-blue-600' : 'text-gray-500'
              ]">
                Resultado
              </span>
            </div>
          </div>
        </div>
      </div>

      <!-- Step 1: Form -->
      <div v-if="currentStep === 1" class="bg-white rounded-lg shadow p-6">
        <h2 class="text-xl font-semibold text-gray-900 mb-6">Dados da Transferência</h2>
        
        <form @submit.prevent="validateStep1" class="space-y-6">
          <!-- Conta Destino -->
          <div>
            <label for="targetAccount" class="block text-sm font-medium text-gray-700 mb-2">
              Número da Conta Destino
            </label>
            <input
              id="targetAccount"
              v-model="form.targetAccount"
              type="text"
              placeholder="Digite o número da conta (10 dígitos)"
              maxlength="10"
              :class="[
                'w-full px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500',
                accountError ? 'border-red-500' : 'border-gray-300'
              ]"
              @input="handleAccountInput"
              @blur="searchAccount"
            />
            <div v-if="accountError" class="mt-1 text-sm text-red-600">
              {{ accountError }}
            </div>
            <div v-if="accountLoading" class="mt-2 flex items-center text-sm text-gray-600">
              <div class="animate-spin rounded-full h-4 w-4 border-2 border-blue-600 border-t-transparent mr-2"></div>
              Verificando conta...
            </div>
          </div>

          <!-- Destinatário Info -->
          <div v-if="recipientInfo" class="bg-green-50 border border-green-200 rounded-lg p-4">
            <h3 class="text-sm font-medium text-green-800 mb-2">Destinatário Encontrado</h3>
            <div class="space-y-1 text-sm text-green-700">
              <p><strong>Nome:</strong> {{ recipientInfo.name }}</p>
              <p><strong>Conta:</strong> {{ recipientInfo.accountNumber }}</p>
              <p><strong>CPF:</strong> {{ recipientInfo.cpf }}</p>
            </div>
          </div>

          <!-- Valor -->
          <div>
            <label for="amount" class="block text-sm font-medium text-gray-700 mb-2">
              Valor da Transferência
            </label>
            <div class="relative">
              <span class="absolute left-3 top-2 text-gray-500">R$</span>
              <input
                id="amount"
                v-model="displayAmount"
                type="text"
                placeholder="0,00"
                :disabled="!recipientInfo"
                :class="[
                  'w-full pl-10 pr-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500',
                  !recipientInfo ? 'bg-gray-100 cursor-not-allowed' : 'border-gray-300'
                ]"
                @input="handleAmountInput"
                @blur="formatAmountDisplay"
              />
            </div>
          </div>

          <!-- Data -->
          <div>
            <label for="targetDate" class="block text-sm font-medium text-gray-700 mb-2">
              Data da Transferência
            </label>
            <DatePicker
              v-model="form.targetDate"
              :disabled="!recipientInfo"
              :minDate="today"
              placeholder="Selecione a data da transferência"
              @update:modelValue="handleDateChange"
            />
          </div>

          <!-- Descrição -->
          <div>
            <label for="description" class="block text-sm font-medium text-gray-700 mb-2">
              Descrição (opcional)
            </label>
            <textarea
              id="description"
              v-model="form.description"
              rows="3"
              placeholder="Digite uma descrição para a transferência"
              :disabled="!recipientInfo"
              :class="[
                'w-full px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500',
                !recipientInfo ? 'bg-gray-100 cursor-not-allowed' : 'border-gray-300'
              ]"
            ></textarea>
          </div>

          <!-- Cálculo da Taxa -->
          <div v-if="taxCalculation" class="bg-blue-50 border border-blue-200 rounded-lg p-4">
            <h3 class="text-sm font-medium text-blue-800 mb-2">Cálculo da Taxa</h3>
            <div class="space-y-1 text-sm text-blue-700">
              <div class="flex justify-between">
                <span>Valor da transferência:</span>
                <span>{{ formatCurrency(parseFloat(form.amount)) }}</span>
              </div>
              <div class="flex justify-between">
                <span>Taxa calculada:</span>
                <span>{{ formatCurrency(taxCalculation.calculatedTax - parseFloat(form.amount)) }}</span>
              </div>
              <div class="border-t border-blue-300 pt-1 mt-2">
                <div class="flex justify-between font-medium">
                  <span>Total a ser debitado:</span>
                  <span>{{ formatCurrency(taxCalculation.calculatedTax) }}</span>
                </div>
              </div>
            </div>
          </div>

          <!-- Tax Loading -->
          <div v-if="taxLoading" class="bg-gray-50 border border-gray-200 rounded-lg p-4">
            <div class="flex items-center text-sm text-gray-600">
              <div class="animate-spin rounded-full h-4 w-4 border-2 border-blue-600 border-t-transparent mr-2"></div>
              Calculando taxa...
            </div>
          </div>

          <!-- Actions -->
          <div class="flex justify-end space-x-4 pt-6">
            <button
              type="button"
              @click="goBack"
              class="px-6 py-2 border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50 transition-colors"
            >
              Cancelar
            </button>
            <button
              type="submit"
              :disabled="!canProceed"
              :class="[
                'px-6 py-2 rounded-md font-medium transition-colors',
                canProceed 
                  ? 'bg-blue-600 text-white hover:bg-blue-700' 
                  : 'bg-gray-300 text-gray-500 cursor-not-allowed'
              ]"
              :title="!canProceed ? 'Preencha todos os campos obrigatórios' : ''"
            >
              {{ taxCalculation ? 'Continuar' : 'Calcular Taxa e Continuar' }}
            </button>
          </div>
        </form>
      </div>

      <!-- Step 2: Confirmation -->
      <div v-if="currentStep === 2" class="bg-white rounded-lg shadow p-6">
        <h2 class="text-xl font-semibold text-gray-900 mb-6">Confirmar Transferência</h2>
        
        <div class="space-y-6">
          <!-- Destinatário -->
          <div class="bg-gray-50 rounded-lg p-4">
            <h3 class="text-sm font-medium text-gray-700 mb-3">Destinatário</h3>
            <div class="space-y-2 text-sm">
              <div class="flex justify-between">
                <span class="text-gray-600">Nome:</span>
                <span class="font-medium">{{ recipientInfo.name }}</span>
              </div>
              <div class="flex justify-between">
                <span class="text-gray-600">Conta:</span>
                <span class="font-medium">{{ recipientInfo.accountNumber }}</span>
              </div>
              <div class="flex justify-between">
                <span class="text-gray-600">CPF:</span>
                <span class="font-medium">{{ recipientInfo.cpf }}</span>
              </div>
            </div>
          </div>

          <!-- Detalhes da Transferência -->
          <div class="bg-gray-50 rounded-lg p-4">
            <h3 class="text-sm font-medium text-gray-700 mb-3">Detalhes da Transferência</h3>
            <div class="space-y-2 text-sm">
              <div class="flex justify-between">
                <span class="text-gray-600">Valor:</span>
                <span class="font-medium">{{ formatCurrency(parseFloat(form.amount)) }}</span>
              </div>
              <div class="flex justify-between">
                <span class="text-gray-600">Data:</span>
                <span class="font-medium">{{ formatDate(form.targetDate) }}</span>
              </div>
              <div class="flex justify-between">
                <span class="text-gray-600">Descrição:</span>
                <span class="font-medium">{{ form.description || 'Sem descrição' }}</span>
              </div>
              <div class="flex justify-between">
                <span class="text-gray-600">Taxa:</span>
                <span class="font-medium text-red-600">{{ formatCurrency(taxCalculation.calculatedTax - parseFloat(form.amount)) }}</span>
              </div>
              <div class="border-t border-gray-300 pt-2 mt-2">
                <div class="flex justify-between">
                  <span class="text-gray-700 font-medium">Total a ser debitado:</span>
                  <span class="font-bold text-lg">{{ formatCurrency(taxCalculation.calculatedTax) }}</span>
                </div>
              </div>
            </div>
          </div>

          <!-- Actions -->
          <div class="flex justify-between pt-6">
            <button
              @click="goToPreviousStep"
              class="px-6 py-2 border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50 transition-colors"
            >
              Voltar
            </button>
            <button
              @click="confirmTransfer"
              :disabled="transferLoading"
              :class="[
                'px-6 py-2 rounded-md font-medium transition-colors',
                transferLoading 
                  ? 'bg-gray-300 text-gray-500 cursor-not-allowed' 
                  : 'bg-green-600 text-white hover:bg-green-700'
              ]"
            >
              <div v-if="transferLoading" class="flex items-center">
                <div class="animate-spin rounded-full h-4 w-4 border-2 border-white border-t-transparent mr-2"></div>
                Processando...
              </div>
              <span v-else>Confirmar Transferência</span>
            </button>
          </div>
        </div>
      </div>

      <!-- Step 3: Result -->
      <div v-if="currentStep === 3" class="bg-white rounded-lg shadow p-6">
        <div class="text-center">
          <!-- Success Icon -->
          <div class="mx-auto flex items-center justify-center h-16 w-16 rounded-full bg-green-100 mb-4">
            <svg class="h-8 w-8 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
            </svg>
          </div>
          
          <h2 class="text-2xl font-bold text-gray-900 mb-2">Transferência Realizada!</h2>
          <p class="text-gray-600 mb-6">Sua transferência foi processada com sucesso.</p>
          
          <!-- Transaction Details -->
          <div v-if="transferResult" class="bg-gray-50 rounded-lg p-6 text-left max-w-md mx-auto">
            <h3 class="text-lg font-medium text-gray-900 mb-4">Comprovante</h3>
            <div class="space-y-2 text-sm">
              <div class="flex justify-between">
                <span class="text-gray-600">ID da Transação:</span>
                <span class="font-mono text-xs">{{ transferResult.transactionHash?.substring(0, 16) }}...</span>
              </div>
              <div class="flex justify-between">
                <span class="text-gray-600">Valor:</span>
                <span class="font-medium">{{ formatCurrency(transferResult.amount) }}</span>
              </div>
              <div class="flex justify-between">
                <span class="text-gray-600">Taxa:</span>
                <span class="font-medium">{{ formatCurrency(transferResult.taxes) }}</span>
              </div>
              <div class="flex justify-between">
                <span class="text-gray-600">Data:</span>
                <span class="font-medium">{{ formatDate(transferResult.targetDate) }}</span>
              </div>
              <div class="flex justify-between">
                <span class="text-gray-600">Status:</span>
                <span class="font-medium text-yellow-600">{{ transferResult.status }}</span>
              </div>
            </div>
          </div>

          <!-- Actions -->
          <div class="flex justify-center space-x-4 mt-8">
            <button
              @click="goToDashboard"
              class="px-6 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition-colors"
            >
              Voltar ao Dashboard
            </button>
            <button
              @click="startNewTransfer"
              class="px-6 py-2 border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50 transition-colors"
            >
              Nova Transferência
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
import DatePicker from '../components/DatePicker.vue'

export default {
  name: 'Transfer',
  components: {
    DatePicker
  },
  setup() {
    const router = useRouter()
    
    // Estado reativo
    const currentStep = ref(1)
    const today = ref('')
    
    // Form data
    const form = ref({
      targetAccount: '',
      amount: '',
      targetDate: '',
      description: ''
    })
    
    // Display amount for currency mask
    const displayAmount = ref('')
    
    // Estados de carregamento e dados
    const accountLoading = ref(false)
    const accountError = ref('')
    const recipientInfo = ref(null)
    
    const taxLoading = ref(false)
    const taxCalculation = ref(null)
    
    const transferLoading = ref(false)
    const transferResult = ref(null)

    // Computed
    const canProceed = computed(() => {
      return recipientInfo.value && 
             form.value.amount && 
             parseFloat(form.value.amount) > 0 &&
             form.value.targetDate && 
             !accountError.value &&
             !accountLoading.value
    })

    // Métodos
    const goBack = () => {
      router.push('/dashboard')
    }

    const goToDashboard = () => {
      router.push('/dashboard')
    }

    const startNewTransfer = () => {
      // Reset form
      currentStep.value = 1
      form.value = {
        targetAccount: '',
        amount: '',
        targetDate: '',
        description: ''
      }
      displayAmount.value = ''
      recipientInfo.value = null
      taxCalculation.value = null
      transferResult.value = null
      accountError.value = ''
    }

    const goToPreviousStep = () => {
      if (currentStep.value > 1) {
        currentStep.value--
      }
    }

    // Formata valores monetários
    const formatCurrency = (value) => {
      return transactionService.formatCurrency(value)
    }

    // Formata datas
    const formatDate = (dateString) => {
      return transactionService.formatDate(dateString)
    }

    // Handle account input
    const handleAccountInput = () => {
      accountError.value = ''
      recipientInfo.value = null
      
      // Reset campos dependentes
      form.value.amount = ''
      displayAmount.value = ''
      form.value.targetDate = ''
      taxCalculation.value = null
    }

    // Buscar conta
    const searchAccount = async () => {
      if (!form.value.targetAccount || form.value.targetAccount.length !== 10) {
        if (form.value.targetAccount) {
          accountError.value = 'Número da conta deve ter 10 dígitos'
        }
        return
      }

      try {
        accountLoading.value = true
        accountError.value = ''
        
        const account = await userService.getUserByAccountNumber(form.value.targetAccount)
        recipientInfo.value = account
        
      } catch (error) {
        console.error('Erro ao buscar conta:', error)
        accountError.value = 'Usuário não encontrado'
        recipientInfo.value = null
      } finally {
        accountLoading.value = false
      }
    }

    // Handle amount input with currency mask
    const handleAmountInput = (event) => {
      let value = event.target.value
      
      // Remove todos os caracteres não numéricos
      value = value.replace(/\D/g, '')
      
      if (value === '') {
        displayAmount.value = ''
        form.value.amount = ''
        taxCalculation.value = null
        return
      }
      
      // Converte para centavos
      const cents = parseInt(value)
      const reais = cents / 100
      
      // Atualiza o valor real do form
      form.value.amount = reais.toString()
      
      // Formata para exibição
      displayAmount.value = formatCurrencyInput(reais)
      
      // Calcula a taxa
      calculateTax()
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

    // Handle amount change
    const handleAmountChange = () => {
      calculateTax()
    }

    // Handle date change
    const handleDateChange = () => {
      calculateTax()
    }

    // Calcular taxa
    const calculateTax = async () => {
      console.log('calculateTax chamado:', {
        amount: form.value.amount,
        targetDate: form.value.targetDate,
        recipientInfo: !!recipientInfo.value
      })
      
      if (!form.value.amount || !form.value.targetDate || !recipientInfo.value) {
        console.log('Condições não atendidas para calcular taxa')
        taxCalculation.value = null
        return
      }

      const amount = parseFloat(form.value.amount)
      if (amount <= 0) {
        console.log('Valor inválido:', amount)
        taxCalculation.value = null
        return
      }

      try {
        taxLoading.value = true
        console.log('Calculando taxa...', { amount, targetDate: form.value.targetDate })
        
        const taxData = await transactionService.calculateTax({
          amount: amount,
          targetDate: form.value.targetDate
        })
        
        console.log('Taxa calculada:', taxData)
        taxCalculation.value = taxData
        
      } catch (error) {
        console.error('Erro ao calcular taxa:', error)
        taxCalculation.value = null
      } finally {
        taxLoading.value = false
      }
    }

    // Validar step 1
    const validateStep1 = async () => {
      if (!canProceed.value) return
      
      // Se a taxa ainda não foi calculada, calcular agora
      if (!taxCalculation.value) {
        await calculateTax()
      }
      
      // Só avança se tiver a taxa calculada
      if (taxCalculation.value) {
        currentStep.value = 2
      }
    }

    // Confirmar transferência
    const confirmTransfer = async () => {
      try {
        transferLoading.value = true
        
        const transferData = {
          targetAccount: form.value.targetAccount,
          amount: parseFloat(form.value.amount),
          targetDate: form.value.targetDate,
          description: form.value.description || 'Transferência via sistema'
        }
        
        const result = await transactionService.createTransaction(transferData)
        transferResult.value = result
        currentStep.value = 3
        
      } catch (error) {
        console.error('Erro ao realizar transferência:', error)
        // Aqui você pode adicionar tratamento de erro
      } finally {
        transferLoading.value = false
      }
    }

    // Inicialização
    onMounted(() => {
      // Setar data mínima como hoje usando timezone de São Paulo
      const now = new Date()
      const saoPauloDate = new Intl.DateTimeFormat('pt-BR', {
        timeZone: 'America/Sao_Paulo',
        year: 'numeric',
        month: '2-digit',
        day: '2-digit'
      }).format(now)
      
      // Converter de DD/MM/YYYY para YYYY-MM-DD
      const [day, month, year] = saoPauloDate.split('/')
      today.value = `${year}-${month}-${day}`
    })

    return {
      // Estado
      currentStep,
      today,
      form,
      displayAmount,
      accountLoading,
      accountError,
      recipientInfo,
      taxLoading,
      taxCalculation,
      transferLoading,
      transferResult,
      
      // Computed
      canProceed,
      
      // Métodos
      goBack,
      goToDashboard,
      startNewTransfer,
      goToPreviousStep,
      formatCurrency,
      formatDate,
      handleAccountInput,
      searchAccount,
      handleAmountInput,
      formatAmountDisplay,
      handleAmountChange,
      handleDateChange,
      validateStep1,
      confirmTransfer
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

/* Estilo para inputs desabilitados */
input:disabled, textarea:disabled {
  background-color: #f9fafb;
  cursor: not-allowed;
}

/* Remove as setas dos inputs de número */
input[type="number"]::-webkit-outer-spin-button,
input[type="number"]::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

input[type="number"] {
  -moz-appearance: textfield;
  appearance: textfield;
}

/* Estilo para o campo de valor com máscara de moeda */
input[type="text"]#amount {
  font-family: monospace;
  text-align: right;
  padding-right: 12px;
}
</style>
