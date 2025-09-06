package com.discord.messengeraggregator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.ComponentScan


@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = ["com.discord.messengeraggregator", "com.discord.events"])
class MessageServiceApplication

fun main(args: Array<String>) {
    runApplication<MessageServiceApplication>(*args)
}
