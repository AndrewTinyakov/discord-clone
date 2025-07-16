package com.discord.authservice.repository

import com.discord.authservice.entity.User
import org.springframework.data.cassandra.repository.CassandraRepository
import java.util.UUID

interface UserRepository : CassandraRepository<User, UUID> {

}