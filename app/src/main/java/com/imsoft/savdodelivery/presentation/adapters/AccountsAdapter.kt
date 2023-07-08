package com.imsoft.savdodelivery.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imsoft.savdodelivery.R
import com.imsoft.savdodelivery.data.model.Company

class AccountsAdapter(
    private val list: ArrayList<Company>,
    val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<AccountsAdapter.AccountViewHolder>() {

    inner class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val accountName = itemView.findViewById<TextView>(R.id.accauntName)
        fun onBind(company: Company, position: Int) {
            accountName.text = company.name
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(company, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.account_item, parent, false)
        return AccountViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount() = list.size
    interface OnItemClickListener {
        fun onItemClick(company: Company, position: Int)
    }
}