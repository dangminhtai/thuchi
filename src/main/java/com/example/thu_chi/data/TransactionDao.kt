package com.example.thu_chi.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE date >= :startDate AND date <= :endDate ORDER BY date DESC")
    fun getTransactionsInRange(startDate: Long, endDate: Long): Flow<List<Transaction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction)

    @Update
    suspend fun updateTransaction(transaction: Transaction)

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    @Query("SELECT SUM(amount) FROM transactions WHERE isIncome = 1 AND date >= :startDate AND date <= :endDate")
    fun getTotalIncomeInRange(startDate: Long, endDate: Long): Flow<Long?>

    @Query("SELECT SUM(amount) FROM transactions WHERE isIncome = 0 AND date >= :startDate AND date <= :endDate")
    fun getTotalExpenseInRange(startDate: Long, endDate: Long): Flow<Long?>
}
