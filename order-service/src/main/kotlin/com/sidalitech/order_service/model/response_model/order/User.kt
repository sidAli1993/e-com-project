package com.sidalitech.order_service.model.response_model.order

data class User(
    val id: String,
    val roles: List<String>,
    val username: String
)