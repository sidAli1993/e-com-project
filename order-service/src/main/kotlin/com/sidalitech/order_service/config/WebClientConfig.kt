package com.sidalitech.order_service.config


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient
import java.util.*

@Configuration
class WebClientConfig {

    @Bean
    fun webClientAuth(): WebClient {
        return WebClient.builder()
            .baseUrl("http://localhost:8081")
            .defaultCookie("Key", "Value")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8081"))
            .build()
    }
    @Bean
    fun webClientProduct(): WebClient {
        return WebClient.builder()
            .baseUrl("http://localhost:8084")
            .defaultCookie("Key", "Value")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8084"))
            .build()
    }

}
