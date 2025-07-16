package com.discord.authservice.entity

import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.util.UUID

@Table("users")
data class User(
    @PrimaryKey
    @Column("id")
    val id: UUID = UUID.randomUUID(),

    @Column("username")
    val username: String,

    @Column("password")
    val hashedPassword: String,

    @Column("roles")
    val roles: Set<String>
)