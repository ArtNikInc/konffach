package org.konffach.service.chat

import konffach.generated.jooq.package_.tables.records.UsersRecord
import org.konffach.dto.request.MessageRequest
import org.konffach.dto.response.MessageResponse
import org.konffach.persistance.repository.UserRepository
import org.konffach.service.MessageSender
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val messageService: MessageService,
    private val userRepository: UserRepository,
    private val sender: MessageSender
) {

    fun sendMessage(message: MessageRequest, user: UsersRecord) {
        messageService.saveMessage(message, user)
        val usersInChat = userRepository.getAllWithoutUser(user)
        sender.sendToAllInChat(usersInChat, message)
    }

    fun getHistory(): List<MessageResponse> = messageService.getHistory()
}