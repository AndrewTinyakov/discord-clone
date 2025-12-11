package com.discord.websocket

import com.discord.clients.wschannel.WsChannelServiceClient
import com.discord.websocket.channel.ChannelManager
import com.discord.websocket.payload.Message
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.*

class WsHandler(
    private val objectMapper: ObjectMapper,
    private val channelManager: ChannelManager,
    private val wsChannelServiceClient: WsChannelServiceClient
) : TextWebSocketHandler() {

    val log = LoggerFactory.getLogger(this::class.java)!!

    override fun handleTextMessage(
        session: WebSocketSession,
        message: TextMessage
    ) {
        val userId = UUID.fromString(session.attributes["userId"] as String)
        val roles = session.attributes["roles"] as String

        val jsonPayload = runCatching {
            objectMapper.readValue<Message>(message.payload)
        }.onFailure { ex ->
            session.sendMessage(
                TextMessage(
                    ex.message ?: "error mapping"
                )
            )
        }.getOrThrow()

        if (jsonPayload.event == "subscribe") {
            handleSubscribeEvent(
                userId,
                roles,
                session,
                jsonPayload
            )
        } else {
            //todo put in kafka (think how to emit a message only to existing services
            // + know how each service will know what messages to pull)
            // maybe get an event prefix
            // + have store with valid prefixes in redis + register prefixed with a controller (all in ws service)
        }
    }

    private fun handleSubscribeEvent(
        userId: UUID,
        roles: String,
        session: WebSocketSession,
        jsonPayload: Message
    ) {
        val channels: List<String>? =
            jsonPayload.payload.get("channels")?.asText()?.split(", ")
        if (channels == null) {
            session.sendMessage(
                TextMessage("Channels are null - pass channels in payload to subscribe")
            )
            return
        }
        val valid = wsChannelServiceClient.validateSubscription(
            userId,
            roles,
            channels
        )
        if (valid) {
            channelManager.subscribeSession(
                session,
                channels
            )
        } else {
            session.sendMessage(
                TextMessage("Can not subscribe to $channels - not authorized")
            )
        }
    }


    override fun afterConnectionEstablished(session: WebSocketSession) {
        val userId = UUID.fromString(session.attributes["userId"] as String)
        val roles = session.attributes["roles"] as String

        val channels = runCatching {
            wsChannelServiceClient.geChannelsByUserIdOnInit(userId, roles)
        }.onFailure {
            log.error("Error getting channels on init for user: $userId", it)
            session.close(CloseStatus.SERVER_ERROR)
        }.getOrThrow()

        channelManager.subscribeSession(
            session,
            channels
        )
    }

    override fun afterConnectionClosed(
        session: WebSocketSession,
        status: CloseStatus
    ) {
        val userId = session.attributes["userId"] as String
        log.info("User: $userId disconnected")

        channelManager.unsubscribeSession(
            session
        )
    }
}