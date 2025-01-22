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

    suspend fun save(userId: String, productObject: ProductObject): Map<String, Any> = withContext(Dispatchers.IO) {
        val allCartProductsListToGetProductsResponse = mutableListOf(productObject)
        val cart = checkIfCartExistsByUser(userId) ?: cartRepoCRUD.save(
            Cart(
                userId = userId,
                cartProducts = mutableListOf(),
                totalPrice = 0.0
            )
        ).also {
            allCartProductsListToGetProductsResponse.addAll(it.cartProducts)
        }
        val productIds = allCartProductsListToGetProductsResponse.groupBy { it.productId }
        println("productsIds $productIds")
        val productMapById =
            apiService.getAllProductsByIds(productIds.keys.toList(), "/product/ids").data.let {
                it.associateBy {p-> p["_id"] as String }
            }
//        println(productsResponse)
//        val productMapById = productsResponse.associateBy { it["_id"] as String }
        val priceOfCorrespondingProduct = productMapById[productObject.productId]?.get("price") as Double
        val updatedProdCartObjects = amendQtyOfDuplicateProducts(cart, productObject, priceOfCorrespondingProduct)
        update(
            Cart(
                userId = userId,
                cartProducts = updatedProdCartObjects,
                totalPrice = cart.totalPrice
            )
        )


        val cartProducts = cart.cartProducts.mapNotNull { cartProduct ->
            val prodId = cartProduct.productId
            val correspondingProduct = productMapById[prodId]
            correspondingProduct?.toMutableMap()?.apply {
                put("quantity", cartProduct.quantity)
            }
        }

        val finalMap = mapOf(
            "id" to cart.id.toString(),
            "userId" to cart.userId,
            "totalPrice" to cart.totalPrice,
            "products" to cartProducts
        )
        finalMap
    }

    suspend fun decreaseQty(userId: String, prodId: String) : Map<String, Any> =
        withContext(Dispatchers.IO) {
            val cart = checkIfCartExistsByUser(userId) ?: throw IllegalArgumentException("cart not exists")
            val prod = cart.cartProducts.find { it.productId == prodId }
            prod?.let { p ->
                if (p.quantity > 1) {
                    val productsResponse =
                        apiService.getAllProductsByIds(listOf(p.productId), "/product/ids").data
                    val prodMap = productsResponse.associateBy { it["_id"] as String }
                    val correspondProdPrice = prodMap[prodId]?.get("price") as Double
                   val minusAmountFromTotalPrice = p.quantity * correspondProdPrice
                    cart.totalPrice-=minusAmountFromTotalPrice
                    cart.totalPrice += minusAmountFromTotalPrice-correspondProdPrice
                    cart.cartProducts.find { it.productId==p.productId }?.apply {
                        this.quantity -= 1
                    }
                    update(cart)
                } else {
                    removeProduct(p.productId, userId)
                }
            }
            mapOf("cart" to cart)
        }


    suspend fun removeProduct(prodId: String, userId: String): Map<String, Any> {
        val cart = checkIfCartExistsByUser(userId) ?: throw IllegalArgumentException("cart not exists")
        val prod = cart.cartProducts.find { it.productId == prodId }
        val productsResponse =
            apiService.getAllProductsByIds(listOf(prod?.productId.toString()), "/product/ids").data
        val prodMap = productsResponse.associateBy { it["_id"] as String }
        val correspondProdPrice = prodMap[prodId]?.get("price") as Double
        cart.totalPrice -= (correspondProdPrice * (prod?.quantity ?: 1))
        cart.cartProducts.remove(prod)
        update(cart)
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

    private fun amendQtyOfDuplicateProducts(
        cart: Cart,
        pObj: ProductObject,
        priceOfCurrentProduct: Double
    ): MutableList<ProductObject> {
        val productsList: MutableList<ProductObject> = cart.cartProducts
        val productExists = productsList.find { it.productId == pObj.productId }

        if (productExists != null) {
            // Adjust the total price by subtracting the existing product's price
            cart.totalPrice -= productExists.quantity * priceOfCurrentProduct

            // Update the quantity of the existing product
            productExists.quantity += pObj.quantity

            // Add the updated price based on the new quantity
            cart.totalPrice += productExists.quantity * priceOfCurrentProduct
        } else {
            cart.totalPrice += pObj.quantity * priceOfCurrentProduct
            productsList.add(pObj)
        }

        return productsList
    }

}