package com.sidalitech.order_service.model.response_model

data class TempCart(
    val userId:String,
    val cartProducts:MutableList<ProductObject> = mutableListOf(),
    var totalPrice:Double,
    val updatedAt:String?=System.currentTimeMillis().toString(),
)
data class ProductObject(
    val productId:String,
    var quantity:Int,
    var price:Double,
)