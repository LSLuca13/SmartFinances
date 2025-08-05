package com.example.smartfinancesce

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartfinancesce.data.FixedAccount

class FixedAccountAdapter : RecyclerView.Adapter<FixedAccountAdapter.FixedAccountViewHolder>() {

    private val items = mutableListOf<FixedAccount>()

    fun submitList(list: List<FixedAccount>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FixedAccountViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_fixed_account, parent, false)
        return FixedAccountViewHolder(view)
    }

    override fun onBindViewHolder(holder: FixedAccountViewHolder, position: Int) {
        val item = items[position]
        holder.name.text = item.name
        holder.amount.text = String.format("R$ %.2f", item.amount)
        holder.date.text = item.paymentDate
    }

    override fun getItemCount(): Int = items.size

    class FixedAccountViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvFixedAccountName)
        val amount: TextView = view.findViewById(R.id.tvFixedAccountAmount)
        val date: TextView = view.findViewById(R.id.tvFixedAccountDate)
    }
}
