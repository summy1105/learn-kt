package com.plcoding.spring_boot_crash_course.security

import io.jsonwebtoken.Jwts
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class JwtServiceTest {

    private val base64Secret = Base64.getEncoder().encodeToString("my-very-secret-key-that-is-long-enough".toByteArray())
    private val jwtService = JwtService(base64Secret)

    @Test
    fun `access token should be valid`() {
        val userId = "user123@test.com"
        val token = jwtService.generateAccessToken(userId)

        assertTrue(jwtService.validateAccessToken("Bearer $token"))
        assertEquals(userId, jwtService.getUserIdFromToken("Bearer $token"))
    }

    @Test
    fun `refresh token should be valid`() {
        val userId = "user123@test.com"
        val token = jwtService.generateRefreshToken(userId)

        assertTrue(jwtService.validateRefreshToken("Bearer $token"))
        assertEquals(userId, jwtService.getUserIdFromToken("Bearer $token"))
    }

    @Test
    fun `invalid token should be rejected`() {
        val invalidToken = "Bearer this.is.not.valid"

        assertFalse(jwtService.validateAccessToken(invalidToken))
        assertFalse(jwtService.validateRefreshToken(invalidToken))
        assertThrows(Exception::class.java) {
            jwtService.getUserIdFromToken(invalidToken)
        }
    }

    @Test
    fun `token without Bearer prefix should still work`() {
        val userId = "user123@test.com"
        val token = jwtService.generateAccessToken(userId)

        // no "Bearer " prefix
        assertTrue(jwtService.validateAccessToken(token))
        assertEquals(userId, jwtService.getUserIdFromToken(token))
    }
}