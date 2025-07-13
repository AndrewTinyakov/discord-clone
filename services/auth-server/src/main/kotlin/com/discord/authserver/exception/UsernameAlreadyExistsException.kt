package com.discord.authserver.exception

import java.lang.RuntimeException

class UsernameAlreadyExistsException(message: String) : RuntimeException(message)