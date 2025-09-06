package com.discord.chatservice.repository

import com.discord.chatservice.entity.Chat
import org.springframework.data.cassandra.repository.CassandraRepository

interface ChatRepository : CassandraRepository<Chat, String> {



}