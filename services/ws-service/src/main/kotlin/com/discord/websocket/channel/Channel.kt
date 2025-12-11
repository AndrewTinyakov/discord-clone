package com.discord.websocket.channel

import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap

class Channel(
    val name: String
) {

    private val sessions = ConcurrentHashMap.newKeySet<WebSocketSession>()

    fun addSession(session: WebSocketSession) {
        sessions += session
    }

    fun removeSession(session: WebSocketSession): ChannelStatus {
        sessions -= session
        return if (sessions.isEmpty()) ChannelStatus.EMPTY else ChannelStatus.ACTIVE
    }

    fun broadcast(message: String) {
        sessions.forEach { session ->
            if (session.isOpen) {
                session.sendMessage(
                    TextMessage(message)
                )
            }
        }
    }

    fun sessionCount(): Int = sessions.size

}