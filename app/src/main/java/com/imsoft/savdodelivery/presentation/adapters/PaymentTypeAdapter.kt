package com.imsoft.savdodelivery.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imsoft.savdodelivery.R
import com.imsoft.savdodelivery.data.model.PaymentType

class PaymentTypeAdapter(
    var list: List<PaymentType>,
    val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<PaymentTypeAdapter.PaymentTypeViewHolder>() {

    inner class PaymentTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.clientsName)
        fun onBind(paymentTypesData: PaymentType, position: Int) {
            name.text = paymentTypesData.name
            name.setOnClickListener {
                onItemClickListener.onItemClick(paymentTypesData, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentTypeViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.agents_item, null, false)
        return PaymentTypeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PaymentTypeViewHolder, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount() = list.size

    //Filter List with searchView
    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filteredList: ArrayList<PaymentType>) {
        list = filteredList
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(paymentType: PaymentType, position: Int)
    }
}