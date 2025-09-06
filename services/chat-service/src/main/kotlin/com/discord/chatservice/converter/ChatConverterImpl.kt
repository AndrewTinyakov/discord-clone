package com.discord.chatservice.converter

import com.discord.chatservice.entity.Chat
import com.discord.chatservice.entity.ChatPreview
import com.discord.dto.chat.ChatPreviewResponse
import com.discord.dto.chat.ChatResponse
import com.discord.pagination.CursorPage
import com.discord.pagination.CursorPageResponse
import org.springframework.stereotype.Component
import java.util.*

@Component
class ChatConverterImpl : ChatConverter {

    override fun convertMany(page: CursorPage<ChatPreview>): CursorPageResponse<ChatPreviewResponse> {
        return CursorPageResponse(
            content = page.content.map {
                convertToPreview(it)
            },
            cursor = page.cursor
        )
    }

    private fun convertToPreview(chat: ChatPreview): ChatPreviewResponse {
        return ChatPreviewResponse(
            id = chat.id,
            groupChatName = chat.groupChatName,
            directRecipientId = chat.directRecipientId,
            type = chat.type,
            lastMessageTime = chat.lastMessageTime?.toString()
        )
    }

    override fun convert(chat: Chat, currentUserId: UUID): ChatResponse {
        return ChatResponse(
            id = chat.id,
            groupChatName = chat.groupChatName,
            directRecipientId = chat.memberIds.find { it != currentUserId },
            type = chat.type,
            members = chat.memberIds,
        )
    }
}