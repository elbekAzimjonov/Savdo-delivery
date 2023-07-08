package com.imsoft.savdodelivery.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.imsoft.savdodelivery.R
import com.imsoft.savdodelivery.data.model.Currency


class PriceTypeAdapter(val list: List<Currency>, val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<PriceTypeAdapter.PriceTypeViewHolder>() {
    var selectedPosition = 0
    inner class PriceTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val radioBtn = itemView.findViewById<RadioButton>(R.id.paymentType)

        fun onBind(currencies: Currency, position: Int) {
            radioBtn.text = currencies.name
            radioBtn.isChecked = position === selectedPosition
            radioBtn.tag = position
            radioBtn.setOnClickListener { view ->
                onItemClickListener.onItemClick(currencies,position)
                selectedPosition = view.tag as Int
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceTypeViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.select_type, null, false)
        return PriceTypeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PriceTypeViewHolder, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount() = list.size

    interface OnItemClickListener {
        fun onItemClick(currency: Currency, position: Int)
    }
}