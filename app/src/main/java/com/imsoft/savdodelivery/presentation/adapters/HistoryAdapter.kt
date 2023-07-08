package com.imsoft.savdodelivery.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imsoft.savdodelivery.R
import com.imsoft.savdodelivery.data.model.DataData
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(private val list: List<DataData>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val orderPrice = itemView.findViewById<TextView>(R.id.orderPrice)
        private var dateText = itemView.findViewById<TextView>(R.id.date)
        private var time = itemView.findViewById<TextView>(R.id.time)
        private var ordersType = itemView.findViewById<TextView>(R.id.ordersType)
        private var customerName = itemView.findViewById<TextView>(R.id.customerName)
        fun onBind(company: DataData, position: Int) {
            orderPrice.text = company.customer_name
            orderPrice.text = company.total_price.UZS.toString()
            val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date: Date = df.parse(company.date)!! // converting String to date
            val n = 3
            val compileDate = removeLastNchars(df.format(date), n)
            val finalDate = compileDate.slice(0..10)
            val timeDate = compileDate.slice(10..15)
            customerName.text =  company.customer_name
            time.text = timeDate
            dateText.text = finalDate
            ordersType.text = company.currency_name
        }
    }

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.history_item, null, false)
        return HistoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.onBind(list[position], position)
    }

    private fun removeLastNchars(str: String, n: Int): String {
        return str.substring(0, str.length - n)
    }

    override fun getItemCount() = list.size
}