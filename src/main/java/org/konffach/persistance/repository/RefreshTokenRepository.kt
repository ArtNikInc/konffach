package org.konffach.persistance.repository

import konffach.generated.jooq.package_.Tables.REFRESH_TOKENS
import konffach.generated.jooq.package_.tables.records.RefreshTokensRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class RefreshTokenRepository(dls: DSLContext) : AbstractRepository(dls) {

    fun save(token: RefreshTokensRecord) = dls.insertInto(REFRESH_TOKENS)
        .set(REFRESH_TOKENS.ID, token.id)
        .set(REFRESH_TOKENS.TOKEN, token.token)
        .set(REFRESH_TOKENS.USER_ID, token.userId)
        .set(REFRESH_TOKENS.EXPIRY_DATE, token.expiryDate)
        .execute()

    fun getByUser(userId: UUID): RefreshTokensRecord? = dls.selectFrom(REFRESH_TOKENS)
        .where(REFRESH_TOKENS.USER_ID.eq(userId))
        .fetchOne()

    fun delete(tokenId: UUID) = dls.deleteFrom(REFRESH_TOKENS)
        .where(REFRESH_TOKENS.ID.eq(tokenId))
        .execute()

    fun tokenExists(userId: UUID): Boolean = dls.fetchExists(REFRESH_TOKENS.where(REFRESH_TOKENS.USER_ID.eq(userId)))

    fun update(token: RefreshTokensRecord) = dls.update(REFRESH_TOKENS)
        .set(REFRESH_TOKENS.TOKEN, token.token)
        .set(REFRESH_TOKENS.EXPIRY_DATE, token.expiryDate)
        .where(REFRESH_TOKENS.ID.eq(token.id))
        .execute()
}