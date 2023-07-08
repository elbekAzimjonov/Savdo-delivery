package com.imsoft.savdodelivery.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(
    val currency_name: String,
    val price: String,
    val product_category: String,
    val product_name: String,
    val quantity: String
): Parcelable