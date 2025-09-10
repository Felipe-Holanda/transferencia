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
        
        if (response.data.user) {
          localStorage.setItem('user', JSON.stringify(response.data.user))
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
  }
}

export default authService
