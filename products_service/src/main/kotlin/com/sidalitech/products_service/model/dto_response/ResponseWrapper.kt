package com.sidalitech.products_service.model.dto_response

data class ResponseWrapper<T>(
    val status: String,
    val message: String,
    val data: T
)