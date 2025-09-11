import api from './api.js'

/**
 * Serviço para gerenciar transações
 */
export const transactionService = {
  /**
   * Calcula o valor da taxa de transferência
   * @param {Object} data - Dados para cálculo da taxa
   * @param {number} data.amount - Valor da transferência
   * @param {string} data.targetDate - Data da transferência (formato: YYYY-MM-DD)
   * @returns {Promise<Object>} Objeto com a taxa calculada
   */
  async calculateTax(data) {
    try {
      const response = await api.post('/transaction/calculate', data)
      return response.data
    } catch (error) {
      console.error('Erro ao calcular taxa de transferência:', error)
      throw error
    }
  },

  /**
   * Cadastra uma nova transação
   * @param {Object} transactionData - Dados da transação
   * @param {string} transactionData.targetAccount - Conta de destino
   * @param {number} transactionData.amount - Valor da transferência
   * @param {string} transactionData.targetDate - Data da transferência
   * @param {string} transactionData.description - Descrição da transação
   * @returns {Promise<Object>} Dados da transação criada
   */
  async createTransaction(transactionData) {
    try {
      const response = await api.post('/transaction', transactionData)
      return response.data
    } catch (error) {
      console.error('Erro ao criar transação:', error)
      throw error
    }
  },

  /**
   * Faz depósito de saldo na conta
   * @param {Object} depositData - Dados do depósito
   * @param {number} depositData.amount - Valor do depósito
   * @returns {Promise<Object>} Dados do depósito realizado
   */
  async makeDeposit(depositData) {
    try {
      const response = await api.put('/transaction', depositData)
      return response.data
    } catch (error) {
      console.error('Erro ao fazer depósito:', error)
      throw error
    }
  },

  /**
   * Lista todas as transações do usuário
   * @returns {Promise<Array>} Lista de transações
   */
  async getTransactions() {
    try {
      const response = await api.get('/transaction')
      return response.data
    } catch (error) {
      console.error('Erro ao buscar transações:', error)
      throw error
    }
  },

  /**
   * Busca uma transação específica pelo ID
   * @param {string} transactionId - ID da transação
   * @returns {Promise<Object>} Dados da transação
   */
  async getTransactionById(transactionId) {
    try {
      const response = await api.get(`/transaction/${transactionId}`)
      return response.data
    } catch (error) {
      console.error('Erro ao buscar transação:', error)
      throw error
    }
  },

  /**
   * Formata o valor monetário para exibição
   * @param {number} value - Valor a ser formatado
   * @returns {string} Valor formatado em moeda brasileira
   */
  formatCurrency(value) {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL'
    }).format(value)
  },

  /**
   * Formata a data para exibição
   * @param {string} dateString - Data em formato ISO
   * @returns {string} Data formatada para pt-BR
   */
  formatDate(dateString) {
    return new Date(dateString).toLocaleDateString('pt-BR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric'
    })
  },

  /**
   * Formata a data e hora para exibição
   * @param {string} dateTimeString - Data e hora em formato ISO
   * @returns {string} Data e hora formatadas para pt-BR
   */
  formatDateTime(dateTimeString) {
    return new Date(dateTimeString).toLocaleString('pt-BR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    })
  },

  /**
   * Valida se uma data está no futuro
   * @param {string} dateString - Data a ser validada
   * @returns {boolean} True se a data for válida (hoje ou futuro)
   */
  isValidTargetDate(dateString) {
    const targetDate = new Date(dateString)
    const today = new Date()
    today.setHours(0, 0, 0, 0)
    
    return targetDate >= today
  },

  /**
   * Valida se o valor é positivo
   * @param {number} amount - Valor a ser validado
   * @returns {boolean} True se o valor for válido
   */
  isValidAmount(amount) {
    return typeof amount === 'number' && amount > 0
  },

  /**
   * Valida formato de conta bancária
   * @param {string} accountNumber - Número da conta
   * @returns {boolean} True se o formato for válido
   */
  isValidAccountNumber(accountNumber) {
    // Assumindo formato de 10 dígitos
    const accountRegex = /^\d{10}$/
    return accountRegex.test(accountNumber)
  },

  /**
   * Retorna o ícone baseado no tipo de transação
   * @param {string} type - Tipo da transação (TRANSFER, DEPOSIT)
   * @returns {string} Nome do ícone
   */
  getTransactionIcon(type) {
    const icons = {
      TRANSFER: 'swap-horizontal',
      DEPOSIT: 'trending-up'
    }
    return icons[type] || 'help-circle'
  },

  /**
   * Retorna a cor baseada na direção da transação
   * @param {string} direction - Direção da transação (INCOMING, OUTGOING)
   * @returns {string} Classe CSS para cor
   */
  getTransactionColor(direction) {
    const colors = {
      INCOMING: 'text-green-600',
      OUTGOING: 'text-red-600'
    }
    return colors[direction] || 'text-gray-600'
  },

  /**
   * Retorna o prefixo baseado na direção da transação
   * @param {string} direction - Direção da transação (INCOMING, OUTGOING)
   * @returns {string} Prefixo (+ ou -)
   */
  getTransactionPrefix(direction) {
    return direction === 'INCOMING' ? '+' : '-'
  },

  /**
   * Formata o status da transação para exibição
   * @param {string} status - Status da transação (PENDING, COMPLETED, CANCELLED)
   * @returns {string} Status formatado em português
   */
  formatTransactionStatus(status) {
    const statusMap = {
      'PENDING': 'Pendente',
      'COMPLETED': 'Concluído',
      'CANCELLED': 'Cancelado'
    }
    return statusMap[status] || status
  },

  /**
   * Retorna a cor CSS baseada no status da transação
   * @param {string} status - Status da transação
   * @returns {string} Classes CSS para estilização
   */
  getTransactionStatusColor(status) {
    const colorMap = {
      'PENDING': 'bg-yellow-100 text-yellow-800',
      'COMPLETED': 'bg-green-100 text-green-800',
      'CANCELLED': 'bg-red-100 text-red-800'
    }
    return colorMap[status] || 'bg-gray-100 text-gray-800'
  },

  /**
   * Retorna o texto do tipo de transação formatado
   * @param {string} type - Tipo da transação (DEPOSIT, WITHDRAWAL, TRANSFER)
   * @returns {string} Tipo formatado em português
   */
  formatTransactionType(type) {
    const typeMap = {
      'DEPOSIT': 'Depósito',
      'WITHDRAWAL': 'Saque',
      'TRANSFER': 'Transferência'
    }
    return typeMap[type] || type
  },

  /**
   * Retorna o ícone SVG baseado no tipo de transação
   * @param {string} type - Tipo da transação
   * @returns {string} Path do ícone SVG
   */
  getTransactionIconPath(type) {
    const iconPaths = {
      'DEPOSIT': 'M7 16l-4-4m0 0l4-4m-4 4h18',
      'WITHDRAWAL': 'M17 8l4 4m0 0l-4 4m4-4H3',
      'TRANSFER': 'M8 7h12m0 0l-4-4m4 4l-4 4m0 6H4m0 0l4 4m-4-4l4-4'
    }
    return iconPaths[type] || 'M8.228 9c.549-1.165 2.03-2 3.772-2 2.21 0 4 1.343 4 3 0 1.4-1.278 2.575-3.006 2.907-.542.104-.994.54-.994 1.093m0 3h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z'
  }
}

export default transactionService
