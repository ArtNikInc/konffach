package org.konffach.controller

import jakarta.validation.Valid
import org.konffach.dto.request.UserRequest
import org.konffach.dto.response.JwtResponse
import org.konffach.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @PostMapping("/register")
    fun register(@RequestBody request: UserRequest): JwtResponse = userService.register(request)

    @PostMapping("/login")
    fun login(@RequestBody request: UserRequest): JwtResponse = userService.login(request)
}