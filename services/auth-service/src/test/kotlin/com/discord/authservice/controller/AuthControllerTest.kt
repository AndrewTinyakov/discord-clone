package com.discord.authservice.controller

import com.discord.authservice.dto.LoginDTO
import com.discord.authservice.dto.RegisterDTO
import com.discord.authservice.service.AuthService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(AuthController::class)
class AuthControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var authService: AuthService

    @Test
    fun `POST register should return 200 and empty body`() {
        val username = "SantaClaus"
        val password = "password12345"

        val json = """
            {
                "username": "$username",
                "password": "$password"
            }
        """.trimIndent()

        val dto = RegisterDTO(username, password)

        mockMvc.perform(
            post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(status().isOk)
            .andExpect(content().string(""))

        verify(authService).register(dto)
    }

    @Test
    fun `POST login should set HttpOnly jwt cookie`() {
        val username = "SantaClaus"
        val password = "password12345"

        val json = """
            {
                "username": "$username",
                "password": "$password"
            }
        """.trimIndent()

        val dto = LoginDTO(username, password)
        val fakeJwtToken = "fake-jwt-token" // "Надо будет добавить токен"

        given(authService.login(dto))
            .willReturn(fakeJwtToken)

        mockMvc.perform(
            post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(status().isOk)
            .andExpect(cookie().exists("jwt"))
            .andExpect(cookie().httpOnly("jwt", true))
            .andExpect(cookie().value("jwt", fakeJwtToken))

        verify(authService).login(dto)
    }
}