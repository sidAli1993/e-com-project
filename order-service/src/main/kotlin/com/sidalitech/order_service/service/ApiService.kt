package com.sidalitech.order_service.service

import com.sidalitech.order_service.model.dto_model.ResponseWrapper
import com.sidalitech.order_service.model.response_model.order.CartResponseModel
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class ApiService @Autowired constructor(
    private val webClientBuilder: WebClient.Builder

) {
    suspend fun getAllProductsByIds(ids:List<String>,endpoint: String):ResponseWrapper<List<Map<String,Any>>> {
        return webClientBuilder.build().get()
            .uri { uriBuilder ->
                uriBuilder
                    .scheme("http")
                    .host("localhost")
                    .port(8084)
                    .path(endpoint)
                    .queryParam("ids", ids)
                    .build()
            }
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<ResponseWrapper<List<Map<String,Any>>>>() {})
            .awaitFirstOrNull() ?: ResponseWrapper("error", "No users found", emptyList())
    }

    suspend fun getCartOfById(id:String,endpoint: String):ResponseWrapper<CartResponseModel>? {
        return webClientBuilder.build().get()
            .uri { uriBuilder ->
                uriBuilder
                    .scheme("http")
                    .host("localhost")
                    .port(8085)
                    .path(endpoint)
                    .queryParam("cartId", id)
                    .build()
            }
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<ResponseWrapper<CartResponseModel>>() {})
            .awaitFirstOrNull()
    }
}
