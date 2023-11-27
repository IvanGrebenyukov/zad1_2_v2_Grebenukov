package com.example.zad1_2_v2_grebenukov

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController

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
    private lateinit var incomeDao: IncomeDao
    private lateinit var binding: FragmentAddIncomeBinding
    private var incomeId: Long? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddIncomeBinding.inflate(inflater, container, false)


        val database = IncomeDatabase.getDatabase(requireContext())
        incomeDao = database.incomeDao()
        incomeId = arguments?.getLong("incomeId")
        incomeId?.let {
            loadIncome(it)
            binding.buttonAdd.visibility = View.GONE
            binding.buttonEdit.visibility = View.VISIBLE
        }
        val filter = InputFilter { source, start, end, dest, dstart, dend ->
            val input = source.toString()
            if (input.matches(Regex("[0-9]*\\.?[0-9]*"))) {
                null
            } else {
                ""
            }
        }
        binding.editTextAmount.filters = arrayOf(filter)
        binding.buttonEdit.setOnClickListener {
            updateIncome(binding.editTextAmount.text.toString().toDoubleOrNull(), binding.editTextDescription.text.toString())
        }
        binding.buttonSelectDate.setOnClickListener {
            showDatePicker()
        }
        binding.buttonAdd.setOnClickListener {
            addIncomeToDatabase()
        }
        return binding.root
    }
    private fun loadIncome(id: Long) {
        incomeDao.getIncomeById(id).observe(viewLifecycleOwner, { income ->
            income?.let {
                binding.editTextAmount.setText(income.amount.toString())
                binding.editTextDescription.setText(income.description)
                binding.date.setText(income.date)
            }
        })
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
        if(amount != null && amount > 0 && description.isNotBlank() && currentDate != null){
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
        else{
            Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT)
        }


    }
    private fun updateIncome(amount: Double?, description: String) {
        if (amount != null && amount > 0 && description.isNotBlank() && selectedDate != null && incomeId != null) {
            val updatedIncome = Income(incomeId!!, amount, selectedDate, description)
            lifecycleScope.launch(Dispatchers.IO) {
                incomeDao.updateIncome(updatedIncome)
            }
            findNavController().popBackStack(R.id.incomeListFragment, false)
        } else {
           Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT)
        }
    }


}