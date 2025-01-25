package com.sidalitech.order_service.model.dto_model

import org.springframework.data.annotation.Id
import java.util.*

data class DTOUsers(
    val id:String,
    val username: String,
    val roles: List<String>
)
