package com.imsoft.savdodelivery.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imsoft.savdodelivery.R
import com.imsoft.savdodelivery.data.model.Order

class OrderAdapters(
    private val list: ArrayList<Order>
) :
    RecyclerView.Adapter<OrderAdapters.OrderViewHolder>() {
    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val orderType = itemView.findViewById<TextView>(R.id.orderType)
        private val productName = itemView.findViewById<TextView>(R.id.productName)

        private val fullPrice = itemView.findViewById<TextView>(R.id.fullPrice)
        fun onBind(orderList: Order, position: Int) {
            orderType.text = orderList.product_category
            productName.text = orderList.product_name
            fullPrice.text = "${orderList.quantity} X ${orderList.price} ${orderList.currency_name}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.order_list_item, parent, false)
        return OrderViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount() = list.size


}