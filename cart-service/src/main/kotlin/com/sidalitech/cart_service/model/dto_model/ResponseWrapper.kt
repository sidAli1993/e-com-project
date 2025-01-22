package com.sidalitech.cart_service.model.dto_model

data class ResponseWrapper<T>(
    val status: String,
    val message: String,
    val data: T
)