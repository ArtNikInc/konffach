package org.konffach.service

import konffach.generated.jooq.package_.tables.records.UsersRecord
import org.konffach.dto.request.MessageRequest
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service

@Service
class MessageSender(private val messagingTemplate: SimpMessagingTemplate) {

    fun sendToAllInChat(usersInChat: List<UsersRecord>, message: MessageRequest) {
        for (user: UsersRecord in usersInChat) {
            messagingTemplate.convertAndSendToUser(
                user.login,
                "/queue/chat",
                message
            )
        }
    }
}