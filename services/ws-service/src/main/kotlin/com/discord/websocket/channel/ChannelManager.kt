package com.discord.websocket.channel

import org.springframework.web.socket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap

class ChannelManager {

    private val channels = ConcurrentHashMap<String, Channel>()

    fun subscribeSession(session: WebSocketSession, vararg channelNames: String) {
        for (channelName in channelNames) {
            val channel = channels.computeIfAbsent(channelName) {
                Channel(channelName)
            }
            channel.addSession(session)
        }
    }

    fun subscribeSession(session: WebSocketSession, channelNames: List<String>) {
        for (channelName in channelNames) {
            val channel = channels.computeIfAbsent(channelName) {
                Channel(channelName)
            }
            channel.addSession(session)
        }
    }


    fun unsubscribeSession(channelName: String, session: WebSocketSession) {
        val channel = channels[channelName] ?: return
        if (channel.removeSession(session) == ChannelStatus.EMPTY) {
            channels.remove(channelName, channel)
        }
    }

    fun unsubscribeSession(session: WebSocketSession) {
        channels.forEach { (name, channel) ->
            if (channel.removeSession(session) == ChannelStatus.EMPTY) {
                channels.remove(name, channel)
            }
        }
    }

    fun broadcast(channelName: String, message: String) {
        channels[channelName]?.broadcast(message)
    }

    fun broadcastAll(message: String) {
        channels.values.forEach { it.broadcast(message) }
    }

    fun listChannels(): Set<String> = channels.keys

}