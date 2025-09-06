package com.discord.chatservice.service

import com.discord.auth.Auth
import com.discord.chatservice.entity.Chat
import com.discord.chatservice.entity.ChatPreview
import com.discord.chatservice.exception.NotFoundException
import com.discord.chatservice.repository.ChatPreviewRepository
import com.discord.chatservice.repository.ChatRepository
import com.discord.pagination.CursorPage
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
@Transactional(readOnly = true)
class ChatServiceImpl(
    private val chatRepository: ChatRepository,
    private val chatPreviewRepository: ChatPreviewRepository
) : ChatService {

    override fun getChats(cursor: String?, limit: Int): CursorPage<ChatPreview> {
        val userId = Auth.userId()
        val instant = Instant.parse(cursor)

        val chats = chatPreviewRepository.findByUserId(userId, instant, limit)

        return CursorPage(
            content = chats,
            cursor = chats.lastOrNull()?.lastMessageTime?.toString()
        )
    }

    override fun getChatById(chatId: String): Chat {
        val userId = Auth.userId()
        val chat = chatRepository.findById(chatId)
            .orElseThrow { NotFoundException("Chat not found") }

        val currentUserIsMember = chat.memberIds.contains(userId)
        if (!currentUserIsMember) {
            throw NotFoundException("Chat not found")
        }

        return chat
    }

}