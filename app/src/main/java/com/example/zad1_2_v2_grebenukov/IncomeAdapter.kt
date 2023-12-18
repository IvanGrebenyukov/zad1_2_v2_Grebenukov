package com.example.zad1_2_v2_grebenukov

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.zad1_2_v2_grebenukov.Database.Income

class IncomeAdapter(
    private val incomes: List<Income>,
    private val onEditClickListener: (Income) -> Unit,
    private val onDeleteClickListener: (Income) -> Unit
) : RecyclerView.Adapter<IncomeAdapter.IncomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.income_item, parent, false)
        return IncomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: IncomeViewHolder, position: Int) {
        val income = incomes[position]
        holder.bind(income)
        holder.itemView.findViewById<AppCompatButton>(R.id.buttonEdit).setOnClickListener {
            onEditClickListener(income)
        }
        holder.itemView.findViewById<AppCompatButton>(R.id.buttonDelete).setOnClickListener {
            onDeleteClickListener(income)
        }
    }

    override fun getItemCount(): Int {
        return incomes.size
    }

    class IncomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(income: Income) {
            itemView.findViewById<TextView>(R.id.tvAmount).text = "Сумма: " + income.amount.toString()
            itemView.findViewById<TextView>(R.id.tvDescription).text = "Название: " +  income.description
            itemView.findViewById<TextView>(R.id.tvDate).text = "Дата: " +  income.date
            itemView.findViewById<AppCompatButton>(R.id.buttonEdit)
        }
    }
}