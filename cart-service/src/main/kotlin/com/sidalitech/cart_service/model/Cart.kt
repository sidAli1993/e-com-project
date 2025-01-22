package com.sidalitech.cart_service.model

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.apache.logging.log4j.util.StringMap
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date
import java.util.UUID

@Document("cart")
data class Cart(
    @Id
    val id:String ?= UUID.randomUUID().toString(),
    @NotBlank(message = "user id must not be empty")
    @Indexed(unique = true)
    val userId:String,
    val cartProducts:MutableList<ProductObject> = mutableListOf(),
    @NotBlank(message = "total price must not be empty")
    var totalPrice:Double,
    val updatedAt:String?=System.currentTimeMillis().toString(),
)
data class ProductObject(
    @NotBlank(message = "product id must not be empty")
    val productId:String,
    @NotBlank(message = "quantity must not be empty")
    var quantity:Int,
)
