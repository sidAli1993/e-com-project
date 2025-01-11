//package com.sidalitech.demo_service.config
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.web.SecurityFilterChain
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
//
//@Configuration
//@EnableWebSecurity
//class SecurityConfig(
//    private val jwtTokenFilter: JwtTokenFilter
//) {
//
//    @Bean
//    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
//        http
//            .csrf { it.disable() }
//            .authorizeHttpRequests { auth ->
//                auth
//                    .requestMatchers("/public/**").permitAll()
//                    .requestMatchers("/admin/**").hasRole("ADMIN")
//                    .anyRequest().authenticated()  // Other endpoints require authentication
//            }
//            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java) // Add JWT filter
//        return http.build()
//    }
//}
