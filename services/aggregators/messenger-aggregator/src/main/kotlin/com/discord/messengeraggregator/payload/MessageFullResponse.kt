package com.discord.messengeraggregator.payload

import com.discord.dto.user.UserResponse
import java.util.UUID

data class MessageFullResponse(
    val id: UUID,
    val createdAt: String,
    val content: String,
    val user: UserResponse,
)