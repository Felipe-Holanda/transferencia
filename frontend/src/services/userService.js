import api from './api'
import authService from './authService'

export const userService = {
  /**
   * Registra um novo usuário
   * @param {Object} userData - Dados do usuário
   * @param {string} userData.fullName - Nome completo
   * @param {string} userData.email - Email
   * @param {string} userData.cpf - CPF
   * @param {string} userData.phone - Telefone
   * @param {string} userData.password - Senha
   * @returns {Promise} Promise com a resposta da API
   */
  async register(userData) {
    try {
      const response = await api.post('/user', userData)
      
      // Auto-login após registro bem-sucedido
      if (response.data?.success) {
        try {
          await authService.login({
            email: userData.email,
            password: userData.password
          })
        } catch (loginError) {
          console.warn('Auto-login falhou após registro:', loginError)
        }
      }
      
      return response.data
    } catch (error) {
      console.error('Erro no registro:', error)
      throw new Error(error.response?.data?.message || 'Erro ao registrar usuário')
    }
  },

  /**
   * Obtém dados do usuário atual
   * @returns {Promise} Promise com os dados do usuário
   */
  async getCurrentUser() {
    try {
      const response = await api.get('/user')
      return response.data
    } catch (error) {
      console.error('Erro ao obter usuário atual:', error)
      throw new Error(error.response?.data?.message || 'Erro ao obter dados do usuário')
    }
  },

  /**
   * Atualiza dados do usuário
   * @param {Object} userData - Dados para atualizar
   * @returns {Promise} Promise com a resposta da API
   */
  async updateUser(userData) {
    try {
      const response = await api.patch('/user', userData)
      
      // Atualizar dados no localStorage se existirem
      if (response.data?.user) {
        localStorage.setItem('user', JSON.stringify(response.data.user))
      }
      
      return response.data
    } catch (error) {
      console.error('Erro ao atualizar usuário:', error)
      throw new Error(error.response?.data?.message || 'Erro ao atualizar usuário')
    }
  },

  /**
   * Desativa conta do usuário
   * @returns {Promise} Promise com a resposta da API
   */
  async deactivateAccount() {
    try {
      const response = await api.delete('/user')
      
      // Fazer logout após desativação
      authService.logout()
      
      return response.data
    } catch (error) {
      console.error('Erro ao desativar conta:', error)
      throw new Error(error.response?.data?.message || 'Erro ao desativar conta')
    }
  },

  /**
   * Cancela/deleta conta do usuário (alias para deactivateAccount)
   * @returns {Promise} Promise com a resposta da API
   */
  async deleteAccount() {
    return this.deactivateAccount()
  },

  /**
   * Busca usuário por número da conta
   * @param {string} accountNumber - Número da conta
   * @returns {Promise} Promise com os dados do usuário
   */
  async getUserByAccountNumber(accountNumber) {
    try {
      const response = await api.get(`/user/account/${accountNumber}`)
      return response.data
    } catch (error) {
      console.error('Erro ao buscar usuário por conta:', error)
      throw new Error(error.response?.data?.message || 'Usuário não encontrado')
    }
  }
}

export default userService
