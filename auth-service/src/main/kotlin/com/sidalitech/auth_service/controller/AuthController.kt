package com.sidalitech.auth_service.controller

import com.sidalitech.auth_service.model.User
import com.sidalitech.auth_service.model.dtomodel.AuthRequest
import com.sidalitech.auth_service.model.dtomodel.AuthResponse
import com.sidalitech.auth_service.model.dtomodel.RegisterRequest
import com.sidalitech.auth_service.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): String {
        return authService.register(request)
    }

    @PostMapping("/login")
    fun login(@RequestBody request: AuthRequest): AuthResponse {
        return authService.login(request)
    }

    @GetMapping("/{username}")
    fun getUser(@PathVariable username:String
    ): ResponseEntity<Any>{
        val user=authService.getUserByUsername(username)
    val map= mapOf(
        "status" to "success",
        "message" to "user found",
        "data" to user
    )
        return ResponseEntity(map,HttpStatus.OK)

    }

    @GetMapping("/id/{id}")
    fun getUserById(@PathVariable id:String): ResponseEntity<Any>{
        val user=authService.getUserById(id)
    val map= mapOf(
        "status" to "success",
        "message" to "user found",
        "data" to user
    )
        return ResponseEntity(map,HttpStatus.OK)

    }

    @GetMapping("/ids")
    fun getUsersByIds(@RequestParam ids:List<String>):ResponseEntity<Any>{
        val users =authService.findAllUsers(ids)
        val map= mapOf(
            "status" to "success",
            "message" to "all users with the ids matched found",
            "data" to users
        )
        return ResponseEntity(map,HttpStatus.OK)
    }
}
