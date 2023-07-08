package com.imsoft.savdodelivery.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imsoft.savdodelivery.R
import com.imsoft.savdodelivery.data.model.DebitItem

class DebtAdapter(val list: ArrayList<DebitItem>) :
    RecyclerView.Adapter<DebtAdapter.DebtViewHolder>() {
    inner class DebtViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val value = itemView.findViewById<TextView>(R.id.deptDollor)
        fun onBind(debtItem: DebitItem, position: Int) {
            value.text = "- ${debtItem.value} ${debtItem.name}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebtViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.debit_item, null, false)
        return DebtViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DebtViewHolder, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount() = list.size
}