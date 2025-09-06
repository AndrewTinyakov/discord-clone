package com.discord.messengeraggregator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MessengerAggregatorApplication

fun main(args: Array<String>) {
    runApplication<MessengerAggregatorApplication>(*args)
}
