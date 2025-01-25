package com.sidalitech.order_service.service

import com.sidalitech.order_service.model.dto_model.DTOUsers
import com.sidalitech.order_service.model.dto_model.ResponseWrapper
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class ApiService @Autowired constructor(
    private val webClientAuth: WebClient,
    private val webClientProduct: WebClient,

) {

    suspend fun getUserById(endpoint: String): ResponseWrapper<DTOUsers>? {
        return webClientAuth.get()
            .uri(endpoint)
            .retrieve()
            .bodyToMono<ResponseWrapper<DTOUsers>>()
            .awaitFirstOrNull()
    }

    suspend fun getAllUsersByIds(ids:List<String>,endpoint: String):ResponseWrapper<List<DTOUsers>> {
        return webClientAuth.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path(endpoint)
                    .queryParam("ids", ids)
                    .build()
            }
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<ResponseWrapper<List<DTOUsers>>>() {})
            .awaitFirstOrNull() ?: ResponseWrapper("error", "No users found", emptyList())
    }

    suspend fun getAllProductsByIds(ids:List<String>,endpoint: String):ResponseWrapper<List<Map<String,Any>>> {
        return webClientProduct.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path(endpoint)
                    .queryParam("ids", ids)
                    .build()
            }
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<ResponseWrapper<List<Map<String,Any>>>>() {})
            .awaitFirstOrNull() ?: ResponseWrapper("error", "No users found", emptyList())
    }
}
