package com.example.thu_chi.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [Transaction::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "thu_chi_database_v2"
                )
                .addCallback(object : RoomDatabase.Callback() {
                    @OptIn(DelicateCoroutinesApi::class)
                    override fun onCreate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Seed data
                        INSTANCE?.let { database ->
                            GlobalScope.launch {
                                val dao = database.transactionDao()
                                val cal = java.util.Calendar.getInstance()
                                val now = cal.timeInMillis
                                dao.insertTransaction(Transaction(amount = 15000000, note = "Lương tháng", date = now, isIncome = true, category = "Thu nhập"))
                                dao.insertTransaction(Transaction(amount = 35000, note = "Ăn sáng", date = now, isIncome = false, category = "Ăn uống"))
                                dao.insertTransaction(Transaction(amount = 3500000, note = "Tiền nhà", date = now, isIncome = false, category = "Tiền nhà"))
                                dao.insertTransaction(Transaction(amount = 500000, note = "Tiền điện", date = now, isIncome = false, category = "Tiền điện"))
                                dao.insertTransaction(Transaction(amount = 200000, note = "Mỹ phẩm", date = now, isIncome = false, category = "Mỹ phẩm"))
                                dao.insertTransaction(Transaction(amount = 45000, note = "Phí giao lưu", date = now, isIncome = false, category = "Phí giao lưu"))
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
