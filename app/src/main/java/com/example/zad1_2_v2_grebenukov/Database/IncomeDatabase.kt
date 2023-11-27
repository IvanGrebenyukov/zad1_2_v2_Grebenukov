package com.example.zad1_2_v2_grebenukov.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Income::class], version = 1, exportSchema = false)
abstract class IncomeDatabase: RoomDatabase() {
    abstract fun incomeDao(): IncomeDao
    companion object{
        @Volatile
        private var INSTANCE: IncomeDatabase ?= null

        fun getDatabase(context: Context): IncomeDatabase{
            synchronized(this ){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        IncomeDatabase::class.java,
                        "income_database").build()
                }
                return INSTANCE!!
            }
        }
    }
}