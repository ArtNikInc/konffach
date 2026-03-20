package org.konffach.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(
    description = "Refresh token request for obtaining new JWT tokens",
    example = """
        {
            "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huX2RvZSIsImlhdCI6MTcxMDkzNDQwMCwiZXhwIjoxNzEwOTQxNjAwfQ.signature"
        }
    """
)
data class RefreshTokenRequest(
    @get:NotBlank(message = "Refresh token cannot be empty")
    @param:Schema(
        description = "Refresh token obtained during authentication",
        example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huX2RvZSIsImlhdCI6MTcxMDkzNDQwMCwiZXhwIjoxNzEwOTQxNjAwfQ.signature",
        required = true
    )
    val refreshToken: String
)