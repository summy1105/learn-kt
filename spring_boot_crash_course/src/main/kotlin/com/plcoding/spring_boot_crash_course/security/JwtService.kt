package com.plcoding.spring_boot_crash_course.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class JwtService(@Value("\${jwt.secret}") private val jwtSecret: String){

    private val secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret))
    val accessTokenValidityMs = 15L * 60 * 1000L
    val refreshTokenValidityMs = 30L * 24 * 60 * 60 * 1000L

    private val kType = "type"
    private val vAccess = "access"
    private val vRefresh = "refresh"

    private fun generateToken(
        userId: String,
        type: String,
        expiry: Long
    ): String {
        val now = Date()
        val expiryDate = Date(now.time+expiry)
        return Jwts.builder()
            .subject(userId)
            .claim(kType, type)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(secretKey, Jwts.SIG.HS256)
            .compact()
    }

    fun generateAccessToken(userId: String):String {
        return generateToken(userId, vAccess, accessTokenValidityMs)
    }

    fun generateRefreshToken(userId: String): String {
        return generateToken(userId, vRefresh, refreshTokenValidityMs)
    }

    fun validateAccessToken(token: String): Boolean {
        val claims = parseAllClaims(token) ?: return false
        val tokenType = claims[kType] as? String ?: return false
        return tokenType == vAccess
    }

    fun validateRefreshToken(token: String): Boolean {
        val claims = parseAllClaims(token) ?: return false
        val tokenType = claims[kType] as? String ?: return false
        return tokenType == vRefresh
    }

    fun getUserIdFromToken(token: String): String {
        val claims = parseAllClaims(token) ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.")
        return claims.subject
    }

    private fun parseAllClaims(token: String): Claims? {
        val tokenPrefix = "Bearer "
        val rawToken = if (token.startsWith(tokenPrefix)) {
            token.removePrefix(tokenPrefix)
        } else {
            token
        }

        return try{
            Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(rawToken) // 만료 검증함
                .payload
        } catch (e:Exception){
            null
        }
    }
}