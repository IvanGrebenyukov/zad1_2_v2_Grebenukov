package com.example.zad1_2_v2_grebenukov

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope

import com.example.zad1_2_v2_grebenukov.Database.Income
import com.example.zad1_2_v2_grebenukov.Database.IncomeDao
import com.example.zad1_2_v2_grebenukov.Database.IncomeDatabase
import com.example.zad1_2_v2_grebenukov.Database.IncomeViewModel
import com.example.zad1_2_v2_grebenukov.databinding.FragmentAddIncomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

//private lateinit var incomeDao: IncomeDao
//private lateinit var incomeDatabase: IncomeDatabase


class AddIncomeFragment : Fragment() {
    private lateinit var selectedDate: String
    private lateinit var binding: FragmentAddIncomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddIncomeBinding.inflate(inflater, container, false)
        binding.buttonSelectDate.setOnClickListener {
            showDatePicker()
        }
        binding.buttonAdd.setOnClickListener {
            addIncomeToDatabase()
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
            binding.date.text = "-> ${selectedDate}"
        }, year, month, dayOfMonth)

        datePickerDialog.show()
    }
    private fun addIncomeToDatabase() {
        val amount = binding.editTextAmount.text.toString().toDouble()
        val description = binding.editTextDescription.text.toString()
        val currentDate = selectedDate
        val newIncome = Income(
            amount = amount,
            date = currentDate,
            description = description
        )
        val incomeDao = IncomeDatabase.getDatabase(requireContext()).incomeDao()
        CoroutineScope(Dispatchers.IO).launch {
            incomeDao.insertIncome(newIncome)
        }

    }


}