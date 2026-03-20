package org.konffach.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

@Schema(
    description = "User authentication request containing login credentials",
    example = """
        {
            "login": "john_doe",
            "password": "SecurePass123!"
        }
    """
)
data class UserRequest(
    @get:NotBlank(message = "Login cannot be empty")
    @get:Size(min = 3, max = 50, message = "Login must be between 3 and 50 characters")
    @get:Pattern(
        regexp = "^[a-zA-Z0-9._-]+$",
        message = "Login can only contain letters, numbers, dots, underscores and hyphens"
    )
    @param:Schema(
        description = "User login name",
        example = "john_doe",
        minLength = 3,
        maxLength = 50,
        pattern = "^[a-zA-Z0-9._-]+$",
        required = true
    )
    val login: String,

    @get:NotBlank(message = "Password cannot be empty")
    @get:Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    @get:Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{6,}$",
        message = "Password must contain at least one digit, one uppercase, one lowercase letter and one special character"
    )
    @param:Schema(
        description = "User password",
        example = "SecurePass123!",
        minLength = 6,
        maxLength = 100,
        pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{6,}$",
        required = true,
        format = "password"
    )
    val password: String
)