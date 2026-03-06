package org.konffach.security

import konffach.generated.jooq.package_.tables.records.RefreshTokensRecord
import konffach.generated.jooq.package_.tables.records.UsersRecord
import org.konffach.exception.RefreshTokenException
import org.konffach.persistance.repository.RefreshTokenRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.util.UUID

@Service
 class RefreshTokenService(
    private val refreshTokenRepository: RefreshTokenRepository,
    @param:Value("\${jwt.refreshExpiration}") private val refreshExpiration: Long
) {

    @Transactional
     fun save(userRecord: UsersRecord, token: String): String {
        return if (refreshTokenRepository.tokenExists(userRecord.id))
            updateRefreshToken(userRecord, token)
        else createRefreshToken(userRecord, token)
    }

    private fun createRefreshToken(userRecord: UsersRecord, refreshToken: String): String {
        val refreshToken = RefreshTokensRecord().apply {
            id = UUID.randomUUID()
            token = refreshToken
            userId = userRecord.id
            expiryDate = OffsetDateTime.now().plusSeconds(refreshExpiration)
        }
        refreshTokenRepository.save(refreshToken)
        return refreshToken.token
    }

    private fun updateRefreshToken(userRecord: UsersRecord, refreshToken: String): String {
        val existedToken =
            refreshTokenRepository.getByUser(userRecord.id) ?: throw RefreshTokenException("Invalid refresh token")

        val refreshToken = existedToken.apply {
            token = refreshToken
            expiryDate = OffsetDateTime.now().plusSeconds(refreshExpiration)
        }

        refreshTokenRepository.update(refreshToken)
        return refreshToken.token
    }

    @Transactional
     fun verifyRefreshToken(user: UsersRecord, refreshToken: String): Boolean {
        val existedRefreshToken =
            refreshTokenRepository.getByUser(user.id) ?: throw RefreshTokenException("Invalid refresh token")

        if (existedRefreshToken.expiryDate.isBefore(OffsetDateTime.now())) {
            refreshTokenRepository.delete(existedRefreshToken.id)
            throw RefreshTokenException("Refresh token expired")
        }
        return existedRefreshToken.token.equals(refreshToken)
    }
}