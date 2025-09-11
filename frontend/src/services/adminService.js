import api from './api.js'

/**
 * Serviço para gerenciar funcionalidades administrativas
 */
export const adminService = {
  // ========== GERENCIAMENTO DE USUÁRIOS ==========

  /**
   * Lista todos os usuários do sistema
   * @returns {Promise<Array>} Lista de todos os usuários
   */
  async getAllUsers() {
    try {
      const response = await api.get('/admin/user')
      return response.data
    } catch (error) {
      console.error('Erro ao buscar todos os usuários:', error)
      throw error
    }
  },

  /**
   * Busca um usuário específico pelo ID
   * @param {number} userId - ID do usuário
   * @returns {Promise<Object>} Dados do usuário
   */
  async getUserById(userId) {
    try {
      const response = await api.get(`/admin/user/id/${userId}`)
      return response.data
    } catch (error) {
      console.error(`Erro ao buscar usuário com ID ${userId}:`, error)
      throw error
    }
  },

  /**
   * Edita um usuário existente
   * @param {number} userId - ID do usuário
   * @param {Object} userData - Dados para atualização
   * @param {string} userData.email - Email do usuário
   * @param {string} userData.phone - Telefone do usuário
   * @param {string} userData.password - Senha do usuário (opcional)
   * @param {string} userData.cpf - CPF do usuário
   * @param {boolean} userData.isActive - Status ativo/inativo
   * @param {string} userData.role - Papel do usuário (USER ou ADMIN)
   * @returns {Promise<Object>} Dados do usuário atualizado
   */
  async updateUser(userId, userData) {
    try {
      const response = await api.patch(`/admin/user/id/${userId}`, userData)
      return response.data
    } catch (error) {
      console.error(`Erro ao atualizar usuário com ID ${userId}:`, error)
      throw error
    }
  },

  /**
   * Desativa um usuário (soft delete)
   * @param {number} userId - ID do usuário
   * @returns {Promise<void>}
   */
  async deactivateUser(userId) {
    try {
      await api.delete(`/admin/user/id/${userId}`)
    } catch (error) {
      console.error(`Erro ao desativar usuário com ID ${userId}:`, error)
      throw error
    }
  },

  /**
   * Reativa um usuário desativado
   * @param {number} userId - ID do usuário
   * @returns {Promise<Object>} Dados do usuário reativado
   */
  async reactivateUser(userId) {
    try {
      const response = await api.put(`/admin/user/id/${userId}`)
      return response.data
    } catch (error) {
      console.error(`Erro ao reativar usuário com ID ${userId}:`, error)
      throw error
    }
  },

  /**
   * Remove permanentemente um usuário (hard delete)
   * @param {number} userId - ID do usuário
   * @returns {Promise<void>}
   */
  async deleteUserPermanently(userId) {
    try {
      await api.delete(`/admin/user/id/${userId}/hard`)
    } catch (error) {
      console.error(`Erro ao remover permanentemente usuário com ID ${userId}:`, error)
      throw error
    }
  },

  // ========== ESTATÍSTICAS DO SISTEMA ==========

  /**
   * Busca todas as transações do sistema para análise administrativa
   * @returns {Promise<Array>} Lista de todas as transações
   */
  async getAllTransactions() {
    try {
      const response = await api.get('/admin/transactions')
      return response.data
    } catch (error) {
      console.error('Erro ao buscar todas as transações:', error)
      throw error
    }
  },

  /**
   * Calcula estatísticas gerais do sistema
   * @param {Array} users - Lista de usuários
   * @param {Array} transactions - Lista de transações
   * @returns {Object} Objeto com estatísticas
   */
  calculateSystemStats(users, transactions) {
    const totalUsers = users.length
    const activeUsers = users.filter(user => user.isActive).length
    const inactiveUsers = users.filter(user => !user.isActive).length
    const adminUsers = users.filter(user => user.role === 'ADMIN').length
    const totalMoney = users.reduce((sum, user) => sum + (user.balance || 0), 0)
    const totalTransactions = transactions ? transactions.length : 0
    
    return {
      totalUsers,
      activeUsers,
      inactiveUsers,
      adminUsers,
      totalMoney,
      totalTransactions
    }
  },

  // ========== GERENCIAMENTO DE TAXAS ==========

  /**
   * Lista todas as taxas cadastradas
   * @returns {Promise<Array>} Lista de taxas
   */
  async getAllTaxes() {
    try {
      const response = await api.get('/admin/taxes')
      return response.data
    } catch (error) {
      console.error('Erro ao buscar taxas:', error)
      throw error
    }
  },

  /**
   * Cadastra uma nova taxa
   * @param {Object} taxData - Dados da taxa
   * @param {number} taxData.amountDays - Quantidade de dias
   * @param {number} taxData.fixedTax - Taxa fixa
   * @param {number} taxData.taxPercentage - Percentual da taxa
   * @returns {Promise<Object>} Dados da taxa criada
   */
  async createTax(taxData) {
    try {
      const response = await api.post('/admin/taxes', taxData)
      return response.data
    } catch (error) {
      console.error('Erro ao criar taxa:', error)
      throw error
    }
  },

  /**
   * Atualiza uma taxa existente
   * @param {number} taxId - ID da taxa
   * @param {Object} taxData - Dados para atualização
   * @param {number} taxData.amountDays - Quantidade de dias
   * @param {number} taxData.fixedTax - Taxa fixa
   * @param {number} taxData.taxPercentage - Percentual da taxa
   * @returns {Promise<Object>} Dados da taxa atualizada
   */
  async updateTax(taxId, taxData) {
    try {
      const response = await api.patch(`/admin/taxes/id/${taxId}`, taxData)
      return response.data
    } catch (error) {
      console.error(`Erro ao atualizar taxa com ID ${taxId}:`, error)
      throw error
    }
  },

  /**
   * Remove uma taxa
   * @param {number} taxId - ID da taxa
   * @returns {Promise<void>}
   */
  async deleteTax(taxId) {
    try {
      await api.delete(`/admin/taxes/id/${taxId}`)
    } catch (error) {
      console.error(`Erro ao remover taxa com ID ${taxId}:`, error)
      throw error
    }
  },

  // ========== UTILITÁRIOS ==========

  /**
   * Formata o papel do usuário para exibição
   * @param {string} role - Papel do usuário (USER ou ADMIN)
   * @returns {string} Papel formatado
   */
  formatUserRole(role) {
    const roles = {
      USER: 'Usuário',
      ADMIN: 'Administrador'
    }
    return roles[role] || role
  },

  /**
   * Formata o status do usuário para exibição
   * @param {boolean} isActive - Status ativo/inativo
   * @returns {string} Status formatado
   */
  formatUserStatus(isActive) {
    if (isActive) {
      return 'Ativo'
    }
    return 'Inativo'
  },

  /**
   * Retorna a cor CSS baseada no status do usuário
   * @param {boolean} isActive - Status ativo/inativo
   * @returns {string} Classe CSS para cor
   */
  getUserStatusColor(isActive) {
    if (isActive) {
      return 'text-green-600'
    }
    return 'text-red-600'
  },

  /**
   * Retorna a cor CSS baseada no papel do usuário
   * @param {string} role - Papel do usuário (USER ou ADMIN)
   * @returns {string} Classe CSS para cor
   */
  getUserRoleColor(role) {
    const colors = {
      ADMIN: 'text-purple-600',
      USER: 'text-blue-600'
    }
    return colors[role] || 'text-gray-600'
  },

  /**
   * Formata valores monetários para exibição
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
   * Formata CPF para exibição
   * @param {string} cpf - CPF a ser formatado
   * @returns {string} CPF formatado
   */
  formatCPF(cpf) {
    if (!cpf) return ''
    
    // Remove caracteres não numéricos
    const numbers = cpf.replace(/\D/g, '')
    
    // Aplica a máscara
    return numbers.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4')
  },

  /**
   * Formata telefone para exibição
   * @param {string} phone - Telefone a ser formatado
   * @returns {string} Telefone formatado
   */
  formatPhone(phone) {
    if (!phone) return ''
    
    // Remove caracteres não numéricos
    const numbers = phone.replace(/\D/g, '')
    
    // Aplica a máscara baseada no tamanho
    if (numbers.length === 11) {
      return numbers.replace(/(\d{2})(\d{5})(\d{4})/, '($1) $2-$3')
    } else if (numbers.length === 10) {
      return numbers.replace(/(\d{2})(\d{4})(\d{4})/, '($1) $2-$3')
    }
    
    return phone
  },

  /**
   * Valida se o usuário tem permissão de administrador
   * @param {string} role - Papel do usuário
   * @returns {boolean} True se for administrador
   */
  isAdmin(role) {
    return role === 'ADMIN'
  },

  /**
   * Valida dados de usuário para edição
   * @param {Object} userData - Dados do usuário
   * @returns {Object} Objeto com resultado da validação
   */
  validateUserData(userData) {
    const errors = []

    if (!userData.email?.includes?.('@')) {
      errors.push('Email inválido')
    }

    if (!userData.phone || userData.phone.length < 10) {
      errors.push('Telefone inválido')
    }

    if (!userData.cpf || userData.cpf.replace(/\D/g, '').length !== 11) {
      errors.push('CPF inválido')
    }

    if (!['USER', 'ADMIN'].includes(userData.role)) {
      errors.push('Papel inválido')
    }

    return {
      isValid: errors.length === 0,
      errors
    }
  },

  /**
   * Valida dados de taxa
   * @param {Object} taxData - Dados da taxa
   * @returns {Object} Objeto com resultado da validação
   */
  validateTaxData(taxData) {
    const errors = []

    if (typeof taxData.amountDays !== 'number' || taxData.amountDays < 0) {
      errors.push('Quantidade de dias deve ser um número positivo')
    }

    if (typeof taxData.fixedTax !== 'number' || taxData.fixedTax < 0) {
      errors.push('Taxa fixa deve ser um número positivo')
    }

    if (typeof taxData.taxPercentage !== 'number' || taxData.taxPercentage < 0 || taxData.taxPercentage > 100) {
      errors.push('Percentual da taxa deve ser entre 0 e 100')
    }

    if (taxData.fixedTax === 0 && taxData.taxPercentage === 0) {
      errors.push('Pelo menos uma taxa (fixa ou percentual) deve ser maior que zero')
    }

    return {
      isValid: errors.length === 0,
      errors
    }
  },

  /**
   * Formata percentual para exibição
   * @param {number} percentage - Percentual a ser formatado
   * @returns {string} Percentual formatado
   */
  formatPercentage(percentage) {
    return `${percentage.toFixed(1)}%`
  },

  /**
   * Formata quantidade de dias
   * @param {number} days - Quantidade de dias
   * @returns {string} Dias formatados
   */
  formatDays(days) {
    if (days === 0) return 'Mesmo dia'
    if (days === 1) return '1 dia'
    return `${days} dias`
  }
}

export default adminService
