package com.sidalitech.order_service.model.response_model.order

data class CartResponseModel(
    val message: String,
    val products: List<Product>,
    val totalPrice: Double,
    val userId: String
)