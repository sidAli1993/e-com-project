package com.sidalitech.order_service.model

import com.sidalitech.order_service.enums.OrderStatus
import com.sidalitech.order_service.enums.PaymentStatus
import jakarta.validation.constraints.NotBlank
import java.time.Instant
import java.util.Date
import java.util.UUID

data class Order(
    val id:String ?= UUID.randomUUID().toString(),
    @NotBlank(message = "user id must not be null or empty")
    var userId:String,
    @NotBlank(message = "totalAmount must not be null or empty")
    val totalAmount:Double,
    @NotBlank(message = "paymentStatus must not be null or empty")
    val paymentStatus:PaymentStatus,
    @NotBlank(message = "orderStatus must not be null or empty")
    val orderStatus:OrderStatus=OrderStatus.PENDING,
    val orderProducts: List<OrderProducts>,
    val shippingAddress: ShippingAddress,
    @NotBlank(message = "remarks must not be null or empty")
    val remarks:String,
    val createdAt:String=Instant.now().toString(),
    val updatedAt:String=Instant.now().toString()
)
data class OrderProducts(
    val productId:String,
    val quantity:Int,
    val price:Double
)

data class ShippingAddress(
    val street:String,
    val city:String,
    val state:String,
    val zipCode:String,
    val country:String,
    val address:String
)
