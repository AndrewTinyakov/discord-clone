package com.discord.chatservice.exception

class NotFoundException(
    override val message: String?
) : RuntimeException(message)