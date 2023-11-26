package com.example.zad1_2_v2_grebenukov.Database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "income")
data class Income(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Double,
    val date: String,
    val description: String
)



