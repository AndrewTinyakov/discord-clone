package com.discord.gateway.filter

import com.discord.gateway.util.JwtUtil
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

@Component
class AuthenticationFilter(
    private val jwtUtil: JwtUtil,
    private val validator: RouteValidator
) : AbstractGatewayFilterFactory<AuthenticationFilter.Config?>(Config::class.java) {


    override fun apply(config: Config?): GatewayFilter? {
        return (GatewayFilter { exchange, chain ->
            val request = exchange.request

            if (!validator.isSecured.test(request)) {
                return@GatewayFilter chain.filter(exchange)
            }

            val token = request.cookies["access-token"]?.firstOrNull()?.value
                ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing access token")

            val (userId, roles) = try {
                jwtUtil.validateToken(token)
            } catch (_: Exception) {
                throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized: Invalid access token")
            }

            val mutatedRequest = request.mutate()
                .header("userId", userId)
                .header("roles", roles)
                .build()

            val mutatedExchange = exchange.mutate().request(mutatedRequest).build()
            chain.filter(mutatedExchange)
        })
    }

    class Config
}
