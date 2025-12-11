package com.discord.wschannel

import com.discord.clients.chat.ChatServiceClient
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChannelInitSubscriberImpl(
    private val chatServiceClient: ChatServiceClient
) : ChannelInitSubscriber {

    override fun getInitChannels(userId: UUID, roles: String): List<String> {
        val personalChannel = "personal:user#$userId"
//        val groupChatChannels = chatServiceClient.geChannelsByUserIdOnInit(userId, roles) todo get user group chats

        return listOf(personalChannel)
    }

    override fun validateSubscription(
        userId: UUID,
        roles: String,
        channels: List<String>
    ): Boolean {
        for (channel in channels) {
            if (channel == "personal:user#$userId") {
                continue
            } else if (channel.startsWith("chat")) {
                val chatId = channel.split(":")[1]
//                todo validate

                continue
            } else {
                return false
            }
        }
        return true
    }

}