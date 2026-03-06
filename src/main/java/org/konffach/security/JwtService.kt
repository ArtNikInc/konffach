package org.konffach.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import konffach.generated.jooq.package_.tables.records.UsersRecord
import org.konffach.dto.request.RefreshTokenRequest
import org.konffach.dto.response.JwtResponse
import org.konffach.exception.RefreshTokenException
import org.konffach.persistance.repository.AbstractRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date

@Component
class JwtService(
    @param:Value("\${jwt.accessSecret}") private val accessSecret: String,
    @param:Value("\${jwt.accessExpiration}") private val accessExpiration: Long,
    @param:Value("\${jwt.refreshSecret}") private val refreshSecret: String,
    @param:Value("\${jwt.refreshExpiration}") private val refreshExpiration: Long,
    private val refreshTokenService: RefreshTokenService
) {

    fun generatePair(user: UsersRecord): JwtResponse = JwtResponse(
        accessToken = generateAccessToken(user),
        refreshToken = generateRefreshToken(user)
    )

    fun generateRefreshToken(user: UsersRecord): String {
        val token = generateToken(user, refreshSecret, refreshExpiration)
        refreshTokenService.save(user, token)
        return token
    }

    fun generateAccessToken(user: UsersRecord): String {
        return generateToken(user, accessSecret, accessExpiration)
    }

    private fun generateToken(user: UsersRecord, secret: String, expiration: Long): String = Jwts.builder()
        .setSubject(user.login)
        .setIssuedAt(Date(System.currentTimeMillis()))
        .setExpiration(Date(System.currentTimeMillis() + expiration * 1000))
        .signWith(getSignKey(secret), SignatureAlgorithm.HS256)
        .compact()

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
            .setSigningKey(getSignKey(accessSecret))
            .build()
            .parseClaimsJws(token)
            .body
    }

    private fun getSignKey(secret: String): Key {
        return Keys.hmacShaKeyFor(secret.toByteArray())
    }

    fun refreshToken(user: UsersRecord, refreshTokenRequest: RefreshTokenRequest): JwtResponse {
        if (!refreshTokenService.verifyRefreshToken(user, refreshTokenRequest.refreshToken)) {
            throw RefreshTokenException("Invalid refresh token")
        }
        return generatePair(user)
    }
}