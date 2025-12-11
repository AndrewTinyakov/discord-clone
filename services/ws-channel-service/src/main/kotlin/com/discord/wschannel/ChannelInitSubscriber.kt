package com.discord.wschannel

import java.util.*

interface ChannelInitSubscriber {
    fun getInitChannels(userId: UUID, roles: String): List<String>
    fun validateSubscription(userId: UUID, roles: String, channels: List<String>): Boolean
}