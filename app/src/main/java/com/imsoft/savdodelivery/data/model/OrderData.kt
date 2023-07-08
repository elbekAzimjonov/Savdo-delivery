package com.imsoft.savdodelivery.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class OrderData(
    val coordinate: String,
    val customer_name: String,
    val date: String,
    val id: Int,
    val phone: String,
    val status: String,
    val total_price: String,
    val currency_name: String,
    val price: String,
    val product_category: String,
    val product_name: String,
    val quantity: String
): Parcelable