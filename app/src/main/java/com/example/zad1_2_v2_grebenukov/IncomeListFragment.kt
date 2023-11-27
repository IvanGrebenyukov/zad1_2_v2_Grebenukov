package com.example.zad1_2_v2_grebenukov

import android.app.AlertDialog
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zad1_2_v2_grebenukov.Database.Income
import com.example.zad1_2_v2_grebenukov.Database.IncomeDao
import com.example.zad1_2_v2_grebenukov.Database.IncomeDatabase

import com.example.zad1_2_v2_grebenukov.databinding.FragmentIncomeListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class IncomeListFragment : Fragment() {

    private lateinit var binding: FragmentIncomeListBinding
    private lateinit var incomeDao: IncomeDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIncomeListBinding.inflate(inflater, container, false)

        val database = IncomeDatabase.getDatabase(requireContext())
        incomeDao = database.incomeDao()


        binding.recyclerViewIncomes.layoutManager = LinearLayoutManager(requireContext())

        loadIncomes()

//        binding.buttonNewIncome.setOnClickListener {
//            findNavController().navigate(R.id.action_incomeListFragment_to_addIncomeFragment)
//        }
        return binding.root
    }

    private fun loadIncomes() {
        incomeDao.getAllIncomes().observe(viewLifecycleOwner, { incomes ->
            binding.recyclerViewIncomes.adapter = IncomeAdapter(incomes,
                onEditClickListener = { selectedIncome -> editIncome(selectedIncome) },
                onDeleteClickListener = { selectedIncome -> deleteIncome(selectedIncome) }
            )
        })
    }
    private fun deleteIncome(income: Income) {
        AlertDialog.Builder(requireContext())
            .setTitle("Удаление")
            .setMessage("Вы действительно хотите удалить этот элемент?")
            .setPositiveButton("Удалить") { dialog, _ ->
                deleteIncomeFromDatabase(income)
                dialog.dismiss()
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun deleteIncomeFromDatabase(income: Income) {
        lifecycleScope.launch(Dispatchers.IO) {
            incomeDao.deleteIncome(income)
        }
    }
    private fun editIncome(income: Income) {
        val bundle = bundleOf("incomeId" to income.id)
        val navController = findNavController()
        navController.navigate(R.id.addIncomeFragment, bundle)
    }


}