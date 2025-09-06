package com.discord.messengeraggregator.controller

import com.discord.auth.Auth
import com.discord.clients.chat.ChatServiceClient
import com.discord.clients.message.MessageServiceClient
import com.discord.clients.user.UserServiceClient
import com.discord.messengeraggregator.payload.ChatFullResponse
import com.discord.messengeraggregator.payload.ChatInListResponse
import com.discord.messengeraggregator.payload.ChatWithMessagesResponse
import com.discord.messengeraggregator.payload.MessageFullResponse
import com.discord.pagination.CursorPageResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/messenger/chats")
class ChatController(
    private val chatServiceClient: ChatServiceClient,
    private val userServiceClient: UserServiceClient,
    private val messageServiceClient: MessageServiceClient
) {

    @GetMapping
    fun getChats(
        @RequestParam(required = false) cursorId: Long?,
        @RequestParam(defaultValue = "20") limit: Int
    ): CursorPageResponse<ChatInListResponse> {
        val chats = chatServiceClient.getChats(cursorId, limit)

        val userIds = chats.content.mapNotNull {
            it.directRecipientId
        }.toSet()
        val userMap = userServiceClient.getByIds(userIds).associateBy { it.id }

        val content = chats.content.map {
            ChatInListResponse(
                id = it.id,
                name = if (it.directRecipientId != null) {
                    userMap[it.directRecipientId!!]!!.username
                } else it.groupChatName!!,
                type = it.type,
                lastMessage = it.lastMessageTime
            )
        }
        return CursorPageResponse(
            content,
            chats.cursor,
        )
    }

    @GetMapping("/{id}")
    fun getChatById(@PathVariable id: String): ChatWithMessagesResponse {
        val userId = Auth.userId()

        val chat = chatServiceClient.getChatById(id, userId)
        val messages = messageServiceClient.getMessagesByChatId(chat.id)

        val userMap = userServiceClient.getByIds(
            (chat.members + messages.content.map { it.userId }).toSet()
        ).associateBy {
            it.id
        }

        val chatResponse = ChatFullResponse(
            id = chat.id,
            name = if (chat.directRecipientId != null) {
                userMap[chat.directRecipientId!!]!!.username
            } else chat.groupChatName!!,
            type = chat.type,
            members = chat.members.map { userMap[it]!! },

            )
        val messagesResponse = messages.content.map {
            MessageFullResponse(
                id = it.id,
                createdAt = it.createdAt,
                content = it.content,
                user = userMap[it.userId]!!
            )
        }
        return ChatWithMessagesResponse(
            chatResponse,
            CursorPageResponse(
                messagesResponse,
                messages.cursor
            )
        )
    }

}