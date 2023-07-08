package com.imsoft.savdodelivery.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val coordinate: String,
    val currency_name: String,
    val customer_name: String,
    val date: String,
    val id: Int,
    val order_list: List<Order>,
    val phone: String,
    val agent_status:String,
    val status: String,
    val total_price: TotalPrice
): Parcelable