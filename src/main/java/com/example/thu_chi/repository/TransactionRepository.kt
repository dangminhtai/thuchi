package com.example.thu_chi.repository

import com.example.thu_chi.data.*
import kotlinx.coroutines.flow.Flow

class TransactionRepository(
    private val transactionDao: TransactionDao,
    private val budgetDao: BudgetDao,
    private val categoryDao: CategoryDao
) {
    val allTransactions: Flow<List<Transaction>> = transactionDao.getAllTransactions()
    val allCategories: Flow<List<Category>> = categoryDao.getAllCategories()

    fun getBudgetsForMonth(monthYear: String): Flow<List<Budget>> = budgetDao.getBudgetsForMonth(monthYear)

    suspend fun setBudget(budget: Budget) = budgetDao.setBudget(budget)

    suspend fun insertCategory(category: Category) = categoryDao.insertCategory(category)

    suspend fun deleteCategory(category: Category) = categoryDao.deleteCategory(category)

    suspend fun clearCategories() = categoryDao.clearCategories()

    suspend fun insert(transaction: Transaction) {
        transactionDao.insertTransaction(transaction)
    }

    suspend fun update(transaction: Transaction) {
        transactionDao.updateTransaction(transaction)
    }

    suspend fun delete(transaction: Transaction) {
        transactionDao.deleteTransaction(transaction)
    }

    fun getTransactionsInRange(startDate: Long, endDate: Long): Flow<List<Transaction>> {
        return transactionDao.getTransactionsInRange(startDate, endDate)
    }

    fun getTotalIncome(startDate: Long, endDate: Long): Flow<Long?> {
        return transactionDao.getTotalIncomeInRange(startDate, endDate)
    }

    fun getTotalExpense(startDate: Long, endDate: Long): Flow<Long?> {
        return transactionDao.getTotalExpenseInRange(startDate, endDate)
    }
}
