<template>
  <div class="relative">
    <!-- Input Field -->
    <div
      @click="toggleCalendar"
      :class="[
        'w-full px-3 py-2 border rounded-md shadow-sm cursor-pointer flex items-center justify-between',
        disabled ? 'bg-gray-100 cursor-not-allowed' : 'border-gray-300 hover:border-blue-400',
        'focus-within:ring-2 focus-within:ring-blue-500 focus-within:border-blue-500'
      ]"
    >
      <span :class="selectedDate ? 'text-gray-900' : 'text-gray-500'">
        {{ selectedDate ? formatDisplayDate(selectedDate) : placeholder }}
      </span>
      <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
      </svg>
    </div>

    <!-- Calendar Dropdown -->
    <div
      v-if="showCalendar"
      class="absolute top-full left-0 mt-1 bg-white border border-gray-300 rounded-lg shadow-lg z-50 p-4 w-80"
    >
      <!-- Calendar Header -->
      <div class="flex items-center justify-between mb-4">
        <button
          type="button"
          @click="previousMonth"
          class="p-1 hover:bg-gray-100 rounded"
        >
          <svg class="w-5 h-5 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
          </svg>
        </button>
        
        <h3 class="text-lg font-medium text-gray-900">
          {{ formatMonthYear(currentMonth, currentYear) }}
        </h3>
        
        <button
          type="button"
          @click="nextMonth"
          class="p-1 hover:bg-gray-100 rounded"
        >
          <svg class="w-5 h-5 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
          </svg>
        </button>
      </div>

      <!-- Days of Week -->
      <div class="grid grid-cols-7 gap-1 mb-2">
        <div
          v-for="day in daysOfWeek"
          :key="day"
          class="text-center text-sm font-medium text-gray-500 py-2"
        >
          {{ day }}
        </div>
      </div>

      <!-- Calendar Grid -->
      <div class="grid grid-cols-7 gap-1">
        <!-- Empty cells for days before month start -->
        <div
          v-for="empty in emptyDays"
          :key="`empty-${empty}`"
          class="h-10"
        ></div>
        
        <!-- Days of current month -->
        <button
          v-for="day in daysInMonth"
          :key="day"
          type="button"
          @click="selectDate(day)"
          :disabled="isDateDisabled(day)"
          :class="[
            'h-10 w-10 rounded-full text-sm font-medium transition-colors',
            isDateDisabled(day) 
              ? 'text-gray-300 cursor-not-allowed' 
              : 'text-gray-900 hover:bg-blue-100',
            isSelectedDate(day) 
              ? 'bg-blue-600 text-white hover:bg-blue-700' 
              : '',
            isToday(day) && !isSelectedDate(day) 
              ? 'bg-blue-50 text-blue-600 border border-blue-200' 
              : ''
          ]"
        >
          {{ day }}
        </button>
      </div>

      <!-- Quick Actions -->
      <div class="flex justify-between items-center mt-4 pt-3 border-t border-gray-200">
        <button
          type="button"
          @click="selectToday"
          class="text-sm text-blue-600 hover:text-blue-700 font-medium"
        >
          Hoje
        </button>
        <button
          type="button"
          @click="clearDate"
          class="text-sm text-gray-500 hover:text-gray-700"
        >
          Limpar
        </button>
      </div>
    </div>

    <!-- Overlay to close calendar -->
    <div
      v-if="showCalendar"
      @click="closeCalendar"
      class="fixed inset-0 z-40"
    ></div>
  </div>
</template>

<script>
import { ref, computed, watch, onMounted, onUnmounted } from 'vue'

export default {
  name: 'DatePicker',
  props: {
    modelValue: {
      type: String,
      default: ''
    },
    disabled: {
      type: Boolean,
      default: false
    },
    placeholder: {
      type: String,
      default: 'Selecione uma data'
    },
    minDate: {
      type: String,
      default: ''
    }
  },
  emits: ['update:modelValue'],
  setup(props, { emit }) {
    // Estado reativo
    const showCalendar = ref(false)
    const currentMonth = ref(new Date().getMonth())
    const currentYear = ref(new Date().getFullYear())
    const selectedDate = ref(props.modelValue)

    // Computed
    const daysOfWeek = ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb']
    
    const daysInMonth = computed(() => {
      return new Date(currentYear.value, currentMonth.value + 1, 0).getDate()
    })
    
    const emptyDays = computed(() => {
      return new Date(currentYear.value, currentMonth.value, 1).getDay()
    })

    // Métodos
    const toggleCalendar = () => {
      if (!props.disabled) {
        showCalendar.value = !showCalendar.value
      }
    }

    const closeCalendar = () => {
      showCalendar.value = false
    }

    const previousMonth = () => {
      if (currentMonth.value === 0) {
        currentMonth.value = 11
        currentYear.value--
      } else {
        currentMonth.value--
      }
    }

    const nextMonth = () => {
      if (currentMonth.value === 11) {
        currentMonth.value = 0
        currentYear.value++
      } else {
        currentMonth.value++
      }
    }

    const selectDate = (day) => {
      if (isDateDisabled(day)) return
      
      const year = currentYear.value
      const month = currentMonth.value
      
      const date = new Date()
      date.setFullYear(year, month, day)
      date.setHours(12, 0, 0, 0) 
      
      const formattedDate = date.toLocaleDateString('pt-BR', {
        timeZone: 'America/Sao_Paulo',
        year: 'numeric',
        month: '2-digit',
        day: '2-digit'
      }).split('/').reverse().join('-')
      
      selectedDate.value = formattedDate
      emit('update:modelValue', formattedDate)
      closeCalendar()
    }

    const selectToday = () => {
      // Obter data atual no timezone de São Paulo
      const today = new Date()
      const saoPauloDate = new Intl.DateTimeFormat('pt-BR', {
        timeZone: 'America/Sao_Paulo',
        year: 'numeric',
        month: '2-digit',
        day: '2-digit'
      }).format(today)
      
      // Converter de DD/MM/YYYY para YYYY-MM-DD
      const [day, month, year] = saoPauloDate.split('/')
      const formattedDate = `${year}-${month}-${day}`
      
      selectedDate.value = formattedDate
      emit('update:modelValue', formattedDate)
      closeCalendar()
    }

    const clearDate = () => {
      selectedDate.value = ''
      emit('update:modelValue', '')
      closeCalendar()
    }

    const isDateDisabled = (day) => {
      // Obter data atual no timezone de São Paulo
      const now = new Date()
      const saoPauloNow = new Date(now.toLocaleString('en-US', { timeZone: 'America/Sao_Paulo' }))
      const todayYear = saoPauloNow.getFullYear()
      const todayMonth = saoPauloNow.getMonth()
      const todayDay = saoPauloNow.getDate()
      
      // Verifica se a data é anterior a hoje
      if (currentYear.value < todayYear) return true
      if (currentYear.value === todayYear && currentMonth.value < todayMonth) return true
      if (currentYear.value === todayYear && currentMonth.value === todayMonth && day < todayDay) return true
      
      // Verifica se há uma data mínima configurada
      if (props.minDate) {
        const minDate = new Date(props.minDate + 'T12:00:00')
        const minYear = minDate.getFullYear()
        const minMonth = minDate.getMonth()
        const minDay = minDate.getDate()
        
        if (currentYear.value < minYear) return true
        if (currentYear.value === minYear && currentMonth.value < minMonth) return true
        if (currentYear.value === minYear && currentMonth.value === minMonth && day < minDay) return true
      }
      
      return false
    }

    const isSelectedDate = (day) => {
      if (!selectedDate.value) return false
      
      const date = new Date(selectedDate.value)
      return date.getDate() === day && 
             date.getMonth() === currentMonth.value && 
             date.getFullYear() === currentYear.value
    }

    const isToday = (day) => {
      // Obter data atual no timezone de São Paulo
      const now = new Date()
      const saoPauloNow = new Date(now.toLocaleString('en-US', { timeZone: 'America/Sao_Paulo' }))
      
      return saoPauloNow.getDate() === day && 
             saoPauloNow.getMonth() === currentMonth.value && 
             saoPauloNow.getFullYear() === currentYear.value
    }

    const formatDisplayDate = (dateString) => {
      const date = new Date(dateString)
      return date.toLocaleDateString('pt-BR', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
      })
    }

    const formatMonthYear = (month, year) => {
      const date = new Date(year, month)
      return date.toLocaleDateString('pt-BR', {
        month: 'long',
        year: 'numeric'
      })
    }

    // Handle escape key
    const handleEscape = (event) => {
      if (event.key === 'Escape') {
        closeCalendar()
      }
    }

    // Watch for prop changes
    watch(() => props.modelValue, (newValue) => {
      selectedDate.value = newValue
      if (newValue) {
        const date = new Date(newValue)
        currentMonth.value = date.getMonth()
        currentYear.value = date.getFullYear()
      }
    })

    // Lifecycle
    onMounted(() => {
      document.addEventListener('keydown', handleEscape)
    })

    onUnmounted(() => {
      document.removeEventListener('keydown', handleEscape)
    })

    return {
      // Estado
      showCalendar,
      currentMonth,
      currentYear,
      selectedDate,
      
      // Computed
      daysOfWeek,
      daysInMonth,
      emptyDays,
      
      // Métodos
      toggleCalendar,
      closeCalendar,
      previousMonth,
      nextMonth,
      selectDate,
      selectToday,
      clearDate,
      isDateDisabled,
      isSelectedDate,
      isToday,
      formatDisplayDate,
      formatMonthYear
    }
  }
}
</script>

<style scoped>
/* Ensure calendar appears above other elements */
.z-50 {
  z-index: 50;
}

.z-40 {
  z-index: 40;
}

/* Smooth transitions */
.transition-colors {
  transition-property: background-color, border-color, color;
  transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
  transition-duration: 150ms;
}
</style>
