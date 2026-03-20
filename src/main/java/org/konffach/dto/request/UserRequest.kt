package org.konffach.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class UserRequest(
    @field:NotBlank(message = "Login cannot be empty")
    @field:Size(min = 3, max = 50, message = "Login must be between 3 and 50 characters")
    @field:Pattern(
        regexp = "^[a-zA-Z0-9._-]+$",
        message = "Login can only contain letters, numbers, dots, underscores and hyphens"
    )
    val login: String,

    @field:NotBlank(message = "Password cannot be empty")
    @field:Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    @field:Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{6,}$",
        message = "Password must contain at least one digit, one uppercase, one lowercase letter and one special character"
    )
    val password: String
)