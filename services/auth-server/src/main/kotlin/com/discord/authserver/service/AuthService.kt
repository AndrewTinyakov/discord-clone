package com.discord.authserver.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.discord.authserver.dto.LoginDTO
import com.discord.authserver.dto.RegisterDTO
import com.discord.authserver.entity.User
import com.discord.authserver.exception.InvalidCredentialsException
import com.discord.authserver.exception.UsernameAlreadyExistsException
import com.discord.authserver.repository.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.security.PrivateKey
import java.security.PublicKey
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.*

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val privateKey: PrivateKey,
    private val publicKey: PublicKey
) {
    fun register(request: RegisterDTO) {
        val existing = userRepository.findByUsername(request.username)

        if (existing != null) {
            throw UsernameAlreadyExistsException("User='${request.username}' already exists")
        }

        val hash = passwordEncoder.encode(request.password)
        val user = User(
            username = request.username,
            hashedPassword = hash,
            roles = setOf("USER")
        )

        userRepository.save(user)
    }

    fun login(request: LoginDTO): String {
        val user = userRepository.findByUsername(request.username)
            ?: throw UsernameNotFoundException("User='${request.username}' not found")

        if (!passwordEncoder.matches(request.password, user.hashedPassword)) {
            throw InvalidCredentialsException("Invalid password")
        }

        val token = JWT.create()
            .withSubject(request.username)
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
    }
}