package com.example.smartfinancesce

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartfinancesce.data.Expense

class ExpenseAdapter(private val colorProvider: (String) -> Int) :
    RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    private val items = mutableListOf<Expense>()

    fun submitList(list: List<Expense>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.title
        holder.amount.text = String.format("R$ %.2f", item.amount)
        holder.colorBar.setBackgroundColor(colorProvider(item.category))
    }

    override fun getItemCount(): Int = items.size

    class ExpenseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val colorBar: View = view.findViewById(R.id.colorView)
        val title: TextView = view.findViewById(R.id.tvTitle)
        val amount: TextView = view.findViewById(R.id.tvAmount)
    }
}
