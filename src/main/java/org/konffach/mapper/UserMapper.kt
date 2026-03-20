package org.konffach.mapper

import konffach.generated.jooq.package_.tables.records.UsersRecord
import org.konffach.dto.request.UserRequest
import org.mapstruct.Mapper

@Mapper
interface UserMapper {

    fun mapToEntity(user: UserRequest): UsersRecord
}