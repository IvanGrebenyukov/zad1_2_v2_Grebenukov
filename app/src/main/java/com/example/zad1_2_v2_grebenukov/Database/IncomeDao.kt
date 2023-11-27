package com.example.zad1_2_v2_grebenukov.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface IncomeDao {
    @Query("SELECT * FROM income ORDER BY date DESC")
    fun getAllIncomes(): LiveData<List<Income>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIncome(income: Income)
    @Update
    suspend fun updateIncome(income: Income)
    @Query("SELECT * FROM income WHERE id = :id")
    fun getIncomeById(id: Long): LiveData<Income?>
    @Delete
    suspend fun deleteIncome(income: Income)
}