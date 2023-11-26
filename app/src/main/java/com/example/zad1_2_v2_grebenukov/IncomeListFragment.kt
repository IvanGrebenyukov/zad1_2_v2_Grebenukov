package com.example.zad1_2_v2_grebenukov

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.example.zad1_2_v2_grebenukov.databinding.FragmentIncomeListBinding


class IncomeListFragment : Fragment() {

    private lateinit var binding: FragmentIncomeListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIncomeListBinding.inflate(inflater, container, false)

        binding.buttonNewIncome.setOnClickListener {
            findNavController().navigate(R.id.action_incomeListFragment_to_addIncomeFragment)
        }
        return binding.root
    }


}