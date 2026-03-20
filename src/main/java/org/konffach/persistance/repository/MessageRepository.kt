package org.konffach.persistance.repository

import konffach.generated.jooq.package_.Tables.MESSAGES
import konffach.generated.jooq.package_.Tables.USERS
import konffach.generated.jooq.package_.tables.records.MessagesRecord
import org.jooq.DSLContext
import org.konffach.dto.response.MessageResponse
import org.springframework.stereotype.Component

@Component
class MessageRepository(dsl: DSLContext) : AbstractRepository(dsl) {

    fun save(message: MessagesRecord) {
        dsl.insertInto(MESSAGES)
            .set(MESSAGES.SENDER_ID, message.senderId)
            .set(MESSAGES.CONTENT, message.content)
            .set(MESSAGES.TYPE, message.type)
            .set(MESSAGES.REPLY_TO, message.replyTo)
            .execute()
    }

    fun getHistory(): List<MessageResponse> {
        return dsl.select(
            MESSAGES.ID,
            MESSAGES.SENDER_ID,
            MESSAGES.TYPE,
            MESSAGES.REPLY_TO,
            USERS.LOGIN,
            MESSAGES.CONTENT,
            MESSAGES.CREATED_AT
        )
            .from(MESSAGES)
            .join(USERS).on(MESSAGES.SENDER_ID.eq(USERS.ID))
            .orderBy(MESSAGES.CREATED_AT.asc())
            .fetch { record ->
                MessageResponse(
                    id = record.get(MESSAGES.ID),
                    user = record.get(USERS.LOGIN),
                    content = record.get(MESSAGES.CONTENT),
                    type = record.get(MESSAGES.TYPE),
                    replyTo = record.get(MESSAGES.REPLY_TO)
                )
            }
    }
}