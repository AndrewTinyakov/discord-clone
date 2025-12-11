package com.discord.clients.chat

import com.discord.dto.chat.ChatPreviewResponse
import com.discord.dto.chat.ChatResponse
import com.discord.pagination.CursorPageResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

@FeignClient(name = "chat-service")
interface ChatServiceClient {

    @GetMapping("/api/internal/chats")
    fun getChats(
        @RequestParam(required = false) cursorId: Long?,
        @RequestParam(defaultValue = "20") limit: Int
    ): CursorPageResponse<ChatPreviewResponse>

    @GetMapping("/api/internal/chats/{id}")
    fun getChatById(
        @PathVariable id: String,
        userId: UUID
    ): ChatResponse


}