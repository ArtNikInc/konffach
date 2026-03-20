package org.konffach.dto.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    description = "JWT authentication response containing access and refresh tokens",
    example = """
        {
            "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huX2RvZSIsImlhdCI6MTcxMDkzNDQwMCwiZXhwIjoxNzEwOTM4MDAwfQ.signature",
            "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huX2RvZSIsImlhdCI6MTcxMDkzNDQwMCwiZXhwIjoxNzEwOTQxNjAwfQ.signature"
        }
    """
)
data class JwtResponse(
    @param:Schema(
        description = "JWT access token for API authorization",
        example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huX2RvZSIsImlhdCI6MTcxMDkzNDQwMCwiZXhwIjoxNzEwOTM4MDAwfQ.signature",
        required = true
    )
    val accessToken: String,

    @param:Schema(
        description = "JWT refresh token for obtaining new access tokens",
        example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huX2RvZSIsImlhdCI6MTcxMDkzNDQwMCwiZXhwIjoxNzEwOTQxNjAwfQ.signature",
        required = true
    )
    val refreshToken: String
)