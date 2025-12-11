package com.discord.clients.wschannel

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

@FeignClient(name = "ws-channel-service")
interface WsChannelServiceClient {

    @GetMapping("/api/internal/wschannels/init")
    fun geChannelsByUserIdOnInit(
        @RequestParam userId: UUID,
        @RequestParam roles: String
    ): List<String>

    @GetMapping("/api/internal/wschannels/subscribe")
    fun validateSubscription(
        @RequestParam userId: UUID,
        @RequestParam roles: String,
        @RequestParam channels: List<String>
    ): Boolean

}