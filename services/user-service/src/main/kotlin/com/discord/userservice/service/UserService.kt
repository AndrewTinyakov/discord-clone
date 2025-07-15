package com.discord.userservice.service

import com.discord.userservice.entity.User
import java.util.*

interface UserService {

    fun getById(userId: UUID): User

    fun getByUsername(username: String): User

    suspend fun insert(id: UUID, username: String)
}