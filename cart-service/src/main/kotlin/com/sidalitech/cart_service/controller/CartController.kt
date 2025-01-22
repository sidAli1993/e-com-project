package com.sidalitech.cart_service.controller

import com.sidalitech.cart_service.common.buildResponse
import com.sidalitech.cart_service.model.Cart
import com.sidalitech.cart_service.model.ProductObject
import com.sidalitech.cart_service.model.response_model.BaseResponse
import com.sidalitech.cart_service.service.CartService
import com.sidalitech.cart_service.util.JwtContextHolder
import com.sidalitech.cart_service.util.JwtTokenProvider
import jakarta.validation.Valid
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/cart")
class CartController @Autowired constructor(private val cartService: CartService,
    private val jwtTokenProvider: JwtTokenProvider) {

    @PostMapping("/update-cart")
    fun save(@Valid @RequestBody productObject: ProductObject):ResponseEntity<BaseResponse<Any>>{
        val userId=jwtTokenProvider.getIdFromToken(JwtContextHolder.getToken() ?: "")
        val cartResponse= runBlocking {  cartService.save(userId,productObject) }
//        runBlocking { cartService.decreaseQty(userId,productObject.productId) }
//        runBlocking { cartService.removeProduct(productObject.productId,userId) }
//        runBlocking { cartService.removeCart("6462421c-6c23-4d6a-b469-a8d20605b37c") }
        return buildResponse(
            "success",
            "cart item added successfully",
            HttpStatus.CREATED,
            data = cartResponse
        )
    }

    @PostMapping("/remove-cart")
    fun removeProduct(@RequestParam productId: String):ResponseEntity<BaseResponse<Any>>{
        val userId=jwtTokenProvider.getIdFromToken(JwtContextHolder.getToken() ?: throw IllegalArgumentException("token is invalid"))
        val resp= runBlocking {  cartService.removeProduct(productId,userId) }

        return buildResponse(
            "success",
            "product removed success",
            HttpStatus.OK,
            data = resp
        )
    }

    @DeleteMapping("/clear-cart")
    fun clearCart(@RequestParam cartId:String):ResponseEntity<BaseResponse<Any>>{
        runBlocking { cartService.removeCart(cartId) }

        return buildResponse(
            "success",
            "cart cleared success",
            HttpStatus.OK,
            data = "cart is cleared"
        )
    }

    @PostMapping("/decrease-qty")
    fun decreaseQty(@RequestParam productId:String):ResponseEntity<BaseResponse<Any>>{
        val userId=jwtTokenProvider.getIdFromToken(JwtContextHolder.getToken() ?: throw IllegalArgumentException("token is invalid"))
        val resp= runBlocking { cartService.decreaseQty(userId,productId) }
        return buildResponse(
            "success",
            "quantity decreased",
            HttpStatus.CREATED,
            data = resp
        )
    }

}