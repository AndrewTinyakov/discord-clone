package com.discord.chatservice.controller

import com.discord.auth.Auth
import com.discord.chatservice.converter.ChatConverter
import com.discord.chatservice.service.ChatService
import com.discord.dto.chat.ChatPreviewResponse
import com.discord.dto.chat.ChatResponse
import com.discord.pagination.CursorPageResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/chats")
class ChatController(
    private val chatService: ChatService,
    private val chatConverter: ChatConverter
) {

    @GetMapping
    fun getChats(
        @RequestParam(required = false) cursor: String?,
        @RequestParam(defaultValue = "20") limit: Int
    ): CursorPageResponse<ChatPreviewResponse> {
        val chats = chatService.getChats(cursor, limit)
        val response = chatConverter.convertMany(chats)

        return response
    }

    @GetMapping("/{id}")
    fun getChatById(@PathVariable id: String): ChatResponse {
        val chat = chatService.getChatById(id)
        val response = chatConverter.convert(chat, Auth.userId())
        return response
    }


    //todo create group chat

    //todo add members to group chat

}