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
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
        return buildResponse(
            "success",
            "cart item added successfully",
            HttpStatus.CREATED,
            data = cartResponse
        )
    }

    @GetMapping()
    fun getCart():ResponseEntity<BaseResponse<Any>>{
        val userId=jwtTokenProvider.getIdFromToken(JwtContextHolder.getToken() ?: "")
        val cartResponse= runBlocking {  cartService.getCart(userId) }
        return buildResponse(
            "success",
            "cart items of user",
            HttpStatus.OK,
            data = cartResponse
        )
    }

    @GetMapping("/getCartById")
    fun getCartById(@RequestParam cartId:String):ResponseEntity<BaseResponse<Any>>{
        val cartResponse= runBlocking {  cartService.getCartById(cartId) }
        return buildResponse(
            "success",
            "cart items of this id",
            HttpStatus.OK,
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
}