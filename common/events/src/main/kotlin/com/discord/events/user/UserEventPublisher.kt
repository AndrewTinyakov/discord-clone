package com.discord.events.user

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import java.util.function.Supplier

@Component
class UserEventPublisher {

    private val userRegisteredQueue: BlockingQueue<UserRegisteredEvent> = LinkedBlockingQueue()

    fun publish(event: UserRegisteredEvent) {
        userRegisteredQueue.add(event)
    }

    @Bean
    fun userRegistered(): Supplier<UserRegisteredEvent> = Supplier {
        userRegisteredQueue.poll()
    }

}