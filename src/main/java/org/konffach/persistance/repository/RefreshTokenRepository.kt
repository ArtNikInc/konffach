package org.konffach.persistance.repository

import konffach.generated.jooq.package_.Tables.REFRESH_TOKENS
import konffach.generated.jooq.package_.tables.records.RefreshTokensRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class RefreshTokenRepository(dls: DSLContext) : AbstractRepository(dls) {

    fun save(token: RefreshTokensRecord) = dsl.insertInto(REFRESH_TOKENS)
        .set(REFRESH_TOKENS.ID, token.id)
        .set(REFRESH_TOKENS.TOKEN, token.token)
        .set(REFRESH_TOKENS.USER_ID, token.userId)
        .set(REFRESH_TOKENS.EXPIRY_DATE, token.expiryDate)
        .execute()

    fun getByUser(userId: UUID): RefreshTokensRecord? = dsl.selectFrom(REFRESH_TOKENS)
        .where(REFRESH_TOKENS.USER_ID.eq(userId))
        .fetchOne()

    fun delete(tokenId: UUID) = dsl.deleteFrom(REFRESH_TOKENS)
        .where(REFRESH_TOKENS.ID.eq(tokenId))
        .execute()

    fun tokenExists(userId: UUID): Boolean = dsl.fetchExists(REFRESH_TOKENS.where(REFRESH_TOKENS.USER_ID.eq(userId)))

    fun update(token: RefreshTokensRecord) = dsl.update(REFRESH_TOKENS)
        .set(REFRESH_TOKENS.TOKEN, token.token)
        .set(REFRESH_TOKENS.EXPIRY_DATE, token.expiryDate)
        .where(REFRESH_TOKENS.ID.eq(token.id))
        .execute()
}