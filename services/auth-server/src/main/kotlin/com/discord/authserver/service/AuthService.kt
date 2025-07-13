package com.discord.authserver.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.discord.authserver.dto.LoginDTO
import com.discord.authserver.dto.RegisterDTO
import com.discord.authserver.entity.User
import com.discord.authserver.repository.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
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
    fun register(request: RegisterDTO): Mono<Void> =
        userRepository.findByUsername(request.username)
            .flatMap<User> {
                Mono.error(UsernameNotFoundException("User='${request.username}' not found"))
            }
            .switchIfEmpty(
                Mono.defer {
                    val hash = passwordEncoder.encode(request.password)
                    val user = User(
                        username = request.username,
                        hashedPassword = hash,
                        roles = setOf("USER")
                    )

                    userRepository.save(user)
                }
            )
            .then()

    fun login(request: LoginDTO): Mono<Pair<String, User>> =
        userRepository.findByUsername(request.username)
            .switchIfEmpty (
                Mono.error(IllegalArgumentException("User='${request.username}' exist"))
            )
            .flatMap { user ->
                if (!passwordEncoder.matches(request.password, user.hashedPassword)) {
                    Mono.error(IllegalArgumentException("Bad credentials"))
                } else {
                    val token = JWT.create()
                        .withSubject(user.id.toString())
                        .withArrayClaim("roles", user.roles.toTypedArray())
                        .withExpiresAt(Date(System.currentTimeMillis() + EXPIRED_DATE))
                        .sign(
                            Algorithm.RSA256(
                                publicKey as RSAPublicKey,
                                privateKey as RSAPrivateKey
                            )
                        )

                    Mono.just(token to user)
                }
            }

    private companion object {
        const val EXPIRED_DATE = 3_600_000
    }
}