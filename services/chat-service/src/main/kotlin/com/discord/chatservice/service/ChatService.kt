package com.discord.chatservice.service

import com.discord.chatservice.entity.Chat
import com.discord.chatservice.entity.ChatPreview
import com.discord.pagination.CursorPage

interface ChatService {

    fun getChats(cursor: String?, limit: Int): CursorPage<ChatPreview>

    fun getChatById(chatId: String): Chat

}