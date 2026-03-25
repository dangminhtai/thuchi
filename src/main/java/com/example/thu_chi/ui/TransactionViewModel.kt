package com.example.thu_chi.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.thu_chi.data.Transaction
import com.example.thu_chi.repository.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.util.Calendar

class TransactionViewModel(private val repository: TransactionRepository) : ViewModel() {

    private val _currentMonthRange = MutableStateFlow(getMonthRange(Calendar.getInstance()))
    private val _selectedDay = MutableStateFlow(getDayRange(Calendar.getInstance()))
    
    val transactionsInMonth = _currentMonthRange.flatMapLatest { (start, end) ->
        repository.getTransactionsInRange(start, end)
    }.asLiveData()

    val transactionsByDay = _selectedDay.flatMapLatest { (start, end) ->
        repository.getTransactionsInRange(start, end)
    }.asLiveData()

    val totalIncome = _currentMonthRange.flatMapLatest { (start, end) ->
        repository.getTotalIncome(start, end)
    }.asLiveData()

    val totalExpense = _currentMonthRange.flatMapLatest { (start, end) ->
        repository.getTotalExpense(start, end)
    }.asLiveData()

    fun insert(transaction: Transaction) = viewModelScope.launch {
        repository.insert(transaction)
    }

    fun delete(transaction: Transaction) = viewModelScope.launch {
        repository.delete(transaction)
    }

    fun setMonth(calendar: Calendar) {
        _currentMonthRange.value = getMonthRange(calendar)
    }

    fun setDay(calendar: Calendar) {
        _selectedDay.value = getDayRange(calendar)
    }

    private fun getDayRange(calendar: Calendar): Pair<Long, Long> {
        val cal = calendar.clone() as Calendar
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        val start = cal.timeInMillis

        cal.set(Calendar.HOUR_OF_DAY, 23)
        cal.set(Calendar.MINUTE, 59)
        cal.set(Calendar.SECOND, 59)
        cal.set(Calendar.MILLISECOND, 999)
        val end = cal.timeInMillis

        return Pair(start, end)
    }

    private fun getMonthRange(calendar: Calendar): Pair<Long, Long> {
        val cal = calendar.clone() as Calendar
        cal.set(Calendar.DAY_OF_MONTH, 1)
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        val start = cal.timeInMillis

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
        cal.set(Calendar.HOUR_OF_DAY, 23)
        cal.set(Calendar.MINUTE, 59)
        cal.set(Calendar.SECOND, 59)
        cal.set(Calendar.MILLISECOND, 999)
        val end = cal.timeInMillis

        return Pair(start, end)
    }

    class Factory(private val repository: TransactionRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TransactionViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
