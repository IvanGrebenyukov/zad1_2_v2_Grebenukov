package com.example.zad1_2_v2_grebenukov

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zad1_2_v2_grebenukov.Database.Income

class IncomeAdapter(private val incomes: List<Income>, private val onItemClick: (Income) -> Unit)
    :RecyclerView.Adapter<IncomeAdapter.IncomeViewHolder>() {
    class IncomeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val textViewAmount: TextView = itemView.findViewById(R.id.tvAmount)
        private val textViewDescription: TextView = itemView.findViewById(R.id.tvDescription)
        private val textViewDate: TextView = itemView.findViewById(R.id.tvDate)
        fun bind(income: Income){
            textViewAmount.text = income.amount.toString()
            textViewDescription.text = income.description
            textViewDate.text = income.date
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.income_item, parent, false)
        return IncomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return incomes.size
    }

    override fun onBindViewHolder(holder: IncomeViewHolder, position: Int) {
        val income = incomes[position]
        holder.bind(income)
        holder.itemView.setOnClickListener{onItemClick.invoke(income)}
    }
}