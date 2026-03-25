package com.example.thu_chi.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [Transaction::class, Budget::class, Category::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun budgetDao(): BudgetDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "thu_chi_database_v5"
                )
                .addCallback(object : RoomDatabase.Callback() {
                    @OptIn(DelicateCoroutinesApi::class)
                    override fun onCreate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Seed data
                        INSTANCE?.let { database ->
                            GlobalScope.launch {
                                val cDao = database.categoryDao()
                                val tDao = database.transactionDao()
                                val bDao = database.budgetDao()
                                
                                val categories = listOf(
                                    Category(name = "Ăn uống", icon = "🍽️"),
                                    Category(name = "Phí giao lưu", icon = "🍻"),
                                    Category(name = "Y tế", icon = "🏥"),
                                    Category(name = "Mỹ phẩm", icon = "💄"),
                                    Category(name = "Tiền điện", icon = "⚡"),
                                    Category(name = "Tiền nhà", icon = "🏠"),
                                    Category(name = "Chi tiêu hàng ngày", icon = "🛒"),
                                    Category(name = "Giáo dục", icon = "🎓"),
                                    Category(name = "Quần áo", icon = "👕")
                                )
                                categories.forEach { cDao.insertCategory(it) }

                                val cal = java.util.Calendar.getInstance()
                                val now = cal.timeInMillis
                                tDao.insertTransaction(Transaction(amount = 15000000, note = "Lương tháng", date = now, isIncome = true, category = "Thu nhập"))
                                tDao.insertTransaction(Transaction(amount = 35000, note = "Ăn sáng", date = now, isIncome = false, category = "Ăn uống"))
                                tDao.insertTransaction(Transaction(amount = 3500000, note = "Tiền nhà", date = now, isIncome = false, category = "Tiền nhà"))
                                tDao.insertTransaction(Transaction(amount = 500000, note = "Tiền điện", date = now, isIncome = false, category = "Tiền điện"))
                                tDao.insertTransaction(Transaction(amount = 200000, note = "Mỹ phẩm", date = now, isIncome = false, category = "Mỹ phẩm"))
                                tDao.insertTransaction(Transaction(amount = 45000, note = "Phí giao lưu", date = now, isIncome = false, category = "Phí giao lưu"))

                                // Budgets
                                val monthYear = java.text.SimpleDateFormat("MM/yyyy", java.util.Locale.getDefault()).format(cal.time)
                                bDao.setBudget(Budget("Ăn uống", 1000000, monthYear))
                                bDao.setBudget(Budget("Tiền nhà", 4000000, monthYear))
                                bDao.setBudget(Budget("Tiền điện", 600000, monthYear))
                                bDao.setBudget(Budget("Mỹ phẩm", 500000, monthYear))

                                // Set default PIN for testing
                                com.example.thu_chi.util.SecurityUtils.setPin(context, "1234")
                            }
                        }
                    }
                })
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
