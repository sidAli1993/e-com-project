package com.sidalitech.order_service.service


import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.sidalitech.order_service.common.toFilterMap
import com.sidalitech.order_service.common.toNullableMap
import com.sidalitech.order_service.model.Order
import com.sidalitech.order_service.model.OrderProducts
import com.sidalitech.order_service.model.dto_model.DTOOrder
import com.sidalitech.order_service.model.response_model.order.CartResponseModel
import com.sidalitech.order_service.reposittory.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service

@Service
class OrderService
@Autowired constructor(
    private val orderRepository: OrderRepository,
) {

    suspend fun save(cartResponseModel: CartResponseModel,dtoOrder:DTOOrder):Map<String,Any> = withContext(Dispatchers.IO){
        val orderItems= cartResponseModel.products.map { OrderProducts(it.product._id,it.quantity,it.price) }
        val order=Order(
            userId = cartResponseModel.userId,
            paymentStatus = dtoOrder.paymentStatus,
            orderProducts = orderItems,
            shippingAddress = dtoOrder.shippingAddress,
            remarks = dtoOrder.remarks,
            totalAmount = cartResponseModel.totalPrice
        )
        orderRepository.save(order)
        val productsMapById = cartResponseModel.products.associateBy { it.product._id }

        val orderProducts=order.orderProducts.map {product->
            mapOf(
                "price" to product.price,
                "quantity" to product.quantity,
                "product" to productsMapById[product.productId]
            )
        }
        val orderMap = ObjectMapper().convertValue(order, object : TypeReference<MutableMap<String, Any>>() {})
        orderMap["orderProducts"] = orderProducts
        orderMap.toMap()
    }
}