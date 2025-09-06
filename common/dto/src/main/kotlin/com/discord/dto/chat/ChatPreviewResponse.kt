package com.discord.dto.chat

import java.util.*

data class ChatPreviewResponse(
    val id: String,
    val groupChatName: String?,
    val directRecipientId: UUID?,
    val type: ChatType,
    val lastMessageTime: String?
)