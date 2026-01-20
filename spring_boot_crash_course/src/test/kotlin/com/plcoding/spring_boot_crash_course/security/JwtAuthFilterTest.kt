package com.plcoding.spring_boot_crash_course.security

import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.security.core.context.SecurityContextHolder

internal class JwtAuthFilterTest{
    private val jwtService = mockk<JwtService>()
    private val filter = JwtAuthFilter(jwtService)

    private val request = mockk<HttpServletRequest>()
    private val response = mockk<HttpServletResponse>(relaxed = true)
    private val filterChain = mockk<FilterChain>(relaxed = true)

    @BeforeEach
    fun setup() {
        clearAllMocks()
        SecurityContextHolder.clearContext()
    }

    @AfterEach
    fun cleanup() {
        SecurityContextHolder.clearContext()
    }

    @Test
    fun `유효한 토큰이 있는 경우 SecurityContext에 인증 정보가 저장된다`() {
        // given
        every { request.getHeader("Authorization") } returns "Bearer valid.token"
        every { jwtService.validateAccessToken("Bearer valid.token") } returns true
        every { jwtService.getUserIdFromToken("Bearer valid.token") } returns "user123"

        // when
        filter.doFilterInternal(request, response, filterChain)

        // then
        val auth = SecurityContextHolder.getContext().authentication
        assertEquals("user123", auth.principal)
        assertEquals(null, auth.credentials)
        assertEquals(true, auth.isAuthenticated)
    }

    @Test
    fun `Authorization 헤더가 없으면 인증되지 않는다`() {
        // given
        every { request.getHeader("Authorization") } returns null

        // when
        filter.doFilterInternal(request, response, filterChain)

        // then
        assertNull(SecurityContextHolder.getContext().authentication)
    }

    @Test
    fun `Authorization 헤더가 Bearer로 시작하지 않으면 인증되지 않는다`() {
        // given
        every { request.getHeader("Authorization") } returns "Token something"

        // when
        filter.doFilterInternal(request, response, filterChain)

        // then
        assertNull(SecurityContextHolder.getContext().authentication)
    }

    @Test
    fun `유효하지 않은 토큰이면 인증되지 않는다`() {
        // given
        every { request.getHeader("Authorization") } returns "Bearer invalid.token"
        every { jwtService.validateAccessToken("Bearer invalid.token") } returns false

        // when
        filter.doFilterInternal(request, response, filterChain)

        // then
        assertNull(SecurityContextHolder.getContext().authentication)
    }
}