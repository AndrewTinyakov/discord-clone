package com.discord.chatservice.converter

import com.discord.chatservice.entity.Chat
import com.discord.chatservice.entity.ChatPreview
import com.discord.dto.chat.ChatPreviewResponse
import com.discord.dto.chat.ChatResponse
import com.discord.pagination.CursorPage
import com.discord.pagination.CursorPageResponse
import java.util.*

interface ChatConverter {

    fun convertMany(page: CursorPage<ChatPreview>): CursorPageResponse<ChatPreviewResponse>

    fun convert(chat: Chat, currentUserId: UUID): ChatResponse

}