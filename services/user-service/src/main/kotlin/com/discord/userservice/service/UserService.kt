package com.discord.userservice.service

import com.discord.userservice.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface UserService {

    fun getUserById(userId: UUID): User

    fun getUserByUsername(username: String): User

    fun searchByUsername(username: String, pageable: Pageable): Page<User>

}