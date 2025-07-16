package com.discord.authserver.repository

import com.discord.authserver.entity.UsernameLookup
import org.springframework.data.cassandra.repository.CassandraRepository

interface UsernameLookupRepository : CassandraRepository<UsernameLookup, String> {
}