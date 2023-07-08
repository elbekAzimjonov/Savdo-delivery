package com.imsoft.savdodelivery.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imsoft.savdodelivery.R
import com.imsoft.savdodelivery.data.model.Data
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CustomerAdapter(
    private val list: List<Data>,
    val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {

    inner class CustomerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val orderPrice = itemView.findViewById<TextView>(R.id.orderPrice)
        private val enter = itemView.findViewById<ImageView>(R.id.enter)
        private val customerName = itemView.findViewById<TextView>(R.id.customer_name)
        private val orderText = itemView.findViewById<TextView>(R.id.orderText)
        private var dateText = itemView.findViewById<TextView>(R.id.date)
        private var time = itemView.findViewById<TextView>(R.id.time)


        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun onBind(orderList: Data, position: Int) {
            val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date: Date = df.parse(orderList.date)!! // converting String to date
            val n = 3
            val compileDate = removeLastNchars(df.format(date), n)
            val finalDate = compileDate.slice(0..10)
            val timeDate = compileDate.slice(10..15)
            time.text = timeDate
            dateText.text = finalDate
            orderPrice.text = "${orderList.total_price.UZS} ${orderList.currency_name}"
            customerName.text = orderList.customer_name
            orderText.text = "Buyurtma № ${orderList.id}"
            enter.setOnClickListener {
                onItemClickListener.onItemClick(orderList, position, enter)
            }
        }

        private fun removeLastNchars(str: String, n: Int): String {
            return str.substring(0, str.length - n)
        }
    }

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.order_item, null, false)
        return CustomerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount() = list.size

    interface OnItemClickListener {
        fun onItemClick(orderList: Data, position: Int, enter: ImageView)
    }
}