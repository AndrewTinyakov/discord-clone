package com.discord.userservice.repository

import com.discord.userservice.entity.User
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : CassandraRepository<User, UUID> {

}