package com.sidalitech.auth_service.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Component
class JwtTokenProvider(
    @Value("\${jwt.secret}") private val jwtSecret: String,
    @Value("\${jwt.expiration}") private val jwtExpiration: Long
) {
    private val secretKey: SecretKey = SecretKeySpec(jwtSecret.toByteArray(), SignatureAlgorithm.HS512.jcaName)
    private val expiration: Long = 3600000 // 1 hour

    fun generateToken(username: String, roles: List<String>,id:String): String {
        val now = Date()
        val expiryDate = Date(now.time + jwtExpiration)

        return Jwts.builder()
            .setSubject(username)
            .claim("id",id)
            .claim("roles", roles) // Include roles in the token
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(secretKey, SignatureAlgorithm.HS512)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
            true
        } catch (ex: Exception) {
            false  // Token is invalid
        }
    }

    private fun getUsernameFromToken(token: String): String {
        val claims: Claims = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
        return claims.subject
    }

    private fun getRolesFromToken(token: String): List<String> {
        val claims: Claims = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
        return claims["roles"] as List<String>
    }

//    fun getRolesFromToken(token: String): List<String> {
//        val claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body
//        return claims["roles"] as List<String>
//    }
}
