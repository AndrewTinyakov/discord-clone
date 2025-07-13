package com.discord.authserver.exception

class InvalidCredentialsException(override val message: String) : RuntimeException(message)