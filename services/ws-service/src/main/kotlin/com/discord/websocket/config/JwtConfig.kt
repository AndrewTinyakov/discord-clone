package com.discord.websocket.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import java.security.KeyFactory
import java.security.interfaces.RSAPublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.*

@Configuration
class JwtConfig {

    @Value("\${jwt.public-key}")
    lateinit var publicKeyPath: Resource

    @Bean
    fun keyFactory(): KeyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM)

    @Bean
    fun publicKey(keyFactory: KeyFactory): RSAPublicKey {
        val pem = publicKeyPath.inputStream.bufferedReader().use { it.readText() }

        val publicKeyPEM = pem
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "")
            .replace("\\s".toRegex(), "")

        val decoded = Base64.getDecoder().decode(publicKeyPEM)
        val keySpec = X509EncodedKeySpec(decoded)
        return keyFactory.generatePublic(keySpec) as RSAPublicKey
    }

    private companion object {
        const val KEY_FACTORY_ALGORITHM = "RSA"
    }
}