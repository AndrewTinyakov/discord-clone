package com.discord.gateway.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.stereotype.Component
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

@Component
class JwtUtil(
    private val privateKey: RSAPrivateKey,
    private val publicKey: RSAPublicKey
) {


    fun validateToken(token: String?): Pair<String, String> {
        val valid = JWT.require(Algorithm.RSA256(publicKey, privateKey))
            .build()
            .verify(token)

        val userId = valid.claims["userId"]!!.toString()
        val roles = valid.claims["roles"]!!.toString()

        return userId to roles
    }


}