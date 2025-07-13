package com.discord.authserver.controller

import com.discord.authserver.dto.LoginDTO
import com.discord.authserver.dto.RegisterDTO
import com.discord.authserver.service.AuthService
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/register")
    fun register(
        @RequestBody request: RegisterDTO
    ): Mono<ResponseEntity<Void>> =
        authService.register(request)
            .thenReturn(ResponseEntity.ok().build())

    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginDTO,
        response: ServerHttpResponse
    ): Mono<ResponseEntity<Void>> =
        authService.login(request)
            .map { (token, user) ->
                val jwtCookie = ResponseCookie.from("jwt", token)
                    .path("/")
                    .httpOnly(true)
                    .build()

                val userIdCookie = ResponseCookie.from("user_id", user.id.toString())
                    .path("/")
                    .build()

                val rolesCookie = ResponseCookie.from("roles", user.roles.joinToString(","))
                    .path("/")
                    .build()

                response.addCookie(jwtCookie);
                response.addCookie(userIdCookie);
                response.addCookie(rolesCookie);

                ResponseEntity.ok().build()
            }
}