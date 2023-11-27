package com.example.zad1_2_v2_grebenukov.Database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class IncomeViewModel(application: Application):AndroidViewModel(application) {
    private val incomeDao: IncomeDao
    private val database: IncomeDatabase = IncomeDatabase.getDatabase(application)
    init {
        incomeDao = database.incomeDao()
    }
    fun insertIncome(income: Income){
        viewModelScope.launch {
            incomeDao.insertIncome(income)
        }
    }
    fun getAllIncomes(): LiveData<List<Income>>{
        return incomeDao.getAllIncomes()
    }
}