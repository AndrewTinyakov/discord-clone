package com.discord.websocket.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.stereotype.Component
import java.security.interfaces.RSAPublicKey

@Component
class JwtUtil(
    private val publicKey: RSAPublicKey
) {


    fun validateToken(token: String?): Pair<String, String> {
        val valid = JWT.require(Algorithm.RSA256(publicKey))
            .build()
            .verify(token)

        val userId = valid.claims["userId"]!!.toString()
        val roles = valid.claims["roles"]!!.toString()

        return userId to roles
    }


}