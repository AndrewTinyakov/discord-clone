package com.discord.authserver.service

import com.discord.authserver.dto.LoginDTO
import com.discord.authserver.dto.RegisterDTO

interface AuthService {
    fun register(request: RegisterDTO)
    fun login(request: LoginDTO): String
}