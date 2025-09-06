package com.discord.chatservice.entity

import com.discord.dto.chat.ChatType
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.time.Instant
import java.util.*


@Table("chats")
data class Chat(
    @PrimaryKey
    @Column("id")
    val id: String,
    @Column("name")
    val groupChatName: String?,
    @Column("member_ids")
    val memberIds: List<UUID>,
    @Column("type")
    val type: ChatType,
    @Column("created_at")
    val createdAt: Instant
)