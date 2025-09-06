package com.discord.dto.user

import java.util.UUID

data class UserResponse(
    val id: UUID,
    val username: String,
)

data class CurrentUserResponse(
    val id: UUID,
    val username: String,
    val roles: List<String>
)
