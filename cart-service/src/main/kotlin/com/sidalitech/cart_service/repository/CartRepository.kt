package com.sidalitech.cart_service.repository

import com.sidalitech.cart_service.model.Cart
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface CartRepository {
    suspend fun findAll(page:Int,size:Int): Flow<Map<String, Any>>
    suspend fun findByCartId(id:String):Cart
    suspend fun findByUserId(id:String):Cart?
    suspend fun removeCartItem(userId:String,productId:String):Cart
    suspend fun removeCompleteUserCart(userId: String)
    suspend fun removeAllCarts()
}