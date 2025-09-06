package com.discord.dto.message

import java.util.UUID

data class MessageResponse(
    val id: UUID,
    val chatId: String,
    val content: String,
    val userId: UUID,
    val createdAt: String,
)