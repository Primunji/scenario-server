package dev.euns.scenarioserver.domain.call.controller

import dev.euns.scenarioserver.domain.call.dto.request.CreateCallRequest
import dev.euns.scenarioserver.domain.call.service.CallService
import dev.euns.scenarioserver.global.dto.BaseResponse
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/call")
class CallController(private val callService: CallService) {
    private val logger = LoggerFactory.getLogger(CallController::class.java)

    @PostMapping
    fun createCall(
        principal: Principal,
        @RequestBody request: CreateCallRequest
    ): BaseResponse<Any> {
        logger.info("Call creation request received for scenario: ${request.scenario_id}")
        return callService.createCall(principal, request.scenario_id)
    }
}