package com.discord.wschannel

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

@Controller
@RequestMapping("/api/internal/wschannels")
class ChannelInternalController(
    private val channelInitSubscriber: ChannelInitSubscriber
) {

    @RequestMapping("/init")
    fun init(
        @RequestParam userId: UUID,
        @RequestParam roles: String
    ): List<String> {
        return channelInitSubscriber.getInitChannels(userId, roles)
    }

    @RequestMapping("/subscribe")
    fun validateSubscription(
        @RequestParam userId: UUID,
        @RequestParam roles: String,
        @RequestParam channels: List<String>
    ): Boolean {
        return channelInitSubscriber.validateSubscription(userId, roles, channels)
    }


}