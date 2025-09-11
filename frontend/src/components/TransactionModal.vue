<template>
  <!-- Modal Overlay -->
  <div
    v-if="isOpen"
    class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4"
    @click="closeModal"
  >
    <!-- Modal Content -->
    <div
      class="bg-white rounded-lg shadow-xl max-w-md w-full max-h-[90vh] overflow-y-auto"
      @click.stop
    >
      <!-- Modal Header -->
      <div class="flex items-center justify-between p-6 border-b border-gray-200">
        <h3 class="text-lg font-semibold text-gray-900">Detalhes da Transação</h3>
        <button
          @click="closeModal"
          class="text-gray-400 hover:text-gray-600 transition-colors"
        >
          <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
          </svg>
        </button>
      </div>

      <!-- Loading State -->
      <div v-if="loading" class="p-6">
        <div class="flex items-center justify-center py-8">
          <div class="animate-spin rounded-full h-8 w-8 border-2 border-blue-600 border-t-transparent"></div>
          <p class="ml-3 text-gray-600">Carregando detalhes...</p>
        </div>
      </div>

      <!-- Error State -->
      <div v-else-if="error" class="p-6">
        <div class="text-center py-8">
          <svg class="w-12 h-12 text-red-500 mx-auto mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
          <p class="text-red-600 font-medium">{{ error }}</p>
        </div>
      </div>

      <!-- Transaction Details -->
      <div v-else-if="transaction" class="p-6">
        <div class="space-y-4">
          <!-- Transaction ID -->
          <div class="bg-gray-50 rounded-lg p-4">
            <span class="block text-sm font-medium text-gray-700 mb-1">ID da Transação</span>
            <p class="text-sm text-gray-900 font-mono break-all">{{ transaction.transactionId }}</p>
          </div>

          <!-- Amount and Direction -->
          <div class="grid grid-cols-2 gap-4">
            <div>
              <span class="block text-sm font-medium text-gray-700 mb-1">Valor</span>
              <p class="text-lg font-semibold" :class="transaction.direction === 'INCOMING' ? 'text-green-600' : 'text-red-600'">
                {{ getTransactionPrefix(transaction.direction) }}{{ formatCurrency(transaction.amount) }}
              </p>
            </div>
            <div>
              <span class="block text-sm font-medium text-gray-700 mb-1">Tipo</span>
              <p class="text-lg font-semibold text-gray-900">{{ transactionService.formatTransactionType(transaction.type) }}</p>
            </div>
          </div>

          <!-- Transaction Info -->
          <div class="border-t pt-4">
            <h4 class="text-sm font-medium text-gray-700 mb-3">Informações da Transação</h4>
            <div class="space-y-2">
              <div>
                <span class="block text-xs text-gray-500">Remetente</span>
                <p class="text-sm text-gray-900">{{ transaction.senderName }}</p>
              </div>
              <div>
                <span class="block text-xs text-gray-500">Data</span>
                <p class="text-sm text-gray-900">{{ formatDate(transaction.date) }}</p>
              </div>
              <div>
                <span class="block text-xs text-gray-500">Direção</span>
                <p class="text-sm text-gray-900" :class="transaction.direction === 'INCOMING' ? 'text-green-600' : 'text-red-600'">
                  {{ transaction.direction === 'INCOMING' ? 'Recebido' : 'Enviado' }}
                </p>
              </div>
            </div>
          </div>

          <!-- Description -->
          <div class="border-t pt-4">
            <span class="block text-sm font-medium text-gray-700 mb-1">Descrição</span>
            <p class="text-sm text-gray-900">{{ transaction.description }}</p>
          </div>

          <!-- Transaction Summary -->
          <div class="border-t pt-4 bg-blue-50 rounded-lg p-4">
            <div class="flex justify-between items-center">
              <span class="text-sm font-medium text-gray-700">Status</span>
              <span :class="[
                'inline-flex px-2 py-1 text-sm font-semibold rounded-full',
                transactionService.getTransactionStatusColor(transaction.status)
              ]">
                {{ transactionService.formatTransactionStatus(transaction.status) }}
              </span>
            </div>
          </div>
        </div>
      </div>

      <!-- Modal Footer -->
      <div class="px-6 py-4 border-t border-gray-200 bg-gray-50 rounded-b-lg">
        <div class="flex justify-end">
          <button
            @click="closeModal"
            class="px-4 py-2 bg-gray-600 text-white rounded-lg hover:bg-gray-700 transition-colors"
          >
            Fechar
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import transactionService from '../services/transactionService.js'

export default {
  name: 'TransactionModal',
  props: {
    isOpen: {
      type: Boolean,
      default: false
    },
    transaction: {
      type: Object,
      default: null
    }
  },
  emits: ['close'],
  setup(props, { emit }) {
    // Estado reativo
    const loading = ref(false)
    const error = ref(null)

    // Fecha o modal
    const closeModal = () => {
      emit('close')
    }

    // Formata valores monetários
    const formatCurrency = (value) => {
      return transactionService.formatCurrency(value)
    }

    // Formata datas
    const formatDate = (dateString) => {
      return transactionService.formatDate(dateString)
    }

    // Retorna o prefixo da transação
    const getTransactionPrefix = (direction) => {
      return transactionService.getTransactionPrefix(direction)
    }

    return {
      // Estado
      loading,
      error,
      
      // Métodos
      closeModal,
      formatCurrency,
      formatDate,
      getTransactionPrefix,
      
      // Services
      transactionService
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

/* Estilo para quebra de linha em texto longo */
.break-all {
  word-break: break-all;
}

/* Estilo para fonte monoespaçada */
.font-mono {
  font-family: ui-monospace, SFMono-Regular, "SF Mono", Consolas, "Liberation Mono", Menlo, monospace;
}
</style>
