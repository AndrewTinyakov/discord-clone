package com.discord.userservice.service

import com.discord.userservice.entity.User
import com.discord.userservice.entity.UsernameLookup
import com.discord.userservice.exception.NotFoundException
import com.discord.userservice.repository.UserRepository
import com.discord.userservice.repository.UsernameLookupRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
@Transactional(readOnly = true)
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val usernameLookupRepository: UsernameLookupRepository
) : UserService {

    override fun getById(userId: UUID): User {
        val user = userRepository.findById(userId)
            .getOrNull()
            ?: throw NotFoundException("User not found by userId: $userId")
        return user
    }

    override fun getByUsername(username: String): User {
        val findByUsername = usernameLookupRepository.findById(username)
            .getOrNull()
            ?: throw NotFoundException("User not found by username: $username")

        val user = userRepository.findById(findByUsername.id)
            .getOrNull()!!
        return user
    }

    @Transactional
    override suspend fun insert(id: UUID, username: String): Unit = coroutineScope {
        val existingUser = userRepository.findById(id)
            .getOrNull()
        if (existingUser != null) {
            return@coroutineScope
        }
        val user = User(id, username, Instant.now())
        val usernameLookup = UsernameLookup(username, id)

        launch(Dispatchers.IO) {
            userRepository.insert(user)
        }
        launch(Dispatchers.IO) {
            usernameLookupRepository.insert(usernameLookup)
        }
    }

}