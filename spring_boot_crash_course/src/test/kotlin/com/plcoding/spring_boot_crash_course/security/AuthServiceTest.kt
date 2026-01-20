package com.example.auth

import com.plcoding.spring_boot_crash_course.database.model.RefreshToken
import com.plcoding.spring_boot_crash_course.database.model.User
import com.plcoding.spring_boot_crash_course.database.repository.RefreshTokenRepository
import com.plcoding.spring_boot_crash_course.database.repository.UserRepository
import com.plcoding.spring_boot_crash_course.security.AuthService
import com.plcoding.spring_boot_crash_course.security.HashEncoder
import com.plcoding.spring_boot_crash_course.security.JwtService
import io.mockk.*
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.security.authentication.BadCredentialsException
import java.time.Instant

class AuthServiceTest {

    private lateinit var userRepository: UserRepository
    private lateinit var hashEncoder: HashEncoder
    private lateinit var jwtService: JwtService
    private lateinit var refreshTokenRepository: RefreshTokenRepository
    private lateinit var authService: AuthService

    @BeforeEach
    fun setUp() {
        userRepository = mockk()
        jwtService = mockk()
        refreshTokenRepository = mockk(relaxed = true)
        hashEncoder = HashEncoder() // 실제 BCrypt 사용

        authService = AuthService(
            jwtService = jwtService,
            userRepository = userRepository,
            hashEncoder = hashEncoder,
            refreshTokenRepository = refreshTokenRepository
        )
    }

    @Test
    fun `login 성공 - 올바른 이메일과 비밀번호면 토큰을 반환한다`() {
        // given
        val email = "test@example.com"
        val rawPassword = "password123"
        val hashedPassword = hashEncoder.encode(rawPassword)
        val userId = ObjectId.get()
        val refreshTokenValidityMs = 60 * 1000L

        val user = User(
            id = userId,
            email = email,
            hashedPassword = hashedPassword
        )

        every { userRepository.findByEmail(email) } returns user
        every { jwtService.generateAccessToken(userId.toHexString()) } returns "access-token"
        every { jwtService.generateRefreshToken(userId.toHexString()) } returns "refresh-token"
        every { jwtService.refreshTokenValidityMs } returns refreshTokenValidityMs
        every { refreshTokenRepository.save(any()) } returns RefreshToken(
            userId = userId,
            hashedToken = "hashed.refresh.token",
            createdAt = Instant.now(),
            expiresAt = Instant.now().plusSeconds(3600)
        )

        // when
        val tokenPair = authService.login(email, rawPassword)

        // then
        assertEquals("access-token", tokenPair.accessToken)
        assertEquals("refresh-token", tokenPair.refreshToken)

        verify(exactly = 1) {
            refreshTokenRepository.save(any())
        }
    }


    @Test
    fun `login 실패 - 이메일이 존재하지 않으면 예외 발생`() {
        every { userRepository.findByEmail(any()) } returns null

        assertThrows(BadCredentialsException::class.java) {
            authService.login("notfound@example.com", "password")
        }
    }

    @Test
    fun `login 실패 - 비밀번호가 틀리면 예외 발생`() {
        val email = "test@example.com"
        val user = User(
            id = ObjectId.get(),
            email = email,
            hashedPassword = hashEncoder.encode("correct-password")
        )

        every { userRepository.findByEmail(email) } returns user

        assertThrows(BadCredentialsException::class.java) {
            authService.login(email, "wrong-password")
        }
    }
}