package com.discord.authservice.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.discord.authservice.dto.LoginDTO
import com.discord.authservice.dto.RegisterDTO
import com.discord.authservice.entity.User
import com.discord.authservice.exception.InvalidCredentialsException
import com.discord.authservice.exception.UsernameAlreadyExistsException
import com.discord.authservice.repository.UserRepository
import com.discord.authservice.repository.UsernameLookupRepository
import com.discord.events.core.KafkaEventPublisher
import com.discord.events.user.UserRegisteredEvent
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.PrivateKey
import java.security.PublicKey
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.time.OffsetDateTime
import java.util.*

@Service
@Transactional(readOnly = true)
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val usernameLookupRepository: UsernameLookupRepository,
    private val passwordEncoder: PasswordEncoder,
    private val privateKey: PrivateKey,
    private val publicKey: PublicKey,
    private val kafkaEventPublisher: KafkaEventPublisher
) : AuthService {
    override fun register(request: RegisterDTO) {
        usernameLookupRepository.findById(request.username)
            .ifPresent {
                throw UsernameAlreadyExistsException("User='${request.username}' already exists")
            }

        val hash = passwordEncoder.encode(request.password)
        val user = User(
            username = request.username,
            hashedPassword = hash,
            roles = setOf("USER")
        )

        userRepository.save(user)

        val event = UserRegisteredEvent(
            id = user.id,
            username = user.username,
            registeredAt = OffsetDateTime.now()
        )

        kafkaEventPublisher.publish(
            USER_REGISTERED_EVENT_TOPIC,
            event
        )
    }

    override fun login(request: LoginDTO): String {
        val userLookup = usernameLookupRepository.findById(request.username)
            .orElseThrow {
                throw UsernameNotFoundException("User='${request.username}' not found")
            }

        val user = userRepository.findById(userLookup.id).get()

        if (!passwordEncoder.matches(request.password, user.hashedPassword)) {
            throw InvalidCredentialsException("Invalid password")
        }

        val token = JWT.create()
            .withSubject( request.username)
            .withArrayClaim("roles", user.roles.toTypedArray())
            .withExpiresAt(Date(System.currentTimeMillis() + EXPIRED_DATE))
            .sign(
                Algorithm.RSA256(
                    publicKey as RSAPublicKey,
                    privateKey as RSAPrivateKey
                )
            )

        return token
    }

    private companion object {
        const val EXPIRED_DATE = 3_600_000
        const val USER_REGISTERED_EVENT_TOPIC = "user-registered"
    }
}