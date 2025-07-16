package com.discord.authservice.exception

import java.lang.RuntimeException

class UsernameAlreadyExistsException(override val message: String) : RuntimeException(message)