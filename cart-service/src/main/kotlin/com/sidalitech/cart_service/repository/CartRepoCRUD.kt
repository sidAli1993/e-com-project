package com.sidalitech.cart_service.repository

import com.sidalitech.cart_service.model.Cart
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CartRepoCRUD : MongoRepository<Cart,String> {
}