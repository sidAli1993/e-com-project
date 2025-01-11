package com.sidalitech.products_service.config

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FilterConfig {

    @Bean
    fun jwtFilterRegistration(): FilterRegistrationBean<JwtCustomFilter> {
        val registrationBean = FilterRegistrationBean<JwtCustomFilter>()
        registrationBean.filter = JwtCustomFilter()
        registrationBean.addUrlPatterns("/product/**") // Filter ko har request ke liye apply karein
        return registrationBean
    }
}
