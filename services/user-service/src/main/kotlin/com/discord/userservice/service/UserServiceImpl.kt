package com.discord.userservice.service

import com.discord.userservice.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class UserServiceImpl : UserService {

    override fun getUserById(userId: UUID): User {
        TODO("Not yet implemented")
    }

    override fun getUserByUsername(username: String): User {
        TODO("Not yet implemented")
    }

    override fun searchByUsername(username: String, pageable: Pageable): Page<User> {
        TODO("Not yet implemented")
    }
}