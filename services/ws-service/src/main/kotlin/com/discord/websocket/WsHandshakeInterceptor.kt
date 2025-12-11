package com.discord.websocket

import com.discord.websocket.util.JwtUtil
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.HandshakeInterceptor


class WsHandshakeInterceptor(
    private val jwtUtil: JwtUtil
) : HandshakeInterceptor {

    override fun beforeHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        attributes: MutableMap<String?, Any?>
    ): Boolean {
        if (request is ServletServerHttpRequest) {
            val servletRequest = request.servletRequest
            val cookies = servletRequest.cookies

            val token = cookies?.firstOrNull { it.name == "access-token" }?.value
                ?: return false

            return try {
                val claims = jwtUtil.validateToken(token)
                attributes["userId"] = claims.first
                attributes["roles"] = claims.second
                true
            } catch (_: Exception) {
                false
            }
        }
        return false
    }

    override fun afterHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        exception: Exception?
    ) {

    }
}