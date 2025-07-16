package com.discord.authservice.repository

import com.discord.authservice.entity.UsernameLookup
import org.springframework.data.cassandra.repository.CassandraRepository

interface UsernameLookupRepository : CassandraRepository<UsernameLookup, String> {
}