package dev.euns.fishingstopserver.domain.auth.controller


import com.authtemplate.domain.auth.dto.request.AuthLoginRequest
import com.authtemplate.domain.auth.dto.request.AuthRefreshRequest
import com.authtemplate.domain.auth.dto.request.AuthSignupRequest
import dev.euns.fishingstopserver.domain.auth.dto.response.AuthTokenResponse
import dev.euns.fishingstopserver.domain.auth.service.AuthService
import dev.euns.fishingstopserver.global.dto.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Auth", description = "인증/인가")
@RestController
@RequestMapping("/auth")
class AuthController (
    private val authService: AuthService

) {
    @Operation(summary = "Login")
    @PostMapping("/login")
    fun login(@RequestBody authLoginRequest: AuthLoginRequest): BaseResponse<AuthTokenResponse> {
        return authService.login(authLoginRequest)
    }

    @Operation(summary = "User Sign-Up")
    @PostMapping("/sign-up")
    fun register(@RequestBody authSignupRequest: AuthSignupRequest): BaseResponse<Unit> {
        return authService.signup(authSignupRequest)
    }

    @Operation(summary = "Token Refresh")
    @PostMapping("/refresh")
    fun refresh(
        @RequestBody authRefreshRequest: AuthRefreshRequest
    ): BaseResponse<AuthTokenResponse> {
        return authService.refresh(authRefreshRequest)
    }
}