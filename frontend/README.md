# VueBank - Sistema de Transferências Bancárias

> Uma aplicação moderna de transferências bancárias desenvolvida com Vue.js 3, Vite e TailwindCSS

## 📋 Sumário

- [Visão Geral](#-visão-geral)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Arquitetura do Projeto](#-arquitetura-do-projeto)
- [Estrutura de Diretórios](#-estrutura-de-diretórios)
- [Padrões de Desenvolvimento](#-padrões-de-desenvolvimento)
- [Sistema de Rotas](#-sistema-de-rotas)
- [Serviços e APIs](#-serviços-e-apis)
- [Componentes](#-componentes)
- [Composables](#-composables)
- [Sistema de Autenticação](#-sistema-de-autenticação)
- [Features Implementadas](#-features-implementadas)
- [Design System](#-design-system)
- [Instalação e Execução](#-instalação-e-execução)
- [Scripts Disponíveis](#-scripts-disponíveis)

## 🔍 Visão Geral

O **VueBank** é uma aplicação Single Page Application (SPA) que simula um sistema bancário completo para transferências financeiras. O sistema oferece funcionalidades como autenticação, transferências entre contas, depósitos, histórico de transações e um painel administrativo.

### Principais Funcionalidades:
- ✅ Sistema completo de autenticação (login, cadastro, recuperação de senha)
- ✅ Dashboard interativo com resumo financeiro
- ✅ Transferências entre contas com cálculo automático de taxas
- ✅ Sistema de depósitos
- ✅ Histórico completo de transações com filtros
- ✅ Painel administrativo para gerenciamento
- ✅ Perfil do usuário editável
- ✅ Sistema responsivo e acessível

## 🛠 Tecnologias Utilizadas

### Frontend Core
- **Vue.js 3** - Framework JavaScript reativo com Composition API
- **Vite** - Build tool moderno e rápido
- **Vue Router 4** - Roteamento SPA oficial do Vue

### Estilização e UI
- **TailwindCSS 3** - Framework CSS utility-first
- **PostCSS** - Processador CSS
- **Autoprefixer** - Prefixos automáticos para compatibilidade

### Comunicação e Estado
- **Axios** - Cliente HTTP para comunicação com API
- **Vue Toastification** - Sistema de notificações

### Ferramentas de Desenvolvimento
- **@vitejs/plugin-vue** - Plugin Vue para Vite
- **ESModules** - Sistema de módulos moderno

## 🏗 Arquitetura do Projeto

O projeto segue uma arquitetura modular baseada nos padrões do Vue.js 3, utilizando:

### 1. **Composition API**
Toda a aplicação utiliza a Composition API do Vue 3, proporcionando:
- Melhor reutilização de lógica
- Tipagem mais forte
- Código mais organizadodo
- Performance otimizada

### 2. **Arquitetura por Camadas**
```
📁 Presentation Layer (Views + Components)
    ↓
📁 Business Logic Layer (Composables + Services)
    ↓
📁 Data Layer (API Services + Local Storage)
```

### 3. **Padrão Repository**
Os serviços seguem o padrão Repository para abstração da camada de dados:
- `authService.js` - Gerenciamento de autenticação
- `userService.js` - Operações do usuário
- `transactionService.js` - Transações financeiras
- `adminService.js` - Funcionalidades administrativas

## 📂 Estrutura de Diretórios

```
frontend/
├── 📁 public/                    # Arquivos estáticos
├── 📁 src/
│   ├── 📄 App.vue               # Componente raiz
│   ├── 📄 main.js               # Entry point da aplicação
│   ├── 📄 style.css             # Estilos globais (TailwindCSS)
│   │
│   ├── 📁 components/           # Componentes reutilizáveis
│   │   ├── 📄 DatePicker.vue    # Seletor de data customizado
│   │   ├── 📄 LoadingSpinner.vue # Componente de loading
│   │   └── 📄 TransactionModal.vue # Modal de detalhes de transação
│   │
│   ├── 📁 composables/          # Lógica reutilizável (Composition API)
│   │   └── 📄 useAuth.js        # Hook de autenticação
│   │
│   ├── 📁 router/               # Configuração de rotas
│   │   └── 📄 index.js          # Definição das rotas e guards
│   │
│   ├── 📁 services/             # Camada de serviços
│   │   ├── 📄 api.js            # Configuração base do Axios
│   │   ├── 📄 authService.js    # Serviços de autenticação
│   │   ├── 📄 userService.js    # Serviços do usuário
│   │   ├── 📄 transactionService.js # Serviços de transações
│   │   └── 📄 adminService.js   # Serviços administrativos
│   │
│   └── 📁 views/                # Páginas da aplicação
│       ├── 📄 Login.vue         # Página de login
│       ├── 📄 Cadastro.vue      # Página de registro
│       ├── 📄 Dashboard.vue     # Dashboard principal
│       ├── 📄 Transfer.vue      # Página de transferências
│       ├── 📄 Deposit.vue       # Página de depósitos
│       ├── 📄 AdminDashboard.vue # Dashboard administrativo
│       ├── 📄 EditProfile.vue   # Edição de perfil
│       ├── 📄 ChangePassword.vue # Alteração de senha
│       ├── 📄 ForgotPassword.vue # Recuperação de senha
│       ├── 📄 ResetPassword.vue # Reset de senha
│       └── 📄 AccessDenied.vue  # Página de acesso negado
│
├── 📄 index.html                # Template HTML principal
├── 📄 package.json              # Dependências e scripts
├── 📄 vite.config.js            # Configuração do Vite
├── 📄 tailwind.config.js        # Configuração do TailwindCSS
└── 📄 postcss.config.js         # Configuração do PostCSS
```

## 🎯 Padrões de Desenvolvimento

### 1. **Composition API Pattern**
```javascript
// Exemplo de estrutura padrão nos componentes
export default {
  name: 'ComponentName',
  components: { /* componentes filhos */ },
  props: { /* propriedades */ },
  emits: ['event-name'],
  setup(props, { emit }) {
    // Estado reativo
    const state = ref(initialValue)
    
    // Computed properties
    const computedValue = computed(() => state.value * 2)
    
    // Métodos
    const handleAction = () => {
      // lógica do método
    }
    
    // Lifecycle hooks
    onMounted(() => {
      // lógica de inicialização
    })
    
    return {
      // Expor apenas o necessário
      state,
      computedValue,
      handleAction
    }
  }
}
```

### 2. **Service Layer Pattern**
```javascript
// Estrutura padrão dos serviços
export const serviceName = {
  async methodName(data) {
    try {
      const response = await api.post('/endpoint', data)
      return response.data
    } catch (error) {
      console.error('Erro:', error)
      throw error
    }
  }
}
```

### 3. **Error Handling Pattern**
- Tratamento consistente de erros em todos os serviços
- Mensagens de erro amigáveis para o usuário
- Logging detalhado para desenvolvimento

## 🚦 Sistema de Rotas

### Configuração das Rotas
O sistema de rotas é gerenciado pelo Vue Router 4 com as seguintes características:

#### **Rotas Públicas** (Requer `requiresGuest: true`)
```javascript
'/' → Login.vue                    // Página de login
'/cadastro' → Cadastro.vue         // Registro de usuário
'/forgot-password' → ForgotPassword.vue // Recuperação de senha
'/reset-password' → ResetPassword.vue   // Reset de senha
```

#### **Rotas Protegidas** (Requer `requiresAuth: true`)
```javascript
'/dashboard' → Dashboard.vue       // Dashboard principal
'/transfer' → Transfer.vue         // Transferências
'/deposit' → Deposit.vue          // Depósitos
'/edit-profile' → EditProfile.vue // Edição de perfil
'/change-password' → ChangePassword.vue // Alteração de senha
'/access-denied' → AccessDenied.vue     // Acesso negado
```

#### **Rotas Administrativas** (Requer `requiresAdmin: true`)
```javascript
'/admin' → AdminDashboard.vue      // Dashboard administrativo
```

### Navigation Guards
O sistema implementa guards de navegação para:

1. **Proteção de Rotas Autenticadas**
   ```javascript
   if (to.meta.requiresAuth && !isAuthenticated) {
     next('/') // Redireciona para login
   }
   ```

2. **Controle de Acesso Administrativo**
   ```javascript
   if (to.meta.requiresAdmin && user.role !== 'ADMIN') {
     next('/access-denied')
   }
   ```

3. **Redirecionamento de Usuários Logados**
   ```javascript
   if (to.meta.requiresGuest && isAuthenticated) {
     next('/dashboard')
   }
   ```

## 🔌 Serviços e APIs

### 1. **API Service (`api.js`)**
Configuração base do Axios com:
- Base URL: `http://localhost:8080`
- Timeout: 10 segundos
- Interceptors para autenticação automática
- Tratamento global de erros 401 (não autorizado)

```javascript
// Interceptor de Request - Adiciona token automaticamente
config.headers.Authorization = `Bearer ${token}`

// Interceptor de Response - Remove token em caso de 401
if (error.response?.status === 401) {
  localStorage.removeItem('token')
  localStorage.removeItem('user')
}
```

### 2. **Auth Service (`authService.js`)**
Gerencia toda autenticação:
- `login(credentials)` - Autenticação do usuário
- `logout()` - Desconexão segura
- `isAuthenticated()` - Verificação de estado
- `getCurrentUser()` - Dados do usuário atual
- `requestPasswordReset(email)` - Solicitação de reset
- `resetPassword(data)` - Redefinição de senha

### 3. **Transaction Service (`transactionService.js`)**
Operações financeiras:
- `calculateTax(data)` - Cálculo de taxas de transferência
- `createTransaction(data)` - Criação de transferências
- `makeDeposit(data)` - Realização de depósitos
- `getTransactions()` - Histórico de transações
- Métodos utilitários para formatação

### 4. **User Service (`userService.js`)**
Gerenciamento de usuário:
- `getCurrentUser()` - Dados atuais do usuário
- `updateProfile(data)` - Atualização de perfil
- `changePassword(data)` - Alteração de senha
- `deleteAccount()` - Exclusão de conta

### 5. **Admin Service (`adminService.js`)**
Funcionalidades administrativas:
- `getAllUsers()` - Lista todos os usuários
- `getUserTransactions(userId)` - Transações por usuário
- `getSystemStats()` - Estatísticas do sistema

## 🧩 Componentes

### 1. **DatePicker.vue**
Componente customizado para seleção de datas:

**Features:**
- Interface calendário intuitiva
- Suporte a datas mínimas
- Timezone São Paulo configurado
- Formatação brasileira (DD/MM/YYYY)
- Acessibilidade (tecla ESC para fechar)
- Actions rápidas (Hoje, Limpar)

**Props:**
- `modelValue` - Data selecionada
- `disabled` - Estado desabilitado
- `placeholder` - Texto placeholder
- `minDate` - Data mínima permitida

### 2. **LoadingSpinner.vue**
Componente de loading reutilizável:

**Features:**
- Animação CSS suave
- Mensagem customizável
- Subtítulo opcional
- Design consistente

**Props:**
- `message` - Mensagem principal (padrão: "Carregando...")
- `subtitle` - Texto adicional

### 3. **TransactionModal.vue**
Modal para exibição de detalhes de transações:

**Features:**
- Exibição completa dos dados da transação
- Formatação monetária brasileira
- Status colorido da transação
- Design responsivo
- Fechamento por overlay ou botão

**Props:**
- `isOpen` - Estado do modal
- `transaction` - Objeto da transação

## 🪝 Composables

### useAuth.js
Composable para gerenciamento global de autenticação:

```javascript
export function useAuth() {
  // Estado global reativo
  const isAuthenticated = ref(authService.isAuthenticated())
  const currentToken = ref(authService.getToken())
  
  // Métodos reativos
  const login = async (credentials) => {
    const result = await authService.login(credentials)
    isAuthenticated.value = true
    currentToken.value = authService.getToken()
    return result
  }
  
  const logout = () => {
    authService.logout()
    isAuthenticated.value = false
    currentToken.value = null
  }
  
  return {
    isAuthenticated: computed(() => isAuthenticated.value),
    currentToken: computed(() => currentToken.value),
    login,
    logout,
    checkAuth
  }
}
```

**Vantagens:**
- Estado global reativo
- Sincronização automática
- Reutilização entre componentes
- Performance otimizada

## 🔐 Sistema de Autenticação

### Fluxo de Autenticação

1. **Login**
   ```
   Usuário → Credenciais → API → Token JWT → localStorage → Estado Global
   ```

2. **Proteção de Rotas**
   ```
   Navegação → Router Guard → Verificação Token → Autorização/Redirecionamento
   ```

3. **Auto-logout**
   ```
   Token Expirado → Interceptor 401 → Limpeza Storage → Redirecionamento Login
   ```

### Tipos de Usuário
- **USER** - Usuário comum (transferências, depósitos, perfil)
- **ADMIN** - Administrador (acesso total + dashboard admin)

### Armazenamento Seguro
- **JWT Token** - Armazenado no localStorage
- **Dados do Usuário** - Cache local sincronizado
- **Auto-refresh** - Busca dados atualizados quando necessário

## ✨ Features Implementadas

### 1. **Dashboard Principal**
- Resumo do saldo atual
- Últimas transações com filtros
- Acesso rápido a funcionalidades
- Perfil do usuário com dropdown
- Menu administrativo (para admins)

### 2. **Sistema de Transferências**
**Processo em 3 etapas:**
1. **Dados** - Conta destino, valor, data, descrição
2. **Confirmação** - Revisão com cálculo de taxas
3. **Resultado** - Comprovante da transferência

**Features:**
- Busca automática de conta de destino
- Cálculo dinâmico de taxas
- Validação de saldo
- Agendamento de transferências futuras

### 3. **Sistema de Depósitos**
**Processo simplificado:**
1. **Valor** - Inserção do valor do depósito
2. **Confirmação** - Revisão dos dados
3. **Resultado** - Comprovante do depósito

**Features:**
- Máscara monetária automática
- Validação de valores
- Atualização instantânea do saldo

### 4. **Histórico de Transações**
- Lista completa de transações
- Filtros por tipo e status
- Modal de detalhes completos
- Formatação automática de valores
- Ordenação cronológica

### 5. **Gestão de Perfil**
- Edição de dados pessoais
- Alteração de senha segura
- Exclusão de conta
- Validação de formulários

### 6. **Dashboard Administrativo**
- Lista de todos os usuários
- Estatísticas do sistema
- Visualização de transações por usuário
- Relatórios gerenciais

### 7. **Sistema de Recuperação de Senha**
- Solicitação por email
- Token de segurança
- Redefinição segura
- Validação de token

## 🎨 Design System

### Paleta de Cores (TailwindCSS Customizada)

#### **Bank Primary** (Azul Corporativo)
```css
bank-primary-50:  #f0f9ff  /* Fundos claros */
bank-primary-500: #0ea5e9  /* Cor principal */
bank-primary-700: #0369a1  /* Estados hover */
bank-primary-900: #0c4a6e  /* Textos escuros */
```

#### **Bank Secondary** (Cinza Neutro)
```css
bank-secondary-50:  #f8fafc  /* Fundos sutis */
bank-secondary-500: #64748b  /* Textos secundários */
bank-secondary-800: #1e293b  /* Textos principais */
```

#### **Bank Accent** (Amarelo/Dourado)
```css
bank-accent-400: #facc15    /* Destaques */
bank-accent-600: #ca8a04    /* Estados ativos */
```

### Tipografia
- **Font Family:** Inter (sistema, sans-serif)
- **Font Mono:** Para valores monetários e IDs
- **Escalas:** text-sm, text-base, text-lg, text-xl, text-2xl, text-3xl

### Componentes de UI

#### **Botões**
```css
.btn-primary   /* Azul, ações principais */
.btn-secondary /* Cinza, ações secundárias */
.btn-danger    /* Vermelho, ações destrutivas */
.btn-success   /* Verde, confirmações */
```

#### **Cards**
```css
.card-default  /* Fundo branco, sombra sutil */
.card-hover    /* Efeitos de hover */
.card-active   /* Estado ativo/selecionado */
```

#### **Estados**
- **Loading:** Spinners animados consistentes
- **Error:** Ícones e cores vermelhas
- **Success:** Ícones e cores verdes
- **Warning:** Ícones e cores amarelas

### Responsividade
- **Mobile First:** Design otimizado para dispositivos móveis
- **Breakpoints:** sm (640px), md (768px), lg (1024px), xl (1280px)
- **Grid:** Flexbox e CSS Grid para layouts
- **Typography:** Escalas responsivas

### Animações e Transições
```css
/* Transições suaves */
transition-colors    /* Mudanças de cor */
transition-transform /* Transformações */
transition-opacity   /* Opacidade */

/* Animações */
animate-spin         /* Loading spinners */
animate-pulse        /* Placeholder loading */
```

## 🚀 Instalação e Execução

### Pré-requisitos
- **Node.js** 18+ 
- **npm** ou **yarn**
- **Git**

### Instalação
```bash
# 1. Clone o repositório
git clone [url-do-repositorio]
cd desafio/frontend

# 2. Instale as dependências
npm install

# 3. Configure as variáveis de ambiente (opcional)
# A aplicação está configurada para usar http://localhost:8080 como backend

# 4. Execute em modo de desenvolvimento
npm run dev
```

### Execução
```bash
# Desenvolvimento (com hot-reload)
npm run dev

# Build para produção
npm run build

# Preview do build de produção
npm run preview
```

## 📜 Scripts Disponíveis

```json
{
  "dev": "vite",           // Servidor de desenvolvimento
  "build": "vite build",   // Build para produção
  "preview": "vite preview" // Preview do build
}
```

### Comandos Úteis

```bash
# Análise do bundle
npm run build -- --analyze

# Linting (se configurado)
npm run lint

# Testes (se configurados)
npm run test
```

---

## 📝 Notas Técnicas

### Decisões Arquiteturais

1. **Vue 3 + Composition API**: Escolhido pela melhor organização de código e performance
2. **Vite**: Build tool moderno, desenvolvimento mais rápido que Webpack
3. **TailwindCSS**: Desenvolvimento ágil com design system consistente
4. **Axios**: Cliente HTTP robusto com interceptors
5. **Vue Router**: Roteamento oficial com guards de proteção

### Padrões de Código

1. **Naming Convention**: camelCase para JavaScript, kebab-case para componentes
2. **File Organization**: Agrupamento por feature e responsabilidade
3. **Error Handling**: Tratamento consistente em todas as camadas
4. **Code Splitting**: Lazy loading implementado nas rotas
5. **Performance**: Computed properties e watchers otimizados

### Segurança

1. **JWT Tokens**: Autenticação stateless segura
2. **Route Guards**: Proteção de rotas sensíveis
3. **Input Validation**: Validação no frontend e backend
4. **XSS Protection**: Sanitização automática do Vue
5. **CORS**: Configuração adequada para ambiente de produção

---

**Desenvolvido com ❤️ usando Vue.js 3 e TailwindCSS**
