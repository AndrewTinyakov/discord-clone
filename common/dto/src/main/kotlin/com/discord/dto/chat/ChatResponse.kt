package com.discord.dto.chat

import java.util.*

data class ChatResponse(
    val id: String,
    val groupChatName: String?,
    val directRecipientId: UUID?,
    val type: ChatType,
    val members: List<UUID>
)