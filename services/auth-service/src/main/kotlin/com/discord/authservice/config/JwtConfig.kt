package com.discord.authservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.Base64

@Configuration
class JwtConfig {
    @Value("\${jwt.private-key}")
    lateinit var privateKeyPath: Resource

    @Value("\${jwt.public-key}")
    lateinit var publicKeyPath: Resource

    @Bean
    fun keyFactory(): KeyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM)

    @Bean
    fun privateKey(keyFactory: KeyFactory): PrivateKey {
        val pem = privateKeyPath.inputStream.bufferedReader().use { it.readText() }

        val privateKeyPEM = pem
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replace("\\s".toRegex(), "")

        val decoded = Base64.getDecoder().decode(privateKeyPEM)
        val keySpec = X509EncodedKeySpec(decoded)
        return keyFactory.generatePrivate(keySpec)
    }

    @Bean
    fun publicKey(keyFactory: KeyFactory): PublicKey {
        val pem = publicKeyPath.inputStream.bufferedReader().use { it.readText() }

        val publicKeyPEM = pem
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "")
            .replace("\\s".toRegex(), "")

        val decoded = Base64.getDecoder().decode(publicKeyPEM)
        val keySpec = X509EncodedKeySpec(decoded)
        return keyFactory.generatePublic(keySpec)
    }

    private companion object {
        const val KEY_FACTORY_ALGORITHM = "RSA"
    }
}