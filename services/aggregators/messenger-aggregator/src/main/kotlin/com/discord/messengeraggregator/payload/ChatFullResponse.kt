package com.discord.messengeraggregator.payload

import com.discord.dto.chat.ChatType
import com.discord.dto.user.UserResponse

data class ChatFullResponse(
    val id: String,
    val name: String,
    val type: ChatType,
    val members: List<UserResponse>
)