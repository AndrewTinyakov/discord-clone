package com.discord.authserver.exception

import java.lang.RuntimeException

class UsernameAlreadyExistsException(override val message: String) : RuntimeException(message)