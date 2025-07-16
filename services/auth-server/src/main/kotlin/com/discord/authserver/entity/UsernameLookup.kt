package com.discord.authserver.entity

import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.util.UUID

@Table("user_username_to_id")
data class UsernameLookup(
    @PrimaryKey
    val username: String,

    @Column("user_id")
    val id: UUID
)
