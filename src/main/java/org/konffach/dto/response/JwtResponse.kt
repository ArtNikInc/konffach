package org.konffach.dto.response

data class JwtResponse(
    val accessToken: String,
    val refreshToken: String
)