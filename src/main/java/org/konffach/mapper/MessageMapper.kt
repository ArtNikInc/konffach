package org.konffach.mapper

import konffach.generated.jooq.package_.tables.records.MessagesRecord
import org.konffach.dto.request.MessageRequest
import org.konffach.dto.response.MessageResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import java.util.UUID

@Mapper
interface MessageMapper {

    @Mapping(target = "senderId", source = "userId")
    fun mapToEntity(message: MessageRequest, userId: UUID): MessagesRecord

    fun mapToDto(message: MessagesRecord): MessageResponse

    fun mapToDto(message: List<MessagesRecord>): List<MessageResponse>
}