package org.konffach.controller

import org.konffach.exception.LoginIncorrectException
import org.konffach.exception.UserIncorrectException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, Any>> {
        val errors = mutableMapOf<String, Any>()

        ex.bindingResult.allErrors.forEach { error ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.defaultMessage ?: "Validation failed"
            errors[fieldName] = errorMessage
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            mapOf(
                "errors" to errors
            )
        )
    }

    @ExceptionHandler(UserIncorrectException::class)
    fun handleUserIncorrectException(ex: UserIncorrectException): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            mapOf(
                "error" to (ex.message ?: "User exception")
            )
        )
    }

    @ExceptionHandler(LoginIncorrectException::class)
    fun loginIncorrectException(ex: LoginIncorrectException): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            mapOf(
                "error" to (ex.message ?: "User exception")
            )
        )
    }
}