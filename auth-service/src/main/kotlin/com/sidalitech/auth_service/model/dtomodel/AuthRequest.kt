package com.sidalitech.auth_service.model.dtomodel

data class AuthRequest(val username: String, val password: String)
data class AuthResponse(val token: String)
data class RegisterRequest(val username: String, val password: String, val roles: List<String>)
