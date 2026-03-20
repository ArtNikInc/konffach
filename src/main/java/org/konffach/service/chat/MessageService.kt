package org.konffach.service.chat

import konffach.generated.jooq.package_.tables.records.UsersRecord
import org.konffach.dto.request.MessageRequest
import org.konffach.dto.response.MessageResponse
import org.konffach.mapper.MessageMapper
import org.konffach.persistance.repository.MessageRepository
import org.springframework.stereotype.Service

@Service
class MessageService(
    private val repository: MessageRepository,
    private val messageMapper: MessageMapper
) {

    fun saveMessage(message: MessageRequest, user: UsersRecord) {
        repository.save(messageMapper.mapToEntity(message, user.id))
    }

    fun getHistory(): List<MessageResponse> = repository.getHistory()

}