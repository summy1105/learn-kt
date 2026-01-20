package com.plcoding.spring_boot_crash_course.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.plcoding.spring_boot_crash_course.security.AuthService
import com.plcoding.spring_boot_crash_course.security.JwtService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@WebMvcTest(AuthController::class)
@AutoConfigureMockMvc(addFilters = false)   // Security 필터 끔
class AuthControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var authService: AuthService

    @MockitoBean
    private lateinit var jwtService: JwtService

    private val objectMapper = jacksonObjectMapper()

    @Test
    fun `register - valid input returns 200`() {
        val request = AuthController.AuthRequest(
            email = "test@example.com",
            password = "StrongPass1"
        )

        mockMvc.post("/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isOk() }
        }

        verify(authService, times(1))
            .register("test@example.com", "StrongPass1")
    }

    @Test
    fun `login - valid credentials return token pair`() {
        val request = AuthController.AuthRequest(
            email = "test@example.com",
            password = "StrongPass1"
        )

        val expectedToken = AuthService.TokenPair(
            accessToken = "access-token",
            refreshToken = "refresh-token"
        )

        `when`(authService.login(anyString(), anyString()))
            .thenReturn(expectedToken)

        mockMvc.post("/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isOk() }
            jsonPath("$.accessToken") { value("access-token") }
            jsonPath("$.refreshToken") { value("refresh-token") }
        }

        verify(authService, times(1))
            .login("test@example.com", "StrongPass1")
    }

    @Test
    fun `refreshToken - valid refresh token returns new tokens`() {
        val request = AuthController.RefreshRequest(refreshToken = "refresh-token")
        val expectedToken = AuthService.TokenPair("new-access", "new-refresh")

        `when`(authService.refresh(anyString()))
            .thenReturn(expectedToken)

        mockMvc.post("/auth/refreshToken") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isOk() }
            jsonPath("$.accessToken") { value("new-access") }
            jsonPath("$.refreshToken") { value("new-refresh") }
        }

        verify(authService, times(1))
            .refresh("refresh-token")
    }
}