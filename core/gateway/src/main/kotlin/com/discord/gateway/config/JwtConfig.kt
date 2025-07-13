package com.discord.gateway.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
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
    fun privateKey(keyFactory: KeyFactory): RSAPrivateKey {
        val bytesData = privateKeyPath.inputStream.readAllBytes()
        val spec = PKCS8EncodedKeySpec(bytesData)

        return keyFactory.generatePrivate(spec) as RSAPrivateKey
    }

    @Bean
    fun publicKey(keyFactory: KeyFactory): RSAPublicKey {
        val bytesData = publicKeyPath.inputStream.readAllBytes()
        val spec = X509EncodedKeySpec(bytesData)

        return keyFactory.generatePublic(spec) as RSAPublicKey
    }

    private companion object {
        const val KEY_FACTORY_ALGORITHM = "RSA"
    }
}