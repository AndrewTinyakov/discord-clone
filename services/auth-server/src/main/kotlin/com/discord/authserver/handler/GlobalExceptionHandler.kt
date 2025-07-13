package com.discord.authserver.handler

import com.discord.authserver.exception.InvalidCredentialsException
import com.discord.authserver.exception.UsernameAlreadyExistsException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(UsernameAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleUsernameAlreadyExists(exception: UsernameAlreadyExistsException): Map<String, String> {
        val error = mutableMapOf<String, String>()
        error["error"] = exception.message

        return error
    }

    @ExceptionHandler(InvalidCredentialsException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleInvalidCredentials(exception: InvalidCredentialsException): Map<String, String> {
        val error = mutableMapOf<String, String>()
        error["error"] = exception.message

        return error
    }
}