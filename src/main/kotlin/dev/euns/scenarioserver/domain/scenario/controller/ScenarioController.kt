package dev.euns.scenarioserver.domain.scenario.controller

import dev.euns.scenarioserver.domain.scenario.dto.reqeust.CreateScenarioRequest
import dev.euns.scenarioserver.domain.scenario.dto.response.CreateScenarioResponse
import dev.euns.scenarioserver.domain.scenario.entity.Scenario
import dev.euns.scenarioserver.domain.scenario.repository.ScenarioRepository
import dev.euns.scenarioserver.domain.scenario.service.ScenarioService
import dev.euns.scenarioserver.global.dto.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.security.Principal

@RestController
@RequestMapping("/scenario")
class ScenarioController(
    private val scenarioService: ScenarioService,
) {
    @PostMapping
    fun createScenario(@RequestBody request: CreateScenarioRequest): BaseResponse<Unit> {
        return scenarioService.createScenario(request)
    }
    @GetMapping
    fun getScenario(): BaseResponse<Any> {
        return scenarioService.getScenario()
    }

    @Operation(summary = "프로필 업로드")
    @PostMapping("/upload-profile", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadProfile(
        principal: Principal,
        @RequestPart image: MultipartFile
    ) : BaseResponse<Unit> = scenarioService.uploadProfile(principal,image)
}

