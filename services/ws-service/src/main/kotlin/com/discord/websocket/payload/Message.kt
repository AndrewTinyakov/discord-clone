package com.discord.websocket.payload

import com.fasterxml.jackson.databind.JsonNode

data class Message(
    val event: String,
    val payload: JsonNode
)