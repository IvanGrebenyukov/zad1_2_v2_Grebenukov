package com.example.zad1_2_v2_grebenukov

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.room.Room
import com.example.zad1_2_v2_grebenukov.Database.Income
import com.example.zad1_2_v2_grebenukov.Database.IncomeDao
import com.example.zad1_2_v2_grebenukov.Database.IncomeDatabase
import com.example.zad1_2_v2_grebenukov.databinding.FragmentAddIncomeBinding
import com.example.zad1_2_v2_grebenukov.databinding.FragmentIncomeListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class AddIncomeFragment : Fragment() {
    private lateinit var incomeDao: IncomeDao
    private lateinit var selectedDate: String
    private lateinit var binding: FragmentAddIncomeBinding
    private lateinit var incomeDatabase: IncomeDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddIncomeBinding.inflate(inflater, container, false)
        val calendar = Calendar.getInstance()
        val currentDate = DateFormat.getDateInstance().format(calendar.time)
//        incomeDatabase = Room.databaseBuilder(requireContext(), IncomeDatabase::class.java, "income").build()
        binding.buttonSelectDate.setOnClickListener {
            showDatePicker()
        }
        binding.buttonAdd.setOnClickListener {
            val amount = binding.editTextAmount.text.toString().toDouble()
            val description = binding.editTextDescription.text.toString()
            binding.date.text = selectedDate
            binding.amount.text = amount.toString()
//
            val newIncome = Income(
                amount = amount,
                date = selectedDate,
                description = description
            )
            Toast.makeText(requireContext(), "Неверное имя пользователя или пароль.", Toast.LENGTH_SHORT).show()
            addIncomeToDatabase(newIncome)
            Toast.makeText(requireContext(), "Нffffffffffffffffffff", Toast.LENGTH_LONG).show()
        }

        return binding.root
    }
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val chosenDateCalendar = Calendar.getInstance()
            chosenDateCalendar.set(year, month, dayOfMonth)
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            selectedDate = sdf.format(chosenDateCalendar.time)
        }, year, month, dayOfMonth)

        datePickerDialog.show()
    }
    private fun addIncomeToDatabase(income: Income) {
        val database = IncomeDatabase.getDatabase(requireContext())
        incomeDao = database.incomeDao()

//        CoroutineScope(Dispatchers.IO).launch {
//            incomeDao.insertIncome(income)
//        }


    }


}