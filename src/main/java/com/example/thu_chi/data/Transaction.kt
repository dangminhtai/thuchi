package com.example.thu_chi.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Long,
    val note: String,
    val date: Long, // Epoch timestamp
    val isIncome: Boolean,
    val category: String
) : Serializable
