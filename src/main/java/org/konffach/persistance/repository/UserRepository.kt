package org.konffach.persistance.repository

import konffach.generated.jooq.package_.Tables.USERS
import konffach.generated.jooq.package_.tables.records.UsersRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserRepository(dls: DSLContext) : AbstractRepository(dls) {

    fun save(user: UsersRecord) {
        dsl.insertInto(USERS)
            .set(USERS.ID, UUID.randomUUID())
            .set(USERS.LOGIN, user.login)
            .set(USERS.PASSWORD, user.password)
            .execute()
    }

    fun findByLogin(login: String): UsersRecord? = dsl.selectFrom(USERS)
        .where(USERS.LOGIN.eq(login))
        .fetchOne()

    fun isUserExist(login: String): Boolean = dsl.fetchExists(USERS.where(USERS.LOGIN.eq(login)))

    fun getAllWithoutUser(user: UsersRecord): List<UsersRecord> = dsl.selectFrom(USERS)
        .fetch()
}