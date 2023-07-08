package com.imsoft.savdodelivery.data.network


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

import javax.inject.Inject

class MovieDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getNewOrders(id: String): NewOrders = apiService.getNewOrders(id)

    suspend fun acceptedOrder(id: String): OrderMessage = apiService.acceptedOrders(id)

    suspend fun finishedOrder(id: String): OrderMessage = apiService.finishedOrder(id)

    suspend fun myOrders(id: String): NewOrders = apiService.myOrders(id)

    suspend fun getAllHistory(id: String): History = apiService.getAllHistory(id)

    suspend fun postPayment(payment: Payment): OrderMessage = apiService.postPayment(payment)

    suspend fun postCustomerDebt(paymentCustomerDebt: PaymentCustomerDebt): CustomerDebt =
        apiService.postCustomerDebt(paymentCustomerDebt)

    suspend fun clientsData(): Clients = apiService.clientsData()

    suspend fun agentsData(): Agents = apiService.agentsData()

    suspend fun paymentDebits(postDebit: PostDebit): Debit = apiService.paymentDebits(postDebit)

    suspend fun getCurrencies(): Currencies = apiService.getCurrencies()

    suspend fun paymentTypes(): PaymentTypes = apiService.paymentTypes()


}