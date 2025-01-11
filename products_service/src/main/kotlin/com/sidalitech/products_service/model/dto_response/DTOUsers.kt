package com.sidalitech.products_service.model.dto_response

import org.springframework.data.annotation.Id
import java.util.*

data class DTOUsers(
    val id:String,
    val username: String,
    val roles: List<String>
)
