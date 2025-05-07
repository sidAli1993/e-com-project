package com.sidalitech.order_service.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.sidalitech.order_service.common.buildResponse
import com.sidalitech.order_service.model.Order
import com.sidalitech.order_service.model.OrderProducts
import com.sidalitech.order_service.model.dto_model.DTOOrder
import com.sidalitech.order_service.model.response_model.BaseResponse
import com.sidalitech.order_service.model.response_model.TempCart
import com.sidalitech.order_service.service.ApiService
import com.sidalitech.order_service.service.OrderService
import com.sidalitech.order_service.util.JwtContextHolder
import com.sidalitech.order_service.util.JwtTokenProvider
import jakarta.validation.Valid
import jakarta.validation.ValidationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/order")
class OrderController {

    @Autowired
    lateinit var jwtTokenProvider: JwtTokenProvider

    @Autowired
    lateinit var orderService: OrderService

    @Autowired
    lateinit var apiService: ApiService

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @PostMapping()
    fun createOrder(@Valid @RequestBody dtoOrder: DTOOrder):ResponseEntity<BaseResponse<Any>> {
        val cart= runBlocking(Dispatchers.IO) { apiService.getCartOfById(dtoOrder.cartId,"/cart/getCartById")?.data }
        if (cart==null) throw ValidationException("cart is empty")

        val orderCreatedResponse= runBlocking(Dispatchers.IO) {
            orderService.save(cart,dtoOrder)
        }

        return buildResponse(
            "success",
            "Order created successfully",
            HttpStatus.CREATED,
            data = orderCreatedResponse
        )
    }
}