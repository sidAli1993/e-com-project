package com.sidalitech.auth_service.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*


@Document
data class User(
    @Id
    val id:String?= UUID.randomUUID().toString(),
    val username: String,
    val password: String,
    val roles: List<String>
)
