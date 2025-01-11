package com.sidalitech.products_service.service

import com.sidalitech.products_service.model.dto_response.DTOUsers
import com.sidalitech.products_service.model.dto_response.ResponseWrapper
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class ApiService @Autowired constructor(private val webClient: WebClient) {
    suspend fun dummyApiCall(endpoint: String,token:String=""): String? {
        return webClient.get()
            .uri(endpoint)
            .headers { headers ->
                headers.set("X-token", token)
            }
            .retrieve()
            .bodyToMono<String>()
            .awaitFirstOrNull()
    }

    suspend fun getUserById(endpoint: String): ResponseWrapper<DTOUsers>? {
        return webClient.get()
            .uri(endpoint)
            .retrieve()
            .bodyToMono<ResponseWrapper<DTOUsers>>()
            .awaitFirstOrNull()
    }

    suspend fun getAllUsersByIds(ids:List<String>,endpoint: String):ResponseWrapper<List<DTOUsers>> {
        return webClient.get()
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
}
