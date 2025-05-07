package com.sidalitech.cart_service.service

import com.sidalitech.cart_service.common.toNonNullMap
import com.sidalitech.cart_service.common.toNullableMap
import com.sidalitech.cart_service.model.Cart
import com.sidalitech.cart_service.model.ProductObject
import com.sidalitech.cart_service.repository.CartRepoCRUD
import com.sidalitech.cart_service.repository.CartRepository
import jakarta.validation.ValidationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service

@Service
class CartService @Autowired constructor(
    private val cartRepoCRUD: CartRepoCRUD,
    private val mongoTemplate: MongoTemplate,
    private val apiService: ApiService,
) {
    @Autowired
    lateinit var cartRepository: CartRepository

    suspend fun checkIfCartExistsByUser(userId: String): Cart? {
        return cartRepository.findByUserId(userId)
    }

    suspend fun getById(cartId: String): Cart? {
        return cartRepository.findById(cartId)
    }

    suspend fun save(userId: String, productObject: ProductObject): Map<String, Any> = withContext(Dispatchers.IO) {
        var cart = checkIfCartExistsByUser(userId)
        if (cart == null) {
            cart = cartRepoCRUD.save(
                Cart(
                    userId = userId,
                    cartProducts = mutableListOf(productObject),
                    totalPrice = (productObject.price * productObject.quantity)
                )
            )
        } else {
            val checkIfProductExistsInCart = cart.cartProducts.find { it.productId == productObject.productId }
            if (checkIfProductExistsInCart == null) {
                var existingTotalPrice = cart.totalPrice
                val existingCartProducts = cart.cartProducts
                existingCartProducts.add(productObject)
                existingTotalPrice += (productObject.price * productObject.quantity)
                cart.totalPrice = existingTotalPrice

            } else {
                var existingTotalPrice = cart.totalPrice
                existingTotalPrice -= (checkIfProductExistsInCart.price * checkIfProductExistsInCart.quantity)
                checkIfProductExistsInCart.quantity = productObject.quantity
                checkIfProductExistsInCart.price = productObject.price
                existingTotalPrice += (productObject.price * productObject.quantity)
                cart.totalPrice = existingTotalPrice
            }
        }
        update(cart)
//        prepare response map
        val productIds=cart.cartProducts.groupBy { it.productId }
        val productsMapById=apiService.getAllProductsByIds(productIds.keys.toList(),"/product/ids").data.let {
            it.associateBy { p-> p["_id"] as String }
        }

        val finalResponse=cart.cartProducts.map {
            mapOf("quantity" to it.quantity,
                "price" to it.price,
                "product" to productsMapById[it.productId]
            )
        }
        val finalMap = mapOf(
            "message" to "cart has been updated successfully",
            "userId" to cart.userId,
            "totalPrice" to cart.totalPrice,
            "products" to finalResponse
        )
        finalMap
    }

    suspend fun getCart(userId: String):Map<String,Any>{
        val cart= checkIfCartExistsByUser(userId) ?: return mapOf<String,Any>()
        val productsIds=cart.cartProducts.groupBy { it.productId }
        val productsMapById=apiService.getAllProductsByIds(productsIds.keys.toList(),"/product/ids").data.let {
            it.associateBy { p-> p["_id"] as String }
        }
        val finalResponse=cart.cartProducts.map {
            "price" to it.price
            "quantity" to it.quantity
            "product" to productsMapById[it.productId]
        }
        val response= mapOf(
            "message" to "user cart details",
            "userId" to cart.userId,
            "totalPrice" to cart.totalPrice,
            "products" to finalResponse
        )

        return response
    }

    suspend fun getCartById(cartId: String):Map<String,Any>{
        val cart= getById(cartId) ?: return mapOf<String,Any>()
        val productsIds=cart.cartProducts.groupBy { it.productId }
        val productsMapById=apiService.getAllProductsByIds(productsIds.keys.toList(),"/product/ids").data.let {
            it.associateBy { p-> p["_id"] as String }
        }
        val finalResponse=cart.cartProducts.map {
            mapOf(
                "price" to it.price,
                "quantity" to it.quantity,
                "product" to productsMapById[it.productId]
            )
        }
        val response= mapOf(
            "message" to "user cart details",
            "userId" to cart.userId,
            "totalPrice" to cart.totalPrice,
            "products" to finalResponse
        )

        return response
    }

    suspend fun removeProduct(prodId: String, userId: String): Map<String, Any> {
        val cart = checkIfCartExistsByUser(userId) ?: throw IllegalArgumentException("cart not exists")
        val product=cart.cartProducts.find { it.productId==prodId }
        if (product!=null){
            cart.totalPrice-= (product.price * product.quantity)
            cart.cartProducts.removeIf { it.productId == prodId }
            update(cart)
        }
        return mapOf("cart" to cart)
    }

    suspend fun removeCart(id: String) {
        cartRepoCRUD.deleteById(id)
    }

    private fun update(cart: Cart): Cart {
        val query = Query(Criteria.where("userId").`is`(cart.userId))
        val update = Update()
            .set("cartProducts", cart.cartProducts)
            .set("totalPrice", cart.totalPrice)
        return mongoTemplate.findAndModify(query, update, Cart::class.java)
            ?: throw ClassNotFoundException("Cart not found for userId: ${cart.userId}")  // Handle case where cart is not found
    }
}