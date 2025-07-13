package com.discord.authserver.repository

import com.discord.authserver.entity.User
import org.springframework.data.cassandra.repository.CassandraRepository
import java.util.UUID

interface UserRepository : CassandraRepository<User, UUID> {
    fun findByUsername(username: String): User?
}