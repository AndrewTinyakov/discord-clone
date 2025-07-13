package com.discord.authserver.repository

import com.discord.authserver.entity.User
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository
import reactor.core.publisher.Mono
import java.util.UUID

interface UserRepository : ReactiveCassandraRepository<User, UUID> {
    fun findByUsername(username: String): Mono<User>
}