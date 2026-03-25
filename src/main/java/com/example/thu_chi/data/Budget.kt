package com.example.thu_chi.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budgets")
data class Budget(
    @PrimaryKey
    val category: String, // One budget per category
    val limitAmount: Long,
    val monthYear: String // Format "MM/yyyy"
)
