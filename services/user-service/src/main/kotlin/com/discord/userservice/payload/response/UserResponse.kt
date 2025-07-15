package com.discord.userservice.payload.response

data class UserResponse(
    val id: String,
    val username: String,
)

data class CurrentUserResponse(
    val id: String,
    val username: String,
    val roles: List<String>
)
