package com.discord.clients.message

import com.discord.dto.message.MessageResponse
import com.discord.pagination.CursorPageResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam


@FeignClient(name = "message-service")
interface MessageServiceClient {

    @GetMapping("/api/internal/messages")
    fun getMessagesByChatId(
        @RequestParam("chatId") chatId: String
    ): CursorPageResponse<MessageResponse>

}