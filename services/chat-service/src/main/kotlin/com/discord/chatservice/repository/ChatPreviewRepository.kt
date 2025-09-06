package com.discord.chatservice.repository

import com.discord.chatservice.entity.ChatPreview
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.data.cassandra.repository.Query
import java.time.Instant
import java.util.UUID

interface ChatPreviewRepository : CassandraRepository<ChatPreview, String> {

    @Query(
        """
            SELECT * FROM chat_by_user_id 
            WHERE user_id = ?0 
            AND last_message_at < ?1 
            LIMIT ?2
        """
    )
    fun findByUserId(
        userId: UUID,
        lastMessageAt: Instant,
        limit: Int
    ): List<ChatPreview>

}