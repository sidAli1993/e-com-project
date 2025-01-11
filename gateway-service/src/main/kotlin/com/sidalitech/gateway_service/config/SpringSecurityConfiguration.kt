package com.sidalitech.gateway_service.config

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskDecorator
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SpringSecurityConfiguration(
) {

//    @Autowired
//    lateinit var jwtTokenProvider: JwtTokenProvider

    @Value("\$spring.data.mongodb.uri")
    lateinit var mongoUri:String
    @Value("\$spring.data.mongodb.database")
    lateinit var databaseName:String



    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http
            .csrf { it.disable() }
            .authorizeExchange { exchanges ->
                exchanges
                    .pathMatchers("/auth/**").permitAll() // Public endpoints
                    .pathMatchers("/product/**").hasAnyRole("ADMIN","VENDOR")
                    .anyExchange().authenticated() // All other endpoints require authentication
            }
            .addFilterAfter(JwtAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION) // Add custom filter for JWT validation
        return http.build()
    }
//    @Bean
//    fun jwtTokenProvider(
//        @Value("\${jwt.secret}") jwtSecret: String,
//    ): JwtTokenProvider {
//        return JwtTokenProvider(jwtSecret)
//    }

}
