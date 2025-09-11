import api from './api'

export const authService = {
  /**
   * Realiza o login do usuário
   * @param {Object} credentials - Credenciais do usuário
   * @param {string} credentials.email - Email do usuário
   * @param {string} credentials.password - Senha do usuário
   * @returns {Promise} Promise com a resposta da API
   */
  async login(credentials) {
    try {
      let response = await api.post('/auth', credentials)
      
      if (response.data?.token) {
        localStorage.setItem('token', response.data.token)
        
        // Buscar dados do usuário após login bem-sucedido
        try {
          const userResponse = await api.get('/user')
          if (userResponse.data) {
            localStorage.setItem('user', JSON.stringify(userResponse.data))
          }
        } catch (userError) {
          console.error('Erro ao buscar dados do usuário após login:', userError)
          // Não falha o login se não conseguir buscar os dados do usuário
          // Os dados serão buscados quando necessário
        }
      }
      
      return response.data
    } catch (error) {
      console.error('Erro no authService:', error)
      
      let errorMessage = 'Erro interno do servidor'
      
      if (error?.response?.data?.message) {
        errorMessage = error.response.data.message
      } else if (error?.message) {
        errorMessage = error.message
      }
      
      // Criar um erro consistente
      const authError = new Error(errorMessage)
      authError.status = error.response ? error.response.status : 500
      
      throw authError
    }
  },

  /**
   * Realiza o logout do usuário
   */
  logout() {
    localStorage.removeItem('token')
    localStorage.removeItem('user')
  },

  /**
   * Verifica se o usuário está autenticado
   * @returns {boolean} True se estiver autenticado
   */
  isAuthenticated() {
    return !!localStorage.getItem('token')
  },

  /**
   * Obtém o token atual
   * @returns {string|null} Token atual ou null
   */
  getToken() {
    return localStorage.getItem('token')
  },

  /**
   * Obtém os dados do usuário atual
   * @returns {Object|null} Dados do usuário ou null
   */
  getCurrentUser() {
    const userStr = localStorage.getItem('user')
    return userStr ? JSON.parse(userStr) : null
  },

  /**
   * Solicita redefinição de senha via email
   * @param {string} email - Email do usuário
   * @returns {Promise} Promise com a resposta da API
   */
  async requestPasswordReset(email) {
    try {
      const response = await api.post('/auth/password-reset/request', { email })
      return response.data
    } catch (error) {
      console.error('Erro ao solicitar redefinição de senha:', error)
      
      let errorMessage = 'Erro interno do servidor'
      
      if (error?.response?.data?.message) {
        errorMessage = error.response.data.message
      } else if (error?.message) {
        errorMessage = error.message
      }
      
      const authError = new Error(errorMessage)
      authError.status = error.response ? error.response.status : 500
      
      throw authError
    }
  },

  /**
   * Confirma redefinição de senha com OTP
   * @param {Object} data - Dados para confirmação
   * @param {string} data.token - Token OTP de 6 dígitos
   * @param {string} data.newPassword - Nova senha
   * @returns {Promise} Promise com a resposta da API
   */
  async confirmPasswordReset(data) {
    try {
      const response = await api.post('/auth/password-reset/confirm', data)
      return response.data
    } catch (error) {
      console.error('Erro ao confirmar redefinição de senha:', error)
      
      let errorMessage = 'Erro interno do servidor'
      
      if (error?.response?.data?.message) {
        errorMessage = error.response.data.message
      } else if (error?.message) {
        errorMessage = error.message
      }
      
      const authError = new Error(errorMessage)
      authError.status = error.response ? error.response.status : 500
      
      throw authError
    }
  },

  /**
   * Altera a senha do usuário logado
   * @param {Object} data - Dados para alteração de senha
   * @param {string} data.currentPassword - Senha atual
   * @param {string} data.newPassword - Nova senha
   * @returns {Promise} Promise com a resposta da API
   */
  async changePassword(data) {
    try {
      const response = await api.patch('/auth/change-password', data)
      return response.data
    } catch (error) {
      console.error('Erro ao alterar senha:', error)
      
      let errorMessage = 'Erro interno do servidor'
      
      if (error?.response?.data?.message) {
        errorMessage = error.response.data.message
      } else if (error?.message) {
        errorMessage = error.message
      }
      
      const authError = new Error(errorMessage)
      authError.status = error.response ? error.response.status : 500
      
      throw authError
    }
  }
}

export default authService
