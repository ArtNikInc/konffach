package org.konffach.dto.response

import java.util.Date

data class JwtResponse(
    val token: String,
    val expirationDate: Date,
    val refreshToken: String
)