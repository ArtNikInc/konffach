package org.konffach.service

import konffach.generated.jooq.package_.tables.records.UsersRecord
import org.konffach.dto.request.UserRequest
import org.konffach.dto.response.JwtResponse
import org.konffach.exception.LoginIncorrectException
import org.konffach.exception.UserIncorrectException
import org.konffach.mapper.UserMapper
import org.konffach.persistance.repository.UserRepository
import org.konffach.security.JwtService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class UserService(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService
) {

    private val userIncorrectMsg: String = "Incorrect login or password"
    private val loginAlready: String = "This user login is already in use"


    fun findByLogin(login: String): UsersRecord = userRepository.findByLogin(login)
        ?: throw UserIncorrectException(userIncorrectMsg)

    @Transactional
    open fun register(userRequest: UserRequest): JwtResponse {
        if (userRepository.isUserExist(userRequest.login)) {
            throw LoginIncorrectException(loginAlready);
        }
        val updatedUser = userRequest.copy(password = passwordEncoder.encode(userRequest.password))
        val user: UsersRecord = userMapper.mapToEntity(updatedUser)
        userRepository.save(user)
        return jwtService.generateToken(user)
    }

    @Transactional(readOnly = true)
    open fun login(userRequest: UserRequest): JwtResponse {
        val user: UsersRecord = findByLogin(userRequest.login)
        if (!passwordEncoder.matches(userRequest.password, user.password)) {
            throw UserIncorrectException(userIncorrectMsg)
        }

        return jwtService.generateToken(user)
    }
}