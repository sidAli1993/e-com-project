package com.sidalitech.products_service.model

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.NotNull
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

@Document(value = "category")
data class Category(
    var id:String?= UUID.randomUUID().toString(),
    @Indexed(unique = true)
    @field:NotNull(message = "category name should not be empty or null")
    val catName:String,
    val description:String,
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    var createdAt:LocalDateTime?=null,
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    var updatedAt:LocalDateTime?=null
)
