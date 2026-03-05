package org.konffach.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import konffach.generated.jooq.package_.tables.records.UsersRecord
import org.konffach.dto.response.JwtResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date

@Component
class JwtService(
    @param:Value("\${jwt.secret}") private val secret: String,
    @param:Value("\${jwt.expiration}") private val jwtExpiration: Long
) {

    fun generateToken(user: UsersRecord): JwtResponse {
        val expatriationDate = Date(System.currentTimeMillis() + jwtExpiration * 1000)
        val jwt = Jwts.builder()
            .setSubject(user.login)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(expatriationDate)
            .signWith(getSignKey(), SignatureAlgorithm.HS256)
            .compact()

        return JwtResponse(jwt, expatriationDate, "")
    }

    fun extractUsername(token: String): String {
        return extractAllClaims(token).subject
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractAllClaims(token).expiration
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(getSignKey())
            .build()
            .parseClaimsJws(token)
            .body
    }

    private fun getSignKey(): Key {
        return Keys.hmacShaKeyFor(secret.toByteArray())
    }
}