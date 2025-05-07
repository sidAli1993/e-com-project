package com.sidalitech.order_service.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean


@Bean
fun getObjectMapper():ObjectMapper{
    return ObjectMapper().registerKotlinModule()
}