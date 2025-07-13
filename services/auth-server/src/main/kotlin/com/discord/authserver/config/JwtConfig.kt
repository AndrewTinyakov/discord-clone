package com.discord.authserver.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

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
        val bytesData = privateKeyPath.inputStream.readAllBytes()
        val spec = PKCS8EncodedKeySpec(bytesData)

        return keyFactory.generatePrivate(spec)
    }

    @Bean
    fun publicKey(keyFactory: KeyFactory): PublicKey {
        val bytesData = publicKeyPath.inputStream.readAllBytes()
        val spec = X509EncodedKeySpec(bytesData)

        return keyFactory.generatePublic(spec)
    }

    private companion object {
        const val KEY_FACTORY_ALGORITHM = "RSA"
    }
}