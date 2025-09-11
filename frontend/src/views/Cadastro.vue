<template>
  <div class="min-h-screen bg-gradient-to-br from-bank-secondary-50 via-white to-bank-primary-50 flex items-center justify-center p-4">
    <div class="absolute inset-0 opacity-40">
      <div class="absolute inset-0 bg-bank-primary-100" style="background-image: radial-gradient(circle at 1px 1px, rgba(14, 165, 233, 0.15) 1px, transparent 0); background-size: 20px 20px;"></div>
    </div>
    
    <div class="relative w-full max-w-lg">
      <div class="text-center mb-8">
        <div class="w-16 h-16 bg-gradient-to-br from-bank-primary-600 to-bank-primary-800 rounded-2xl flex items-center justify-center mx-auto mb-4 shadow-bank-lg">
          <svg class="w-8 h-8 text-white" fill="currentColor" viewBox="0 0 20 20">
            <path d="M4 4a2 2 0 00-2 2v1h16V6a2 2 0 00-2-2H4z"/>
            <path fill-rule="evenodd" d="M18 9H2v5a2 2 0 002 2h12a2 2 0 002-2V9zM4 13a1 1 0 011-1h1a1 1 0 110 2H5a1 1 0 01-1-1zm5-1a1 1 0 100 2h1a1 1 0 100-2H9z" clip-rule="evenodd"/>
          </svg>
        </div>
        <h1 class="text-3xl font-bold text-bank-secondary-800 mb-2">Criar Conta</h1>
        <p class="text-bank-secondary-600">Junte-se ao BankVue hoje mesmo</p>
      </div>

      <div class="card backdrop-blur-sm bg-white/90 p-8">
        <form @submit.prevent="handleCadastro" class="space-y-6">
          <div>
            <label for="fullName" class="form-label">Nome Completo</label>
            <div class="relative">
              <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <svg class="h-5 w-5 text-bank-secondary-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
                </svg>
              </div>
              <input 
                id="fullName" 
                v-model="fullName"
                type="text" 
                required 
                class="input-field"
                placeholder="Digite seu nome completo"
              >
            </div>
          </div>

          <div>
            <label for="email" class="form-label">E-mail</label>
            <div class="relative">
              <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <svg class="h-5 w-5 text-bank-secondary-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 12a4 4 0 10-8 0 4 4 0 008 0zm0 0v1.5a2.5 2.5 0 005 0V12a9 9 0 10-9 9m4.5-1.206a8.959 8.959 0 01-4.5 1.207"/>
                </svg>
              </div>
              <input 
                id="email" 
                v-model="email"
                type="email" 
                required 
                class="input-field"
                placeholder="Digite seu e-mail"
              >
            </div>
          </div>

          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <label for="cpf" class="form-label">CPF</label>
              <div class="relative">
                <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <svg class="h-5 w-5 text-bank-secondary-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 6H5a2 2 0 00-2 2v9a2 2 0 002 2h14a2 2 0 002-2V8a2 2 0 00-2-2h-5m-4 0V5a2 2 0 114 0v1m-4 0a2 2 0 104 0m-5 8a2 2 0 100-4 2 2 0 000 4zm0 0c1.306 0 2.417.835 2.83 2M9 14a3.001 3.001 0 00-2.83 2M15 11h3m-3 4h2"/>
                  </svg>
                </div>
                <input 
                  id="cpf" 
                  v-model="cpf"
                  @input="formatCPF"
                  type="text" 
                  required 
                  maxlength="14"
                  class="input-field"
                  placeholder="000.000.000-00"
                >
              </div>
            </div>

            <div>
              <label for="phone" class="form-label">Telefone</label>
              <div class="relative">
                <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <svg class="h-5 w-5 text-bank-secondary-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z"/>
                  </svg>
                </div>
                <input 
                  id="phone" 
                  v-model="phone"
                  @input="formatPhone"
                  type="text" 
                  required 
                  maxlength="16"
                  class="input-field"
                  placeholder="(00) 00000-0000"
                >
              </div>
            </div>
          </div>

          <div>
            <label for="password" class="form-label">Senha</label>
            <div class="relative">
              <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <svg class="h-5 w-5 text-bank-secondary-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"/>
                </svg>
              </div>
              <input
                id="password"
                v-model="password"
                :type="showPassword ? 'text' : 'password'"
                placeholder="Digite uma senha forte"
                class="input-field pr-10"
                required
                @focus="showPasswordRequirements = true"
                @blur="showPasswordRequirements = false"
              />
              <button
                type="button"
                @click="togglePasswordVisibility"
                class="absolute inset-y-0 right-0 pr-3 flex items-center"
              >
                <svg v-if="showPassword" class="h-5 w-5 text-bank-secondary-400 hover:text-bank-secondary-600 transition-colors" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.543-7a9.97 9.97 0 011.563-3.029m5.858.908a3 3 0 114.243 4.243M9.878 9.878l4.242 4.242M9.878 9.878L3 3m6.878 6.878L21 21"/>
                </svg>
                <svg v-else class="h-5 w-5 text-bank-secondary-400 hover:text-bank-secondary-600 transition-colors" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"/>
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"/>
                </svg>
              </button>
            </div>
            
            <div v-if="showPasswordRequirements" class="mt-3 p-3 bg-bank-secondary-50 rounded-lg border border-bank-secondary-200">
              <h4 class="text-sm font-medium text-bank-secondary-700 mb-2">Requisitos da senha:</h4>
              <ul class="space-y-1 text-xs">
                <li class="flex items-center space-x-2">
                  <svg :class="passwordRequirements.hasSpecialChar ? 'text-green-500' : 'text-red-500'" class="w-4 h-4" fill="currentColor" viewBox="0 0 20 20">
                    <path v-if="passwordRequirements.hasSpecialChar" fill-rule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clip-rule="evenodd"/>
                    <path v-else fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"/>
                  </svg>
                  <span :class="passwordRequirements.hasSpecialChar ? 'text-green-700' : 'text-red-700'">
                    Deve ter 1 caractere especial @#$%&*
                  </span>
                </li>
                <li class="flex items-center space-x-2">
                  <svg :class="passwordRequirements.hasUppercase ? 'text-green-500' : 'text-red-500'" class="w-4 h-4" fill="currentColor" viewBox="0 0 20 20">
                    <path v-if="passwordRequirements.hasUppercase" fill-rule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clip-rule="evenodd"/>
                    <path v-else fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"/>
                  </svg>
                  <span :class="passwordRequirements.hasUppercase ? 'text-green-700' : 'text-red-700'">
                    Deve ter 1 letra maiúscula
                  </span>
                </li>
                <li class="flex items-center space-x-2">
                  <svg :class="passwordRequirements.hasLowercase ? 'text-green-500' : 'text-red-500'" class="w-4 h-4" fill="currentColor" viewBox="0 0 20 20">
                    <path v-if="passwordRequirements.hasLowercase" fill-rule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clip-rule="evenodd"/>
                    <path v-else fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"/>
                  </svg>
                  <span :class="passwordRequirements.hasLowercase ? 'text-green-700' : 'text-red-700'">
                    Deve ter 1 letra minúscula
                  </span>
                </li>
                <li class="flex items-center space-x-2">
                  <svg :class="passwordRequirements.hasNumber ? 'text-green-500' : 'text-red-500'" class="w-4 h-4" fill="currentColor" viewBox="0 0 20 20">
                    <path v-if="passwordRequirements.hasNumber" fill-rule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clip-rule="evenodd"/>
                    <path v-else fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"/>
                  </svg>
                  <span :class="passwordRequirements.hasNumber ? 'text-green-700' : 'text-red-700'">
                    Deve ter 1 número
                  </span>
                </li>
                <li class="flex items-center space-x-2">
                  <svg :class="passwordRequirements.hasMinLength ? 'text-green-500' : 'text-red-500'" class="w-4 h-4" fill="currentColor" viewBox="0 0 20 20">
                    <path v-if="passwordRequirements.hasMinLength" fill-rule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clip-rule="evenodd"/>
                    <path v-else fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"/>
                  </svg>
                  <span :class="passwordRequirements.hasMinLength ? 'text-green-700' : 'text-red-700'">
                    Deve ter no mínimo 8 dígitos
                  </span>
                </li>
              </ul>
            </div>
            
            <!-- Password strength indicator -->
            <div class="mt-2">
              <div class="flex items-center space-x-2 text-xs">
                <div class="flex-1 bg-bank-secondary-200 rounded-full h-1">
                  <div :class="passwordStrengthClass" class="h-1 rounded-full transition-all duration-300"></div>
                </div>
                <span :class="passwordStrengthTextClass" class="font-medium">{{ passwordStrengthText }}</span>
              </div>
            </div>
          </div>

          <div>
            <label for="confirmPassword" class="form-label">Confirmar Senha</label>
            <div class="relative">
              <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <svg class="h-5 w-5 text-bank-secondary-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"/>
                </svg>
              </div>
              <input
                id="confirmPassword"
                v-model="confirmPassword"
                :type="showConfirmPassword ? 'text' : 'password'"
                placeholder="Confirme sua senha"
                class="input-field pr-10"
                :class="{ 'border-red-500 focus:border-red-500 focus:ring-red-100': !passwordsMatch && confirmPassword !== '' }"
                required
              />
              <button
                type="button"
                @click="toggleConfirmPasswordVisibility"
                class="absolute inset-y-0 right-0 pr-3 flex items-center"
              >
                <svg v-if="showConfirmPassword" class="h-5 w-5 text-bank-secondary-400 hover:text-bank-secondary-600 transition-colors" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.543-7a9.97 9.97 0 011.563-3.029m5.858.908a3 3 0 114.243 4.243M9.878 9.878l4.242 4.242M9.878 9.878L3 3m6.878 6.878L21 21"/>
                </svg>
                <svg v-else class="h-5 w-5 text-bank-secondary-400 hover:text-bank-secondary-600 transition-colors" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"/>
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"/>
                </svg>
              </button>
            </div>
            
            <div v-if="!passwordsMatch && confirmPassword !== ''" class="mt-2 flex items-center space-x-2">
              <svg class="w-4 h-4 text-red-500" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"/>
              </svg>
              <span class="text-red-600 text-xs font-medium">As senhas não coincidem</span>
            </div>
          </div>

          <div class="flex items-start space-x-3">
            <div class="flex items-center h-5 mt-1">
              <input 
                id="terms" 
                v-model="acceptTerms"
                type="checkbox" 
                class="h-4 w-4 text-bank-primary-600 focus:ring-bank-primary-500 border-bank-secondary-300 rounded transition-colors"
                required
              >
            </div>
            <div class="text-sm">
              <label for="terms" class="text-bank-secondary-700 leading-relaxed">
                Aceito os 
                <a href="#" class="font-medium text-bank-primary-600 hover:text-bank-primary-500 underline">Termos de Uso</a>
                e a 
                <a href="#" class="font-medium text-bank-primary-600 hover:text-bank-primary-500 underline">Política de Privacidade</a>
              </label>
            </div>
          </div>

          <button 
            type="submit" 
            class="btn-primary w-full" 
            :disabled="!acceptTerms || isLoading"
            :class="{ 'opacity-50 cursor-not-allowed': !acceptTerms || isLoading }"
          >
            <div v-if="isLoading" class="flex items-center justify-center">
              <svg class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
              </svg>
              Criando conta...
            </div>
            <div v-else class="flex items-center justify-center">
              <svg class="w-5 h-5 mr-2 inline-block" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M18 9v3m0 0v3m0-3h3m-3 0h-3m-2-5a4 4 0 11-8 0 4 4 0 018 0zM3 20a6 6 0 0112 0v1H3v-1z"/>
              </svg>
              Criar Minha Conta
            </div>
          </button>

          <div class="text-center">
            <span class="text-bank-secondary-600">Já tem uma conta?</span>
            <button 
              type="button" 
              @click="goToLogin" 
              class="ml-1 font-medium text-bank-primary-600 hover:text-bank-primary-500 transition-colors underline"
            >
              Fazer Login
            </button>
          </div>
        </form>

        <div class="mt-6 grid grid-cols-1 md:grid-cols-3 gap-4">
          <div class="text-center p-3 bg-bank-primary-50 rounded-lg">
            <svg class="w-6 h-6 text-bank-primary-600 mx-auto mb-2" fill="currentColor" viewBox="0 0 20 20">
              <path fill-rule="evenodd" d="M2.166 4.999A11.954 11.954 0 0010 1.944 11.954 11.954 0 0017.834 5c.11.65.166 1.32.166 2.001 0 5.225-3.34 9.67-8 11.317C5.34 16.67 2 12.225 2 7c0-.682.057-1.35.166-2.001zm11.541 3.708a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"/>
            </svg>
            <p class="text-xs text-bank-primary-700 font-medium">Criptografia 256-bit</p>
          </div>
          <div class="text-center p-3 bg-bank-primary-50 rounded-lg">
            <svg class="w-6 h-6 text-bank-primary-600 mx-auto mb-2" fill="currentColor" viewBox="0 0 20 20">
              <path fill-rule="evenodd" d="M18 8a6 6 0 01-7.743 5.743L10 14l-1 1-1 1H6v2H2v-4l4.257-4.257A6 6 0 1118 8zm-6-4a1 1 0 100 2 2 2 0 012 2 1 1 0 102 0 4 4 0 00-4-4z" clip-rule="evenodd"/>
            </svg>
            <p class="text-xs text-bank-primary-700 font-medium">Autenticação 2FA</p>
          </div>
          <div class="text-center p-3 bg-bank-primary-50 rounded-lg">
            <svg class="w-6 h-6 text-bank-primary-600 mx-auto mb-2" fill="currentColor" viewBox="0 0 20 20">
              <path fill-rule="evenodd" d="M3 4a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zm0 4a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zm0 4a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zm0 4a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1z" clip-rule="evenodd"/>
            </svg>
            <p class="text-xs text-bank-primary-700 font-medium">Conformidade LGPD</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useToast } from 'vue-toastification'
import { userService } from '../services/userService.js'
import authService from '../services/authService.js'

const router = useRouter()
const toast = useToast()

const fullName = ref('')
const email = ref('')
const cpf = ref('')
const phone = ref('')
const password = ref('')
const confirmPassword = ref('')
const acceptTerms = ref(false)
const isLoading = ref(false)
const showPasswordRequirements = ref(false)
const showPassword = ref(false)
const showConfirmPassword = ref(false)

const togglePasswordVisibility = () => {
  showPassword.value = !showPassword.value
}

const toggleConfirmPasswordVisibility = () => {
  showConfirmPassword.value = !showConfirmPassword.value
}

const passwordRequirements = computed(() => {
  const pwd = password.value
  return {
    hasSpecialChar: /[@#$%&*]/.test(pwd),
    hasUppercase: /[A-Z]/.test(pwd),
    hasLowercase: /[a-z]/.test(pwd),
    hasNumber: /\d/.test(pwd),
    hasMinLength: pwd.length >= 8
  }
})

const passwordsMatch = computed(() => {
  return password.value === confirmPassword.value || confirmPassword.value === ''
})

const passwordStrength = computed(() => {
  const requirements = passwordRequirements.value
  const pwd = password.value
  
  if (pwd.length === 0) return 0
  
  let strength = 0
  if (requirements.hasSpecialChar) strength++
  if (requirements.hasUppercase) strength++
  if (requirements.hasLowercase) strength++
  if (requirements.hasNumber) strength++
  if (requirements.hasMinLength) strength++
  
  return strength
})

const passwordStrengthClass = computed(() => {
  const strength = passwordStrength.value
  if (strength === 0) return 'w-0'
  if (strength <= 2) return 'w-1/3 bg-red-500'
  if (strength <= 3) return 'w-2/3 bg-yellow-500'
  return 'w-full bg-green-500'
})

const passwordStrengthText = computed(() => {
  const strength = passwordStrength.value
  if (strength === 0) return ''
  if (strength <= 2) return 'Fraca'
  if (strength <= 3) return 'Média'
  return 'Forte'
})

const passwordStrengthTextClass = computed(() => {
  const strength = passwordStrength.value
  if (strength <= 2) return 'text-red-600'
  if (strength <= 3) return 'text-yellow-600'
  return 'text-green-600'
})

const handleCadastro = async () => {
  if (isLoading.value) return
  
  if (!fullName.value || !email.value || !cpf.value || !phone.value || !password.value) {
    toast.error('Por favor, preencha todos os campos obrigatórios')
    return
  }
  
  if (password.value !== confirmPassword.value) {
    toast.error('As senhas não coincidem')
    return
  }
  
  const requirements = passwordRequirements.value
  if (!requirements.hasSpecialChar || !requirements.hasUppercase || !requirements.hasLowercase || !requirements.hasNumber || !requirements.hasMinLength) {
    toast.error('A senha deve atender a todos os requisitos')
    return
  }
  
  if (!acceptTerms.value) {
    toast.error('Você deve aceitar os termos e condições')
    return
  }
  
  isLoading.value = true
  
  try {
    const userData = {
      fullName: fullName.value,
      email: email.value,
      cpf: cpf.value,
      phone: phone.value,
      password: password.value
    }
    
    // Primeiro, cria a conta
    await userService.register(userData)
    
    // Após criar a conta, faz login automaticamente
    try {
      await authService.login({
        email: email.value,
        password: password.value
      })
      
      toast.success('Conta criada com sucesso! Você já está logado.')
      
      // Redireciona para o dashboard
      setTimeout(() => {
        router.push('/dashboard')
      }, 1500)
      
    } catch (loginError) {
      console.error('Erro no login automático:', loginError)
      toast.success('Conta criada com sucesso! Redirecionando para o login...')
      
      // Se o login automático falhar, redireciona para a página de login
      setTimeout(() => {
        router.push('/login')
      }, 2000)
    }
    
  } catch (error) {
    console.error('Erro ao criar conta:', error)
    toast.error(error.message || 'Erro ao criar conta. Tente novamente.')
  } finally {
    isLoading.value = false
  }
}

const goToLogin = () => {
  router.push('/')
}

const formatCPF = (event) => {
  let value = event.target.value.replace(/\D/g, '')
  value = value.replace(/(\d{3})(\d)/, '$1.$2')
  value = value.replace(/(\d{3})(\d)/, '$1.$2')
  value = value.replace(/(\d{3})(\d{1,2})$/, '$1-$2')
  cpf.value = value
}

const formatPhone = (event) => {
  let value = event.target.value.replace(/\D/g, '')
  value = value.replace(/(\d{2})(\d)/, '($1) $2')
  value = value.replace(/(\d)(\d{4})(\d)/, '$1 $2-$3')
  phone.value = value
}
</script>
