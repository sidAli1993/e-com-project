package com.sidalitech.auth_service.config

import com.sidalitech.auth_service.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {

        val context = SecurityContextHolder.getContext()
        val obj = userRepository.findByUsername(username ?: "")
                    ?: throw UsernameNotFoundException("Username not found: $username")
        println("After: ${SecurityContextHolder.getContext().authentication}")

        return User.builder()
            .username(obj.username)
            .password(obj.password)
            .roles(*(obj.roles.map { it }.toTypedArray() ?: arrayOf()))
            .build()
    }
}
