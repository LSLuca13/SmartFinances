package com.example.smartfinancesce

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartfinancesce.data.BudgetItem

class BudgetAdapter : RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder>() {

    private val items = mutableListOf<BudgetItem>()

    fun submitList(list: List<BudgetItem>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_budget, parent, false)
        return BudgetViewHolder(view)
    }

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        val item = items[position]
        holder.name.text = item.name
        holder.amount.text = String.format("R$ %.2f", item.amount)
    }

    override fun getItemCount(): Int = items.size

    class BudgetViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvBudgetName)
        val amount: TextView = view.findViewById(R.id.tvBudgetAmount)
    }
}
