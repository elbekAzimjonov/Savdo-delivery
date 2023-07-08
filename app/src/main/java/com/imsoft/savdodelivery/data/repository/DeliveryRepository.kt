package com.imsoft.savdodelivery.data.repository

import androidx.lifecycle.MutableLiveData
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
import com.imsoft.savdodelivery.data.domain.Resource
import com.imsoft.savdodelivery.data.network.MovieDataSource
import kotlinx.coroutines.*
import javax.inject.Inject

@Suppress("EXPERIMENTAL_IS_NOT_ENABLED", "OPT_IN_IS_NOT_ENABLED")
@OptIn(DelicateCoroutinesApi::class)
class DeliveryRepository @Inject constructor(private val movieDataSource: MovieDataSource) {

    fun getNewOrders(id: String): MutableLiveData<Resource<NewOrders>> {
        GlobalScope.launch {
            newOrderAll.postValue(Resource.loading(null))
            coroutineScope {
                try {
                    val async = async { movieDataSource.getNewOrders(id) }
                    val newOrderList = async.await()
                    newOrderAll.postValue(Resource.success(newOrderList))
                } catch (e: Exception) {
                    newOrderAll.postValue(Resource.error(e.message.toString(), null))
                }
            }
        }
        return newOrderAll
    }

    fun getMyOrders(id: String): MutableLiveData<Resource<NewOrders>> {
        GlobalScope.launch {
            myOrderAll.postValue(Resource.loading(null))
            coroutineScope {
                try {
                    val async = async { movieDataSource.myOrders(id) }
                    val myOrderList = async.await()
                    myOrderAll.postValue(Resource.success(myOrderList))
                } catch (e: Exception) {
                    myOrderAll.postValue(Resource.error(e.message.toString(), null))
                }
            }
        }
        return myOrderAll
    }

    fun acceptedOrder(id: String): MutableLiveData<Resource<OrderMessage>> {
        GlobalScope.launch {
            orderMessage.postValue(Resource.loading(null))
            coroutineScope {
                try {
                    val async = async { movieDataSource.acceptedOrder(id) }
                    val accepted = async.await()
                    orderMessage.postValue(Resource.success(accepted))
                } catch (e: Exception) {
                    orderMessage.postValue(Resource.error(e.message.toString(), null))
                }
            }
        }
        return orderMessage
    }

    fun finishOrder(id: String): MutableLiveData<Resource<OrderMessage>> {
        GlobalScope.launch {
            orderMessage.postValue(Resource.loading(null))
            coroutineScope {
                try {
                    val async = async { movieDataSource.finishedOrder(id) }
                    val accepted = async.await()
                    orderMessage.postValue(Resource.success(accepted))
                } catch (e: Exception) {
                    orderMessage.postValue(Resource.error(e.message.toString(), null))
                }
            }
        }
        return orderMessage
    }

    fun historyData(id: String): MutableLiveData<Resource<History>> {
        GlobalScope.launch {
            historyData.postValue(Resource.loading(null))
            coroutineScope {
                try {
                    val async = async { movieDataSource.getAllHistory(id) }
                    val accepted = async.await()
                    historyData.postValue(Resource.success(accepted))
                } catch (e: Exception) {
                    historyData.postValue(Resource.error(e.message.toString(), null))
                }
            }
        }
        return historyData

    }

    fun postPayment(payment: Payment): MutableLiveData<Resource<OrderMessage>> {
        GlobalScope.launch {
            paymentMessage.postValue(Resource.loading(null))
            coroutineScope {
                try {
                    val async = async { movieDataSource.postPayment(payment) }
                    val message = async.await()
                    paymentMessage.postValue(Resource.success(message))
                } catch (e: Exception) {
                    paymentMessage.postValue(Resource.error(e.message.toString(), null))
                }
            }
        }
        return paymentMessage
    }

    fun postCustomerDebt(paymentCustomerDebt: PaymentCustomerDebt): MutableLiveData<Resource<CustomerDebt>> {
        GlobalScope.launch {
            debtCustomer.postValue(Resource.loading(null))
            coroutineScope {
                try {
                    val async = async { movieDataSource.postCustomerDebt(paymentCustomerDebt) }
                    val debtData = async.await()
                    debtCustomer.postValue(Resource.success(debtData))
                } catch (e: Exception) {
                    debtCustomer.postValue(Resource.error(e.message.toString(), null))

                }
            }
        }
        return debtCustomer
    }

    fun clientsData(): MutableLiveData<Resource<Clients>> {
        GlobalScope.launch {
            clientsData.postValue(Resource.loading(null))
            coroutineScope {
                try {
                    val async = async { movieDataSource.clientsData() }
                    val clients = async.await()
                    clientsData.postValue(Resource.success(clients))
                } catch (e: Exception) {
                    clientsData.postValue(Resource.error(e.message.toString(), null))
                }
            }
        }
        return clientsData
    }

    fun agentsData(): MutableLiveData<Resource<Agents>> {
        GlobalScope.launch {
            agentData.postValue(Resource.loading(null))
            coroutineScope {
                try {
                    val async = async { movieDataSource.agentsData() }
                    val agents = async.await()
                    agentData.postValue(Resource.success(agents))
                } catch (e: Exception) {
                    agentData.postValue(Resource.error(e.message.toString(), null))
                }
            }
        }
        return agentData
    }

    fun paymentDebits(postDebit: PostDebit): MutableLiveData<Resource<Debit>> {
        GlobalScope.launch {
            paymentsDebits.postValue(Resource.loading(null))
            coroutineScope {
                try {
                    val async = async { movieDataSource.paymentDebits(postDebit) }
                    val debit = async.await()
                    paymentsDebits.postValue(Resource.success(debit))
                } catch (e: Exception) {
                    paymentsDebits.postValue(Resource.error(e.message.toString(), null))
                }
            }
        }
        return paymentsDebits
    }

    fun currencies(): MutableLiveData<Resource<Currencies>> {
        GlobalScope.launch {
            currencies.postValue(Resource.loading(null))
            coroutineScope {
                try {
                    val async = async { movieDataSource.getCurrencies() }
                    val currenciesData = async.await()
                    currencies.postValue(Resource.success(currenciesData))
                } catch (e: Exception) {
                    currencies.postValue(Resource.error(e.message.toString(), null))
                }
            }
        }
        return currencies
    }

    fun paymentTypes(): MutableLiveData<Resource<PaymentTypes>> {
        GlobalScope.launch {
            paymentTypes.postValue(Resource.loading(null))
            coroutineScope {
                try {
                    val async = async { movieDataSource.paymentTypes() }
                    val payment = async.await()
                    paymentTypes.postValue(Resource.success(payment))
                } catch (e: Exception) {
                    paymentTypes.postValue(Resource.error(e.message.toString(), null))
                }
            }
        }
        return paymentTypes
    }

    companion object {
        val paymentTypes = MutableLiveData<Resource<PaymentTypes>>()
        val currencies = MutableLiveData<Resource<Currencies>>()
        val paymentsDebits = MutableLiveData<Resource<Debit>>()
        val agentData = MutableLiveData<Resource<Agents>>()
        val clientsData = MutableLiveData<Resource<Clients>>()
        val debtCustomer = MutableLiveData<Resource<CustomerDebt>>()
        val paymentMessage = MutableLiveData<Resource<OrderMessage>>()
        val historyData = MutableLiveData<Resource<History>>()
        val orderMessage = MutableLiveData<Resource<OrderMessage>>()
        val myOrderAll = MutableLiveData<Resource<NewOrders>>()
        val newOrderAll = MutableLiveData<Resource<NewOrders>>()
    }
}