//package com.sidalitech.demo_service.config
//
//import jakarta.servlet.FilterChain
//import jakarta.servlet.http.HttpServletRequest
//import jakarta.servlet.http.HttpServletResponse
//import org.springframework.security.core.context.SecurityContextHolder
//import org.springframework.stereotype.Component
//import org.springframework.web.filter.OncePerRequestFilter
//
//@Component
//class JwtTokenFilter(
//    private val jwtTokenProvider: JwtTokenProvider
//) : OncePerRequestFilter() {
//
//    override fun doFilterInternal(
//        request: HttpServletRequest,
//        response: HttpServletResponse,
//        filterChain: FilterChain
//    ) {
//        val token = resolveToken(request)
//        if (token != null && JwtTokenProvider().validateToken(token)) {
//            val authentication = JwtTokenProvider().getAuthentication(token)
//            SecurityContextHolder.getContext().authentication = authentication
//            println("gettingroles "+JwtTokenProvider().getClaims(token))
//        }
//        filterChain.doFilter(request, response)
//    }
//
//    private fun resolveToken(request: HttpServletRequest): String? {
//        val bearerToken = request.getHeader("Authorization")
//        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            bearerToken.substring(7)
//        } else null
//    }
//}
