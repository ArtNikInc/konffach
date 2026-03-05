package org.konffach.dto.response

import java.util.Date

data class JwtResponse(
    val accessToken: String,
    val expirationDate: Date,
    val refreshToken: String
)