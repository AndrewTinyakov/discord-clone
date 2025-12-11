package com.discord.messengeraggregator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan


@SpringBootApplication
@ComponentScan(basePackages = ["com.discord.messengeraggregator", "com.discord.events"])
class MessengerAggregator

fun main(args: Array<String>) {
    runApplication<MessengerAggregator>(*args)
}
