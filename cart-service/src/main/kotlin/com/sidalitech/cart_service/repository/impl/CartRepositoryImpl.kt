package com.sidalitech.cart_service.repository.impl

import com.sidalitech.cart_service.model.Cart
import com.sidalitech.cart_service.repository.CartRepoCRUD
import com.sidalitech.cart_service.repository.CartRepository
import com.sidalitech.cart_service.service.ApiService
import com.sidalitech.cart_service.service.CartService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Component
class CartRepositoryImpl(
    private val mongoTemplate: MongoTemplate,
    private val apiService: ApiService,
    private val cartService: CartService,
) : CartRepository {

    override suspend fun findAll(page: Int, size: Int): Flow<Map<String, Any>> = flow {
        val skip=page*size

    }

    override suspend fun findByCartId(id: String): Cart {
        TODO("Not yet implemented")
    }

    override suspend fun findByUserId(id: String): Cart? {
        val query=Query(Criteria.where("userId").`is`(id))
        return mongoTemplate.findOne(query,Cart::class.java)
    }

    override suspend fun findById(id: String): Cart? {
        return mongoTemplate.findById(id,Cart::class.java)    }

    override suspend fun removeCartItem(userId: String, productId: String): Cart {
        TODO("Not yet implemented")
    }

    override suspend fun removeCompleteUserCart(userId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun removeAllCarts() {
        TODO("Not yet implemented")
    }
}