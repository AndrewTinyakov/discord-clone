package com.discord.authservice.controller

import com.discord.authservice.dto.LoginDTO
import com.discord.authservice.dto.RegisterDTO
import com.discord.authservice.service.AuthServiceImpl
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthServiceImpl
) {
    @PostMapping("/register")
    fun register(
        @RequestBody request: RegisterDTO
    ) = authService.register(request)

    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginDTO,
        response: HttpServletResponse
    ) {
        val token = authService.login(request)

        val jwtCookie = Cookie(
            "jwt",
            token
        ).apply {
            path = "/"
            isHttpOnly = true
        }

        response.addCookie(jwtCookie)
    }

    @PostMapping("/logout")
    fun logout(response: HttpServletResponse) {
        val jwtCookie = Cookie(
            "jwt",
            ""
        ).apply {
            path = "/"
            isHttpOnly = true
        }

        response.addCookie(jwtCookie)
    }
}