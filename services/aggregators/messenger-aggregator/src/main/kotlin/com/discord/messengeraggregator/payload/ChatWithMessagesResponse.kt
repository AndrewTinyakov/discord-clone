package com.discord.messengeraggregator.payload

import com.discord.pagination.CursorPageResponse

data class ChatWithMessagesResponse(
    val chat: ChatFullResponse,
    val messages: CursorPageResponse<MessageFullResponse>
)