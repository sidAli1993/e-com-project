package com.sidalitech.products_service.config

import com.sidalitech.products_service.utils.JwtContextHolder
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.FilterConfig
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component

@Component
class JwtCustomFilter : Filter {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest

        // Token ko "Authorization" header se extract karein
        val authorizationHeader = httpRequest.getHeader("X-Token")
        val token: String? = authorizationHeader

        if (token != null) {
            httpRequest.setAttribute("JWT_TOKEN", token) // Use attributes

            JwtContextHolder.setToken(token)

        }

        try {
            chain.doFilter(request, response) // Request ko process karein
        } finally {
            JwtContextHolder.clear() // ThreadLocal clear karein (zaroori hai)
        }
    }

    override fun init(filterConfig: FilterConfig?) {}
    override fun destroy() {}
}
