package com.imsoft.savdodelivery.data.network

import com.imsoft.savdodelivery.data.*
import com.imsoft.savdodelivery.data.model.Agents
import com.imsoft.savdodelivery.data.model.Clients
import com.imsoft.savdodelivery.data.model.Currencies
import com.imsoft.savdodelivery.data.model.CustomerDebt
import com.imsoft.savdodelivery.data.model.Debit
import com.imsoft.savdodelivery.data.model.History
import com.imsoft.savdodelivery.data.model.NewOrders
import com.imsoft.savdodelivery.data.model.OrderMessage
import com.imsoft.savdodelivery.data.model.Payment
import com.imsoft.savdodelivery.data.model.PaymentCustomerDebt
import com.imsoft.savdodelivery.data.model.PaymentTypes
import com.imsoft.savdodelivery.data.model.PostDebit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("new-orders/{id}")
    suspend fun getNewOrders(@Path("id") id: String): NewOrders

    @GET("accept-orders/{id}")
    suspend fun acceptedOrders(@Path("id") id: String): OrderMessage

    @GET("delivered-orders/{id}")
    suspend fun finishedOrder(@Path("id") id: String): OrderMessage

    @GET("acceptable-orders/{id}")
    suspend fun myOrders(@Path("id") id: String): NewOrders

    @GET("history-orders/{id}")
    suspend fun getAllHistory(@Path("id") id: String): History

    @POST("payment")
    suspend fun postPayment(@Body payment: Payment): OrderMessage

    @POST("customer-debit-credit")
    suspend fun postCustomerDebt(@Body paymentCustomerDebt: PaymentCustomerDebt): CustomerDebt

    @GET("clients")
    suspend fun clientsData(): Clients

    @GET("workers")
    suspend fun agentsData(): Agents

    @POST("customer-debit-credit")
    suspend fun paymentDebits(@Body postDebit: PostDebit): Debit

    @GET("currencies")
    suspend fun getCurrencies(): Currencies

    @GET("payment_types")
    suspend fun paymentTypes(): PaymentTypes


}