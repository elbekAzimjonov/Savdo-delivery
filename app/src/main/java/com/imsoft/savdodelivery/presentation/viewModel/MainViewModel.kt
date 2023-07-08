package com.imsoft.savdodelivery.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
import com.imsoft.savdodelivery.data.repository.DeliveryRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val deliveryRepository: DeliveryRepository) :
    ViewModel() {


    fun getNewOrders(id: String): MutableLiveData<Resource<NewOrders>> {
        return deliveryRepository.getNewOrders(id)
    }

    fun getMyOrders(id: String): MutableLiveData<Resource<NewOrders>> {
        return deliveryRepository.getMyOrders(id)
    }

    fun acceptedOrder(id: String): MutableLiveData<Resource<OrderMessage>> {
        return deliveryRepository.acceptedOrder(id)
    }

    fun finishOrder(id: String): MutableLiveData<Resource<OrderMessage>> {
        return deliveryRepository.finishOrder(id)
    }

    fun historyData(id: String): MutableLiveData<Resource<History>> {
        return deliveryRepository.historyData(id)
    }

    fun postPayment(payment: Payment): MutableLiveData<Resource<OrderMessage>> {
        return deliveryRepository.postPayment(payment)
    }

    fun postCustomerDebt(paymentCustomerDebt: PaymentCustomerDebt): MutableLiveData<Resource<CustomerDebt>> {
        return deliveryRepository.postCustomerDebt(paymentCustomerDebt)
    }

    fun clientsData(): MutableLiveData<Resource<Clients>> {
        return deliveryRepository.clientsData()
    }

    fun agentsData(): MutableLiveData<Resource<Agents>> {
        return deliveryRepository.agentsData()
    }

    fun paymentDebits(postDebit: PostDebit): MutableLiveData<Resource<Debit>> {
        return deliveryRepository.paymentDebits(postDebit)
    }

    fun currencies(): MutableLiveData<Resource<Currencies>> {
        return deliveryRepository.currencies()
    }

    fun paymentTypes(): MutableLiveData<Resource<PaymentTypes>> {
        return deliveryRepository.paymentTypes()
    }
}