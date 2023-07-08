package com.imsoft.savdodelivery.data.model

data class DataData(
    val agent_status: String,
    val coordinate: String,
    val currency_name: String,
    val customer_name: String,
    val date: String,
    val id: Int,
    val phone: String,
    val status: String,
    val total_price: TotalPrice
)