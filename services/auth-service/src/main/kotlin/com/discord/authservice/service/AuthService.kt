package com.discord.authservice.service

import com.discord.authservice.dto.LoginDTO
import com.discord.authservice.dto.RegisterDTO

interface AuthService {
    fun register(request: RegisterDTO)
    fun login(request: LoginDTO): String
}