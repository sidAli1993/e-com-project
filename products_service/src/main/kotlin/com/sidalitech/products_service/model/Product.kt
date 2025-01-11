package com.sidalitech.products_service.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document("product")
data class Product(
    @Id
    var id:String?=UUID.randomUUID().toString(),
    @NotBlank(message = "product name is empty")
    val name:String,
    @NotBlank(message = "price is empty")
    val price:Double,
    @NotBlank(message = "category id is empty")
    val catId:String,
    val description:String?,
    @NotBlank(message = "short description is empty")
    val shortDescription:String,
    @NotBlank(message = "image is empty")
    val image:List<String>,
    val tags:List<String>,
    @NotBlank(message = "stock is empty")
    val stock:Int,
    val status:Boolean?=true,
    val returnPolicy:String?=null,
    var vendorId:String?=null,
    val brand:String?=null

)
