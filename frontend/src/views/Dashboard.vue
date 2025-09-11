<template>
  <div class="min-h-screen bg-gray-50">
    <!-- Header -->
    <header class="bg-white shadow">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between items-center py-6">
          <div class="flex items-center">
            <h1 class="text-3xl font-bold text-gray-900">Dashboard</h1>
          </div>
          <div class="flex items-center space-x-4">
            <!-- Botão Admin (apenas para ADMIN) -->
            <button
              v-if="user && user.role === 'ADMIN'"
              @click="goToAdminDashboard"
              class="bg-purple-600 hover:bg-purple-700 text-white px-4 py-2 rounded-md text-sm font-medium transition-colors"
            >
              Dashboard Admin
            </button>
            
            <!-- Informações do usuário -->
            <div v-if="user" class="text-right">
              <p class="text-sm font-medium text-gray-900">{{ user.fullName }}</p>
              <p class="text-sm text-gray-500">Conta: {{ user.accountNumber }}</p>
            </div>
            
            <!-- Botão de logout -->
            <button
              @click="handleLogout"
              class="bg-red-600 hover:bg-red-700 text-white px-4 py-2 rounded-md text-sm font-medium transition-colors"
            >
              Sair
            </button>
          </div>
        </div>
      </div>
    </header>

    <!-- Loading State -->
    <div v-if="loading" class="flex justify-center items-center py-20">
      <div class="animate-spin rounded-full h-16 w-16 border-4 border-blue-600 border-t-transparent"></div>
      <div class="ml-4">
        <p class="text-lg font-medium text-gray-700">Carregando informações...</p>
        <p class="text-sm text-gray-500">Buscando dados da conta e transações</p>
      </div>
    </div>

    <!-- Main Content -->
    <main v-else class="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
      <!-- Resumo da Conta -->
      <div class="px-4 py-6 sm:px-0">
        <div class="bg-white overflow-hidden shadow rounded-lg">
          <div class="px-4 py-5 sm:p-6">
            <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
              <!-- Saldo Atual -->
              <div class="bg-gradient-to-r from-blue-500 to-blue-600 rounded-lg p-6 text-white">
                <div class="flex items-center">
                  <div class="flex-1">
                    <h3 class="text-lg font-medium">Saldo Atual</h3>
                    <p class="text-3xl font-bold">{{ formatCurrency(user?.balance || 0) }}</p>
                  </div>
                  <div class="ml-4">
                    <svg class="h-12 w-12 text-blue-200" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1"></path>
                    </svg>
                  </div>
                </div>
              </div>

              <!-- Total de Transações -->
              <div class="bg-gradient-to-r from-green-500 to-green-600 rounded-lg p-6 text-white">
                <div class="flex items-center">
                  <div class="flex-1">
                    <h3 class="text-lg font-medium">Total de Transações</h3>
                    <p class="text-3xl font-bold">{{ transactions.length }}</p>
                  </div>
                  <div class="ml-4">
                    <svg class="h-12 w-12 text-green-200" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"></path>
                    </svg>
                  </div>
                </div>
              </div>

              <!-- Status da Conta -->
              <div class="bg-gradient-to-r from-purple-500 to-purple-600 rounded-lg p-6 text-white">
                <div class="flex items-center">
                  <div class="flex-1">
                    <h3 class="text-lg font-medium">Status da Conta</h3>
                    <p class="text-xl font-bold">{{ user?.isActive ? 'Ativa' : 'Inativa' }}</p>
                    <p class="text-sm opacity-90">{{ user?.role === 'ADMIN' ? 'Administrador' : 'Usuário' }}</p>
                  </div>
                  <div class="ml-4">
                    <svg class="h-12 w-12 text-purple-200" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                    </svg>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Ações Rápidas -->
      <div class="px-4 py-6 sm:px-0">
        <div class="bg-white overflow-hidden shadow rounded-lg">
          <div class="px-4 py-5 sm:p-6">
            <h3 class="text-lg font-medium text-gray-900 mb-4">Ações Rápidas</h3>
            <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
              <button
                @click="goToNewTransaction"
                class="flex items-center justify-center px-4 py-3 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 transition-colors"
              >
                <svg class="mr-2 h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4"></path>
                </svg>
                Nova Transferência
              </button>
              
              <button
                @click="goToDeposit"
                class="flex items-center justify-center px-4 py-3 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-green-600 hover:bg-green-700 transition-colors"
              >
                <svg class="mr-2 h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 16l-4-4m0 0l4-4m-4 4h18"></path>
                </svg>
                Fazer Depósito
              </button>
              
              <button
                @click="refreshData"
                class="flex items-center justify-center px-4 py-3 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 transition-colors"
              >
                <svg class="mr-2 h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15"></path>
                </svg>
                Atualizar
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- Lista de Transações -->
      <div class="px-4 py-6 sm:px-0">
        <div class="bg-white overflow-hidden shadow rounded-lg">
          <div class="px-4 py-5 sm:p-6">
            <div class="flex justify-between items-center mb-4">
              <h3 class="text-lg font-medium text-gray-900">Histórico de Transações</h3>
              <div class="flex items-center space-x-4">
                <!-- Filter by Type -->
                <div class="flex items-center space-x-2">
                  <span class="text-sm font-medium text-gray-700">Tipo:</span>
                  <div class="flex rounded-lg border border-gray-200 bg-gray-50 p-1 filter-group">
                    <button
                      @click="transactionTypeFilter = 'all'"
                      :class="[
                        'px-3 py-1 text-xs font-medium rounded-md filter-button',
                        transactionTypeFilter === 'all'
                          ? 'bg-white text-blue-600 shadow-sm active'
                          : 'text-gray-600 hover:text-gray-900'
                      ]"
                    >
                      Todos
                    </button>
                    <button
                      @click="transactionTypeFilter = 'DEPOSIT'"
                      :class="[
                        'px-3 py-1 text-xs font-medium rounded-md filter-button',
                        transactionTypeFilter === 'DEPOSIT'
                          ? 'bg-white text-green-600 shadow-sm active'
                          : 'text-gray-600 hover:text-gray-900'
                      ]"
                    >
                      Depósitos
                    </button>
                    <button
                      @click="transactionTypeFilter = 'TRANSFER'"
                      :class="[
                        'px-3 py-1 text-xs font-medium rounded-md filter-button',
                        transactionTypeFilter === 'TRANSFER'
                          ? 'bg-white text-blue-600 shadow-sm active'
                          : 'text-gray-600 hover:text-gray-900'
                      ]"
                    >
                      Transferências
                    </button>
                  </div>
                </div>
                
                <!-- Filter by Status -->
                <div class="flex items-center space-x-2">
                  <span class="text-sm font-medium text-gray-700">Status:</span>
                  <div class="flex rounded-lg border border-gray-200 bg-gray-50 p-1 filter-group">
                    <button
                      @click="transactionStatusFilter = 'all'"
                      :class="[
                        'px-3 py-1 text-xs font-medium rounded-md filter-button',
                        transactionStatusFilter === 'all'
                          ? 'bg-white text-blue-600 shadow-sm active'
                          : 'text-gray-600 hover:text-gray-900'
                      ]"
                    >
                      Todos
                    </button>
                    <button
                      @click="transactionStatusFilter = 'PENDING'"
                      :class="[
                        'px-3 py-1 text-xs font-medium rounded-md filter-button',
                        transactionStatusFilter === 'PENDING'
                          ? 'bg-white text-yellow-600 shadow-sm active'
                          : 'text-gray-600 hover:text-gray-900'
                      ]"
                    >
                      Pendente
                    </button>
                    <button
                      @click="transactionStatusFilter = 'COMPLETED'"
                      :class="[
                        'px-3 py-1 text-xs font-medium rounded-md filter-button',
                        transactionStatusFilter === 'COMPLETED'
                          ? 'bg-white text-green-600 shadow-sm active'
                          : 'text-gray-600 hover:text-gray-900'
                      ]"
                    >
                      Concluído
                    </button>
                    <button
                      @click="transactionStatusFilter = 'CANCELLED'"
                      :class="[
                        'px-3 py-1 text-xs font-medium rounded-md filter-button',
                        transactionStatusFilter === 'CANCELLED'
                          ? 'bg-white text-red-600 shadow-sm active'
                          : 'text-gray-600 hover:text-gray-900'
                      ]"
                    >
                      Cancelado
                    </button>
                  </div>
                </div>
                
                <span class="text-sm text-gray-500">{{ filteredTransactions.length }} transações</span>
              </div>
            </div>

            <!-- Empty State -->
            <div v-if="filteredTransactions.length === 0" class="text-center py-12">
              <svg class="mx-auto h-12 w-12 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v10a2 2 0 002 2h8a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"></path>
              </svg>
              <p class="mt-4 text-gray-500">Nenhuma transação encontrada</p>
              <p class="text-sm text-gray-400">Suas transações aparecerão aqui</p>
            </div>

            <!-- Transactions List -->
            <div v-else class="space-y-4">
              <div
                v-for="transaction in filteredTransactions"
                :key="transaction.transactionId"
                class="border border-gray-200 rounded-lg p-4 hover:shadow-md transition-shadow cursor-pointer hover:border-blue-300"
                @click="openTransactionModal(transaction)"
              >
                <div class="flex items-center justify-between">
                  <div class="flex items-center space-x-4">
                    <!-- Transaction Icon -->
                    <div :class="[
                      'w-10 h-10 rounded-full flex items-center justify-center',
                      transaction.direction === 'INCOMING' ? 'bg-green-100' : 'bg-red-100'
                    ]">
                      <svg 
                        class="w-5 h-5"
                        :class="transaction.direction === 'INCOMING' ? 'text-green-600' : 'text-red-600'"
                        fill="none" 
                        stroke="currentColor" 
                        viewBox="0 0 24 24"
                      >
                        <path 
                          stroke-linecap="round" 
                          stroke-linejoin="round" 
                          stroke-width="2" 
                          :d="transactionService.getTransactionIconPath(transaction.type)"
                        />
                      </svg>
                    </div>
                    
                    <!-- Transaction Details -->
                    <div>
                      <p class="font-medium text-gray-900">{{ transaction.description }}</p>
                      <p class="text-sm text-gray-500">
                        {{ transaction.senderName }} • {{ formatDate(transaction.date) }}
                      </p>
                      <div class="flex items-center space-x-2">
                        <p class="text-xs text-gray-400">
                          ID: {{ transaction.transactionId.substring(0, 8) }}...
                        </p>
                        <span :class="[
                          'inline-flex px-2 py-1 text-xs font-semibold rounded-full',
                          transactionService.getTransactionStatusColor(transaction.status)
                        ]">
                          {{ transactionService.formatTransactionStatus(transaction.status) }}
                        </span>
                      </div>
                    </div>
                  </div>
                  
                  <!-- Transaction Amount -->
                  <div class="text-right">
                    <p :class="[
                      'text-lg font-semibold',
                      transaction.direction === 'INCOMING' ? 'text-green-600' : 'text-red-600'
                    ]">
                      {{ getTransactionPrefix(transaction.direction) }}{{ formatCurrency(transaction.amount) }}
                    </p>
                    <p class="text-sm text-gray-500">{{ transactionService.formatTransactionType(transaction.type) }}</p>
                    <!-- Indicador de clique -->
                    <p class="text-xs text-blue-500 mt-1">Clique para detalhes</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>

    <!-- Transaction Modal -->
    <TransactionModal
      :isOpen="isModalOpen"
      :transaction="selectedTransaction"
      @close="closeTransactionModal"
    />
  </div>
</template>

<script>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useAuth } from '../composables/useAuth.js'
import userService from '../services/userService.js'
import transactionService from '../services/transactionService.js'
import TransactionModal from '../components/TransactionModal.vue'

export default {
  name: 'Dashboard',
  components: {
    TransactionModal
  },
  setup() {
    const router = useRouter()
    const { logout } = useAuth()
    
    // Estado reativo
    const loading = ref(true)
    const user = ref(null)
    const transactions = ref([])
    const error = ref(null)
    
    // Estado do modal
    const isModalOpen = ref(false)
    const selectedTransaction = ref(null)
    
    // Filtros
    const transactionTypeFilter = ref('all')
    const transactionStatusFilter = ref('all')

    // Carrega os dados iniciais
    const loadDashboardData = async () => {
      try {
        loading.value = true
        error.value = null
        
        // Carrega informações do usuário e transações em paralelo
        const [userData, transactionsData] = await Promise.all([
          userService.getCurrentUser(),
          transactionService.getTransactions()
        ])
        
        user.value = userData
        transactions.value = transactionsData
        
      } catch (err) {
        error.value = 'Erro ao carregar dados do dashboard'
        console.error('Erro ao carregar dashboard:', err)
      } finally {
        loading.value = false
      }
    }

    // Atualiza os dados
    const refreshData = async () => {
      await loadDashboardData()
    }

    // Navega para o dashboard administrativo
    const goToAdminDashboard = () => {
      router.push('/admin')
    }

    // Navega para nova transação
    const goToNewTransaction = () => {
      router.push('/transfer')
    }

    // Navega para depósito
    const goToDeposit = () => {
      router.push('/deposit')
    }

    // Faz logout
    const handleLogout = () => {
      logout()
      router.push('/')
    }

    // Computed para filtrar transações
    const filteredTransactions = computed(() => {
      let filtered = transactions.value

      // Filtro por tipo
      if (transactionTypeFilter.value !== 'all') {
        filtered = filtered.filter(t => t.type === transactionTypeFilter.value)
      }

      // Filtro por status
      if (transactionStatusFilter.value !== 'all') {
        filtered = filtered.filter(t => t.status === transactionStatusFilter.value)
      }

      // Ordena por data mais recente primeiro
      return filtered.sort((a, b) => new Date(b.date) - new Date(a.date))
    })

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

    // Abre o modal de detalhes da transação
    const openTransactionModal = (transaction) => {
      selectedTransaction.value = transaction
      isModalOpen.value = true
    }

    // Fecha o modal de detalhes da transação
    const closeTransactionModal = () => {
      isModalOpen.value = false
      selectedTransaction.value = null
    }

    // Carrega os dados quando o componente é montado
    onMounted(() => {
      loadDashboardData()
    })

    return {
      // Estado
      loading,
      user,
      transactions,
      error,
      
      // Estado do modal
      isModalOpen,
      selectedTransaction,
      
      // Filtros
      transactionTypeFilter,
      transactionStatusFilter,
      
      // Computed
      filteredTransactions,
      
      // Métodos
      refreshData,
      goToAdminDashboard,
      goToNewTransaction,
      goToDeposit,
      handleLogout,
      formatCurrency,
      formatDate,
      getTransactionPrefix,
      openTransactionModal,
      closeTransactionModal,
      
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

/* Hover effects para cards */
.hover\:shadow-md:hover {
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
}

/* Transition effects */
.transition-colors {
  transition-property: background-color, border-color, color, fill, stroke;
  transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
  transition-duration: 150ms;
}

.transition-shadow {
  transition-property: box-shadow;
  transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
  transition-duration: 150ms;
}

/* Animações e efeitos para os filtros */
.filter-button {
  transition: all 0.2s ease-in-out;
}

.filter-button:hover {
  transform: translateY(-1px);
}

/* Efeito de glow nos filtros ativos */
.filter-button.active {
  transform: translateY(-1px);
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

/* Animação suave para mudança de cores */
.filter-group {
  transition: background-color 0.2s ease-in-out;
}

.filter-group:hover {
  background-color: rgb(249 250 251);
}
</style>
