package dev.euns.scenarioserver.domain.user.controller

import dev.euns.scenarioserver.domain.user.entity.UserEntity
import dev.euns.scenarioserver.domain.user.service.CustomUserDetailsService
import dev.euns.scenarioserver.global.dto.BaseResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: CustomUserDetailsService
) {
    @GetMapping
    fun getUser(
        principal: Principal,
    ): BaseResponse<List<UserEntity>> {
        return userService.getUser(principal)
    }
}