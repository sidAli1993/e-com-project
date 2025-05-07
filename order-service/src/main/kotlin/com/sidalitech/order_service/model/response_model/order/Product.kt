package com.sidalitech.order_service.model.response_model.order

data class Product(
    val price: Double,
    val product: ProductX,
    val quantity: Int
)