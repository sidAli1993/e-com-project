package com.sidalitech.order_service.model.dto_model

import com.sidalitech.order_service.enums.PaymentStatus
import com.sidalitech.order_service.model.ShippingAddress

data class DTOOrder(
    val cartId:String,
    val paymentStatus: PaymentStatus,
    val shippingAddress: ShippingAddress,
    val remarks:String
)
