import { ref, computed } from 'vue'
import authService from '../services/authService'

// Estado global da autenticação
const isAuthenticated = ref(authService.isAuthenticated())
const currentToken = ref(authService.getToken())

export function useAuth() {
  /**
   * Realiza o login
   */
  const login = async (credentials) => {
    const result = await authService.login(credentials)
    isAuthenticated.value = true
    currentToken.value = authService.getToken()
    return result
  }

  /**
   * Realiza o logout
   */
  const logout = () => {
    authService.logout()
    isAuthenticated.value = false
    currentToken.value = null
  }

  /**
   * Verifica se o usuário está autenticado
   */
  const checkAuth = () => {
    const authenticated = authService.isAuthenticated()
    isAuthenticated.value = authenticated
    currentToken.value = authService.getToken()
    return authenticated
  }

  return {
    // Estado
    isAuthenticated: computed(() => isAuthenticated.value),
    currentToken: computed(() => currentToken.value),
    
    // Métodos
    login,
    logout,
    checkAuth
  }
}

export default useAuth
