<template>
  <div class="flex justify-center items-center py-12">
    <!-- Spinner animado -->
    <div class="relative">
      <div class="w-16 h-16 border-4 border-blue-200 border-solid rounded-full animate-spin">
        <div class="absolute top-0 left-0 w-16 h-16 border-4 border-blue-600 border-solid rounded-full border-t-transparent animate-spin"></div>
      </div>
    </div>
    
    <!-- Texto de carregamento -->
    <div class="ml-4">
      <p class="text-lg font-medium text-gray-700">{{ message || 'Carregando...' }}</p>
      <p v-if="subtitle" class="text-sm text-gray-500">{{ subtitle }}</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LoadingSpinner',
  props: {
    message: {
      type: String,
      default: 'Carregando...'
    },
    subtitle: {
      type: String,
      default: null
    }
  }
}
</script>

<style scoped>
@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.animate-spin {
  animation: spin 1s linear infinite;
}
</style>
