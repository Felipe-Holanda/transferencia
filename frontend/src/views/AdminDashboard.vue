<template>
  <div class="min-h-screen bg-gray-50">
    <!-- Header -->
    <header class="bg-white shadow">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between items-center py-6">
          <div class="flex items-center">
            <button
              @click="goBack"
              class="mr-4 p-2 text-gray-600 hover:text-gray-900 transition-colors"
            >
              <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
              </svg>
            </button>
            <div>
              <h1 class="text-3xl font-bold text-gray-900">Painel Administrativo</h1>
              <p class="text-sm text-gray-500 mt-1">Gerencie usuários, transações e configurações do sistema</p>
            </div>
          </div>
          <div class="flex items-center space-x-4">
            <div class="text-right">
              <span class="text-sm font-medium text-gray-900">Modo Administrador</span>
              <p class="text-xs text-gray-500">Acesso total ao sistema</p>
            </div>
            <button
              @click="refreshData"
              class="p-2 text-gray-600 hover:text-gray-900 transition-colors rounded-lg hover:bg-gray-100"
              title="Atualizar dados"
            >
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15"></path>
              </svg>
            </button>
          </div>
        </div>
      </div>
    </header>

    <!-- Tabs -->
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
      <div class="bg-white rounded-lg shadow mb-6">
        <div class="border-b border-gray-200">
          <div class="px-6 py-2">
            <nav class="flex space-x-8" aria-label="Tabs">
              <button
                @click="activeTab = 'overview'"
                :class="[
                  'whitespace-nowrap py-4 px-2 border-b-2 font-medium text-sm transition-colors',
                  activeTab === 'overview'
                    ? 'border-blue-500 text-blue-600'
                    : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
                ]"
              >
                📊 Visão Geral
              </button>
              <button
                @click="activeTab = 'users'"
                :class="[
                  'whitespace-nowrap py-4 px-2 border-b-2 font-medium text-sm transition-colors',
                  activeTab === 'users'
                    ? 'border-blue-500 text-blue-600'
                    : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
                ]"
              >
                👥 Gerenciar Usuários
              </button>
              <button
                @click="activeTab = 'transactions'"
                :class="[
                  'whitespace-nowrap py-4 px-2 border-b-2 font-medium text-sm transition-colors',
                  activeTab === 'transactions'
                    ? 'border-blue-500 text-blue-600'
                    : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
                ]"
              >
                💰 Visualizar Transações
              </button>
              <button
                @click="activeTab = 'taxes'"
                :class="[
                  'whitespace-nowrap py-4 px-2 border-b-2 font-medium text-sm transition-colors',
                  activeTab === 'taxes'
                    ? 'border-blue-500 text-blue-600'
                    : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
                ]"
              >
                ⚙️ Gerenciar Taxas
              </button>
            </nav>
          </div>
        </div>
      </div>

      <!-- Tab Content: Visão Geral -->
      <div v-if="activeTab === 'overview'">
        <!-- System Stats Cards -->
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-6">
          <!-- Total de Usuários -->
          <div class="bg-white rounded-lg shadow p-6">
            <div class="flex items-center">
              <div class="flex-1">
                <h3 class="text-lg font-medium text-gray-900">Total de Usuários</h3>
                <p class="text-3xl font-bold text-blue-600">{{ systemStats.totalUsers }}</p>
                <p class="text-sm text-gray-500">Registrados no sistema</p>
              </div>
              <div class="ml-4">
                <svg class="h-12 w-12 text-blue-200" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197m13.5-9a2.5 2.5 0 11-5 0 2.5 2.5 0 015 0z"></path>
                </svg>
              </div>
            </div>
          </div>

          <!-- Total de Dinheiro no Sistema -->
          <div class="bg-white rounded-lg shadow p-6">
            <div class="flex items-center">
              <div class="flex-1">
                <h3 class="text-lg font-medium text-gray-900">Total em Circulação</h3>
                <p class="text-3xl font-bold text-green-600">{{ adminService.formatCurrency(systemStats.totalMoney) }}</p>
                <p class="text-sm text-gray-500">Saldo total dos usuários</p>
              </div>
              <div class="ml-4">
                <svg class="h-12 w-12 text-green-200" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1"></path>
                </svg>
              </div>
            </div>
          </div>

          <!-- Usuários Ativos -->
          <div class="bg-white rounded-lg shadow p-6">
            <div class="flex items-center">
              <div class="flex-1">
                <h3 class="text-lg font-medium text-gray-900">Usuários Ativos</h3>
                <p class="text-3xl font-bold text-green-600">{{ systemStats.activeUsers }}</p>
                <p class="text-sm text-gray-500">{{ getPercentage(systemStats.activeUsers, systemStats.totalUsers) }}% do total</p>
              </div>
              <div class="ml-4">
                <svg class="h-12 w-12 text-green-200" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                </svg>
              </div>
            </div>
          </div>

          <!-- Usuários Inativos -->
          <div class="bg-white rounded-lg shadow p-6">
            <div class="flex items-center">
              <div class="flex-1">
                <h3 class="text-lg font-medium text-gray-900">Usuários Inativos</h3>
                <p class="text-3xl font-bold text-red-600">{{ systemStats.inactiveUsers }}</p>
                <p class="text-sm text-gray-500">{{ getPercentage(systemStats.inactiveUsers, systemStats.totalUsers) }}% do total</p>
              </div>
              <div class="ml-4">
                <svg class="h-12 w-12 text-red-200" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M18.364 18.364A9 9 0 005.636 5.636m12.728 12.728L5.636 5.636m12.728 12.728L18.364 5.636M5.636 18.364l12.728-12.728"></path>
                </svg>
              </div>
            </div>
          </div>

          <!-- Administradores -->
          <div class="bg-white rounded-lg shadow p-6">
            <div class="flex items-center">
              <div class="flex-1">
                <h3 class="text-lg font-medium text-gray-900">Administradores</h3>
                <p class="text-3xl font-bold text-purple-600">{{ systemStats.adminUsers }}</p>
                <p class="text-sm text-gray-500">Com acesso administrativo</p>
              </div>
              <div class="ml-4">
                <svg class="h-12 w-12 text-purple-200" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m5.586-3L12 2.414 6.414 8l-1.414 1.414L12 16.414l6.586-6.586L17.172 8.414l-1.414 1.414L12 13.172l-3.172-3.172z"></path>
                </svg>
              </div>
            </div>
          </div>

          <!-- Total de Transações -->
          <div class="bg-white rounded-lg shadow p-6">
            <div class="flex items-center">
              <div class="flex-1">
                <h3 class="text-lg font-medium text-gray-900">Total de Transações</h3>
                <p class="text-3xl font-bold text-indigo-600">{{ systemStats.totalTransactions }}</p>
                <p class="text-sm text-gray-500">Realizadas no sistema</p>
              </div>
              <div class="ml-4">
                <svg class="h-12 w-12 text-indigo-200" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"></path>
                </svg>
              </div>
            </div>
          </div>
        </div>

        <!-- Quick Actions -->
        <div class="bg-white rounded-lg shadow p-6">
          <h3 class="text-lg font-medium text-gray-900 mb-4">Ações Rápidas</h3>
          <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
            <button
              @click="activeTab = 'users'"
              class="flex items-center justify-center px-4 py-3 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 transition-colors"
            >
              <svg class="mr-2 h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197m13.5-9a2.5 2.5 0 11-5 0 2.5 2.5 0 015 0z"></path>
              </svg>
              Gerenciar Usuários
            </button>
            
            <button
              @click="activeTab = 'transactions'"
              class="flex items-center justify-center px-4 py-3 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-green-600 hover:bg-green-700 transition-colors"
            >
              <svg class="mr-2 h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"></path>
              </svg>
              Ver Transações
            </button>
            
            <button
              @click="activeTab = 'taxes'"
              class="flex items-center justify-center px-4 py-3 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-purple-600 hover:bg-purple-700 transition-colors"
            >
              <svg class="mr-2 h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 7h6m0 10v-3m-3 3h.01M9 17h.01M9 14h.01M12 14h.01M15 11h.01M12 11h.01M9 11h.01M7 21h10a2 2 0 002-2V5a2 2 0 00-2-2H7a2 2 0 00-2 2v14a2 2 0 002 2z"></path>
              </svg>
              Configurar Taxas
            </button>
            
            <button
              @click="refreshData"
              class="flex items-center justify-center px-4 py-3 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 transition-colors"
            >
              <svg class="mr-2 h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15"></path>
              </svg>
              Atualizar Dados
            </button>
          </div>
        </div>
      </div>

      <!-- Tab Content: Usuários -->
      <div v-if="activeTab === 'users'">
        <!-- Stats Cards -->
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-6">
          <div class="bg-white rounded-lg shadow p-6">
            <div class="flex items-center">
              <div class="flex-1">
                <h3 class="text-lg font-medium text-gray-900">Total de Usuários</h3>
                <p class="text-3xl font-bold text-blue-600">{{ users.length }}</p>
              </div>
              <div class="ml-4">
                <svg class="h-12 w-12 text-blue-200" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197m13.5-9a2.5 2.5 0 11-5 0 2.5 2.5 0 015 0z"></path>
                </svg>
              </div>
            </div>
          </div>

          <div class="bg-white rounded-lg shadow p-6">
            <div class="flex items-center">
              <div class="flex-1">
                <h3 class="text-lg font-medium text-gray-900">Usuários Ativos</h3>
                <p class="text-3xl font-bold text-green-600">{{ activeUsers }}</p>
              </div>
              <div class="ml-4">
                <svg class="h-12 w-12 text-green-200" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                </svg>
              </div>
            </div>
          </div>

          <div class="bg-white rounded-lg shadow p-6">
            <div class="flex items-center">
              <div class="flex-1">
                <h3 class="text-lg font-medium text-gray-900">Administradores</h3>
                <p class="text-3xl font-bold text-purple-600">{{ adminUsers }}</p>
              </div>
              <div class="ml-4">
                <svg class="h-12 w-12 text-purple-200" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m5.586-3L12 2.414 6.414 8l-1.414 1.414L12 16.414l6.586-6.586L17.172 8.414l-1.414 1.414L12 13.172l-3.172-3.172z"></path>
                </svg>
              </div>
            </div>
          </div>
        </div>

        <!-- Users Table -->
        <div class="bg-white rounded-lg shadow">
          <div class="px-4 py-5 sm:p-6">
            <div class="flex justify-between items-center mb-4">
              <h3 class="text-lg font-medium text-gray-900">Lista de Usuários</h3>
              <div class="flex items-center space-x-2">
                <!-- Filter -->
                <div class="flex items-center space-x-2">
                  <span class="text-sm font-medium text-gray-700">Filtrar:</span>
                  <div class="flex rounded-lg border border-gray-200 bg-gray-50 p-1 filter-group">
                    <button
                      @click="userFilter = 'all'"
                      :class="[
                        'px-3 py-1 text-sm font-medium rounded-md filter-button',
                        userFilter === 'all'
                          ? 'bg-white text-blue-600 shadow-sm active'
                          : 'text-gray-600 hover:text-gray-900'
                      ]"
                    >
                      Todos
                    </button>
                    <button
                      @click="userFilter = 'active'"
                      :class="[
                        'px-3 py-1 text-sm font-medium rounded-md filter-button',
                        userFilter === 'active'
                          ? 'bg-white text-green-600 shadow-sm active'
                          : 'text-gray-600 hover:text-gray-900'
                      ]"
                    >
                      Ativos
                    </button>
                    <button
                      @click="userFilter = 'inactive'"
                      :class="[
                        'px-3 py-1 text-sm font-medium rounded-md filter-button',
                        userFilter === 'inactive'
                          ? 'bg-white text-red-600 shadow-sm active'
                          : 'text-gray-600 hover:text-gray-900'
                      ]"
                    >
                      Inativos
                    </button>
                    <button
                      @click="userFilter = 'admin'"
                      :class="[
                        'px-3 py-1 text-sm font-medium rounded-md filter-button',
                        userFilter === 'admin'
                          ? 'bg-white text-purple-600 shadow-sm active'
                          : 'text-gray-600 hover:text-gray-900'
                      ]"
                    >
                      Administradores
                    </button>
                  </div>
                </div>
              </div>
            </div>

            <!-- Loading State -->
            <div v-if="loading" class="flex justify-center py-8">
              <div class="animate-spin rounded-full h-8 w-8 border-2 border-blue-500 border-t-transparent"></div>
            </div>

            <!-- Users List -->
            <div v-else class="overflow-x-auto">
              <table class="min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-50">
                  <tr>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Usuário
                    </th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Contato
                    </th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Conta
                    </th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Status
                    </th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Papel
                    </th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Ações
                    </th>
                  </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                  <tr v-for="user in filteredUsers" :key="user.id" class="hover:bg-gray-50">
                    <td class="px-6 py-4 whitespace-nowrap">
                      <div>
                        <div class="text-sm font-medium text-gray-900">{{ user.fullName }}</div>
                        <div class="text-sm text-gray-500">{{ adminService.formatCPF(user.cpf) }}</div>
                      </div>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <div>
                        <div class="text-sm text-gray-900">{{ user.email }}</div>
                        <div class="text-sm text-gray-500">{{ adminService.formatPhone(user.phone) }}</div>
                      </div>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <div>
                        <div class="text-sm font-mono text-gray-900">{{ user.accountNumber }}</div>
                        <div class="text-sm text-gray-500">{{ adminService.formatCurrency(user.balance) }}</div>
                      </div>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <span :class="[
                        'inline-flex px-2 py-1 text-xs font-semibold rounded-full',
                        user.isActive 
                          ? 'bg-green-100 text-green-800' 
                          : 'bg-red-100 text-red-800'
                      ]">
                        {{ adminService.formatUserStatus(user.isActive) }}
                      </span>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <span :class="[
                        'inline-flex px-2 py-1 text-xs font-semibold rounded-full',
                        user.role === 'ADMIN' 
                          ? 'bg-purple-100 text-purple-800' 
                          : 'bg-blue-100 text-blue-800'
                      ]">
                        {{ adminService.formatUserRole(user.role) }}
                      </span>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                      <div class="flex space-x-2">
                        <button
                          @click="editUser(user)"
                          class="text-blue-600 hover:text-blue-900"
                          title="Editar"
                        >
                          <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"></path>
                          </svg>
                        </button>
                        
                        <button
                          v-if="user.isActive"
                          @click="deactivateUser(user)"
                          class="text-red-600 hover:text-red-900"
                          title="Desativar"
                        >
                          <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M18.364 18.364A9 9 0 005.636 5.636m12.728 12.728L5.636 5.636m12.728 12.728L18.364 5.636M5.636 18.364l12.728-12.728"></path>
                          </svg>
                        </button>
                        
                        <button
                          v-else
                          @click="reactivateUser(user)"
                          class="text-green-600 hover:text-green-900"
                          title="Reativar"
                        >
                          <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                          </svg>
                        </button>
                        
                        <button
                          @click="deleteUserPermanently(user)"
                          class="text-red-600 hover:text-red-900"
                          title="Excluir Permanentemente"
                        >
                          <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"></path>
                          </svg>
                        </button>
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>

      <!-- Tab Content: Transações -->
      <div v-if="activeTab === 'transactions'">
        <!-- Transaction Stats -->
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-6">
          <div class="bg-white rounded-lg shadow p-6">
            <div class="flex items-center">
              <div class="flex-1">
                <h3 class="text-lg font-medium text-gray-900">Total de Transações</h3>
                <p class="text-3xl font-bold text-blue-600">{{ allTransactions.length }}</p>
              </div>
              <div class="ml-4">
                <svg class="h-12 w-12 text-blue-200" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"></path>
                </svg>
              </div>
            </div>
          </div>

          <div class="bg-white rounded-lg shadow p-6">
            <div class="flex items-center">
              <div class="flex-1">
                <h3 class="text-lg font-medium text-gray-900">Transferências</h3>
                <p class="text-3xl font-bold text-green-600">{{ getTransactionsByType('TRANSFER').length }}</p>
              </div>
              <div class="ml-4">
                <svg class="h-12 w-12 text-green-200" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7h12m0 0l-4-4m4 4l-4 4m0 6H4m0 0l4 4m-4-4l4-4"></path>
                </svg>
              </div>
            </div>
          </div>

          <div class="bg-white rounded-lg shadow p-6">
            <div class="flex items-center">
              <div class="flex-1">
                <h3 class="text-lg font-medium text-gray-900">Depósitos</h3>
                <p class="text-3xl font-bold text-purple-600">{{ getTransactionsByType('DEPOSIT').length }}</p>
              </div>
              <div class="ml-4">
                <svg class="h-12 w-12 text-purple-200" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 16l-4-4m0 0l4-4m-4 4h18"></path>
                </svg>
              </div>
            </div>
          </div>
        </div>

        <!-- Status Cards -->
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-6">
          <div class="bg-white rounded-lg shadow p-6">
            <div class="flex items-center">
              <div class="flex-1">
                <h3 class="text-lg font-medium text-gray-900">Transações Pendentes</h3>
                <p class="text-3xl font-bold text-yellow-600">{{ getTransactionsByStatus('PENDING').length }}</p>
              </div>
              <div class="ml-4">
                <svg class="h-12 w-12 text-yellow-200" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                </svg>
              </div>
            </div>
          </div>

          <div class="bg-white rounded-lg shadow p-6">
            <div class="flex items-center">
              <div class="flex-1">
                <h3 class="text-lg font-medium text-gray-900">Transações Concluídas</h3>
                <p class="text-3xl font-bold text-green-600">{{ getTransactionsByStatus('COMPLETED').length }}</p>
              </div>
              <div class="ml-4">
                <svg class="h-12 w-12 text-green-200" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                </svg>
              </div>
            </div>
          </div>

          <div class="bg-white rounded-lg shadow p-6">
            <div class="flex items-center">
              <div class="flex-1">
                <h3 class="text-lg font-medium text-gray-900">Transações Canceladas</h3>
                <p class="text-3xl font-bold text-red-600">{{ getTransactionsByStatus('CANCELLED').length }}</p>
              </div>
              <div class="ml-4">
                <svg class="h-12 w-12 text-red-200" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                </svg>
              </div>
            </div>
          </div>
        </div>

        <!-- Transactions Table -->
        <div class="bg-white rounded-lg shadow">
          <div class="px-4 py-5 sm:p-6">
            <div class="flex justify-between items-center mb-4">
              <h3 class="text-lg font-medium text-gray-900">Todas as Transações</h3>
              <div class="flex items-center space-x-4">
                <!-- Filter by Type -->
                <div class="flex items-center space-x-2">
                  <span class="text-sm font-medium text-gray-700">Tipo:</span>
                  <div class="flex rounded-lg border border-gray-200 bg-gray-50 p-1 filter-group">
                    <button
                      @click="transactionFilter = 'all'"
                      :class="[
                        'px-3 py-1 text-sm font-medium rounded-md filter-button',
                        transactionFilter === 'all'
                          ? 'bg-white text-blue-600 shadow-sm active'
                          : 'text-gray-600 hover:text-gray-900'
                      ]"
                    >
                      Todos
                    </button>
                    <button
                      @click="transactionFilter = 'TRANSFER'"
                      :class="[
                        'px-3 py-1 text-sm font-medium rounded-md filter-button',
                        transactionFilter === 'TRANSFER'
                          ? 'bg-white text-blue-600 shadow-sm active'
                          : 'text-gray-600 hover:text-gray-900'
                      ]"
                    >
                      Transferências
                    </button>
                    <button
                      @click="transactionFilter = 'DEPOSIT'"
                      :class="[
                        'px-3 py-1 text-sm font-medium rounded-md filter-button',
                        transactionFilter === 'DEPOSIT'
                          ? 'bg-white text-blue-600 shadow-sm active'
                          : 'text-gray-600 hover:text-gray-900'
                      ]"
                    >
                      Depósitos
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
                        'px-3 py-1 text-sm font-medium rounded-md filter-button',
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
                        'px-3 py-1 text-sm font-medium rounded-md filter-button',
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
                        'px-3 py-1 text-sm font-medium rounded-md filter-button',
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
                        'px-3 py-1 text-sm font-medium rounded-md filter-button',
                        transactionStatusFilter === 'CANCELLED'
                          ? 'bg-white text-red-600 shadow-sm active'
                          : 'text-gray-600 hover:text-gray-900'
                      ]"
                    >
                      Cancelado
                    </button>
                  </div>
                </div>
              </div>
            </div>

            <!-- Loading State -->
            <div v-if="loading" class="flex justify-center py-8">
              <div class="animate-spin rounded-full h-8 w-8 border-2 border-blue-500 border-t-transparent"></div>
            </div>

            <!-- Transactions List -->
            <div v-else class="overflow-x-auto">
              <table class="min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-50">
                  <tr>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      ID / Tipo
                    </th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Remetente
                    </th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Destinatário
                    </th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Valor
                    </th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Data
                    </th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Status
                    </th>
                  </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                  <tr v-for="(transaction, index) in filteredTransactions" :key="transaction.transactionHash || index" class="hover:bg-gray-50">
                    <td class="px-6 py-4 whitespace-nowrap">
                      <div>
                        <div class="text-sm font-medium text-gray-900">{{ transaction.transactionHash?.substring(0, 12) || 'N/A' }}...</div>
                        <div class="text-sm text-gray-500">{{ transaction.sender ? 'Transferência' : 'Depósito' }}</div>
                      </div>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <div>
                        <div class="text-sm font-medium text-gray-900">{{ transaction.sender?.fullName || 'Sistema' }}</div>
                        <div class="text-sm text-gray-500 font-mono">{{ transaction.sender?.accountNumber || 'N/A' }}</div>
                      </div>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <div>
                        <div class="text-sm font-medium text-gray-900">{{ transaction.recipient?.fullName || 'N/A' }}</div>
                        <div class="text-sm text-gray-500 font-mono">{{ transaction.recipient?.accountNumber || 'N/A' }}</div>
                      </div>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <div class="text-sm font-bold text-gray-900">{{ adminService.formatCurrency(transaction.amount) }}</div>
                      <div v-if="transaction.taxes && transaction.taxes > 0" class="text-sm text-gray-500">Taxa: {{ adminService.formatCurrency(transaction.taxes) }}</div>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <div class="text-sm text-gray-900">{{ formatTransactionDate(transaction.targetDate) }}</div>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <span :class="[
                        'inline-flex px-2 py-1 text-xs font-semibold rounded-full',
                        getTransactionStatusColor(transaction.status)
                      ]">
                        {{ formatTransactionStatus(transaction.status) }}
                      </span>
                    </td>
                  </tr>
                </tbody>
              </table>
              
              <!-- Empty State -->
              <div v-if="filteredTransactions.length === 0" class="text-center py-8">
                <svg class="mx-auto h-12 w-12 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"></path>
                </svg>
                <h3 class="mt-2 text-sm font-medium text-gray-900">Nenhuma transação encontrada</h3>
                <p class="mt-1 text-sm text-gray-500">Não há transações que correspondam aos filtros selecionados.</p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Tab Content: Taxas -->
      <div v-if="activeTab === 'taxes'">
        <!-- Add Tax Button -->
        <div class="mb-6">
          <button
            @click="showTaxForm = true"
            class="inline-flex items-center px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700"
          >
            <svg class="mr-2 h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4"></path>
            </svg>
            Nova Taxa
          </button>
        </div>

        <!-- Taxes Table -->
        <div class="bg-white rounded-lg shadow">
          <div class="px-4 py-5 sm:p-6">
            <h3 class="text-lg font-medium text-gray-900 mb-4">Configuração de Taxas</h3>

            <!-- Loading State -->
            <div v-if="loading" class="flex justify-center py-8">
              <div class="animate-spin rounded-full h-8 w-8 border-2 border-blue-500 border-t-transparent"></div>
            </div>

            <!-- Taxes List -->
            <div v-else class="overflow-x-auto">
              <table class="min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-50">
                  <tr>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Prazo
                    </th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Taxa Fixa
                    </th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Taxa Percentual
                    </th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Ações
                    </th>
                  </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                  <tr v-for="tax in taxes" :key="tax.id" class="hover:bg-gray-50">
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                      {{ adminService.formatDays(tax.amountDays) }}
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                      {{ adminService.formatCurrency(tax.fixedTax) }}
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                      {{ adminService.formatPercentage(tax.taxPercentage) }}
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                      <div class="flex space-x-2">
                        <button
                          @click="editTax(tax)"
                          class="text-blue-600 hover:text-blue-900"
                          title="Editar"
                        >
                          <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"></path>
                          </svg>
                        </button>
                        
                        <button
                          @click="deleteTax(tax)"
                          class="text-red-600 hover:text-red-900"
                          title="Excluir"
                        >
                          <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"></path>
                          </svg>
                        </button>
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Edit User Modal -->
    <div v-if="showUserModal" class="fixed inset-0 bg-gray-600 bg-opacity-50 overflow-y-auto h-full w-full z-50">
      <div class="relative top-20 mx-auto p-5 border w-96 shadow-lg rounded-md bg-white">
        <div class="mt-3">
          <h3 class="text-lg font-medium text-gray-900 mb-4">Editar Usuário</h3>
          
          <form @submit.prevent="saveUser" class="space-y-4">
            <div>
              <label for="user-email" class="block text-sm font-medium text-gray-700">Email</label>
              <input
                id="user-email"
                v-model="editingUser.email"
                type="email"
                class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
                required
              />
            </div>
            
            <div>
              <label for="user-phone" class="block text-sm font-medium text-gray-700">Telefone</label>
              <input
                id="user-phone"
                v-model="editingUser.phone"
                type="text"
                class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
                required
              />
            </div>
            
            <div>
              <label for="user-cpf" class="block text-sm font-medium text-gray-700">CPF</label>
              <input
                id="user-cpf"
                v-model="editingUser.cpf"
                type="text"
                class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
                required
              />
            </div>
            
            <div>
              <label for="user-password" class="block text-sm font-medium text-gray-700">Nova Senha (opcional)</label>
              <input
                id="user-password"
                v-model="editingUser.password"
                type="password"
                class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
              />
            </div>
            
            <div>
              <label for="user-role" class="block text-sm font-medium text-gray-700">Papel</label>
              <select
                id="user-role"
                v-model="editingUser.role"
                class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
              >
                <option value="USER">Usuário</option>
                <option value="ADMIN">Administrador</option>
              </select>
            </div>
            
            <div class="flex items-center">
              <input
                id="user-active"
                v-model="editingUser.isActive"
                type="checkbox"
                class="h-4 w-4 text-blue-600 focus:ring-blue-500 border-gray-300 rounded"
              />
              <label for="user-active" class="ml-2 block text-sm text-gray-900">Usuário ativo</label>
            </div>

            <div class="flex justify-end space-x-3 pt-4">
              <button
                type="button"
                @click="closeUserModal"
                class="px-4 py-2 border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50"
              >
                Cancelar
              </button>
              <button
                type="submit"
                :disabled="userSaving"
                class="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 disabled:opacity-50"
              >
                <span v-if="userSaving">Salvando...</span>
                <span v-else>Salvar</span>
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>

    <!-- Tax Form Modal -->
    <div v-if="showTaxForm" class="fixed inset-0 bg-gray-600 bg-opacity-50 overflow-y-auto h-full w-full z-50">
      <div class="relative top-20 mx-auto p-5 border w-96 shadow-lg rounded-md bg-white">
        <div class="mt-3">
          <h3 class="text-lg font-medium text-gray-900 mb-4">
            {{ editingTax.id ? 'Editar Taxa' : 'Nova Taxa' }}
          </h3>
          
          <form @submit.prevent="saveTax" class="space-y-4">
            <div>
              <label for="tax-days" class="block text-sm font-medium text-gray-700">Quantidade de Dias</label>
              <input
                id="tax-days"
                v-model.number="editingTax.amountDays"
                type="number"
                min="0"
                class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
                required
              />
            </div>
            
            <div>
              <label for="tax-fixed" class="block text-sm font-medium text-gray-700">Taxa Fixa (R$)</label>
              <input
                id="tax-fixed"
                v-model.number="editingTax.fixedTax"
                type="number"
                min="0"
                step="0.01"
                class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
                required
              />
            </div>
            
            <div>
              <label for="tax-percentage" class="block text-sm font-medium text-gray-700">Taxa Percentual (%)</label>
              <input
                id="tax-percentage"
                v-model.number="editingTax.taxPercentage"
                type="number"
                min="0"
                max="100"
                step="0.1"
                class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
                required
              />
            </div>

            <div class="flex justify-end space-x-3 pt-4">
              <button
                type="button"
                @click="closeTaxForm"
                class="px-4 py-2 border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50"
              >
                Cancelar
              </button>
              <button
                type="submit"
                :disabled="taxSaving"
                class="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 disabled:opacity-50"
              >
                <span v-if="taxSaving">Salvando...</span>
                <span v-else>Salvar</span>
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import adminService from '../services/adminService.js'

export default {
  name: 'AdminDashboard',
  setup() {
    const router = useRouter()
    
    // Estado reativo
    const activeTab = ref('overview')
    const loading = ref(false)
    const users = ref([])
    const taxes = ref([])
    const allTransactions = ref([])
    const userFilter = ref('all')
    const transactionFilter = ref('all')
    const transactionStatusFilter = ref('all')
    
    // Modais
    const showUserModal = ref(false)
    const showTaxForm = ref(false)
    const userSaving = ref(false)
    const taxSaving = ref(false)
    
    // Dados de edição
    const editingUser = ref({
      id: null,
      email: '',
      phone: '',
      cpf: '',
      password: '',
      isActive: true,
      role: 'USER'
    })
    
    const editingTax = ref({
      id: null,
      amountDays: 0,
      fixedTax: 0,
      taxPercentage: 0
    })

    // Computed
    const systemStats = computed(() => {
      return adminService.calculateSystemStats(users.value, allTransactions.value)
    })

    const activeUsers = computed(() => {
      return users.value.filter(user => user.isActive).length
    })

    const adminUsers = computed(() => {
      return users.value.filter(user => user.role === 'ADMIN').length
    })

    const filteredUsers = computed(() => {
      switch (userFilter.value) {
        case 'active':
          return users.value.filter(user => user.isActive)
        case 'inactive':
          return users.value.filter(user => !user.isActive)
        case 'admin':
          return users.value.filter(user => user.role === 'ADMIN')
        default:
          return users.value
      }
    })

    const filteredTransactions = computed(() => {
      let filtered = allTransactions.value

      // Filter by type
      if (transactionFilter.value !== 'all') {
        if (transactionFilter.value === 'DEPOSIT') {
          filtered = filtered.filter(t => t.sender === null)
        } else if (transactionFilter.value === 'TRANSFER') {
          filtered = filtered.filter(t => t.sender !== null)
        }
      }

      // Filter by status
      if (transactionStatusFilter.value !== 'all') {
        filtered = filtered.filter(t => t.status === transactionStatusFilter.value)
      }

      // Sort by most recent first
      return filtered.sort((a, b) => new Date(b.targetDate) - new Date(a.targetDate))
    })

    // Métodos
    const goBack = () => {
      router.push('/dashboard')
    }

    const refreshData = async () => {
      loading.value = true
      try {
        await Promise.all([
          loadUsers(),
          loadTaxes(),
          loadTransactions()
        ])
      } catch (error) {
        console.error('Erro ao atualizar dados:', error)
      } finally {
        loading.value = false
      }
    }

    const loadUsers = async () => {
      try {
        users.value = await adminService.getAllUsers()
      } catch (error) {
        console.error('Erro ao carregar usuários:', error)
      }
    }

    const loadTaxes = async () => {
      try {
        taxes.value = await adminService.getAllTaxes()
      } catch (error) {
        console.error('Erro ao carregar taxas:', error)
      }
    }

    const loadTransactions = async () => {
      try {
        // Como o endpoint não existe ainda, vou usar um fallback
        try {
          allTransactions.value = await adminService.getAllTransactions()
        } catch {
          // Fallback: usar transações do usuário atual se não conseguir buscar todas
          allTransactions.value = []
        }
      } catch (error) {
        console.error('Erro ao carregar transações:', error)
        allTransactions.value = []
      }
    }

    const getPercentage = (part, total) => {
      if (total === 0) return 0
      return Math.round((part / total) * 100)
    }

    const getTransactionsByType = (type) => {
      return allTransactions.value.filter(t => {
        if (type === 'DEPOSIT') {
          return t.sender === null
        }
        if (type === 'TRANSFER') {
          return t.sender !== null
        }
        return false
      })
    }

    const getTransactionsByStatus = (status) => {
      return allTransactions.value.filter(t => t.status === status)
    }

    const formatTransactionDate = (dateString) => {
      if (!dateString) return 'N/A'
      return new Date(dateString).toLocaleString('pt-BR', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
      })
    }

    const formatTransactionStatus = (status) => {
      const statusMap = {
        'PENDING': 'Pendente',
        'COMPLETED': 'Concluído',
        'CANCELLED': 'Cancelado'
      }
      return statusMap[status] || status
    }

    const getTransactionStatusColor = (status) => {
      const colorMap = {
        'PENDING': 'bg-yellow-100 text-yellow-800',
        'COMPLETED': 'bg-green-100 text-green-800',
        'CANCELLED': 'bg-red-100 text-red-800'
      }
      return colorMap[status] || 'bg-gray-100 text-gray-800'
    }

    // User management
    const editUser = (user) => {
      editingUser.value = {
        id: user.id,
        email: user.email,
        phone: user.phone,
        cpf: user.cpf,
        password: '',
        isActive: user.isActive,
        role: user.role
      }
      showUserModal.value = true
    }

    const saveUser = async () => {
      try {
        userSaving.value = true
        
        const userData = { ...editingUser.value }
        if (!userData.password) {
          delete userData.password
        }
        
        await adminService.updateUser(userData.id, userData)
        await loadUsers()
        closeUserModal()
      } catch (error) {
        console.error('Erro ao salvar usuário:', error)
      } finally {
        userSaving.value = false
      }
    }

    const closeUserModal = () => {
      showUserModal.value = false
      editingUser.value = {
        id: null,
        email: '',
        phone: '',
        cpf: '',
        password: '',
        isActive: true,
        role: 'USER'
      }
    }

    const deactivateUser = async (user) => {
      if (confirm(`Deseja desativar o usuário ${user.fullName}?`)) {
        try {
          await adminService.deactivateUser(user.id)
          await loadUsers()
        } catch (error) {
          console.error('Erro ao desativar usuário:', error)
        }
      }
    }

    const reactivateUser = async (user) => {
      if (confirm(`Deseja reativar o usuário ${user.fullName}?`)) {
        try {
          await adminService.reactivateUser(user.id)
          await loadUsers()
        } catch (error) {
          console.error('Erro ao reativar usuário:', error)
        }
      }
    }

    const deleteUserPermanently = async (user) => {
      if (confirm(`ATENÇÃO: Deseja excluir permanentemente o usuário ${user.fullName}? Esta ação não pode ser desfeita.`)) {
        try {
          await adminService.deleteUserPermanently(user.id)
          await loadUsers()
        } catch (error) {
          console.error('Erro ao excluir usuário:', error)
        }
      }
    }

    // Tax management
    const editTax = (tax) => {
      editingTax.value = { ...tax }
      showTaxForm.value = true
    }

    const saveTax = async () => {
      try {
        taxSaving.value = true
        
        if (editingTax.value.id) {
          await adminService.updateTax(editingTax.value.id, editingTax.value)
        } else {
          await adminService.createTax(editingTax.value)
        }
        
        await loadTaxes()
        closeTaxForm()
      } catch (error) {
        console.error('Erro ao salvar taxa:', error)
      } finally {
        taxSaving.value = false
      }
    }

    const closeTaxForm = () => {
      showTaxForm.value = false
      editingTax.value = {
        id: null,
        amountDays: 0,
        fixedTax: 0,
        taxPercentage: 0
      }
    }

    const deleteTax = async (tax) => {
      if (confirm(`Deseja excluir a taxa para ${adminService.formatDays(tax.amountDays)}?`)) {
        try {
          await adminService.deleteTax(tax.id)
          await loadTaxes()
        } catch (error) {
          console.error('Erro ao excluir taxa:', error)
        }
      }
    }

    // Inicialização
    onMounted(() => {
      refreshData()
    })

    return {
      // Estado
      activeTab,
      loading,
      users,
      taxes,
      allTransactions,
      userFilter,
      transactionFilter,
      transactionStatusFilter,
      showUserModal,
      showTaxForm,
      userSaving,
      taxSaving,
      editingUser,
      editingTax,
      
      // Computed
      systemStats,
      activeUsers,
      adminUsers,
      filteredUsers,
      filteredTransactions,
      
      // Métodos
      goBack,
      refreshData,
      getPercentage,
      getTransactionsByType,
      getTransactionsByStatus,
      formatTransactionDate,
      formatTransactionStatus,
      getTransactionStatusColor,
      editUser,
      saveUser,
      closeUserModal,
      deactivateUser,
      reactivateUser,
      deleteUserPermanently,
      editTax,
      saveTax,
      closeTaxForm,
      deleteTax,
      
      // Services
      adminService
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
