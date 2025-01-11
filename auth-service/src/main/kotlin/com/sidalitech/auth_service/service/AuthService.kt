package com.sidalitech.auth_service.service

import com.sidalitech.auth_service.config.JwtTokenProvider
import com.sidalitech.auth_service.model.User
import com.sidalitech.auth_service.model.dtomodel.AuthRequest
import com.sidalitech.auth_service.model.dtomodel.AuthResponse
import com.sidalitech.auth_service.model.dtomodel.RegisterRequest
import com.sidalitech.auth_service.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val authenticationManager: AuthenticationManager
) {

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    fun register(request: RegisterRequest): String {
        if (userRepository.findByUsername(request.username) != null) {
            throw IllegalArgumentException("User already exists")
        }
        val user = User(
            username = request.username,
            password = passwordEncoder.encode(request.password),
            roles = request.roles
        )
        userRepository.save(user)
        return "User registered successfully"
    }

    fun login(request: AuthRequest): AuthResponse {
        val user = userRepository.findByUsername(request.username)
            ?: throw IllegalArgumentException("Invalid username or password")
        if (!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalArgumentException("Invalid username or password")
        }

        val token = jwtTokenProvider.generateToken(user.username, user.roles,user.id?:"")
        return AuthResponse(token)
    }
    fun getUserByUsername(username:String):User?{
        val user=userRepository.findByUsername(username) ?:
        throw Exception("user not found")

        return user
    }
    fun getUserById(id:String):User?{
        val query=Query(Criteria.where("_id").`is`(id))
        return mongoTemplate.findOne(query,User::class.java)
    }
    fun findAllUsers(ids:List<String>):List<User>{
        val query=Query(Criteria.where("_id").`in`(ids))
        return mongoTemplate.find(query,User::class.java)
    }

//    private fun authenticateUser(loginRequest: AuthRequest): Authentication {
//        // This can be done using AuthenticationManager
//        val authentication = UsernamePasswordAuthenticationToken(
//            loginRequest.username, loginRequest.password
//        )
//        return authenticationManager.authenticate(authentication)
//    }
}
