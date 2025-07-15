package com.discord.userservice.config

import com.discord.auth.CurrentUserContextInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class WebConfig : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        val userContextInterceptor = CurrentUserContextInterceptor()
        registry.addInterceptor(userContextInterceptor)
    }
}