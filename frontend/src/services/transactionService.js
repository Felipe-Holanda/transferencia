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
  }
}

export default transactionService
