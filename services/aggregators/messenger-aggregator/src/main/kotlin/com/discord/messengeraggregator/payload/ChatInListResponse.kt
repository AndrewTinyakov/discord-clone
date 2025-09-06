package com.discord.messengeraggregator.payload

import com.discord.dto.chat.ChatType

data class ChatInListResponse(
    val id: String,
    val name: String,
    val type: ChatType,
    val lastMessage: String?
)