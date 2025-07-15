package com.discord.userservice.repository

import com.discord.userservice.entity.UsernameLookup
import org.springframework.data.cassandra.repository.CassandraRepository

interface UsernameLookupRepository : CassandraRepository<UsernameLookup, String> {


}