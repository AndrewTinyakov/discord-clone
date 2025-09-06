package com.discord.messengeraggregator.controller

import com.discord.clients.message.MessageServiceClient
import com.discord.clients.user.UserServiceClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/messenger/messages")
class MessageController(
    private val userServiceClient: UserServiceClient,
    private val messageServiceClient: MessageServiceClient
) {

    @GetMapping
    fun getMessages(@RequestParam chatId: String, ) {
        val userId = "1234567890"
        val messages = messageServiceClient.getMessagesByChatId(userId)


    }

}