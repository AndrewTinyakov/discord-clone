package com.discord.userservice.controller

import com.discord.auth.Auth
import com.discord.userservice.converter.UserConverter
import com.discord.userservice.payload.response.CurrentUserResponse
import com.discord.userservice.payload.response.UserResponse
import com.discord.userservice.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController("/api/users")
class UserController(
    private val userConverter: UserConverter,
    private val userService: UserService
) {

    @GetMapping("/current-user")
    fun getCurrentUser(): CurrentUserResponse {
        val userId = Auth.userId()
        val currentUser = userService.getById(userId)
        val roles = Auth.roles()
        val response = userConverter.convertToCurrentUser(
            currentUser,
            roles
        )
        return response
    }

    @GetMapping("/{username}")
    fun getByUsername(@PathVariable username: String): UserResponse {
        val user = userService.getByUsername(username)
        val result = userConverter.convertToUser(user)

        return result
    }

}