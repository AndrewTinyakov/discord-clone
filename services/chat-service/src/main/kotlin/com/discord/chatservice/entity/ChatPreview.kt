package com.discord.chatservice.entity

import com.discord.dto.chat.ChatType
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.Table
import java.time.OffsetDateTime
import java.util.*


@Table("chat_by_user_id")
data class ChatPreview(
    @Column("id")
    val id: String,
    @Column("user_id")
    val userId: UUID,
    @Column("name")
    val groupChatName: String?,
    @Column("direct_recipient_id")
    val directRecipientId: UUID?,
    @Column("type")
    val type: ChatType,
    @Column("last_message_time")
    val lastMessageTime: OffsetDateTime?
)