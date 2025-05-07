package com.sidalitech.order_service.model.response_model.order

data class ProductX(
    val _id: String,
    val brand: String,
    val catId: String,
    val description: String,
    val image: List<String>,
    val name: String,
    val price: Double,
    val productCategory: ProductCategory,
    val returnPolicy: String,
    val shortDescription: String,
    val status: Boolean,
    val stock: Int,
    val tags: List<String>,
    val user: User,
    val vendorId: String
)