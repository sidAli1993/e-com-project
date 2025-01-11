package com.sidalitech.gateway_service.config

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono


class JwtAuthenticationFilter(
) : WebFilter {

//    @Autowired
//    lateinit var jwtTokenProvider:JwtTokenProvider


    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val path = exchange.request.uri.path

        if (path.startsWith("/auth/")) {
            return chain.filter(exchange)
        }

        val token = exchange.request.headers.getFirst("Authorization")?.removePrefix("Bearer ")
        println("Extracted Token: $token")

        return if (token != null && JwtTokenProvider().validateToken(token)) {
            val userDetails = JwtTokenProvider().getUserDetailsFromToken(token)
            println("Token is valid. Forwarding request. ${userDetails.authorities.joinToString(",")}")
            val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
            val mutatedRequest = exchange.request.mutate()
//                .header("X-User-Roles", userDetails.authorities.joinToString(","))
//                .header("X-Username", userDetails.username)
                .header("X-Token", token)
                .build()

            return chain.filter(exchange.mutate().request(mutatedRequest).build())
                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))
        } else {
            println("Token is invalid or missing. Returning 401.")

            exchange.response.statusCode = HttpStatus.UNAUTHORIZED // Invalid token, return 401
            exchange.response.setComplete()
        }
    }

    // Token validation
}
