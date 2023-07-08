package com.imsoft.savdodelivery.data.model

data class Payment(
    val currency_id: Int,
    val currency_value: Int,
    val customer_id: Int,
    val description: String,
    val payment_amount: Int,
    val payment_type_id: Int,
    val sales_agent_id: Int,
    val summa: Int,
    val supplier_id: Int
)
//currency_id =  valyuta turi
//currency_value = kurs narxi
//customerId = mijoz idsi
//payment_amount = summani kiritingga qarang
//payment_type_id = tulov tiri idsi
//sales_agent_id = agent idsi
//summa = summa so'mga qarang + qiling
//supplier_id = o'zizi idyiz

//------------------------------------------
//{
//    "sales_agent_id": 1, - Agent
//    "supplier_id": 1, - user Admin
//    "customer_id": 1, - Mijoz
//    "branch_id": 1, - User niki
//    "currency_value":232, -
//    "payment_type_id":"1", - Tolov turi
//    "summa":234,  - Summa
//    "payment_amount":1232, -
//    "description":"asd" -  description
//}

