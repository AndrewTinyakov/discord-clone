package com.discord.websocket

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WsServiceApplication

fun main(args: Array<String>) {
    runApplication<WsServiceApplication>(*args)
}
