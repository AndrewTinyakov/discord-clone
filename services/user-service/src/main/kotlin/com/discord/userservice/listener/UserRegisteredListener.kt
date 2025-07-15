package com.discord.userservice.listener

import com.discord.events.user.UserRegisteredEvent
import com.discord.userservice.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class UserRegisteredListener(
    private val objectMapper: ObjectMapper,
    private val userService: UserService
) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(topics = ["user-registered"], groupId = "user-service")
    suspend fun handle(message: String) {
        val event = objectMapper.readValue(message, UserRegisteredEvent::class.java)
        log.info(
            "User registered event: id = ${event.id}"
        )

        userService.insert(
            event.id, event.username
        )
    }

}