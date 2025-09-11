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
  }
}

export default authService
