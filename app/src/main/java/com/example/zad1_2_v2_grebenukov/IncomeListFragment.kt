package com.example.zad1_2_v2_grebenukov

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zad1_2_v2_grebenukov.Database.IncomeDao
import com.example.zad1_2_v2_grebenukov.Database.IncomeDatabase

import com.example.zad1_2_v2_grebenukov.databinding.FragmentIncomeListBinding


class IncomeListFragment : Fragment() {

    private lateinit var binding: FragmentIncomeListBinding
    private lateinit var incomeAdapter: IncomeAdapter
    private lateinit var incomeDao: IncomeDao
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIncomeListBinding.inflate(inflater, container, false)

        val database = IncomeDatabase.getDatabase(requireContext())
        incomeDao = database.incomeDao()

       binding.recyclerViewIncomes.layoutManager = LinearLayoutManager(requireContext())
       incomeAdapter = IncomeAdapter(emptyList()) { income ->
           val bundle = Bundle()
           bundle.putParcelable("income", income as Parcelable)
           val editIncomeFragment = AddIncomeFragment()
           editIncomeFragment.arguments = bundle
           parentFragmentManager.beginTransaction()
               .replace(R.id.nav_host_fragment, editIncomeFragment)
               .addToBackStack(null)
               .commit()

       }
       binding.recyclerViewIncomes.adapter = incomeAdapter
       loadIncomes()

//        binding.buttonNewIncome.setOnClickListener {
//            findNavController().navigate(R.id.action_incomeListFragment_to_addIncomeFragment)
//        }
        return binding.root
    }

    private fun loadIncomes() {
        incomeDao.getAllIncomes().observe(viewLifecycleOwner, {incomes ->
            incomeAdapter = IncomeAdapter(incomes) { income ->
                incomeAdapter.notifyDataSetChanged()
            }
        })
        binding.recyclerViewIncomes.adapter = incomeAdapter
    }


}