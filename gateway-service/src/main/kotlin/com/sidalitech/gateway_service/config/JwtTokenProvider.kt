package com.sidalitech.gateway_service.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Component
class JwtTokenProvider() {
    private val jwtSecret: String="dGVzdC1rZXktZm9yLWhzNTEyLXRlc3Qta2V5LTZna2RzM2dhZG5mbGh3c29xdHJucnhnZWc="
    private val secretKey: SecretKey = SecretKeySpec(jwtSecret.toByteArray(), SignatureAlgorithm.HS512.jcaName)
    private val expiration: Long = 3600000 // 1 hour

    fun getUserDetailsFromToken(token: String): UserDetails {
        val claims = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body

        val username = claims.subject
        val roles = claims["roles"] as List<String>

        return User(username, "", roles.map { SimpleGrantedAuthority(it) })
    }
    fun validateToken(token: String): Boolean {
        try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body

            // Check if the token is expired
            return claims.expiration.after(Date())
        } catch (e: Exception) {
            return false // Token is invalid
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

    fun getClaims(token: String): Claims {
        val claims: Claims = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
        return claims
    }

    fun getAllClaimsFromToken(token: String?): Claims {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body
    }

    private fun isTokenExpired(token: String): Boolean {
        return getAllClaimsFromToken(token).expiration.before(Date())
    }

    fun isInvalid(token: String): Boolean {
        return this.isTokenExpired(token)
    }

}

